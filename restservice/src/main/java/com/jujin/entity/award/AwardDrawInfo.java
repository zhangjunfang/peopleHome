package com.jujin.entity.award;

import java.util.Date;

/**
 * Title: AwardDrawInfo 
 * Description:用户抽奖信息表对应的实体类 
 * 用户抽奖信息表 AWARD_DRAW_INFO
 * 		user_id 用户编号 
 * 		odd_times 剩余抽奖次数 
 * 		share_date 分享时间（精确到天） 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年4月17日
 */
public class AwardDrawInfo {

	/* 用户编号 */
	private String userId;

	/* 剩余抽奖次数 */
	private int oddTimes;

	/* 分享时间 */
	private Date shareDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getOddTimes() {
		return oddTimes;
	}

	public void setOddTimes(int oddTimes) {
		this.oddTimes = oddTimes;
	}

	public Date getShareDate() {
		return shareDate;
	}

	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}
	
	@Override
	public String toString() {
		return "AwardDrawInfo [userId=" + userId + ", oddTimes=" + oddTimes + ", shareDate=" + shareDate + "]";
	}

}
