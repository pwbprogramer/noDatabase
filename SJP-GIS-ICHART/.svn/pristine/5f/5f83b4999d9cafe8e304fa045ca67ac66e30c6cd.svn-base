package com.berheley.ichart.socketIo;

import com.berheley.ichart.socketIo.model.TMessageBean;
import com.corundumstudio.socketio.SocketIOClient;

public interface SocketIOCurrentClient {
    
	/**
	 * 添加用户
	 * @param client
	 * @param messageBean
	 */
	public void addClient(SocketIOClient client,TMessageBean messageBean);
	
	/**
	 * 删除用户
	 * @param userId
	 */
	public void removeClient(String userId);
	
	/**
	 * 获取用户
	 * @param toClient
	 * @return
	 */
	public SocketIOClient getClient(String toClient);
}
