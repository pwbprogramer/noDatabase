package com.berheley.ichart.socketIo.model;

import java.io.Serializable;

public class TMessageBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fromClient;
	
	private String toClient;
	
	private String content;

	public TMessageBean() {
		
	}

	public TMessageBean(String fromClient, String toClient, String content) {
		super();
		this.fromClient = fromClient;
		this.toClient = toClient;
		this.content = content;
	}

	public String getFromClient() {
		return fromClient;
	}

	public void setFromClient(String fromClient) {
		this.fromClient = fromClient;
	}

	public String getToClient() {
		return toClient;
	}

	public void setToClient(String toClient) {
		this.toClient = toClient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "{fromClient=" + fromClient + ", toClient=" + toClient + ", content=" + content + "}";
	}
	

}
