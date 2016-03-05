package com.jujin.entity.coin;

/**
 * @author wangning
 * 
 *         红包列表
 * 
 */
public class CoinChange {

	private String borrowId;

	private String borrowTitle;

	private String quantity;

	private String orgQuantity;

	private String endTime;

	private String coinEndTime;

	private String root;

	private String memo;

	private String phone;
	
	/**
	 * 已经得到的红包数量
	 */
	private double  getAmount;

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getBorrowTitle() {
		return borrowTitle;
	}

	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getOrgQuantity() {
		return orgQuantity;
	}

	public void setOrgQuantity(String orgQuantity) {
		this.orgQuantity = orgQuantity;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCoinEndTime() {
		return coinEndTime;
	}

	public void setCoinEndTime(String coinEndTime) {
		this.coinEndTime = coinEndTime;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getGetAmount() {
		return getAmount;
	}

	public void setGetAmount(double getAmount) {
		this.getAmount = getAmount;
	}
	
	

}
