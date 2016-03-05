package com.jujin.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.util.StringUtils;

import com.jujin.common.SystemConfig;
import com.wicket.loan.common.utils.UserUtils;

public class UserAttestation {

	private String userId;

	private int mobileFlg;

	private int emailFlg;

	private int idFlg;

	private String mobile;

	private String email;

	private String realName;

	private String idCard;

	private String nickName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getMobileFlg() {
		return mobileFlg;
	}

	public void setMobileFlg(int mobileFlg) {
		this.mobileFlg = mobileFlg;
	}

	public int getEmailFlg() {
		return emailFlg;
	}

	public void setEmailFlg(int emailFlg) {
		this.emailFlg = emailFlg;
	}

	public int getIdFlg() {
		return idFlg;
	}

	public void setIdFlg(int idFlg) {
		this.idFlg = idFlg;
	}

	public String getMobile() {
		return SystemConfig.getPhoneToConceal(mobile);
	}

	@JsonIgnore
	public String getRealMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {

		return email;

	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRealName() {

		if (StringUtils.isEmpty(realName))
			return "";
		return UserUtils.realNameToConceal(realName);
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		if (StringUtils.isEmpty(idCard))
			return "";
		return UserUtils.strCardToConceal(idCard);
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
