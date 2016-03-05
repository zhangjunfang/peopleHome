package com.jujin.entity.account;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.jujin.common.SystemConfig;

/** 收益排名 **/
public class EarningsRank {

	/** 账号信息 **/
	private Account account;

	/** 统计周期 **/
	private String statPeriod;

	/** 收益对比 **/
	private String earningMemo;

	/** 收益对比,返回给客户之前已经排好序 **/
	// private List<DataPoint> earnPoints;

	/** 收益对比,返回给客户之前已经排好序 **/
	// private List<DataPoint> compPoints1;

	// private List<DataPoint> compPoints2;

	private List<Double> points;
	private List<Double> points1;
	private List<Double> points2;

	private List<String> labels;

	/* 投资总收益 */
	private double totalIntervest;
	/* 货币基金 */
	private double totalIntervest1;
	/* 银行活期 */
	private double totalIntervest2;

	public EarningsRank() {
		// earnPoints = new ArrayList<DataPoint>();
		// compPoints1 = new ArrayList<DataPoint>();
		// compPoints2 = new ArrayList<DataPoint>();

		points = new ArrayList<Double>();
		points1 = new ArrayList<Double>();
		points2 = new ArrayList<Double>();

		labels = new ArrayList<String>();
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

	/**
	 * @return the statPeriod
	 */
	public String getStatPeriod() {
		return statPeriod;
	}

	/**
	 * @param statPeriod
	 *            the statPeriod to set
	 */
	public void setStatPeriod(String statPeriod) {
		this.statPeriod = statPeriod;
	}

	/**
	 * @return the earningMemo
	 */
	public String getEarningMemo() {
		return earningMemo;
	}

	/**
	 * @param earningMemo
	 *            the earningMemo to set
	 */
	public void setEarningMemo(String earningMemo) {
		this.earningMemo = earningMemo;
	}

	/**
	 * @return the earnPoints
	 * 
	 *         public List<DataPoint> getEarnPoints() { return earnPoints; }
	 */

	/**
	 * @param earnPoints
	 *            the earnPoints to set
	 * 
	 *            public void setEarnPoints(List<DataPoint> earnPoints) {
	 *            this.earnPoints = earnPoints; }
	 */

	/**
	 * @return the compPoints
	 * 
	 *         public List<DataPoint> getCompPoints1() { return compPoints1; }
	 */

	/**
	 * @param compPoints
	 *            the compPoints to set
	 * 
	 *            public void setCompPoints1(List<DataPoint> compPoints) {
	 *            this.compPoints1 = compPoints; }
	 * 
	 *            public List<DataPoint> getCompPoints2() { return compPoints2;
	 *            }
	 * 
	 *            public void setCompPoints2(List<DataPoint> compPoints2) {
	 *            this.compPoints2 = compPoints2; }
	 */

	public String getTotalIntervest() {
		return SystemConfig.getFormat(totalIntervest);
	}

	public void setTotalIntervest(double totalInverest) {
		this.totalIntervest = totalInverest;
	}

	public List<Double> getPoints() {
		return points;
	}

	public void setPoints(List<Double> points) {
		this.points = points;
	}

	public List<Double> getPoints1() {
		return points1;
	}

	public void setPoints1(List<Double> points1) {
		this.points1 = points1;
	}

	public List<Double> getPoints2() {
		return points2;
	}

	public void setPoints2(List<Double> points2) {
		this.points2 = points2;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public String getTotalIntervest1() {

		return SystemConfig.getFormat(totalIntervest1);
	}

	public void setTotalIntervest1(double totalIntervest1) {

		this.totalIntervest1 = totalIntervest1;
	}

	public String getTotalIntervest2() {
		return SystemConfig.getFormat(totalIntervest2);
	}

	public void setTotalIntervest2(double totalIntervest2) {
		this.totalIntervest2 = totalIntervest2;
	}

}
