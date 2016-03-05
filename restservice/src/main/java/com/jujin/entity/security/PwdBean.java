package com.jujin.entity.security;

public class PwdBean {

	private String userId;
	/**
	 * 新密码
	 */
	private String pwd;
	/**
	 * 原始密码
	 */
	private String oldPwd;
	/**
	 * 确认密码
	 */
	private String againPwd;
	/**
	 * 图片验证码
	 */
	private String validateCode;
	
	private String tel;
	
	

	/**
	 * 0:登陆密码 1：交易密码 2：提现密码
	 */
	private int type;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getAgainPwd() {
		return againPwd;
	}

	public void setAgainPwd(String againPwd) {
		this.againPwd = againPwd;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
