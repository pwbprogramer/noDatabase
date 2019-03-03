package com.berheley.ichart.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.berheley.ichart.domain.SysUser;

public interface UserService extends UserDetailsService{
	
	public void logout(); 
	
	public List<SysUser> getAllUser();

	public String registerNewUser(HttpServletRequest request);

	public String validate(HttpServletRequest request);

	public boolean doRegister(HttpServletRequest request);

	public boolean getUserByname(HttpServletRequest request);
	
	public boolean whetherLogin();

}