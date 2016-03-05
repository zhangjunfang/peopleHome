package com.jujin.entity.duozhuan;

import java.io.Serializable;
import java.util.List;

/**
 * Title: BorrowInfo
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年12月4日
 */
public class DzBorrowInfo implements Serializable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 8669627768387565446L;

	private String projectId;
	
	private String title;
	
	private String loanUrl;
	
	private String userName;
	
	private double amount;
	
	private String schedule;
	
	private String interestRate;
	
	private int deadline;
	
	private String deadlineUnit;
	
	private double reward;
	
	private String type;
	
	private String repaymentType;
	
	private String warrantcom;
	
	private List<Subscribes> subscribesList;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLoanUrl() {
		return loanUrl;
	}

	public void setLoanUrl(String loanUrl) {
		this.loanUrl = loanUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public String getDeadlineUnit() {
		return deadlineUnit;
	}

	public void setDeadlineUnit(String deadlineUnit) {
		this.deadlineUnit = deadlineUnit;
	}

	public double getReward() {
		return reward;
	}

	public void setReward(double reward) {
		this.reward = reward;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRepaymentType() {
		return repaymentType;
	}

	public void setRepaymentType(String repaymentType) {
		this.repaymentType = repaymentType;
	}

	public String getWarrantcom() {
		return warrantcom;
	}

	public void setWarrantcom(String warrantcom) {
		this.warrantcom = warrantcom;
	}

	public List<Subscribes> getSubscribesList() {
		return subscribesList;
	}

	public void setSubscribesList(List<Subscribes> subscribesList) {
		this.subscribesList = subscribesList;
	}
	
	
}
