package com.jujin.entity.xglc.transaction;

import java.util.List;

/**
 * Title: AccountBenefitInfoDTO
 * Description: 账户权益（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class AccountBenefitInfoDTO {

	/**
	 * 体验金数量
	 */
	private int virtualCaptialCount;

	/**
	 * 体验金列表
	 */
	private List<VirtualCaptial> vcList;
	
	/**
	 * 红包数量
	 */
	private int rebateTicketCount;
	
	/**
	 * 红包列表
	 */
	private List<RebateTicket> rtList;
	
	/**
	 * 加息券数量
	 */
	private int raiseRatesTicketCount;
	
	/**
	 * 加息券列表
	 */
	private List<RaiseRatesTicket> rrtList;
	
	
	public AccountBenefitInfoDTO(List<VirtualCaptial> vcList,List<RebateTicket> rtList,List<RaiseRatesTicket> rrtList){
		this.virtualCaptialCount = vcList.size();
		this.vcList = vcList;
		this.raiseRatesTicketCount = rrtList.size();
		this.rrtList = rrtList;
		this.rebateTicketCount = rtList.size();
		this.rtList = rtList;
	}
	

	public int getVirtualCaptialCount() {
		return virtualCaptialCount;
	}

	public void setVirtualCaptialCount(int virtualCaptialCount) {
		this.virtualCaptialCount = virtualCaptialCount;
	}

	public List<VirtualCaptial> getVcList() {
		return vcList;
	}

	public void setVcList(List<VirtualCaptial> vcList) {
		this.vcList = vcList;
	}

	public int getRebateTicketCount() {
		return rebateTicketCount;
	}

	public void setRebateTicketCount(int rebateTicketCount) {
		this.rebateTicketCount = rebateTicketCount;
	}

	public List<RebateTicket> getRtList() {
		return rtList;
	}

	public void setRtList(List<RebateTicket> rtList) {
		this.rtList = rtList;
	}

	public int getRaiseRatesTicketCount() {
		return raiseRatesTicketCount;
	}

	public void setRaiseRatesTicketCount(int raiseRatesTicketCount) {
		this.raiseRatesTicketCount = raiseRatesTicketCount;
	}

	public List<RaiseRatesTicket> getRrtList() {
		return rrtList;
	}

	public void setRrtList(List<RaiseRatesTicket> rrtList) {
		this.rrtList = rrtList;
	}
	
}
