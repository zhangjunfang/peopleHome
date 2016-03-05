package com.jujin.entity.coin;

import java.util.Date;

/**
 * Title: TccActivityBean 
 * Description: 活动实体类
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年5月28日
 */
public class TccActivityBean {

	private int recordId;//活动编号
	private String name;//活动名称
	private String memo;//描述
	private int enable;//是否有效   0：强制失效   1：有效
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private String updUserId;//管理员id
	private Date insDate;//日期
	private Date coinEndTime;//抢红包结束时间

	public Date getCoinEndTime() {
		return coinEndTime;
	}

	public void setCoinEndTime(Date coinEndTime) {
		this.coinEndTime = coinEndTime;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getName() {
		return name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getUpdUserId() {
		return updUserId;
	}

	public void setUpdUserId(String updUserId) {
		this.updUserId = updUserId;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}
	
	public String toString()
	{
		return  "活动编号:"+recordId+",活动名称:"+name+",描述:"+memo+",是否有效:"+enable+",开始时间："+beginTime+",结束时间:"+endTime+",管理员id:"+updUserId+",日期:"+insDate;
	}

}
