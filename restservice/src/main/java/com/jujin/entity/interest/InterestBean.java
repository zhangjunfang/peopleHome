package com.jujin.entity.interest;

import java.util.ArrayList;
import java.util.List;

import com.jujin.common.JsonList;
import com.jujin.entity.borrow.ZhaiQuan;

/**
 * Title: InterestBean Description: 余额生息 Company: jujinziben
 * 
 * @author gaojunfeng
 * @date 2015年7月8日
 */
public class InterestBean {

	/** 余额总收益 **/
	private double interestAmount;

	/** 总额 **/
	private double totalAmount;

	/** 可用余额 **/
	private String capitalTotal;

	/** 昨日收益 **/
	private String dayInterest;

	/** 每万元收益 **/
	private String wanInterest;

	/** 近一个月收益 **/
	private String weekAmount;

	/** 近一个月收益 **/
	private String monthAmount;

	private JsonList<InterestDetail> detail;

	private JsonList<ZhaiQuan> zhaiQuan;

	public InterestBean() {
		
		detail = new JsonList<InterestDetail>();
		zhaiQuan = new JsonList<ZhaiQuan>();
	}

	public double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(double interestAmount) {
		this.interestAmount = interestAmount;
	}

	// 每日的流水
	public JsonList<InterestDetail> getDetail() {
		return detail;
	}

	public void setDetail(JsonList<InterestDetail> detail) {
		this.detail = detail;
	}

	public String getCapitalTotal() {
		return capitalTotal;
	}

	public void setCapitalTotal(String capitalTotal) {
		this.capitalTotal = capitalTotal;
	}

	public String getWanInterest() {
		return wanInterest;
	}

	public void setWanInterest(String wanInterest) {
		this.wanInterest = wanInterest;
	}

	public String getWeekAmount() {
		return weekAmount;
	}

	public void setWeekAmount(String weekAmount) {
		this.weekAmount = weekAmount;
	}

	public String getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(String monthAmount) {
		this.monthAmount = monthAmount;
	}

	public String getDayInterest() {
		return dayInterest;
	}

	public void setDayInterest(String dayInterest) {
		this.dayInterest = dayInterest;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public JsonList<ZhaiQuan> getZhaiQuan() {
		return zhaiQuan;
	}

	public void setZhaiQuan(JsonList<ZhaiQuan> zhaiQuan) {
		this.zhaiQuan = zhaiQuan;
	}
	
}
