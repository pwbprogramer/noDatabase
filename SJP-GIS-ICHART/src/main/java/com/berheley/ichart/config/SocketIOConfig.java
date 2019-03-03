package com.berheley.ichart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * socketIo 配置类
 * @author pwb
 */
@Configuration
public class SocketIOConfig {
	
    @Value("${socketio.host}")
    private String host;

    @Value("${socketio.port}")
    private Integer port;

    @Value("${socketio.bossCount}")
    private int bossCount;

    @Value("${socketio.workCount}")
    private int workCount;

    @Value("${socketio.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketio.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketio.pingTimeout}")
    private int pingTimeout;

    @Value("${socketio.pingInterval}")
    private int pingInterval;
    
    @Bean
    public SocketIOServer currentSocketIOServer() {
    	com.corundumstudio.socketio.SocketConfig socketConfig = new com.corundumstudio.socketio.SocketConfig();
    	socketConfig.setTcpNoDelay(true);
    	com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
    	configuration.setSocketConfig(socketConfig);
    	configuration.setHostname(host);
    	configuration.setPort(port);
    	configuration.setBossThreads(bossCount);
    	configuration.setWorkerThreads(workCount);
    	configuration.setAllowCustomRequests(allowCustomRequests);
    	configuration.setUpgradeTimeout(upgradeTimeout);
    	configuration.setPingTimeout(pingTimeout);
    	configuration.setPingInterval(pingInterval);
    	SocketIOServer socketIOServer = new SocketIOServer(configuration);
    	return socketIOServer;
    } 

}
