package com.berheley.ichart.socketIo.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.berheley.ichart.socketIo.controller.messagePushController;
import com.berheley.ichart.utils.DataUtil;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

@Component
public class SocketClient {
	
	public static Map<String, Socket> SocketClientMap = new HashMap<>();
	
	public Socket getSocketClient (String fromClient) throws IOException, URISyntaxException{
		if(SocketClientMap.containsKey(fromClient)){
			return SocketClientMap.get(fromClient);
		}else{
			SocketClient.SocketClientMap.put(fromClient, createSocketClient(fromClient));
			return SocketClientMap.get(fromClient);
		}
	}
	
	private Socket createSocketClient(String fromClient) throws IOException, URISyntaxException {
		IO.Options options = new IO.Options();
//        options.transports = new String[]{"websocket"};
        options.reconnectionAttempts = Integer.MAX_VALUE;
        //失败重连的时间间隔
        options.reconnectionDelay = 1000;
        //连接超时时间(ms)
        options.timeout = 500;	
        
        Properties poperties = new Properties();
		poperties.load(messagePushController.class.getClassLoader().getResourceAsStream("projectConfig.properties"));
		String address = poperties.getProperty("messagePushIpClientAdress")+":"+poperties.getProperty("messagePushIpClientPort");
		
		Socket socket = IO.socket("http://"+address+"?user="+fromClient, options);
		
		socket.on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(DataUtil.getCurrentDataTime() + ":【SocketIO-Client】客户端连接中。。。。");
            }
        });
		
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
            	System.out.println(DataUtil.getCurrentDataTime() + ":【SocketIO-Client】客户端连接成功! ");
            }
        });
 
        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(DataUtil.getCurrentDataTime() + ":【SocketIO-Client】客户端断开连接");
            }
        });
        
        socket.on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
        	@Override
        	public void call(Object... args) {
        		System.out.println(DataUtil.getCurrentDataTime() + ":【SocketIO-Client】客户端正在重连");
        	}
        });
        
        socket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println(DataUtil.getCurrentDataTime() + ":【SocketIO-Client】客户端成功重连");
            }
        });
		
		socket.connect();
		return socket;
	}
	
    public void emit (final Socket socket, final String event, final Object... args) {
    	if (socket.connected()) {
    		socket.emit(event, args);
    	}else{
    		socket.connect();
    		this.emit(socket, event, args);
    	}
    }
	
}
