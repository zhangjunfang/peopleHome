package com.jujin.entity.xglc.transaction;
/**
 * Title: RaiseRatesTicket
 * Description: 加息券-对应平台的聚金券（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class RaiseRatesTicket {
	/**
	 * 加息券ID
	 */
	private String raiseRatesTicketId;
	/**
	 * 加息券金额
	 */
	private double raiseRatesTicketValue;
	/**
	 * 加息券到期日期(日期格式，yyyy-mm-dd hh:MM:ss)
	 */
	private String raiseRatesTicketExpireDate;
	/**
	 * 加息券使用规则描述
	 */
	private String raiseRatesTicketRule;
	
	public String getRaiseRatesTicketId() {
		return raiseRatesTicketId;
	}
	public void setRaiseRatesTicketId(String raiseRatesTicketId) {
		this.raiseRatesTicketId = raiseRatesTicketId;
	}
	public double getRaiseRatesTicketValue() {
		return raiseRatesTicketValue;
	}
	public void setRaiseRatesTicketValue(double raiseRatesTicketValue) {
		this.raiseRatesTicketValue = raiseRatesTicketValue;
	}
	public String getRaiseRatesTicketExpireDate() {
		return raiseRatesTicketExpireDate;
	}
	public void setRaiseRatesTicketExpireDate(String raiseRatesTicketExpireDate) {
		this.raiseRatesTicketExpireDate = raiseRatesTicketExpireDate;
	}
	public String getRaiseRatesTicketRule() {
		return raiseRatesTicketRule;
	}
	public void setRaiseRatesTicketRule(String raiseRatesTicketRule) {
		this.raiseRatesTicketRule = raiseRatesTicketRule;
	}
	
	
	
}
