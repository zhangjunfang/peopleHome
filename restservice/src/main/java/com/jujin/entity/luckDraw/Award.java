package com.jujin.entity.luckDraw;
/**
 * Title: Award
 * Description: 奖品信息(用以构成大奖奖池)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月5日
 */
public class Award {
	
	private String id;
	/**
	 * 奖品编号
	 */
	private String awardCode;
	/**
	 * 奖品信息（显示用）
	 */
	private String awardMsg;
//	/**
//	 * 奖品类型(1-聚金币，2-聚金券，3-现金红包，4-其他奖励)
//	 */
//	private String awardType;
	/**
	 * 聚金币\聚金券\现金红包数额 
	 */
	private String quantity;
	/**
	 * 是否大奖
	 */
	private String isBigAward;
	
	public Award(String id,String awardCode,String awardMsg,String quantity,String isBigAward){
		this.id = id;
		this.awardCode = awardCode;
		this.awardMsg = awardMsg;
		this.quantity = quantity;
		this.isBigAward =isBigAward;
	}
	
	public Award(){
	}
	
	public String getAwardCode() {
		return awardCode;
	}
	public void setAwardCode(String awardCode) {
		this.awardCode = awardCode;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean equalsAward(Award a){
		if(this.awardCode.equals(a.getAwardCode())){
			return true;
		}else{
			return false;
		}
	}
	
}
