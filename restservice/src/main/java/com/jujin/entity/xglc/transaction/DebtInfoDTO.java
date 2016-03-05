package com.jujin.entity.xglc.transaction;
/**
 * Title: DebtInfoDTO
 * Description: 账户债权状态（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class DebtInfoDTO {
	
	/**
	 * 债权订单号ID
	 */
	private String debtId;
	/**
	 * 债权剩余本金
	 */
	private double debtCaptial;
	/**
	 * 债权剩余利息
	 */
	private double debtInterest;
	/**
	 * 债权状态，满标、还款中、自动退出、债权转让退出
	 */
	private String status;
	/**
	 * 已完成付息期数
	 */
	private int paidCount;
	/**
	 * 最近一次付息日期
	 */
	private String lastPaidDate;
	/**
	 * 最近一次付息本金
	 */
	private double lastPaidCaptial;
	/**
	 * 最近一次付息利息
	 */
	private double lastPaidInterest;
	
	public String getDebtId() {
		return debtId;
	}
	public void setDebtId(String debtId) {
		this.debtId = debtId;
	}
	public double getDebtCaptial() {
		return debtCaptial;
	}
	public void setDebtCaptial(double debtCaptial) {
		this.debtCaptial = debtCaptial;
	}
	public double getDebtInterest() {
		return debtInterest;
	}
	public void setDebtInterest(double debtInterest) {
		this.debtInterest = debtInterest;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPaidCount() {
		return paidCount;
	}
	public void setPaidCount(int paidCount) {
		this.paidCount = paidCount;
	}
	public String getLastPaidDate() {
		return lastPaidDate;
	}
	public void setLastPaidDate(String lastPaidDate) {
		this.lastPaidDate = lastPaidDate;
	}
	public double getLastPaidCaptial() {
		return lastPaidCaptial;
	}
	public void setLastPaidCaptial(double lastPaidCaptial) {
		this.lastPaidCaptial = lastPaidCaptial;
	}
	public double getLastPaidInterest() {
		return lastPaidInterest;
	}
	public void setLastPaidInterest(double lastPaidInterest) {
		this.lastPaidInterest = lastPaidInterest;
	}
	
	
}
