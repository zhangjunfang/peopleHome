package com.jujin.entity.award;

/**
 * Title: AwardResultInfo 
 * Description: 用户抽奖后返回前天的信息对象
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年4月21日
 */
public class AwardResultInfo {

	/* 今日抽奖次数 */
	private int countDrawNumTD;
	/* 剩余抽奖次数 */
	private int oddTimes;
	/* 奖品编号 */
	private int awardCode;


	public int getCountDrawNumTD() {
		return countDrawNumTD;
	}


	public void setCountDrawNumTD(int countDrawNumTD) {
		this.countDrawNumTD = countDrawNumTD;
	}


	public int getOddTimes() {
		return oddTimes;
	}


	public void setOddTimes(int oddTimes) {
		this.oddTimes = oddTimes;
	}


	public int getAwardCode() {
		return awardCode;
	}


	public void setAwardCode(int awardCode) {
		this.awardCode = awardCode;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "AwardWinInfo [countDrawNumTD=" + countDrawNumTD + ",oddTimes=" + oddTimes + ", awardCode=" + awardCode + "]";
	}
	
}
