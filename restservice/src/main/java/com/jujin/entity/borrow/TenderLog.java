/**   
 * @author wangning
 * @date 2015年2月10日 下午4:19:53 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.borrow;

import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;

/**
 * 投资记录
 */
public class TenderLog {

	/* 昵称 */
	private String nickName;

	/* 投标途径 0：网站；1:web app;2:iOS;3:安卓 */
	private int type;

	/* 投资金额 */
	private String account;
	
	/* 有效投资金额 */
	private String validateAccount;

	/* 投资金额 */
	private String time;
	
	/*状态*/
	private String memo;
	
	
	private String borrowId;
	
	private String borrowTitle;
	
	
	
	

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return UserUtils.realNameToConceal(nickName);
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	 * @return the account
	 */
	public String getAccount() {
		return NumberUtils.moneyFormat(account);
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	public String getValidateAccount() {
		return validateAccount;
	}

	public void setValidateAccount(String validateAccount) {
		this.validateAccount = validateAccount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

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
	
	

}
