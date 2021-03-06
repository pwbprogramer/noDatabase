package com.berheley.ichart.socketIo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.berheley.ichart.dao.PermissionRepository;
import com.berheley.ichart.dao.RoleRepository;
import com.berheley.ichart.dao.userRepository;


@Controller
@RequestMapping(value="/socket")
public class messagePushController{
    
	@Value("${socketio.host}")
    private String host;

    @Value("${socketio.port}")
    private Integer port;
	
    @Autowired
    PermissionRepository permissionRepository;
   
    @Autowired
    RoleRepository rolerepository;
    
    @Autowired
    userRepository userrepository;
    
	@RequestMapping(value="/toSendMessage")
	public String toSendMessage(){
		return "socketIo/messagePush";
	}
	
	@ResponseBody
	@RequestMapping(value="/getServerAddress")
	public String getServerAddress() {
		return host+":"+port;
	}
}
