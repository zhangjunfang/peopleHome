package com.jujin.entity.coin;

import java.util.Date;

/**
 * Title: TpaCoinChance 
 * Description: 红包机会表
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年5月29日
 */
public class TpaCoinChanceBean {

	private int recordId;// 编号
	private String userId;// 用户id
	private String phoneNumber;//电话
	private String borrowId;// 表id
	private double validAccountTender;// 有效投资金额
	private String updUserId;// 后台复审标管理员
	private int activityId;// 与活动表TCC_ACTIVITY中RECORD_ID对应
	private int quantity;// 红包机会的数量
	private int enable;// 是否有效：0:强制失效;1:有效;是否可用另外一个条件就是时间
	private Date insDate;// 时间
	private int root;// 红包组的上级,管理员发放的时候为0
	private int orgQuantity;//红包原始个数
	private String groupId;//红包对应的groupId
	
	private String nickName;
	private String borrowTitle;
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public int getOrgQuantity() {
		return orgQuantity;
	}

	public void setOrgQuantity(int orgQuantity) {
		this.orgQuantity = orgQuantity;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getValidAccountTender() {
		return validAccountTender;
	}

	public void setValidAccountTender(double validAccountTender) {
		this.validAccountTender = validAccountTender;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public int getRoot() {
		return root;
	}

	public void setRoot(int root) {
		this.root = root;
	}

	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBorrowTitle() {
		return borrowTitle;
	}

	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	public String toString() {
		return "编号:" + recordId + ",用户id:" + userId + ",电话："+phoneNumber+",标id:" + borrowId
				+ ",有效投资金额:" + validAccountTender + ",后台复审标管理员:" + updUserId
				+ ",活动ID:" + activityId + ",红包机会的数量:" + quantity + ",是否有效:"
				+ enable + ",时间:" + insDate + ",红包组的上级:" + root+",groupId:"+groupId;
	}
}
