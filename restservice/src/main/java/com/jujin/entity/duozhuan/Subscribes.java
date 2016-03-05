package com.jujin.entity.duozhuan;

import java.io.Serializable;

/**
 * Title: Subscribes
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年12月4日
 */
public class Subscribes implements Serializable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 9106457133121438951L;

	private String subscribeUserName;
	
	private double amount;
	
	private double validAmount;
	
	private String addDate;
	
	private int status;
	
	private int type;

	public String getSubscribeUserName() {
		return subscribeUserName;
	}

	public void setSubscribeUserName(String subscribeUserName) {
		this.subscribeUserName = subscribeUserName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getValidAmount() {
		return validAmount;
	}

	public void setValidAmount(double validAmount) {
		this.validAmount = validAmount;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
