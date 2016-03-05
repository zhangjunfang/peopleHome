/**   
 * @author wangning
 * @date 2015年3月12日 下午9:17:43 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.entity.account;

/**
 * 回款信息(以标为单位,呈现信息参考积木盒子)
 * 
 * 投标中没有合同和还款计划,待收金额是0元
 */
public class RecoveEntity {

	private String borrowId;

	private String borrowTitle;

	/* 投资金额 */
	private double investment;

	/* 待收 */
	private double await;

	/* 已收 */
	private double recoverYes;

	private String borrowType;

	private String transferFlg;

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
	 * @return the borrowTitle
	 */
	public String getBorrowTitle() {
		return borrowTitle;
	}

	/**
	 * @param borrowTitle
	 *            the borrowTitle to set
	 */
	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	/**
	 * @return the investment
	 */
	public double getInvestment() {
		return investment;
	}

	/**
	 * @param investment
	 *            the investment to set
	 */
	public void setInvestment(double investment) {
		this.investment = investment;
	}

	/**
	 * @return the await
	 */
	public double getAwait() {
		return await;
	}

	/**
	 * @param await
	 *            the await to set
	 */
	public void setAwait(double await) {
		this.await = await;
	}

	/**
	 * @return the recoverYes
	 */
	public double getRecoverYes() {
		return recoverYes;
	}

	/**
	 * @param recoverYes
	 *            the recoverYes to set
	 */
	public void setRecoverYes(double recoverYes) {
		this.recoverYes = recoverYes;
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getTransferFlg() {
		return transferFlg;
	}

	public void setTransferFlg(String transferFlg) {
		this.transferFlg = transferFlg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RecoveEntity [borrowId=" + borrowId + ", borrowTitle="
				+ borrowTitle + ", investment=" + investment + ", await="
				+ await + ", recoverYes=" + recoverYes + "]";
	}

}
