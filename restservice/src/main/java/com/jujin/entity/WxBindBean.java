package com.jujin.entity;

/* 微信绑定用户模型*/
public class WxBindBean {

	private String weiXinId;
	
	private String userId;
	
	private int bindFlg;
	
	private String insDate;
	
	private String updDate;

	public String getWeiXinId() {
		return weiXinId;
	}

	public void setWeiXinId(String weiXinId) {
		this.weiXinId = weiXinId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getBindFlg() {
		return bindFlg;
	}

	public void setBindFlg(int bindFlg) {
		this.bindFlg = bindFlg;
	}

	public String getInsDate() {
		return insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getUpdDate() {
		return updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}
	
	
	
	
	 
}
