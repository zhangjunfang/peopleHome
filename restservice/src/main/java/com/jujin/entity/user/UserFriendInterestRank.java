package com.jujin.entity.user;

import org.springframework.util.StringUtils;

import com.jujin.common.SystemConfig;
import com.jujin.entity.account.Account;

/*
 * 用户投资金额
 * */
public class UserFriendInterestRank {
	

	 
	
	private String nickName;
	
	private String interestRank;
	
	private String headImgeUrl;
	
	
	private Account account;

	 

 

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getInterestRank() {
		return interestRank;
	}

	public void setInterestRank(String interestRank) {
		this.interestRank = interestRank;
	}

	public String getHeadeImgeUrl() {
		
		if(!StringUtils.isEmpty(headImgeUrl))
		{
			return SystemConfig.getRoot()+headImgeUrl;
		}
		return headImgeUrl;
	}

 

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setHeadImgeUrl(String headImgeUrl) {
		this.headImgeUrl = headImgeUrl;
	}
	
	
	
	
	

}
