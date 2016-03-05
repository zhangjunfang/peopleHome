package com.jujin.entity.luckDraw;

import java.util.List;

/**
 * Title: PersonInfo
 * Description: 用户抽奖信息(实时)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月5日
 */
public class PageData {
	
	private String userId;
	/**
	 * 中奖纪录
	 */
	private List<AwardRecord> awardList;
	/**
	 * 剩余抽奖次数
	 */
	private int oddTimes;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<AwardRecord> getAwardList() {
		return awardList;
	}

	public void setAwardList(List<AwardRecord> awardList) {
		this.awardList = awardList;
	}

	public int getOddTimes() {
		return oddTimes;
	}

	public void setOddTimes(int oddTimes) {
		this.oddTimes = oddTimes;
	}

	
	
	
	
}
