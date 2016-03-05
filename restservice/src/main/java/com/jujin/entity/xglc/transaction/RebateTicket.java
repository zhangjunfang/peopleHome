package com.jujin.entity.xglc.transaction;
/**
 * Title: RebateTicket
 * Description: 红包-对应平台的聚金币（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class RebateTicket {
	
	/**
	 * 红包ID
	 */
	private String rebateTicketId;
	/**
	 * 红包金额
	 */
	private double rebateTicketValue;
	/**
	 * 红包到期日期(日期格式，yyyy-mm-dd hh:MM:ss)
	 */
	private String rebateTicketExpireDate;
	/**
	 * 红包使用规则描述
	 */
	private String rebateTicketRule;
	
	public String getRebateTicketId() {
		return rebateTicketId;
	}
	public void setRebateTicketId(String rebateTicketId) {
		this.rebateTicketId = rebateTicketId;
	}
	public double getRebateTicketValue() {
		return rebateTicketValue;
	}
	public void setRebateTicketValue(double rebateTicketValue) {
		this.rebateTicketValue = rebateTicketValue;
	}
	public String getRebateTicketExpireDate() {
		return rebateTicketExpireDate;
	}
	public void setRebateTicketExpireDate(String rebateTicketExpireDate) {
		this.rebateTicketExpireDate = rebateTicketExpireDate;
	}
	public String getRebateTicketRule() {
		return rebateTicketRule;
	}
	public void setRebateTicketRule(String rebateTicketRule) {
		this.rebateTicketRule = rebateTicketRule;
	}
	
	
}
