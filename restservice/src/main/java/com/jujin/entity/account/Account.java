package com.jujin.entity.account;

import com.jujin.common.SystemConfig;
import com.jujin.entity.UserNickName;

/*用户账户信息*/
public class Account {

	private UserNickName user;

	// 资产总额
	private double capitalTotal;
	// 可用余额
	private double balance;
	/* 冻结金额 */
	private double frost;
	// 总待收
	private double await;
	
	/**聚金宝*/
	private double jujinbao;
	
	/**聚金宝收益*/
	private double jujinbaoInterest;
 
	/** 安全等级 0:低; 1:中;2:高 **/
	private int safeStatus;

	/** 收益排名 **/
	private int rank;
	
	private double continueTotal;

	/**
	 * 投资收益
	 */
	private double recoverInterest;
	
	/**
	 * 聚金币
	 */
	private double coin;
	
	
	/**
	 * 聚金券
	 * **/
	private int jujinQuan;
	
 
	/**昨日余额生息**/
	private String dayInterest;
	
	/**余额生息总收益**/
	private String interestAmount;
	
	/**是否开通余额生息**/
	private String interestFlg;
	
	/**
	 * 奖励
	 */
	private double rewardMoney;


	public Account() {

	}

	public Account(UserNickName user) {
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public UserNickName getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserNickName user) {
		this.user = user;
	}

	/**
	 * @return the capitalTotal
	 */
	public String getCapitalTotal() {
		return SystemConfig.getFormat(capitalTotal);
	}

	/**
	 * @param capitalTotal
	 *            the capitalTotal to set
	 */
	public void setCapitalTotal(double capitalTotal) {

		this.capitalTotal = capitalTotal;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return SystemConfig.getFormat(balance);
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the frost
	 */
	public String getFrost() {

		return SystemConfig.getFormat(frost);
	}

	/**
	 * @param frost
	 *            the frost to set
	 */
	public void setFrost(double frost) {
		this.frost = frost;
	}

	/**
	 * @return the await
	 */
	public String getAwait() {
		return SystemConfig.getFormat(await);
	}

	/**
	 * @param await
	 *            the await to set
	 */
	public void setAwait(double await) {
		this.await = await;
	}

	/**
	 * // 总收益=投资收益+奖励
	 * 
	 * @return the totalEarnings
	 */
	public String getTotalEarnings() {

		return SystemConfig.getFormat(recoverInterest + rewardMoney+jujinbaoInterest);// 总收益=投资收益+奖励;
	}

	/**
	 * @return the safeStatus
	 */
	public int getSafeStatus() {
		return safeStatus;
	}
	
	
	public double getJujinbao() {
		return jujinbao;
	}

	public void setJujinbao(double jujinbao) {
		this.jujinbao = jujinbao;
	}

	/**
	 * @param safeStatus
	 *            the safeStatus to set
	 */
	public void setSafeStatus(int safeStatus) {
		this.safeStatus = safeStatus;
	}

	/**
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank
	 *            the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/**
	 * @return the recoverInterest
	 */
	public String getRecoverInterest() {

		return SystemConfig.getFormat(recoverInterest);
	}

	/**
	 * @param recoverInterest
	 *            the recoverInterest to set
	 */
	public void setRecoverInterest(double recoverInterest) {
		this.recoverInterest = recoverInterest;
	}

	/**
	 * @return the rewardMoney
	 */
	public String getRewardMoney() {
		
		return SystemConfig.getFormat(rewardMoney);
	}

	/**
	 * @param rewardMoney
	 *            the rewardMoney to set
	 */
	public void setRewardMoney(double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public String  getContinueTotal() {
		return SystemConfig.getFormat(continueTotal);
	}

	public void setContinueTotal(double continueTotal) {
		this.continueTotal = continueTotal;
	}
	
	public String getDayInterest() {
		return dayInterest;
	}

	public void setDayInterest(String dayInterest) {
		this.dayInterest = dayInterest;
	}

	public String getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(String interestAmount) {
		this.interestAmount = interestAmount;
	}

	public String getInterestFlg() {
		return interestFlg;
	}

	public void setInterestFlg(String interestFlg) {
		this.interestFlg = interestFlg;
	}

	public double getTotalCapital() {
		return  capitalTotal;
	}

	public double getCoin() {
		return coin;
	}

	public void setCoin(double coin) {
		this.coin = coin;
	}

	public double getJujinbaoInterest() {
		return jujinbaoInterest;
	}

	public void setJujinbaoInterest(double jujinbaoInterest) {
		this.jujinbaoInterest = jujinbaoInterest;
	}

	public int getJujinQuan() {
		return jujinQuan;
	}

	public void setJujinQuan(int jujinQuan) {
		this.jujinQuan = jujinQuan;
	}

	 

	
}
