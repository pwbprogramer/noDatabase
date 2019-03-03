package com.berheley.ichart.socketIo;

import com.corundumstudio.socketio.SocketIOServer;

public interface SocketIOService {
	
	/**
	 * 开启SocketIOServer服务
	 */
	public void startSocketIOServer();
	
	/**
	 * 停止SocketIOServer服务
	 */
	public void stopSocketIOServer();

}
