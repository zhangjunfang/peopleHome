package com.jujin.entity.auto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;


/*
 * 自动投标设置
 * */
public class AutoInvestSetting {

	
	private String id;
	
	private String userId;
	
	/*是否启用*/
	private boolean enable;
	/*账户余额*/
	private double capital;
	/*投资金额*/
	private double amount;
	/*周期*/
	private int periodBegin;
	/*周期*/
	private int periodEnd;
	/*是否奖励*/
	private boolean award;
	/*标类型*/
	private String borrowType;
	
	private List<String> borrowTypes;
	/*利率下限*/
	private double rate;
	
	/*账户余额*/
	private double balance;
	
	/*当前排名*/
	private double sortNum;
	
	
	
	public 	AutoInvestSetting()
	{
		borrowTypes=new ArrayList<String>();
	}
	
 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public double getCapital() {
		return capital;
	}
	public void setCapital(double capital) {
		this.capital = capital;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getPeriodBegin() {
		return periodBegin;
	}
	public void setPeriodBegin(int periodBegin) {
		this.periodBegin = periodBegin;
	}
	public int getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(int periodEnd) {
		this.periodEnd = periodEnd;
	}
	public boolean isAward() {
		return award;
	}
	public void setAward(boolean award) {
		this.award = award;
	}
	public String getBorrowType() {
		if(StringUtils.isEmpty(borrowType))
		{
			return borrowType;
		}
		return borrowType.trim();
	}
	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getSortNum() {
		return sortNum;
	}
	public void setSortNum(double sortNum) {
		this.sortNum = sortNum;
	}
	
	public String  getBorrowTypeMemo() {
		
		String pre="标种类型";
		if(StringUtils.isEmpty(borrowType))
		{
			return "不限";
		}
		String result="";
		String[] array=borrowType.split(",");
		
		for(String type : array){
			switch(type){
			case "0":
				pre="全部标行";
				break;
			case "1":
				pre="信用标";
				break;
			case "2":
				pre="抵押标";
				break;
			case "3":
				pre="净值标";
				break;
			case "4":
				pre="担保标";
				break;
			case "5":
				pre="秒还标";
				break;
			case "6":
				pre="流转标";
				break;
			case "7":
				pre="聚金优选";
				break;
			case "8":
				pre="车辆质押";
				break;
			case "11":
				pre="车分期";
				break;
			case "12":
				pre="慈善标";
				break;
			default:
				break;
			}
			if(result.length()<1)
			{
				result=pre;
			}
			else {
				result+="/"+pre;
			}
		}
		
		return pre;
	}
	
	public void  setBorrowTypeMemo(String borrowTypeMemo) {
		
	}
	
	
	public List<String> getBorrowTypes() {
		if(borrowTypes==null)
		{
			borrowTypes=new ArrayList<String>();
		}
		borrowTypes.clear();
		if(StringUtils.isEmpty(borrowType))
		{
			return borrowTypes;
		}
		String[] tmpArray=borrowType.trim().split(",");
		for (int i = 0; i < tmpArray.length; i++) {
			borrowTypes.add(tmpArray[i]);
		}
		return  borrowTypes;
	}


	public void setBorrowTypes(List<String> borrowTypes) {
		this.borrowTypes = borrowTypes;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
