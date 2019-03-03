package com.berheley.ichart.socketIo.impl;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.berheley.ichart.socketIo.EventListenner;
import com.berheley.ichart.socketIo.model.TMessageBean;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

/**
 * 监听类
 * @author pwb
 *
 */
@Component
public class PushMessage implements EventListenner{
	
	/**
	 * current client map
	 */
	@Autowired
	private SocketIOClientCache clientCache;
	
	@Autowired
    private SocketIOResponse socketIOResponse;
	
	@Autowired
	private UserOffLineServiceImpl UserOffLineServiceImpl;
     
    @OnConnect
    public void onConnect(SocketIOClient client) {
    	HandshakeData hd = client.getHandshakeData();
    	TMessageBean messageBean = new TMessageBean();
    	//记录登录用户
    	messageBean.setFromClient(hd.getSingleUrlParam("user"));
    	clientCache.addClient(client, messageBean);
    	//获取用户离线消息
    	LinkedList<TMessageBean> offLineMessage = UserOffLineServiceImpl.readMessageInRedis(messageBean);
        //发送离线消息
    	sendOffLineMessage(offLineMessage);
    }
 
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
    	clientCache.removeClient(client.get("userId"));
    }

	@OnEvent("sendMessage")
	public void sendMessage(TMessageBean messageBean) {
		 SocketIOClient toClients = clientCache.getClient(messageBean.getToClient());
         //当用户离线时，暂存消息
		 if (toClients == null) {
        	UserOffLineServiceImpl.saveMessageInRedis(messageBean);
            return;
         }
	     socketIOResponse.sendEvent(toClients,"sendMessage",messageBean);
	}
	
	
	public void sendOffLineMessage(LinkedList<TMessageBean> offLineMessage) {
		while(offLineMessage!=null&&offLineMessage.size()!=0) {
			TMessageBean temp = offLineMessage.pop();
			SocketIOClient toClients = clientCache.getClient(temp.getToClient());
			if (toClients == null) {
				UserOffLineServiceImpl.saveMessageInRedis(temp);
				return;
			}
			socketIOResponse.sendEvent(toClients,"sendMessage",temp);
        }
	}
}
