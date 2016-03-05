package com.jujin.entity.xglc.borrow;
/**
 * Title: InterestPaymentDTO
 * Description: 付息方式（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class InterestPaymentDTO {
	/**
	 * 付息方式ID
	 */
	private String id;
	/**
	 * 付息方式名称
	 */
	private String interestPaymentType;
	/**
	 * 按月/按日计息
	 */
	private String calcPeriodType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterestPaymentType() {
		return interestPaymentType;
	}
	public void setInterestPaymentType(String interestPaymentType) {
		this.interestPaymentType = interestPaymentType;
	}
	public String getCalcPeriodType() {
		return calcPeriodType;
	}
	public void setCalcPeriodType(String calcPeriodType) {
		this.calcPeriodType = calcPeriodType;
	}
	
	
}
