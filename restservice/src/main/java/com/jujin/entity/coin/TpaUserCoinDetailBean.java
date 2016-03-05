package com.jujin.entity.coin;

import java.util.Date;

/**
 * Title: TpaUserCoinDefailBean Description: 红包流水表对应的实体类 Company: jujinziben
 * 
 * @author gaojunfeng
 * @date 2015年5月29日
 */
public class TpaUserCoinDetailBean {

	private int recordId;// 编号
	private String phoneNumber;// 电话
	private int bpFlg;// 流水类型 0：收入 1：支出 2：过期
	private String userId;// 用户id
	private int groupId;// 对应红包机会的record_id
	private double amount;// 红包金额
	private String memo;// 描述
	private int state;// 状态： 0:未使用;1:已使用;2:通过GROUP_ID关联TPA_COIN_CHANCE 标判断是否已过期
	private Date insDate;// 时间
	private String borrowId;// 标ID

	private String getTime;
	private String endTime;

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

	public int getBpFlg() {
		return bpFlg;
	}

	public void setBpFlg(int bpFlg) {
		this.bpFlg = bpFlg;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	public String getGetTime() {
		return getTime;
	}

	public void setGetTime(String getTime) {
		this.getTime = getTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String toString() {
		return "编号:" + recordId + ",电话:" + phoneNumber + ",流水类型:" + bpFlg
				+ ",用户id:" + userId + ",红包机会id:" + groupId + ",红包金额:" + amount
				+ ",描述:" + memo + ",状态:" + state + ",时间:" + insDate + ",标ID:"
				+ borrowId;
	}
}
