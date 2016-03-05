package com.jujin.entity.xglc.transaction;
/**
 * Title: RecoverDTO
 * Description: 还款信息（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class RecoverDTO {
	/**
	 * 还款日期
	 */
	private String recoverDate;
	/**
	 * 还款本金
	 */
	private double recoverCaptial;
	/**
	 * 还款利息
	 */
	private double recoverInterest;
	
	public String getRecoverDate() {
		return recoverDate;
	}
	public void setRecoverDate(String recoverDate) {
		this.recoverDate = recoverDate;
	}
	public double getRecoverCaptial() {
		return recoverCaptial;
	}
	public void setRecoverCaptial(double recoverCaptial) {
		this.recoverCaptial = recoverCaptial;
	}
	public double getRecoverInterest() {
		return recoverInterest;
	}
	public void setRecoverInterest(double recoverInterest) {
		this.recoverInterest = recoverInterest;
	}
	
	

}
