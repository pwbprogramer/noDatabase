package com.berheley.ichart.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.berheley.ichart.domain.ResultCode;
import com.berheley.ichart.domain.ResultJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 未登录状态时
 * @author pwb
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	System.out.println("认证失败：" + authException.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        try {
        	String body = ResultJson.failure(ResultCode.UNAUTHORIZED, authException.getMessage()).toString();
        	printWriter.write(body);
        	printWriter.flush();
        }finally {
        	printWriter.close();
        }
    }
}