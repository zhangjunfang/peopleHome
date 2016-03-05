package com.jujin.entity.xglc.borrow;


/**
 * Title: Borrow
 * Description: 平台在售的理财产品（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class BorrowInfoDTO {

	/**
	 * 平台名字
	 */
	private String platformName;
	/**
	 * 系列名称(聚金优选/散标/聚金宝)
	 */
	private String creditSeriesName;
	/**
	 * 系列ID(没有为空字符串)
	 */
	private String creditSeriesId = "";
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 产品的唯一编号
	 */
	private String productCode;
	/**
	 * 募集金额(单位为元)
	 */
	private int totalInvestment;
	/**
	 * 产品收益(小数格式,0.1代表10%)
	 */
	private double annualRevenueRate;
	/**
	 * 周期类型(天或者月)
	 */
	private String loanLifeType;
	/**
	 * 月份值或者天数值 (如果同时有月份和天的值，全部转换成天)
	 */
	private int loanLifePeriod;
	/**
	 * 产品的付息方式
	 */
	private String interestPaymentType;
	/**
	 * 担保类型(0-无担保;1-风险准备金;2-第三方担保)
	 */
	private int guaranteeType;
	/**
	 * 担保方(如果上面的担保方式为2-第三方担保，则填写担保公司的名称，否则填空字符串)
	 */
	private String guaranteeInsitutions = "";
	/**
	 * 产品状态(预售/在售/审核中/还款中/取消/还款完成)
	 */
	private String onlineState;
	/**
	 * 募集进度(小数格式,0.1代表10%,标满为1.0)
	 */
	private double scale;
	/**
	 * 开始募资时间(yyyy-MM-dd HH:mm:ss)
	 */
	private String publishDate;
	/**
	 * 成立时间(标满时间,yyyy-MM-dd HH:mm:ss,没有填空字符串)
	 */
	private String establishmentDate = "";
	/**
	 * 产品到期日,只针对是固定还款日的产品（产品起息日+产品周期,yyyy-MM-dd不是固定还款日的产品填空字符串）
	 */
	private String expireDate = "";
	/**
	 * 固定还款日(0-31,没固定还款日的为0)
	 */
	private int fixedRepaymentDate = 0;
	/**
	 * 是否是购买当日起息(0-不是;1-是)
	 */
	private int isValueDay;
	/**
	 * 奖励收益(小数格式0.1代表10%,没有奖励为0.0)
	 */
	private double rewardRate = 0.0;
	/**
	 * 投资人次(没有为0)
	 */
	private int investTimes = 0;
	/**
	 * 产品URL(产品访问链接,手机端页面)
	 */
	private String productURL;
	/**
	 * 是否为新手标
	 */
	private boolean isFirstBuy;
	
	
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getCreditSeriesName() {
		return creditSeriesName;
	}
	public void setCreditSeriesName(String creditSeriesName) {
		this.creditSeriesName = creditSeriesName;
	}
	public String getCreditSeriesId() {
		return creditSeriesId;
	}
	public void setCreditSeriesId(String creditSeriesId) {
		this.creditSeriesId = creditSeriesId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getTotalInvestment() {
		return totalInvestment;
	}
	public void setTotalInvestment(int totalInvestment) {
		this.totalInvestment = totalInvestment;
	}
	public double getAnnualRevenueRate() {
		return annualRevenueRate;
	}
	public void setAnnualRevenueRate(double annualRevenueRate) {
		this.annualRevenueRate = annualRevenueRate;
	}
	public String getLoanLifeType() {
		return loanLifeType;
	}
	public void setLoanLifeType(String loanLifeType) {
		this.loanLifeType = loanLifeType;
	}
	public int getLoanLifePeriod() {
		return loanLifePeriod;
	}
	public void setLoanLifePeriod(int loanLifePeriod) {
		this.loanLifePeriod = loanLifePeriod;
	}
	public String getInterestPaymentType() {
		return interestPaymentType;
	}
	public void setInterestPaymentType(String interestPaymentType) {
		this.interestPaymentType = interestPaymentType;
	}
	public int getGuaranteeType() {
		return guaranteeType;
	}
	public void setGuaranteeType(int guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	public String getGuaranteeInsitutions() {
		return guaranteeInsitutions;
	}
	public void setGuaranteeInsitutions(String guaranteeInsitutions) {
		this.guaranteeInsitutions = guaranteeInsitutions;
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
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getEstablishmentDate() {
		return establishmentDate;
	}
	public void setEstablishmentDate(String establishmentDate) {
		this.establishmentDate = establishmentDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public int getFixedRepaymentDate() {
		return fixedRepaymentDate;
	}
	public void setFixedRepaymentDate(int fixedRepaymentDate) {
		this.fixedRepaymentDate = fixedRepaymentDate;
	}
	public int getIsValueDay() {
		return isValueDay;
	}
	public void setIsValueDay(int isValueDay) {
		this.isValueDay = isValueDay;
	}
	public double getRewardRate() {
		return rewardRate;
	}
	public void setRewardRate(double rewardRate) {
		this.rewardRate = rewardRate;
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
	public boolean getIsFirstBuy() {
		return isFirstBuy;
	}
	public void setIsFirstBuy(boolean isFirstBuy) {
		this.isFirstBuy = isFirstBuy;
	}
	
	
	
}
