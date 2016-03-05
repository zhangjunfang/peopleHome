package com.jujin.entity.luckDraw;

import java.util.Date;

import com.jujin.util.xglc.CommonUtil;

/**
 * Title: AwardRecord
 * Description: 中奖记录
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月7日
 */
public class AwardRecord {
	
	private String id;

	private String userId;
	
	private String phoneNumber;
	
	private String awardCode;
	
	private String awardMsg;
	
	private String quantity;
	
	private String isBigAward;
	
	private String winDate;
	
	public AwardRecord(){
		
	}
	
	public AwardRecord(Award award,String userId,String phoneNumber){
		this.userId = userId;
		this.phoneNumber = phoneNumber;
		this.awardCode = award.getAwardCode();
		this.awardMsg = award.getAwardMsg();
		this.quantity = award.getQuantity();
		this.isBigAward = award.getIsBigAward();
		this.winDate = CommonUtil.dateToString(new Date());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAwardCode() {
		return awardCode;
	}

	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
	}

	public String getWinDate() {
		return winDate;
	}

	public void setWinDate(String winDate) {
		this.winDate = winDate;
	}

	public String getAwardMsg() {
		return awardMsg;
	}

	public void setAwardMsg(String awardMsg) {
		this.awardMsg = awardMsg;
	}

	public String getIsBigAward() {
		return isBigAward;
	}

	public void setIsBigAward(String isBigAward) {
		this.isBigAward = isBigAward;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	public boolean equalsEntity(AwardRecord ar){
		if(this.awardCode.equals(ar.getAwardCode())){
			return true;
		}else{
			return false;
		}
	}
	
	public String toString()
	{
		return  userId+"["+phoneNumber+"]在"+winDate+"抽中了:"+awardMsg+("1".equals(isBigAward) == true ? "[大奖]":"");
	}
}
