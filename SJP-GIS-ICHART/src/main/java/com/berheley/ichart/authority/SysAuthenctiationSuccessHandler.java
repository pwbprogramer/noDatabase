package com.berheley.ichart.authority;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.berheley.ichart.domain.ResultCode;
import com.berheley.ichart.domain.ResultJson;
import com.berheley.ichart.security.JwtUtils;

@Component
public class SysAuthenctiationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
  
	@Value("${jwt.tokenHead}")
	private String auth_token_start;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		Map<String,Object>  map = generateTokenMessage(authentication);
		if(map==null) {
			failure(response);
			return;
		}
		String token = jwtUtils.generate(authentication.getName(),map);
		String result = auth_token_start + token;
		response.addHeader("Authorization", result);
		Cookie cookie = new Cookie("Authorization", result);
		response.addCookie(cookie);
		request.getRequestDispatcher("/home").forward(request, response);
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	private Map<String,Object> generateTokenMessage(Authentication authentication){
		Map<String,Object> message = new ConcurrentHashMap<>();
		WebAuthenticationDetails wad = (WebAuthenticationDetails) authentication.getDetails();
        if(wad!=null) {
        	message.put("IP", wad.getRemoteAddress());
        }else {
        	return null;
        }
    	Md5PasswordEncoder pw = new Md5PasswordEncoder();
    	String code = pw.encodePassword(authentication.getName()+wad.getRemoteAddress(), auth_token_start);
    	message.put("MD5pw", code);
        return message;
	}
	
	private void failure(HttpServletResponse response) {
		System.out.println("认证失败：" + "未知IP");
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = null;
        try {
        	printWriter = response.getWriter();
        	String body = ResultJson.failure(ResultCode.UNAUTHORIZED, "未知IP").toString();
        	printWriter.write(body);
        	printWriter.flush();
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
        	if(printWriter!=null) {
        		printWriter.close();
        	}
        }
	}
}
