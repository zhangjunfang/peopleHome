package com.jujin.entity.borrow;

/** 标还款模型 **/
public class BorrowRepay {

	/** 借款标题 **/
	private String borrowTitle;

	/** 当前期数/总期数 **/
	private String currentPeriod;

	/** 总期数 **/
	private String totalPeriod;

	/** 还款本息 **/
	private String capitalTotal;

	/** 实际到账日期 **/
	private String repayTime;
	
	

	public String getBorrowTitle() {
		return borrowTitle;
	}

	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	public String getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(String currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public String getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(String totalPeriod) {
		this.totalPeriod = totalPeriod;
	}

	public String getCapitalTotal() {
		return capitalTotal;
	}

	public void setCapitalTotal(String capitalTotal) {
		this.capitalTotal = capitalTotal;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getPeriod() {
		return String.format("%s/%s", currentPeriod, totalPeriod);
	}

}
