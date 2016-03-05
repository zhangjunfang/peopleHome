package com.jujin.entity.xglc.borrow;

import java.util.Date;
import java.util.List;

import com.jujin.util.xglc.CommonUtil;

/**
 * Title: DataTemplateDTO
 * Description: 数据传输容器（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class DataTemplateDTO {
	/**
	 *  dataList的长度
	 */
	private int recordCount;
	/**
	 * 对接方的中文简称
	 */
	private String apiCorp;
	/**
	 * 数据传输时间"
	 */
	private String transferTime;
	/**
	 * 数据数组
	 */
	private List<?> dataList;
	
	public DataTemplateDTO(List<?> dataList){
		this.apiCorp = "聚金资本";
		this.recordCount = dataList.size();
		this.transferTime = CommonUtil.dateToString(new Date());
		this.dataList = dataList;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public String getApiCorp() {
		return apiCorp;
	}
	public void setApiCorp(String apiCorp) {
		this.apiCorp = apiCorp;
	}
	public String getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}
	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	
}
