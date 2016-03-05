/**   
 * @author wangning
 * @date 2015年2月10日 上午9:54:49 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.account;

import org.springframework.util.StringUtils;

import com.jujin.common.SystemConfig;
import com.wicket.loan.common.utils.UserUtils;

/**
 * 好友投资统计
 */
public class FriendInvestment {

	/* 昵称 */
	private String nickName;

	/* 用户名 */
	private String userId;

	/* 投资区间 */
	private String investRange;

	/* 手机 */
	private String tel;

	/* 0:注册;1:投资;2:0+1 */
	private int incomeType;

	/* 收益 */
	private double income;

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		if (!StringUtils.isEmpty(nickName))
			return UserUtils.realNameToConceal(nickName);
		return UserUtils.realNameToConceal(userId);
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

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
	 * @return the investRange
	 */
	public String getInvestRange() {
		if ("0".equals(investRange))
			return "未投资";
		return investRange;
	}

	/**
	 * @param investRange
	 *            the investRange to set
	 */
	public void setInvestRange(String investRange) {
		this.investRange = investRange;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		if (StringUtils.isEmpty(tel))
			return "未认证";
		else
			return SystemConfig.getPhoneToConceal(tel);
	}
	

	/**
	 * @param tel
	 *            the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the incomeType
	 */
	public int getIncomeType() {
		return incomeType;
	}

	/**
	 * @param incomeType
	 *            the incomeType to set
	 */
	public void setIncomeType(int incomeType) {
		this.incomeType = incomeType;
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

}
