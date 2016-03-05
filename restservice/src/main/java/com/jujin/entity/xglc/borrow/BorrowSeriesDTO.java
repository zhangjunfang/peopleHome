package com.jujin.entity.xglc.borrow;

import java.util.List;

/**
 * Title: BorrowTypeDTO
 * Description: 产品系列（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class BorrowSeriesDTO {
	/**
	 * 系列ID
	 */
	private String id;
	/**
	 * 系列名称
	 */
	private String seriesName;
	/**
	 * 利息管理费(没有为:0.0)
	 */
	private double interestManagement;
	/**
	 * 转让规则(没有填空字符串)
	 */
	private String transferFee;
	/**
	 * 逾期垫付规则(没有填空字符串)
	 */
	private String overduePayment;
	/**
	 * 最小投资金额(没有填0)
	 */
	private int minInvestment;
	/**
	 * 付息方式
	 */
	private List<InterestPaymentDTO> interestPaymentList;
	/**
	 * 担保公司
	 */
	private List<GuaranteeDTO> guaranteeList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public double getInterestManagement() {
		return interestManagement;
	}
	public void setInterestManagement(double interestManagement) {
		this.interestManagement = interestManagement;
	}
	public String getTransferFee() {
		return transferFee;
	}
	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}
	public String getOverduePayment() {
		return overduePayment;
	}
	public void setOverduePayment(String overduePayment) {
		this.overduePayment = overduePayment;
	}
	public int getMinInvestment() {
		return minInvestment;
	}
	public void setMinInvestment(int minInvestment) {
		this.minInvestment = minInvestment;
	}
	public List<InterestPaymentDTO> getInterestPaymentList() {
		return interestPaymentList;
	}
	public void setInterestPaymentList(List<InterestPaymentDTO> interestPaymentList) {
		this.interestPaymentList = interestPaymentList;
	}
	public List<GuaranteeDTO> getGuaranteeList() {
		return guaranteeList;
	}
	public void setGuaranteeList(List<GuaranteeDTO> guaranteeList) {
		this.guaranteeList = guaranteeList;
	}
	
	
	
	
}
