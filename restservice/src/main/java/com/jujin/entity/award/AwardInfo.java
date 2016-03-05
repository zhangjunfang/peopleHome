package com.jujin.entity.award;

/**
 * Title: AwardInfo 
 * Description: 奖品信息表对应的实体类 
 * 表结构： AWARD_INFO 
 * 		award_code 奖品编号
 * 		award_msg 奖品信息 
 * 		award_percent 奖品百分比 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年4月17日
 */
public class AwardInfo {

	/* 奖品编号 */
	private int awardCode;
	/* 奖品信息 */
	private String awardMsg;
	/* 奖品抽奖中奖率百分比 */
	private int awardPercentFrom;//起始
	private int awardPercentEnd;
	//最大次数
	private int maxTimes;
	//是否有效
	private boolean isAvailable;

	public int getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(int awardCode) {
		this.awardCode = awardCode;
	}

	public String getAwardMsg() {
		return awardMsg;
	}

	public void setAwardMsg(String awardMsg) {
		this.awardMsg = awardMsg;
	}

	public int getAwardPercentFrom() {
		return awardPercentFrom;
	}

	public void setAwardPercentFrom(int awardPercentFrom) {
		this.awardPercentFrom = awardPercentFrom;
	}

	public int getAwardPercentEnd() {
		return awardPercentEnd;
	}

	public void setAwardPercentEnd(int awardPercentEnd) {
		this.awardPercentEnd = awardPercentEnd;
	}

	public int getMaxTimes() {
		return maxTimes;
	}

	public void setMaxTimes(int maxTimes) {
		this.maxTimes = maxTimes;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "AwardInfo [awardCode=" + awardCode + ", awardMsg=" + awardMsg
				+ ", awardPercentFrom=" + awardPercentFrom
				+ ", awardPercentEnd=" + awardPercentEnd 
				+ ", maxTimes=" + maxTimes 
				+ ", isAvailable=" + isAvailable + "]";
	}

}
