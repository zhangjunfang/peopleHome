package com.jujin.entity.sign;
/**
 * Title: SignDetailBean
 * Description: 签到流水
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月4日
 */
public class SignDetailBean {

	/**
	 * 用户名
	 */
	private String userId;
	/**
	 * 签到日期
	 */
	private String signDate;
	/**
	 * 奖励金额
	 */
	private String reward;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	
	
}
