package com.berheley.ichart.socketIo.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.berheley.ichart.socketIo.SocketIOService;
import com.corundumstudio.socketio.SocketIOServer;


@Component
public class SocketIOServerStart implements SocketIOService{
	  
	  @Autowired
      private PushMessage PushMessage; 
	  
	  @Autowired
	  public SocketIOServer currentSocketIOServer;
	  
	  
	  private Logger logger = LogManager.getLogger(getClass().getName());
	   
	  @PostConstruct
	  public void startSocketIOServer(){
		  try{
			  currentSocketIOServer.addListeners(PushMessage);
			  logger.info("消息推送服务正常启动");
			  currentSocketIOServer.start();
		  }catch(Exception e){
			  logger.error(e.getMessage());
		  }
	  }
	  
	  @PreDestroy
	  public void stopSocketIOServer(){
		   currentSocketIOServer.stop();
		   logger.info("关闭消息推送服务");
	  }
}
