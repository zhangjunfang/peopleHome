package com.jujin.entity.duozhuan;

import java.io.Serializable;
import java.util.List;

/**
 * Title: BorrowList
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年12月4日
 */
public class BorrowList implements Serializable{
	/** serialVersionUID*/
	private static final long serialVersionUID = 3242412182381184914L;

	private long totalPage;
	
	private long currentPage;
	
	private long totalCount;
	
	private List<String> borrowList;

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<String> getBorrowList() {
		return borrowList;
	}

	public void setBorrowList(List<String> borrowList) {
		this.borrowList = borrowList;
	}
	
	
}
