package com.jujin.entity.luckDraw;

import java.util.Date;

import com.jujin.util.xglc.CommonUtil;


/**
 * 
* <p>Title: ScrollAwardRecord.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: jujinziben</p>
* @author zhengshaoxu
* @date 2015年12月18日
* @version 1.0
 */
public class BigAwardRecord {

	private String userId;
	
	private String nickName;
	
	private String phoneNumber;
	
	private String awardName;
	
	private String date;
	
	public BigAwardRecord(){
		
	}
	
	public BigAwardRecord(String userId,String nickName,String phoneNumber,String awardName){
		this.userId = userId;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.awardName = awardName;
		this.date = CommonUtil.dateToString(new Date());
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
