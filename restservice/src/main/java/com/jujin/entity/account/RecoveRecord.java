package com.jujin.entity.account;

public class RecoveRecord {

	/* 应收日期 */
	private String date;

	/* 本期应收 */
	private String await;

	/* 总期数 */
	private int total;

	/* 当前应收期数 */
	private int current;

	/* 1:利息;0:本金 */
	private int type;

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the await
	 */
	public String getAwait() {
		return await;
	}

	/**
	 * @param await
	 *            the await to set
	 */
	public void setAwait(String await) {
		this.await = await;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @return the current
	 */
	public int getCurrent() {
		return current;
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(int current) {
		this.current = current;
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

}
