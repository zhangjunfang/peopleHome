package com.jujin.entity.account;

public class RegisterEntity {

	private String userName;

	private String pwd;

	private String validateCode;

	// 是否验证
	private int ischeck;

	// 邀请人
	private String invite;

	/* 微信用户的OpenId */
	private String openId;

	private String imgVerifyCode;

	private boolean agree;
	
	private String isXglc;

	private String opType;
	
	private String returnUrl;
	
	private String channel;
	
	private String platUsername;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}

	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getImgVerifyCode() {
		return imgVerifyCode;
	}

	public void setImgVerifyCode(String imgVerifyCode) {
		this.imgVerifyCode = imgVerifyCode;
	}
	
	public String getIsXglc() {
		return isXglc;
	}

	public void setIsXglc(String isXglc) {
		this.isXglc = isXglc;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPlatUsername() {
		return platUsername;
	}

	public void setPlatUsername(String platUsername) {
		this.platUsername = platUsername;
	}

	@Override
	public String toString() {
		return "RegisterEntity [userName=" + userName + ", pwd=" + pwd
				+ ", validateCode=" + validateCode + ", ischeck=" + ischeck
				+ ", invite=" + invite + ", openId=" + openId + ", agree="
				+ agree + ", opType=" + opType + ",returnUrl="+returnUrl+"]";
	}
}
