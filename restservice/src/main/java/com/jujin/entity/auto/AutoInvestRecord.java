package com.jujin.entity.auto;



/**
 * 自动投标设置
 * */
public class AutoInvestRecord {
	
	/**借款人**/
	private String borrowUser;
	/**状态**/
	private String status;
	/**标题**/
	private String title;
	/**利率**/
	private String rate;
	/**投资周期**/
	private String period;
	/**投资额**/
	private String amount;
	
	private String borrowId;
	
	
	public String getBorrowUser() {
		return borrowUser;
	}
	public void setBorrowUser(String borrowUser) {
		this.borrowUser = borrowUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	} 
	
	
}
