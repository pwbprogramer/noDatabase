package com.berheley.ichart.socketIo;

import com.berheley.ichart.socketIo.model.TMessageBean;
import com.corundumstudio.socketio.SocketIOClient;

/**
 * 事件监听接口
 * @author pwb
 */
public interface EventListenner {
    
	/**
	 * 连接
	 * @param client
	 */
	public void onConnect(SocketIOClient client);
	
	/**
	 * 发送消息
	 * @param messageBean
	 */
	public void sendMessage(TMessageBean messageBean);
	
	/**
	 * 断开连接
	 * @param client
	 */
	public void onDisconnect(SocketIOClient client); 
	
}
