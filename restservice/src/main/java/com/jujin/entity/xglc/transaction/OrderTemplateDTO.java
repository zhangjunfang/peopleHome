package com.jujin.entity.xglc.transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Title: OrderTemplateDTO
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月27日
 */
public class OrderTemplateDTO implements Serializable{

	/** serialVersionUID*/
	private static final long serialVersionUID = 467466810714214107L;

	/**
	 * 用户持有债权数量
	 */
	private int orderCount;
	
	private int totalCount;
	
	private List<OrderInfoDTO> orderList = new ArrayList<OrderInfoDTO>();
	
	
	
	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public List<OrderInfoDTO> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderInfoDTO> orderList) {
		this.orderList = orderList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
}
