package com.berheley.ichart.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.berheley.ichart.dao.PermissionListRepository;
import com.berheley.ichart.dao.userRepository;
import com.berheley.ichart.domain.SysPermission;
import com.berheley.ichart.domain.SysRole;
import com.berheley.ichart.domain.SysUser;
import com.berheley.ichart.service.UserService;
import com.berheley.ichart.utils.DataUtil;
import com.berheley.ichart.utils.rySmsUtil;

@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {
    
	public static HttpSession currentSession;
	
	@Autowired
	private userRepository userDao;
	
	@Autowired
	private PermissionListRepository permissionListRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername:"+DataUtil.getCurrentDataTime());
		SysUser user = userDao.findByUsernameLike(username);
		return new User(user.getUsername(),user.getPassword(),permissionListRepository.getPermissionCollection(user));
	}
    
	public void logout() {
		currentSession=null;
	}
	 
	/**
	 * 获取全部用户
	 */
	@Override
	public List<SysUser> getAllUser() {
		return userDao.findAll();
	}
    
	/**
	 * 注册新用户
	 */
	@Override
	public String registerNewUser(HttpServletRequest request) {
		try {
			SysUser user  = new SysUser();
			user.setId(UUID.randomUUID().toString().replaceAll("-", "").toLowerCase());
			user.setPassword(request.getParameter("password"));
			user.setTelphone(request.getParameter("telphone"));
			user.setUsername(request.getParameter("username"));
			if(!this.getUserByname(request)) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", user);
				return "success";
			}else{
				return "ExistUserName";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "failure";
		}
	}
    
	/**
	 * 验证用户存在(暂通过getUserByname()替代)
	 */
	public boolean checkUserExist(SysUser newUser) {
		List<SysUser> users = this.getAllUser();
		for (SysUser user : users) {
			if(user.getUsername().equals(newUser.getUsername())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 发送验证码
	 */
	@Override
	public String validate(HttpServletRequest request) {
		try{
			//创建验证码
			int yzm = (int)((Math.random()*9+1)*100000);
    		String smsText = "您的短信验证码是 "+yzm+" ，请在五分钟内输入使用。超时请重新申请";
    		rySmsUtil rySmsUtil = new rySmsUtil();
    		//从session中获取将要登录用户
    		HttpSession session = request.getSession();
    		SysUser user = (SysUser)session.getAttribute("loginUser");
    		//将验证码存入session
    		session.setAttribute("identifyingCode", yzm);
    		List<String> mobile = new ArrayList<String>();
    		mobile.add(user.getTelphone());
    		boolean sendYzSms=rySmsUtil.sendYzSms(smsText,mobile,"0ninG_9oQlo83cfza4FwzH");
    		//验证码是否失效(暂前台处理)
//    		this.removeAttrbute(session, "identifyingCode");
    		if(sendYzSms) {
    			return "success";
    		}else {
    			return "failure";
    		}
		}catch(Exception e) {
			e.printStackTrace();
			return "failure";
		}
	}
	
	/**
	 * 验证码失效
	 * (暂定5分钟)
	 * @param session
	 * @param attrName
	 */
	@SuppressWarnings("unused")
	private void removeAttrbute(final HttpSession session, final String attrName) {
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				session.removeAttribute(attrName);
				timer.cancel();
			}
		}, 5 * 60 * 1000);
	}
    
	/**
	 * 执行注册
	 */
	@Override
	public boolean doRegister(HttpServletRequest request) {
		
		try {
			HttpSession session = request.getSession();
			SysUser user = (SysUser)session.getAttribute("loginUser");
			userDao.save(user);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
    
	/**
	 * 根据登录名获取用户
	 */
	@Override
	public boolean getUserByname(HttpServletRequest request) {
		try {
			String username = request.getParameter("username");
			SysUser user = userDao.findByUsernameLike(username);
			if(user!=null) {
				HttpSession session = request.getSession();
				currentSession = session;
				session.setAttribute("loginUser", user);
				return true;
			}else {
				return false;
			}
		}catch (Exception e) {
			System.out.println("Exception======>>>>>>>>:用户不存在");
			return false;
		}
	}

	@Override
	public boolean whetherLogin() {
		return currentSession==null?false:true;
	}
    
}
