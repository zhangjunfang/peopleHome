/**   
 * @author wangning
 * @date 2015年2月12日 上午8:36:55 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.entity.account;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.jujin.entity.UserNickName;

/**
 * 
 */
public class RecoveLog {

	/* 标ID */
	private String borrowId;

	private String borowTitle;

	/* 借款者 */
	private UserNickName borrower;
	
	private Stack<RecoveRecord> records;
	
	private String  addRate;
	

	
	public  RecoveLog()
	{
		records=new Stack<RecoveRecord>();
	}
	
	/**
	 * @return the borrowId
	 */
	public String getBorrowId() {
		return borrowId;
	}

	/**
	 * @param borrowId
	 *            the borrowId to set
	 */
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	/**
	 * @return the borrower
	 */
	public UserNickName getBorrower() {
		return borrower;
	}

	/**
	 * @param borrower
	 *            the borrower to set
	 */
	public void setBorrower(UserNickName borrower) {
		this.borrower = borrower;
	}

	/**
	 * @return the boorowTitle
	 */
	public String getBorowTitle() {
		return borowTitle;
	}

	/**
	 * @param boorowTitle
	 *            the boorowTitle to set
	 */
	public void setBorowTitle(String boorowTitle) {
		this.borowTitle = boorowTitle;
	}
	
	public void addRecord(RecoveLogItem item)
	{
		if(item==null)
		{
			return;
		}
		RecoveRecord entity=new RecoveRecord();
		entity.setAwait(item.getAwait());
		entity.setCurrent(item.getCurrent());
		entity.setDate(item.getDate());
		entity.setTotal(item.getTotal());
		entity.setType(item.getType());
		records.push(entity);
	}
	
	
	
	
	public String getAddRate() {
		return addRate;
	}

	public void setAddRate(String addRate) {
		this.addRate = addRate;
	}

	public List<RecoveRecord> getRecords()
	{
		return records;
	}

}
