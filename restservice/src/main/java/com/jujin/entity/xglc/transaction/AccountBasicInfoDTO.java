package com.jujin.entity.xglc.transaction;
/**
 * Title: AccountBasicInfoDTO
 * Description: 账户基本信息（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class AccountBasicInfoDTO {

	/**
	 * 账户余额
	 */
	private Double accountBalance;
	/**
	 * 是否已实名认证
	 */
	private boolean isCertified;
	/**
	 * 是否已绑定手机
	 */
	private boolean hasBindingMobile;
	/**
	 * 是否已设定交易密码
	 */
	private boolean hasSetTradePwd;
	/**
	 * 是否已绑定卡
	 */
	private boolean hasBindingDebitCard;
	
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public boolean isCertified() {
		return isCertified;
	}
	public void setCertified(boolean isCertified) {
		this.isCertified = isCertified;
	}
	public boolean isHasBindingMobile() {
		return hasBindingMobile;
	}
	public void setHasBindingMobile(boolean hasBindingMobile) {
		this.hasBindingMobile = hasBindingMobile;
	}
	public boolean isHasSetTradePwd() {
		return hasSetTradePwd;
	}
	public void setHasSetTradePwd(boolean hasSetTradePwd) {
		this.hasSetTradePwd = hasSetTradePwd;
	}
	public boolean isHasBindingDebitCard() {
		return hasBindingDebitCard;
	}
	public void setHasBindingDebitCard(boolean hasBindingDebitCard) {
		this.hasBindingDebitCard = hasBindingDebitCard;
	}
	
	
}
