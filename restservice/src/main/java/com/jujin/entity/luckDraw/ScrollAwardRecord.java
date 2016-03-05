package com.jujin.entity.luckDraw;



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
public class ScrollAwardRecord {

	private String userId;
	
	private String nickName;
	
	private String phoneNumber;
	
	private String awardName;
	
	private String winDate;
	
	public ScrollAwardRecord(){
		
	}
	
	public ScrollAwardRecord(String userId,String nickName,String phoneNumber,String awardName,String winDate){
		this.userId = userId;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.awardName = awardName;
		this.winDate = winDate;
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

	public String getWinDate() {
		return winDate;
	}

	public void setWinDate(String winDate) {
		this.winDate = winDate;
	}

}
