package com.jujin.entity.luckDraw;

/**
 * Title: UserOddTimes Description: Company: jujinziben
 * 
 * @author zhengshaoxu
 * @date 2015年11月18日
 */
public class UserOddTimes {
	
	private String userId;
	private String oddTimes;
	private String nickName;
	private String phoneNumber;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOddTimes() {
		return oddTimes;
	}

	public void setOddTimes(String oddTimes) {
		this.oddTimes = oddTimes;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
