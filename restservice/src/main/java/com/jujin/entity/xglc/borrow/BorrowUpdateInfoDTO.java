package com.jujin.entity.xglc.borrow;
/**
 * Title: BorrowUpdateInfoDTO
 * Description: 产品的当前更新信息（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class BorrowUpdateInfoDTO {
	/**
	 * 产品的唯一编号
	 */
	private String productCode;
	/**
	 * 产品状态(预售/在售/审核中/还款中/取消/还款完成)
	 */
	private String onlineState;
	/**
	 * 募集进度(小数格式,0.1代表10%,标满为1.0)
	 */
	private double scale;
	/**
	 * 状态变更时间(yyyy-MM-dd HH:mm:ss)
	 */
	private String statusChangeDate;
	/**
	 * 投资人次(没有为0)
	 */
	private int investTimes;
	/**
	 * 产品URL(产品访问链接,手机端页面)
	 */
	private String productURL;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOnlineState() {
		return onlineState;
	}
	public void setOnlineState(String onlineState) {
		this.onlineState = onlineState;
	}
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	public String getStatusChangeDate() {
		return statusChangeDate;
	}
	public void setStatusChangeDate(String statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	public int getInvestTimes() {
		return investTimes;
	}
	public void setInvestTimes(int investTimes) {
		this.investTimes = investTimes;
	}
	public String getProductURL() {
		return productURL;
	}
	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}
	
	

}
