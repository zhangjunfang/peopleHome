/**   
* @author wangning
* @date 2015年2月10日 上午11:59:04 
* @version V1.0   
* @Description: TODO
*/
package com.jujin.entity.borrow;

import java.util.Date;
import java.util.List;

import com.jujin.entity.User;
import com.jujin.utils.Toolkit;

/**
 * 标详情
 */
public class BorrowInfo extends Borrow {
	
	
	/*投标数*/
	private int tenderCount;
	
	/*浏览次数*/
	private int browseCount;
	
	/*最小的投资额*/
	private String  minTenderAccount;
	
	/*最大的投资额*/
	private String  maxTenderAccount;
	
	/*融资人*/
	private UserIncomeInfo borrowUser;
	
	/*区域*/
	private String location;
	
	/*借款用途*/
	private String purpose;
	
	/*授信额度*/
	private String cerditLimit;
	
	/*下次还款时间*/
	private String payTime;
	
	/*描述*/
	private String description;
	
	/*概况图片展示*/
	private String imageShow;
	
	private  RepayCredit credit;
 
	
	/*借款资料显示*/
	private List<BorrowInfoItem> showItems;
	
	

	/**
	 * @return the tenderCount
	 */
	public String getSystemTime() {
		return Toolkit.FormatDate(new Date());
	}
	


	/**
	 * @return the tenderCount
	 */
	public int getTenderCount() {
		return tenderCount;
	}

	/**
	 * @param tenderCount the tenderCount to set
	 */
	public void setTenderCount(int tenderCount) {
		this.tenderCount = tenderCount;
	}

	/**
	 * @return the browseCount
	 */
	public int getBrowseCount() {
		return browseCount;
	}

	/**
	 * @param browseCount the browseCount to set
	 */
	public void setBrowseCount(int browseCount) {
		this.browseCount = browseCount;
	}

	/**
	 * @return the minTenderAccount
	 */
	public String getMinTenderAccount() {
		return minTenderAccount;
	}

	/**
	 * @param minTenderAccount the minTenderAccount to set
	 */
	public void setMinTenderAccount(String minTenderAccount) {
		this.minTenderAccount = minTenderAccount;
	}

	/**
	 * @return the maxTenderAccount
	 */
	public String getMaxTenderAccount() {
		return maxTenderAccount;
	}

	/**
	 * @param maxTenderAccount the maxTenderAccount to set
	 */
	public void setMaxTenderAccount(String maxTenderAccount) {
		this.maxTenderAccount = maxTenderAccount;
	}

	/**
	 * @return the borrowUser
	 */
	public UserIncomeInfo getBorrowUser() {
		return borrowUser;
	}

	/**
	 * @param borrowUser the borrowUser to set
	 */
	public void setBorrowUser(UserIncomeInfo borrowUser) {
		this.borrowUser = borrowUser;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}

	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * @return the cerditLimit
	 */
	public String getCerditLimit() {
		return cerditLimit;
	}

	/**
	 * @param cerditLimit the cerditLimit to set
	 */
	public void setCerditLimit(String cerditLimit) {
		this.cerditLimit = cerditLimit;
	}

	/**
	 * @return the payTime
	 */
	public String getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the imageShow
	 */
	public String getImageShow() {
		return imageShow;
	}

	/**
	 * @param imageShow the imageShow to set
	 */
	public void setImageShow(String imageShow) {
		this.imageShow = imageShow;
	}

	/**
	 * @return the showItems
	 */
	public List<BorrowInfoItem> getShowItems() {
		return showItems;
	}

	/**
	 * @param showItems the showItems to set
	 */
	public void setShowItems(List<BorrowInfoItem> showItems) {
		this.showItems = showItems;
	}
	
	

	/**
	 * @return the credit
	 */
	public RepayCredit getCredit() {
		return credit;
	}

	/**
	 * @param credit the credit to set
	 */
	public void setCredit(RepayCredit credit) {
		this.credit = credit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return  super.toString()+"	BorrowInfo [tenderCount=" + tenderCount + ", browseCount="
				+ browseCount + ", minTenderAccount=" + minTenderAccount
				+ ", maxTenderAccount=" + maxTenderAccount + ", borrowUser="
				+ borrowUser + ", location=" + location + ", purpose="
				+ purpose + ", cerditLimit=" + cerditLimit + ", payTime="
				+ payTime + ", description=" + description + ", imageShow="
				+ imageShow + ", showItems=" + showItems + "]";
	}
	
	
}
