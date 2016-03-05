package com.jujin.entity.account;

import org.springframework.util.StringUtils;

import com.sun.istack.internal.logging.Logger;

import sun.swing.StringUIClientPropertyKey;


/**
 * 
 * 
 * **/
public class UserTenderSummary {
	
	/**投标中的金额**/
	private String tenderingAccount;
	
	/**回收中的投**/
	private String repayingAccount;
	
	/**待回收本息**/
	private String repayWaitAccount;
	
	/**已收完的投资**/
	private String repayEndCapital;
	
	/**已回收本息**/
	private String repayEndAccount;
	
	/**逾期的投资**/
	private String overAccount;
	
	/**网站垫付的投资**/
	private String siteAdvance;
	
	/**总投资金额**/
	private String tenderAccount;
	
	
	/**已赚利息**/
	private String incomeInterest;
	
	/**已赚奖励**/
	private String incomeReward;
	
	/**已赚罚息**/
	private String incomePenalty;
	
	/**平均收益率**/
	private String averageIncome;
	
	/**坏账率**/
	private String badRate;

	public String getTenderingAccount() {
		return tenderingAccount;
	}

	public void setTenderingAccount(String tenderingAccount) {
		this.tenderingAccount = tenderingAccount;
	}

	public String getRepayingAccount() {
		return repayingAccount;
	}

	public void setRepayingAccount(String repayingAccount) {
		this.repayingAccount = repayingAccount;
	}

	public String getRepayWaitAccount() {
		return repayWaitAccount;
	}

	public void setRepayWaitAccount(String repayWaitAccount) {
		this.repayWaitAccount = repayWaitAccount;
	}

	public String getRepayEndCapital() {
		return repayEndCapital;
	}

	public void setRepayEndCapital(String repayEndCapital) {
		this.repayEndCapital = repayEndCapital;
	}

	public String getRepayEndAccount() {
		return repayEndAccount;
	}

	public void setRepayEndAccount(String repayEndAccount) {
		this.repayEndAccount = repayEndAccount;
	}

	public String getOverAccount() {
		return overAccount;
	}

	public void setOverAccount(String overAccount) {
		this.overAccount = overAccount;
	}

	public String getSiteAdvance() {
		return siteAdvance;
	}

	public void setSiteAdvance(String siteAdvance) {
		this.siteAdvance = siteAdvance;
	}

	public String getTenderAccount() {
		return tenderAccount;
	}

	public void setTenderAccount(String tenderAccount) {
		this.tenderAccount = tenderAccount;
	}

	public String getIncomeInterest() {
		return incomeInterest;
	}

	public void setIncomeInterest(String incomeInterest) {
		this.incomeInterest = incomeInterest;
	}

	public String getIncomeReward() {
		return incomeReward;
	}

	public void setIncomeReward(String incomeReward) {
		this.incomeReward = incomeReward;
	}

	public String getIncomePenalty() {
		return incomePenalty;
	}

	public void setIncomePenalty(String incomePenalty) {
		this.incomePenalty = incomePenalty;
	}

	public String getAverageIncome() {
		return averageIncome;
	}

	public void setAverageIncome(String averageIncome) {
		this.averageIncome = averageIncome;
	}

	public String getBadRate() {
		return badRate;
	}

	public void setBadRate(String badRate) {
		this.badRate = badRate;
	}
	
	
	public String getTotalEarnings()
	{
		if(StringUtils.isEmpty(incomeInterest)&&StringUtils.isEmpty(incomeReward))
		{
			return "0.00";
		}
		if(StringUtils.isEmpty(incomeInterest))
		{
			return incomeReward;
		}
		
		if(StringUtils.isEmpty(incomeReward))
		{
			return incomeInterest;
		}
		try {
			return String.valueOf(Double.parseDouble(incomeInterest)+Double.parseDouble(incomeReward));
		} catch (Exception e) {
			 
		}
		return "0.00";
	}
	
	
	
	
	
	
	

}
