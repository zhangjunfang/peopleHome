package com.jujin.entity.luckDraw;
/**
 * Title: BorrowRecord
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月9日
 */
public class BorrowRecord {
	
	private String userId;
	
	private String borrowId;
	
	private String borrowTitle;
	
	private String amount;
	
	private String tdate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	public String getBorrowTitle() {
		return borrowTitle;
	}
	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	
}
