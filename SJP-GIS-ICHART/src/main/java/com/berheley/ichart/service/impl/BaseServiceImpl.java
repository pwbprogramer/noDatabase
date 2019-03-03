package com.berheley.ichart.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BaseServiceImpl {
	
	@Value("${jwt.header}")
    public String tokenHeader;
	
	@Autowired
    RestTemplate restTemplate;
	
	@Autowired  
	private HttpServletRequest request;
	
	public Object getRestTemplateObject(String url,Map param,Class c){
		String token = request.getHeader(tokenHeader);
		HttpHeaders headers = new HttpHeaders();
        headers.set(tokenHeader, token);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<?> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, c, param);
        Object object = exchange.getBody();
		return object;
	}
	
}
