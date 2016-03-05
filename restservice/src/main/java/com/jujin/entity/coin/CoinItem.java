package com.jujin.entity.coin;

import java.util.Date;


/**
 * 聚金币流水
 * 
 * **/
public class CoinItem {

	private String userId;
	private String phoneNumber;
	private String memo;
	private String amount;
	private String bpFlg;
	private Date insDate;
	private Date startDate;
	private Date endDate;
	private String actName;
	private String actId;
	private String actMemo;
	
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBpFlg() {
		return bpFlg;
	}
	public void setBpFlg(String bpFlg) {
		this.bpFlg = bpFlg;
	}
	public Date getInsDate() {
		return insDate;
	}
	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getActMemo() {
		return actMemo;
	}
	public void setActMemo(String actMemo) {
		this.actMemo = actMemo;
	}
	
	
	
	
	
	
}
