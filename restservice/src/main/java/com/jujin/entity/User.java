package com.jujin.entity;

/* 用户基本信息(之所以把用户的邮件、手机号、身份证分开是因为这些属于用户相对重要的隐私，只在需要的地方呈现)*/
public class User extends UserNickName {

	/* 视频认证 */
	private String videoFlg;

	/* 身体认证 */
	private int identityFlg;

	/* 手机认证 */
	private int mobileFlg;

	/* 邮箱认证 */
	private int mailFlg;

	/* 注册时间 */
	private String regdate;

	/* 未读消息数 */
	private int messageCount;

	/* vip对应图片 */
	private String typeImageFileId;

	/* 对应客服的QQ号 */
	private String customerQqNumber;

	/* 对应客服的用户名 */
	private String adminUserName;

	/**
	 * @return the videoFlg
	 */
	public String getVideoFlg() {
		return videoFlg;
	}

	/**
	 * @param videoFlg
	 *            the videoFlg to set
	 */
	public void setVideoFlg(String videoFlg) {
		this.videoFlg = videoFlg;
	}

	/**
	 * @return the identityFlg
	 */
	public int getIdentityFlg() {
		return identityFlg;
	}

	/**
	 * @param identityFlg
	 *            the identityFlg to set
	 */
	public void setIdentityFlg(int identityFlg) {
		this.identityFlg = identityFlg;
	}

	/**
	 * @return the mobileFlg
	 */
	public int getMobileFlg() {
		return mobileFlg;
	}

	/**
	 * @param mobileFlg
	 *            the mobileFlg to set
	 */
	public void setMobileFlg(int mobileFlg) {
		this.mobileFlg = mobileFlg;
	}

	/**
	 * @return the mailFlg
	 */
	public int getMailFlg() {
		return mailFlg;
	}

	/**
	 * @param mailFlg
	 *            the mailFlg to set
	 */
	public void setMailFlg(int mailFlg) {
		this.mailFlg = mailFlg;
	}

	/**
	 * @return the regdate
	 */
	public String getRegdate() {
		return regdate;
	}

	/**
	 * @param regdate
	 *            the regdate to set
	 */
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	/**
	 * @return the messageCount
	 */
	public int getMessageCount() {
		return messageCount;
	}

	/**
	 * @param messageCount
	 *            the messageCount to set
	 */
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}

	/**
	 * @return the customerQqNumber
	 */
	public String getCustomerQqNumber() {
		return customerQqNumber;
	}

	/**
	 * @param customerQqNumber
	 *            the customerQqNumber to set
	 */
	public void setCustomerQqNumber(String customerQqNumber) {
		this.customerQqNumber = customerQqNumber;
	}

	/**
	 * @return the adminUserName
	 */
	public String getAdminUserName() {
		return adminUserName;
	}

	/**
	 * @param adminUserName
	 *            the adminUserName to set
	 */
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

}
