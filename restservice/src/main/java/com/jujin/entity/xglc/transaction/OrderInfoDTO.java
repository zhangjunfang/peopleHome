package com.jujin.entity.xglc.transaction;

import java.util.List;

/**
 * Title: OrderInfoDTO
 * Description: 持有订单（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class OrderInfoDTO {
	
	/**
	 * 西瓜发起的订单信息唯一的订单ID号
	 */
	private String xgOrderSn;
	/**
	 * 订单号ID
	 */
	private String orderId;
	/**
	 * 购买金额(单位：分)
	 */
	private int purchaseCaptial;
	/**
	 * 购买时间
	 */
	private String purchaseTime;
	/**
	 * 产品Code
	 */
	private String productCode;
	/**
	 * 利息管理费率
	 */
	private double interestManageFee;

	/**
	 * 还款信息
	 */
	private List<RecoverDTO> recoverList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getPurchaseCaptial() {
		return purchaseCaptial;
	}

	public void setPurchaseCaptial(int purchaseCaptial) {
		this.purchaseCaptial = purchaseCaptial;
	}

	public String getPurchaseTime() {
		return purchaseTime;
	}

	public void setPurchaseTime(String purchaseTime) {
		this.purchaseTime = purchaseTime;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public double getInterestManageFee() {
		return interestManageFee;
	}

	public void setInterestManageFee(double interestManageFee) {
		this.interestManageFee = interestManageFee;
	}

	public List<RecoverDTO> getRecoverList() {
		return recoverList;
	}

	public void setRecoverList(List<RecoverDTO> recoverList) {
		this.recoverList = recoverList;
	}

	public String getXgOrderSn() {
		return xgOrderSn;
	}

	public void setXgOrderSn(String xgOrderSn) {
		this.xgOrderSn = xgOrderSn;
	}
	
	
}
