package com.jujin.entity.coin;

import java.util.Date;

/**
 * Title: TccCoinConfigBean 
 * Description:红包键值配置表对应的实体类 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年5月29日
 */
public class TccCoinConfigBean {

	private int recordId;// 编号
	private String key;// 键
	private String memo;// 描述
	private String groupId;// 组别
	private String value;// 值
	private int type;// 类型:0:红包金额;1:投资额对应红包数量;2:投资额对应红包金额;3:折让金比例
	private Date insDate;// 日期
	private double pecentValue;//红包金额对应的百分比

	public TccCoinConfigBean(){}
	
	public TccCoinConfigBean(String key,String memo,String groupId,String value,int type,Date insDate,double pecentValue)
	{
		this.key = key;
		this.memo = memo;
		this.groupId = groupId;
		this.value = value;
		this.type = type;
		this.insDate = insDate;
		this.pecentValue = pecentValue;
	}
	
	public double getPecentValue() {
		return pecentValue;
	}

	public void setPecentValue(double pecentValue) {
		this.pecentValue = pecentValue;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String toString() {
		return "编号:" + recordId + ",组别:" + groupId + ",描述:" + memo + ",键:"
				+ key + ",值:" + value + ",类型:" + type + ",日期:" + insDate+",红包金额对应的百分比:"+pecentValue;
	}
}
