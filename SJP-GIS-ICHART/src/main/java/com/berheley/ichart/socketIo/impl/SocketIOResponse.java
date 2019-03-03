package com.berheley.ichart.socketIo.impl;

import org.springframework.stereotype.Component;

import com.berheley.ichart.socketIo.model.TMessageBean;
import com.corundumstudio.socketio.SocketIOClient;

@Component
public class SocketIOResponse {
	
	 public void sendEvent(SocketIOClient client, String event, TMessageBean messageBean) {
	        client.sendEvent(event, messageBean);
	 }

}
