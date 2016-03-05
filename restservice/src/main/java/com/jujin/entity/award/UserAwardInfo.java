package com.jujin.entity.award;

/*用户中奖纪录查询*/
public class UserAwardInfo {

	private String nickName;

	private String awardName;

	private String isGain;

	private String winDate;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getIsGain() {
		return isGain;
	}

	public void setIsGain(String isGain) {
		this.isGain = isGain;
	}

	public String getWinDate() {
		return winDate;
	}

	public void setWinDate(String winDate) {
		this.winDate = winDate;
	}

}
