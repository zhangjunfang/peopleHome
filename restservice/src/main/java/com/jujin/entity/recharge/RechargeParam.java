package com.jujin.entity.recharge;

/**
 * @author wangn
 * 
 * 客户端向服务端提交的数据
 *
 */
public class RechargeParam {
	
	
	private String  bankId;
	
	private int cardId;
	
	/**为1表示移动端**/
	private int type;
	
	private String rechargeAmount;
	
	private String rechargeOrderId;
	
	private String rechargeContinue;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getRechargeOrderId() {
		return rechargeOrderId;
	}

	public void setRechargeOrderId(String rechargeOrderId) {
		this.rechargeOrderId = rechargeOrderId;
	}

	public String getRechargeContinue() {
		return rechargeContinue;
	}

	public void setRechargeContinue(String rechargeContinue) {
		this.rechargeContinue = rechargeContinue;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	} 
	
	
	
	

}
