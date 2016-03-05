package com.jujin.entity.recharge;

public class RechargeEntity {
	
	String bankConfig = "";
	String config = "";
	String rechargeAmount = "";
	String feeAmount = "";
	String balanceAmount = "";
	String rechargeBankName = "";
	String bankId = "";
	String merCode = "";
	String merKey = "";
	String paycd = "";
	String currencyType = "RMB";
	String billNoAutoStr ="";
	String merId = "";
	String userId="";
	
	/**
	 * 生成的订单号
	 */
	String orderId="";
	public String getBankConfig() {
		return bankConfig;
	}
	public void setBankConfig(String bankConfig) {
		this.bankConfig = bankConfig;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getRechargeBankName() {
		return rechargeBankName;
	}
	public void setRechargeBankName(String rechargeBankName) {
		this.rechargeBankName = rechargeBankName;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getMerCode() {
		return merCode;
	}
	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	public String getMerKey() {
		return merKey;
	}
	public void setMerKey(String merKey) {
		this.merKey = merKey;
	}
	public String getPaycd() {
		return paycd;
	}
	public void setPaycd(String paycd) {
		this.paycd = paycd;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getBillNoAutoStr() {
		return billNoAutoStr;
	}
	public void setBillNoAutoStr(String billNoAutoStr) {
		this.billNoAutoStr = billNoAutoStr;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	

}
