package com.jujin.entity.borrow;


/*投資實體*/
public class TenderBean {
	
	private String  userId;
	
	private String borrowId;
	
	private String tenderAccount;
	
	private String directionalPWD;
	
	private String isXglc;
	
	private double coin;
	
	/**
	 * 加息券ID
	 */
	private String ticketId;
	

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

	public String getTenderAccount() {
		return tenderAccount;
	}
	
	

	public void setTenderAccount(String tenderAccount) {
		this.tenderAccount = tenderAccount;
	}

	public String getDirectionalPWD() {
		return directionalPWD;
	}

	public void setDirectionalPWD(String directionalPWD) {
		this.directionalPWD = directionalPWD;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public double getCoin() {
		return coin;
	}

	public void setCoin(double coin) {
		this.coin = coin;
	}

	public String getIsXglc() {
		return isXglc;
	}

	public void setIsXglc(String isXglc) {
		this.isXglc = isXglc;
	}
	
	
	
	
	
	
	
	

}
