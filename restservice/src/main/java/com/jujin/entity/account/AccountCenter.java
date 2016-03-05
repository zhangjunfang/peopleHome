/**
 * 
 */
package com.jujin.entity.account;

import com.jujin.common.OpResult;

/**
 * @author 宁
 *需要后台缓存一个表,包含收益排名,有新的数据需要更新这个表
 */
public class AccountCenter{

	private Account account;

	
	public AccountCenter()
	{
		
	}
	
	public AccountCenter(Account account) {
		this.account = account;
	}

	// 以下属性为状态指示

	/* 回款计划 */
	private int recove;

	/* 我的消息 */
	private int message;
	/* 收益排名 */
	private int rewardRank;

	/* 优惠券（或其他特权） */
	private int privilege;

	/* 银行卡 */
	private int bankCard;

	/* 我的认证 */
	private int attestion;

	/* 我的邀请 */
	private int invited;

	 
	/**
	 * @return the recove
	 */
	public int getRecove() {
		return recove;
	}

	/**
	 * @param recove
	 *            the recove to set
	 */
	public void setRecove(int recove) {
		this.recove = recove;
	}

	/**
	 * @return the message
	 */
	public int getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(int message) {
		this.message = message;
	}

	/**
	 * @return the rewardRank
	 */
	public int getRewardRank() {
		return rewardRank;
	}

	/**
	 * @param rewardRank
	 *            the rewardRank to set
	 */
	public void setRewardRank(int rewardRank) {
		this.rewardRank = rewardRank;
	}

	/**
	 * @return the privilege
	 */
	public int getPrivilege() {
		return privilege;
	}

	/**
	 * @param privilege
	 *            the privilege to set
	 */
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	/**
	 * @return the bankCard
	 */
	public int getBankCard() {
		return bankCard;
	}

	/**
	 * @param bankCard
	 *            the bankCard to set
	 */
	public void setBankCard(int bankCard) {
		this.bankCard = bankCard;
	}

	/**
	 * @return the attestion
	 */
	public int getAttestion() {
		return attestion;
	}

	/**
	 * @param attestion
	 *            the attestion to set
	 */
	public void setAttestion(int attestion) {
		this.attestion = attestion;
	}

	/**
	 * @return the invited
	 */
	public int getInvited() {
		return invited;
	}

	/**
	 * @param invited
	 *            the invited to set
	 */
	public void setInvited(int invited) {
		this.invited = invited;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	
	
}
