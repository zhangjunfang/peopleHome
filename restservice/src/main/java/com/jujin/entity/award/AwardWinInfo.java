package com.jujin.entity.award;

import java.util.Date;

/**
 * Title: AwardWinInfo 
 * Description: 用户中奖情况表对应的实体类 
 * 用户中奖情况表 AWARD_WIN_INFO 
 *    user_id  用户编号
 *    award_code 奖品编号 
 *    award_date 中奖时间 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年4月17日
 */
public class AwardWinInfo {

	/* 中奖编号 */
	private int id;
	/* 用户编号 */
	private String userId;
	/* 奖品编号 */
	private int awardCode;
	/* 中奖时间 */
	private Date winDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(int awardCode) {
		this.awardCode = awardCode;
	}

	public Date getWinDate() {
		return winDate;
	}

	public void setWinDate(Date winDate) {
		this.winDate = winDate;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "AwardWinInfo [id=" + id + ",userId=" + userId + ", awardCode=" + awardCode + ", winDate=" + winDate + "]";
	}
	
}
