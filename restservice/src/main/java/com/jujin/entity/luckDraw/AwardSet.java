package com.jujin.entity.luckDraw;

import com.jujin.util.xglc.CommonUtil;

/**
 * Title: RewardInfo
 * Description: 奖品配置
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月5日
 */
public class AwardSet {
	public static final String COIN_CODE_6 = "1";
	public static final String COIN_CODE_66 = "2";
	public static final String TICKET_CODE_5 = "3";
	public static final String TICKET_CODE_1 = "4";
	
	public static final String TICKET_SQL_ID_5 = "2015201605";
	public static final String TICKET_SQL_ID_1 = "201520161";
	
	/**
	 * 抽奖开始时间
	 */
	public static final String BEGIN_DATE_DRAW = "2015-12-22 00:00:00";
	/**
	 * 抽奖结束时间
	 */
	public static final String END_DATE_DRAW = "2016-01-15 23:59:59";
	
	private String id;
	
	/**
	 * 奖品编号
	 */
	private String awardCode;
	/**
	 * 奖品信息
	 */
	private String awardMsg;
	/**
	 * 聚金币\聚金券\现金红包数额 
	 */
	private String quantity;
	/**
	 * 奖品数量(-1为无限)
	 */
	private String totalCount;
	/**
	 * 同一用户最大获得次数(-1为无限)
	 */
	private String maxTimes;
	/**
	 * 中奖概率
	 */
	private double chance;
	
	public AwardSet(){
		
	}
	
	public AwardSet(String awardCode,String awardMsg,String quantity,String totalCount,String maxTimes,double chance){
		this.id = CommonUtil.getUUID();
		this.awardCode = awardCode;
		this.awardMsg = awardMsg;
		this.quantity = quantity;
		this.totalCount = totalCount;
		this.maxTimes = maxTimes;
		this.chance = chance;
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


	public String getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}


	public String getMaxTimes() {
		return maxTimes;
	}


	public void setMaxTimes(String maxTimes) {
		this.maxTimes = maxTimes;
	}


	public double getChance() {
		return chance;
	}


	public void setChance(double chance) {
		this.chance = chance;
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
	
	
	public static String getTicketIdByAwardCode(String code){
		String ticketId = "";
		if(code.equals(TICKET_CODE_1)){
			ticketId = TICKET_SQL_ID_1;
		}
		if(code.equals(TICKET_CODE_5)){
			ticketId = TICKET_SQL_ID_5;
		}
		return ticketId;
	}
}
