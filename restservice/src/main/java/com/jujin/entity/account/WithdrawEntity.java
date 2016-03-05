package com.jujin.entity.account;

/*
 * 提現
 * */
public class WithdrawEntity {

	private String nickName;
	private String money;

	private String cardCode;

	private String bankName;

	private String cashPwd;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCashPwd() {
		return cashPwd;
	}

	public void setCashPwd(String cashPwd) {
		this.cashPwd = cashPwd;
	}

	// private String

	/*
	 * @RequestMapping(value = "/default", method = RequestMethod.GET) public
	 * @ResponseBody Object getAccountCenter(HttpServletRequest request) {
	 * 
	 * if (request == null) { System.out.println("request is null"); } else {
	 * System.out.println("request is not null"); }
	 * com.jujin.authorize.AppSession as = getAppSession(request);
	 * 
	 * if (as != null) { System.out.println("AppSession is not null"); String
	 * userId = as.getStringValue(SystemConfig.USER_ID); return
	 * getAccountCenter(userId); } else {
	 * System.out.println("request is not null"); } return new
	 * OpEntityResult<String>("Get UserName failed");
	 * 
	 * }
	 */
}
