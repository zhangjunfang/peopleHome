package com.jujin.entity;

/*用户资金信息*/
public class UserAccountInfo {

	/*
	 * /*StringBuilder searchSql = new StringBuilder("");
	 * searchSql.append("SELECT T.CAPITAL_TOTAL, ");
	 * searchSql.append(" T.RECHARGE_TOTAL, ");
	 * searchSql.append(" T.CASH_TOTAL, "); searchSql.append(" T.BALANCE, ");
	 * searchSql.append(" T.BALANCE_CASH, ");
	 * searchSql.append(" T.BALANCE_FROST, "); searchSql.append(" T.FROST, ");
	 * searchSql.append(" T.AWAIT, "); searchSql.append(" T.CONTINUE_TOTAL ");
	 * searchSql.append("  FROM USERS_ACCOUNT T ");
	 * searchSql.append(" WHERE T.USER_ID = '").append(userId).append("'");
	 */

	private String userId;

	private String capitalTotal;

	private String rechargeTotal;

	private String cashTotal;

	private String balance;
	private String balanceCash;
	private String balanceFrost;
	private String frost;
	private String await;
	private String continueTotal;

	/**
	 * 红包
	 */
	private String coin;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCapitalTotal() {
		return capitalTotal;
	}

	public void setCapitalTotal(String capitalTotal) {
		this.capitalTotal = capitalTotal;
	}

	public String getRechargeTotal() {
		return rechargeTotal;
	}

	public void setRechargeTotal(String rechargeTotal) {
		this.rechargeTotal = rechargeTotal;
	}

	public String getCashTotal() {
		return cashTotal;
	}

	public void setCashTotal(String cashTotal) {
		this.cashTotal = cashTotal;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getBalanceCash() {
		return balanceCash;
	}

	public void setBalanceCash(String balanceCash) {
		this.balanceCash = balanceCash;
	}

	public String getBalanceFrost() {
		return balanceFrost;
	}

	public void setBalanceFrost(String balanceFrost) {
		this.balanceFrost = balanceFrost;
	}

	public String getFrost() {
		return frost;
	}

	public void setFrost(String frost) {
		this.frost = frost;
	}

	public String getAwait() {
		return await;
	}

	public void setAwait(String await) {
		this.await = await;
	}

	public String getContinueTotal() {
		return continueTotal;
	}

	public void setContinueTotal(String continueTotal) {
		this.continueTotal = continueTotal;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

}
