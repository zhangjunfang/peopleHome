/**
 * 
 */
package com.jujin.entity.account;

import com.jujin.common.SystemConfig;
import com.pro.common.util.StringUtils;

/**
 * @author 宁
 * 
 */
public class AccountLog {

	/* 用户ID */
	private String userId;

	/* 操作类型 */
	private String logType;

	/* 0:收入;1:支出;2:解;3：冻 */
	private int type;

	/* 记录类型 0:资金记录;1:奖励记录 */
	private int recordType;

	/* 收入 */
	private double income;

	/* 支出 */
	private double expend;

	/* 余额 */
	private double balance;
	/* 0:备注 */
	private String remark;

	private double money;

	private String date;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the logType
	 */
	public String getLogType() {
		return logType;
	}

	/**
	 * @param logType
	 *            the logType to set
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the income
	 */
	public double getIncome() {
		return income;
	}

	/**
	 * @param income
	 *            the income to set
	 */
	public void setIncome(double income) {
		this.income = income;
	}

	/**
	 * @return the expend
	 */
	public double getExpend() {
		return expend;
	}

	/**
	 * @param expend
	 *            the expend to set
	 */
	public void setExpend(double expend) {
		this.expend = expend;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the recordType
	 */
	public int getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType
	 *            the recordType to set
	 */
	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return the money
	 */
	public String getMoney() {
		double tmpMoney = money;

		if (this.type == 1) {
			tmpMoney=0-money;
		}
		return SystemConfig.getFormat(tmpMoney);
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}

	public String getTypeMemo() {
		String result = "收";
		/* 0:收入;1:支出;2:解;3：冻 */
		switch (type) {
		case 0:
			result = "收";
			break;
		case 1:
			result = "支";
			break;
		case 2:
			result = "解";
			break;
		case 3:
			result = "冻";
			break;
		default:
			break;
		}
		return result;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
