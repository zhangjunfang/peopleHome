package com.jujin.entity.coin;

/**
 * Title: TpaUserCoin 
 * Description: 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年5月29日
 */
public class TpaUserCoinBean {

	private int recordId;// 编号
	private String phoneNumber;// 电话
	private String userId;// 用户id
	private double amount;// 红包总金额
	private double frost;// 冻结金额

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFrost() {
		return frost;
	}

	public void setFrost(double frost) {
		this.frost = frost;
	}

	public String toString() {
		return "编号：" + recordId + ",电话：" + phoneNumber + ",用户id：" + userId
				+ ",红包总金额：" + amount + ",冻结金额：" + frost;
	}
}
