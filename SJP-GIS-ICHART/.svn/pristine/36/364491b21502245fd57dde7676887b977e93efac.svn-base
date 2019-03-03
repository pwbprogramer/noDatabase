package com.berheley.ichart.socketIo.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.berheley.ichart.socketIo.SocketIOCurrentClient;
import com.berheley.ichart.socketIo.model.TMessageBean;
import com.corundumstudio.socketio.SocketIOClient;

@Component
public class SocketIOClientCache implements SocketIOCurrentClient{
    
	private static Map<String,SocketIOClient> clients = new ConcurrentHashMap<String,SocketIOClient>();
	
    public void addClient(SocketIOClient client,TMessageBean messageBean){
    	client.set("userId", messageBean.getFromClient());
        clients.put(messageBean.getFromClient(),client);
    }
    
    public void removeClient(String userId) {
        clients.remove(userId);
    }

	public SocketIOClient getClient(String toClient) {
		return clients.get(toClient);
	}
    
}
