package com.ocean.session.sessionCluster.users;


public class Account {
	private String username;

	private String logoutUrl;

	private String switchAccountUrl;

	public Account(String username, String logoutUrl, String switchAccountUrl) {
		super();
		this.username = username;
		this.logoutUrl = logoutUrl;
		this.switchAccountUrl = switchAccountUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public String getSwitchAccountUrl() {
		return switchAccountUrl;
	}

}
