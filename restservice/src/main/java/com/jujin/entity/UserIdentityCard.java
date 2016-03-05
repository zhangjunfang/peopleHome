/**
 * 
 */
package com.jujin.entity;

/**
 * @author 宁 身份证
 */
public class UserIdentityCard {

	private String realName;

	private String cardId;

	private String cardFileId1;

	private String cardFileId2;

	private int sex;
	
	/**
	 * 确定投资时的银行卡
	 */
	private String  bankCardId;
	
	
	/**
	 * 用户在平台的注册时间
	 */
	private String  userInfoDtRegister;
	
	
	private String noAgree;

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName
	 *            the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return cardId;
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the cardFileId1
	 */
	public String getCardFileId1() {
		return cardFileId1;
	}

	/**
	 * @param cardFileId1
	 *            the cardFileId1 to set
	 */
	public void setCardFileId1(String cardFileId1) {
		this.cardFileId1 = cardFileId1;
	}

	/**
	 * @return the cardFileId2
	 */
	public String getCardFileId2() {
		return cardFileId2;
	}

	/**
	 * @param cardFileId2
	 *            the cardFileId2 to set
	 */
	public void setCardFileId2(String cardFileId2) {
		this.cardFileId2 = cardFileId2;
	}

	/**
	 * @return the sex
	 */
	public int getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getUserInfoDtRegister() {
		return userInfoDtRegister;
	}

	public void setUserInfoDtRegister(String userInfoDtRegister) {
		this.userInfoDtRegister = userInfoDtRegister;
	}

	public String getNoAgree() {
		return noAgree;
	}

	public void setNoAgree(String noAgree) {
		this.noAgree = noAgree;
	}
	
	
}
