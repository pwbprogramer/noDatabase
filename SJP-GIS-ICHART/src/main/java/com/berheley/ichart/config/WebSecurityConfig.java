package com.berheley.ichart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.berheley.ichart.authority.SysAuthenctiationSuccessHandler;
import com.berheley.ichart.authority.SysFilterSecurityInterceptor;
import com.berheley.ichart.security.JwtAuthenticationEntryPoint;
import com.berheley.ichart.security.JwtAuthenticationTokenFilter;
import com.berheley.ichart.service.UserService;


@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
    @Autowired
    private SysAuthenctiationSuccessHandler successHandler;
    
    @Autowired
    private UserService userDetailsService;
    
    @Autowired
    private SysFilterSecurityInterceptor sysFilterSecurityInterceptor;
    
    /**
     * 配置用户
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    /**
     * 密码加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity.authorizeRequests()
    	            .antMatchers("/",
					   		     "/favicon.ico",
					   		     "/socket/toSendMessage",
					   		     "/images/**",
					   		     "/userCenter",
				                 "/**/*.html",
				                 "/**/*.css",
				                 "/**/*.js").permitAll();
    	httpSecurity.formLogin()
			        .loginPage("/login")//自定义登录页
			        .loginProcessingUrl("/login")//自定义登录表单提交地址
			        .successHandler(successHandler)
			        .failureUrl("/errorPage")
			        .failureForwardUrl("/forbiddenPage")
			        .permitAll() //登录页面允许用户无条件任意访问
			        .and()
			        .authorizeRequests()
			        .anyRequest().authenticated()
			        .and() //任何请求,登录后可以访问
			        .logout().permitAll()//注销行为任意访问
			        .and().csrf().disable()//关闭crsf
			        .exceptionHandling()
			        .accessDeniedPage("/forbiddenPage"); //抛出无权限异常页面
    	
    	//添加url authority控制请求过滤器
	    httpSecurity.addFilterBefore(sysFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	    //禁用缓存
	    httpSecurity.headers().cacheControl();
	    //允许页面在iframe中扩展
	    httpSecurity.headers().frameOptions().disable();
	    //不创建HttpSession
    	httpSecurity.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    //添加JWT fileter 于用户密码验证之前
    	httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	    
    }
}