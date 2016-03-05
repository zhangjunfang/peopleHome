/**   
 * @author wangning
 * @date 2015年3月15日 下午6:08:29 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.entity.account;

import com.jujin.entity.UserNickName;

/**
 * 标还款 计划明细
 */
public class RecoveEntityDetail {

	/* 标ID */
	private String borrowId;

	private String boorowTitle;

	/* 借款者 */
	private UserNickName borrower;

	/* 应收日期 */
	private String date;

	/* 本期应收 */
	private String await;

	/* 0：本金;1:利息 */
	private int type;

	/**
	 * @return the borrowId
	 */
	public String getBorrowId() {
		return borrowId;
	}

	/**
	 * @param borrowId
	 *            the borrowId to set
	 */
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	/**
	 * @return the boorowTitle
	 */
	public String getBoorowTitle() {
		return boorowTitle;
	}

	/**
	 * @param boorowTitle
	 *            the boorowTitle to set
	 */
	public void setBoorowTitle(String boorowTitle) {
		this.boorowTitle = boorowTitle;
	}

	/**
	 * @return the borrower
	 */
	public UserNickName getBorrower() {
		return borrower;
	}

	/**
	 * @param borrower
	 *            the borrower to set
	 */
	public void setBorrower(UserNickName borrower) {
		this.borrower = borrower;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the await
	 */
	public String getAwait() {
		return await;
	}

	/**
	 * @param await
	 *            the await to set
	 */
	public void setAwait(String await) {
		this.await = await;
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
	 * @return the memo
	 */
	public String getMemo() {

		String memo = "本金";
		switch (type) {
		case 0:
			memo = "本金";
			break;
		case 1:
			memo = "利息";
			break;
		default:
			break;

		}
		return memo;
	}

}
