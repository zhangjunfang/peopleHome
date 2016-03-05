package com.jujin.entity.xglc.transaction;
/**
 * Title: VirtualCaptial
 * Description: 体验金（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class VirtualCaptial {
	/**
	 * 体验金ID
	 */
	private String virtualCaptialId;
	/**
	 * 体验金金额
	 */
	private double virtualCaptialValue;
	/**
	 * 体验金到期日期(日期格式，yyyy-mm-dd hh:MM:ss)
	 */
	private String virtualCaptialExpireDate;
	/**
	 * 体验金使用规则描述
	 */
	private String virtualCaptialRule;
	
	public String getVirtualCaptialId() {
		return virtualCaptialId;
	}
	public void setVirtualCaptialId(String virtualCaptialId) {
		this.virtualCaptialId = virtualCaptialId;
	}
	public double getVirtualCaptialValue() {
		return virtualCaptialValue;
	}
	public void setVirtualCaptialValue(double virtualCaptialValue) {
		this.virtualCaptialValue = virtualCaptialValue;
	}
	public String getVirtualCaptialExpireDate() {
		return virtualCaptialExpireDate;
	}
	public void setVirtualCaptialExpireDate(String virtualCaptialExpireDate) {
		this.virtualCaptialExpireDate = virtualCaptialExpireDate;
	}
	public String getVirtualCaptialRule() {
		return virtualCaptialRule;
	}
	public void setVirtualCaptialRule(String virtualCaptialRule) {
		this.virtualCaptialRule = virtualCaptialRule;
	}
	
	
}
