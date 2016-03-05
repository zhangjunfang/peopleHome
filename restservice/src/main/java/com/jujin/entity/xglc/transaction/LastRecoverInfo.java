package com.jujin.entity.xglc.transaction;
/**
 * Title: LastRecoverInfo
 * Description: 最近一次还款信息(西瓜理财)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月27日
 */
public class LastRecoverInfo {
	private String lastPaidDate;
	private int lastPaidCaptial;
	private int lastPaidInterest;
	public String getLastPaidDate() {
		return lastPaidDate;
	}
	public void setLastPaidDate(String lastPaidDate) {
		this.lastPaidDate = lastPaidDate;
	}
	public int getLastPaidCaptial() {
		return lastPaidCaptial;
	}
	public void setLastPaidCaptial(int lastPaidCaptial) {
		this.lastPaidCaptial = lastPaidCaptial;
	}
	public int getLastPaidInterest() {
		return lastPaidInterest;
	}
	public void setLastPaidInterest(int lastPaidInterest) {
		this.lastPaidInterest = lastPaidInterest;
	}
	
	
}
