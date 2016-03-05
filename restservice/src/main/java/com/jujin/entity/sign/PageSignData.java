package com.jujin.entity.sign;

import java.util.List;

import com.jujin.common.JsonList;

/**
 * Title: PageSignData
 * Description: 签到页面数据
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月4日
 */
public class PageSignData {
	
	
	/**
	 * 今天是否签到
	 */
	private String todayIsSign = "0";
	
	/**
	 * 累积签到天数
	 */
	private String continuityDay;
	
	/**
	 * 累计领取奖励
	 */
	private String totalReward;
	
	/**
	 * 最高奖励连续天数
	 */
	private String maxContinuityDay;
	
	/**
	 * 最高奖励金额
	 */
	private String maxReward;
	
	/**
	 * 已签到天数
	 */
	private String signDays;
	
	/**
	 * 最近10条签到记录
	 */
	private JsonList<SignDetailBean> lastSignDetail;

	public String getContinuityDay() {
		return continuityDay;
	}

	public void setContinuityDay(String continuityDay) {
		this.continuityDay = continuityDay;
	}

	public String getTotalReward() {
		return totalReward;
	}

	public void setTotalReward(String totalReward) {
		this.totalReward = totalReward;
	}

	public String getMaxContinuityDay() {
		return maxContinuityDay;
	}

	public void setMaxContinuityDay(String maxContinuityDay) {
		this.maxContinuityDay = maxContinuityDay;
	}

	public String getMaxReward() {
		return maxReward;
	}

	public void setMaxReward(String maxReward) {
		this.maxReward = maxReward;
	}

	public String getSignDays() {
		return signDays;
	}

	public void setSignDays(String signDays) {
		this.signDays = signDays;
	}

	public String getTodayIsSign() {
		return todayIsSign;
	}

	public void setTodayIsSign(String todayIsSign) {
		this.todayIsSign = todayIsSign;
	}

	public JsonList<SignDetailBean> getLastSignDetail() {
		return lastSignDetail;
	}

	public void setLastSignDetail(JsonList<SignDetailBean> lastSignDetail) {
		this.lastSignDetail = lastSignDetail;
	}
	
}
