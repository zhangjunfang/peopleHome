package com.jujin.entity.ticket;

import com.jujin.controller.UserController;

/**
 * 
 * 
 * @author wangning
 * 
 *         聚金券
 * 
 */
public class Ticket {

	// 用户名
	private String userId;

	private int totalCount;

	private int useCount;

	private String ticketId;
	/**
	 * 生效时间
	 */
	private String startTime;

	/**
	 * 过期时间
	 */
	private String endTime;

	/**
	 * 加息券名称
	 */
	private String name;

	/**
	 * 加息券面值(如：4，呈现的时候需要加%)
	 */
	private double ticketValue;

	private int allowUseCount;

	private int useCoddtions;

	/**
	 * 来源
	 */
	private String source;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int userCount) {
		this.useCount = userCount;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTicketValue() {
		return ticketValue;
	}

	public void setTicketValue(double ticketValue) {
		this.ticketValue = ticketValue;
	}

	public int getAllowUseCount() {
		return allowUseCount;
	}

	public void setAllowUseCount(int allowUseCount) {
		this.allowUseCount = allowUseCount;
	}

	public int getUseCoddtions() {
		return useCoddtions;
	}

	public void setUseCoddtions(int useCoddtions) {
		this.useCoddtions = useCoddtions;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getTypeCount(int type) {

		if (type == 2) {
			return useCount;
		} else {
			return totalCount - useCount;
		}
	}

	/**
	 * 浅克隆
	 * 
	 * @return
	 */
	public Ticket Clone() {
		Ticket ticket = new Ticket();
		ticket.setUserId(this.getUserId());
		ticket.setTotalCount(this.getTotalCount());
		ticket.setUseCount(this.getUseCount());
		ticket.setStartTime(this.getStartTime());
		ticket.setEndTime(this.getEndTime());
		ticket.setName(this.getName());
		ticket.setTicketValue(this.getTicketValue());
		ticket.setAllowUseCount(this.getAllowUseCount());
		ticket.setUseCoddtions(this.getUseCoddtions());
		ticket.setSource(this.getSource());
		ticket.setTicketId(this.getTicketId());

		return ticket;
	}
}
