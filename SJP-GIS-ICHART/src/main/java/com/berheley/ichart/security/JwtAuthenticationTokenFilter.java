package com.berheley.ichart.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.berheley.ichart.dao.PermissionListRepository;
import com.berheley.ichart.dao.userRepository;
import com.berheley.ichart.domain.SysUser;
import com.berheley.ichart.utils.DataUtil;

//@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
     
	@Value("${jwt.header}")
    private String token_header;
	
	@Value("${jwt.tokenHead}")
	private String auth_token_start;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private userRepository userDao;
	
	@Autowired
	private PermissionListRepository permissionListRepository;
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String auth_token = request.getHeader(this.token_header);
        //验证是否包含token,以及token合法性 
        if (StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
            auth_token = auth_token.substring(auth_token_start.length());
        } else {
            auth_token = null;
        }
        String username = jwtUtils.getUsernameFromToken(auth_token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	SysUser user = userDao.findByUsernameLike(username);
        	//验证token
        	if(jwtUtils.validateToken(auth_token,user)) {
        		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),permissionListRepository.getPermissionCollection(user));
        		SecurityContextHolder.getContext().setAuthentication(authentication);
        	}
        }
    	chain.doFilter(request, response);
    }
}