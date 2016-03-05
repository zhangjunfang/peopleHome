package com.jujin.entity.security;


/**
 *移动端传来的票据 
 *
 */


public class ClientTicket {
	
	
	private String userId;
	
	private String ticket;
	
	private String updTime;
	
	private String clientInfo;
	
	private String clientVersion;
	
	private int clientType;
	
	private int verifyStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUpdTime() {
		return updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public int getClientType() {
		return clientType;
	}

	
	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public int getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(int verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	 

}
