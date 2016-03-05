/**   
 * @author wangning
 * @date 2015年2月10日 上午9:37:16 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.account;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友收益排名
 */
public class EarnFriendsRank {

	private Account account;

	/* 统计前几名 */
	// private int statNum;

	private List<Account> friendsRank;
	
	public  EarnFriendsRank()
	{
		friendsRank=new ArrayList<Account>();
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * @return the friendsRank
	 */
	public List<Account> getFriendsRank() {
		return friendsRank;
	}

	/**
	 * @param friendsRank the friendsRank to set
	 */
	public void setFriendsRank(List<Account> friendsRank) {
		this.friendsRank = friendsRank;
	}
	
	
	

}
