/**   
 * @author wangning
 * @date 2015年2月10日 上午11:01:38 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.borrow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.util.StringUtils;

import com.cn.wicket.common.common.util.Utils;
import com.jujin.common.JsonList;
import com.sun.org.apache.commons.beanutils.ConvertUtils;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;

/**
 * 标详情(Web展示的时候不是用的模型,对移动端呈现不够方便,需要重新构造出模型)
 * 
 * 允许设置多个奖励类型
 */
public class Borrow {

	/* 标题 */
	private String title;

	private static HashMap<Integer, String> statusMap = new HashMap<Integer, String>();

	private List<KeyValuePair> privileges;

	/** 标的风控信息 **/
	private BorrowAttestion attestion;

	/** 下次还款信息 **/
	private String nextRecoverTime;

	private List<BorrowRepay> repays;

	public Borrow() {
		privileges = new ArrayList<KeyValuePair>();
	}

	static {
		// 表的进度 0:筹款中 1: 复审中 2:还款中 3: 已还完 4:流标 5: 撤回 6:初审中; 7:初审失败;8:筹款中
		// ;9:复审中;10:复审失败;11:已还完;
		statusMap.put(1, "复审中");
		statusMap.put(2, "还款中");
		statusMap.put(3, "已还完");
		statusMap.put(4, "流标");
		statusMap.put(5, "撤回");
		statusMap.put(6, "初审中");
		statusMap.put(7, "初审失败");
		statusMap.put(8, "筹款中");
		statusMap.put(9, "复审失败");
	}
	/* 标类型0:全部标型;1:信用标;2:抵押标;3:净值标;4:担保标;5:秒还标(即满即还);6:流转标;7:聚金U选 */
	private int type;

	/* 是否为新手标:1:新手标;0:非新手标 */
	private int isNew;

	/* 是否奖励0:不奖励 */
	private double awardStatus;

	/* 信用等级 */
	private String creditLevel;

	/*
	 * if ("0".equals(row.getStringValue("AWARD_STATUS"))) {
	 * ctnAward.setVisible(false); } else { if
	 * (!StringUtils.isEmpty(row.getStringValue("AWARD_SCALE"))) { // 按比例奖励
	 * ctnAward.add(AttributeModifier.replace("title",
	 * "按照本金的"+row.getStringValue("AWARD_SCALE") + "%奖励"));
	 * lblAward.setValue("("+row.getStringValue("AWARD_SCALE") + "%)"); } else {
	 * // 按金额奖励 lblAward.setValue("(￥" +
	 * NumberUtils.moneyFormat(row.getStringValue("AWARD_ACCOUNT"))+")"); } }
	 */
	private double awardScale;

	/* 按金额奖励 */
	private double awardAccount;

	/* 奖励类型 */
	private int awardType;

	private int awardScale1;

	/* 按金额奖励 */
	private double awardAccount1;

	/* 奖励类型 */
	private int awardType1;

	private int awardScale2;

	/* 按金额奖励 */
	private double awardAccount2;

	/* 奖励类型 */
	private int awardType2;

	private int awardScale3;

	/* 按金额奖励 */
	private double awardAccount3;

	/* 奖励类型 */
	private int awardType3;

	/* 是否为天标 */
	private int isDay;

	/* 周期 */
	private String period;

	/* 进度 */
	private double accountScale;

	/* 标ID */
	private String borrowId;

	/* 利息 */
	private String borrowRate;

	/* 借款金额 */
	private double borrowAccount;

	/* 状态 */
	private int borrowStatus;

	/* 偿还方式1:等额本金;2:等额本息;3:到期还本还息;4:按月还息，到期还本 */
	private int repayType;

	private int repayTypeMemo;

	/* 当前金额 */
	private double currentAccount;

	/* 显示金额:如将900000显示为90万 */
	private String accountLabel;

	/* 当前金额显示:如将53300显示为 53,300 */
	private String currentAccountLabel;

	/* 投标开始时间 */
	private String beginTime;

	/* 投标结束时间 */
	private String endTime;

	/* 持续时间 */
	private String duration;

	private String borrowUserId;
	/* 表的进度 0:借款中 1: 审核中 2:还款中 3: 还款完了 4:流标 撤回 */
	private int status;

	/* 待还款的金额 */
	private double repayAccountWait;

	/* 是否取消 */
	private int cancelStatus;
	/* 是否失败 */
	private int failedFlg;

	/* 初审标志 */
	private String verifyFlg;
	/* 复审标志 */
	private String reverifyFlg;

	/* 尚需借款 */
	private String borrowAccountWait;

	/* 定向密码 */
	private String directionalPwd;

	/* 红包抵用额度 */
	private String vouchersRate;

	/**
	 * 聚金优选的标是否显示
	 */
	private String uplanBorrowsIsShow;// UPLAN_BORROWS_IS_SHOW

	private String borrowFlag;

	/** 折让比 **/
	private String discountRate;

	/** 折让价值 **/
	private String discountAmount;

	/** 原始债权 **/
	private String originalBorrowAccount;

	/** 原始期限 **/
	private String originalBorrowPeriod;

	/** 原始borrowId **/
	private String originalBorrowId;

	/** 原始borrowTitle **/
	private String originalBorrowTitle;

	/** 是否为债权转让 **/
	private String transferFlg;

	/**
	 * @return the title
	 */
	// 咱们的聚金U选产品分为1月，3月，6月，12月期四个子产品，四个子产品名字初步定为：优选月月赚，优选季丰盈，优选半年红，优选年年余，大家有什么好的建议可以提一下
	public String getTitle() {
		String tmp = "";
		if (this.getType() == 7) {
			if (!StringUtils.isEmpty(borrowFlag)) {
				switch (borrowFlag) {
				case "A":
					tmp = "月月赚";
					break;
				case "B":
					tmp = "季丰盈";
					break;
				case "C":
					tmp = "半年红";
					break;
				case "D":
					tmp = "年年余";
					break;
				default:
					break;
				}
			}
		}
		return tmp + title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the isNew
	 */
	public int getIsNew() {
		return isNew;
	}

	/**
	 * @param isNew
	 *            the isNew to set
	 */
	public void setIsNew(int isNew) {
		this.isNew = isNew;
	}

	/**
	 * @return the awardStatus
	 */
	public double getAwardStatus() {
		return awardStatus;
	}

	/**
	 * @param awardStatus
	 *            the awardStatus to set
	 */
	public void setAwardStatus(double awardStatus) {
		this.awardStatus = awardStatus;
	}

	/**
	 * @return the creditLevel
	 */
	public String getCreditLevel() {
		return creditLevel;
	}

	/**
	 * @param creditLevel
	 *            the creditLevel to set
	 */
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	/**
	 * @return the awardScale
	 */
	public double getAwardScale() {
		return awardScale;
	}

	/**
	 * @param awardScale
	 *            the awardScale to set
	 */
	public void setAwardScale(double awardScale) {
		this.awardScale = awardScale;
	}

	/**
	 * @return the awardAccount
	 */
	public double getAwardAccount() {
		return awardAccount;
	}

	/**
	 * @param awardAccount
	 *            the awardAccount to set
	 */
	public void setAwardAccount(double awardAccount) {
		this.awardAccount = awardAccount;
	}

	/**
	 * @return the isDay
	 */
	public int getIsDay() {
		return isDay;
	}

	/**
	 * @param isDay
	 *            the isDay to set
	 */
	public void setIsDay(int isDay) {
		this.isDay = isDay;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the accountScale
	 */
	public double getAccountScale() {
		return accountScale;
	}

	/**
	 * @param accountScale
	 *            the accountScale to set
	 */
	public void setAccountScale(double accountScale) {
		this.accountScale = accountScale;
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
	 * @return the borrowRate
	 */
	public String getBorrowRate() {
		return borrowRate;
	}

	/**
	 * @param borrowRate
	 *            the borrowRate to set
	 */
	public void setBorrowRate(String borrowRate) {
		this.borrowRate = borrowRate;
	}

	/**
	 * @return the borrowAccount
	 */
	public double getBorrowAccount() {
		return borrowAccount;
	}

	/**
	 * @param borrowAccount
	 *            the borrowAccount to set
	 */
	public void setBorrowAccount(double borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	/**
	 * @return the borrowStatus
	 */
	public int getBorrowStatus() {
		return borrowStatus;
	}

	/**
	 * @param borrowStatus
	 *            the borrowStatus to set
	 */
	public void setBorrowStatus(int borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	/**
	 * @return the repayType
	 */
	public int getRepayType() {
		return repayType;
	}

	/**
	 * @param repayType
	 *            the repayType to set
	 */
	public void setRepayType(int repayType) {
		this.repayType = repayType;
	}

	/**
	 * @return the currentAccount
	 */
	public double getCurrentAccount() {
		return currentAccount;
	}

	/**
	 * @param currentAccount
	 *            the currentAccount to set
	 */
	public void setCurrentAccount(double currentAccount) {
		this.currentAccount = currentAccount;
	}

	/**
	 * @return the accountLabel
	 */
	public String getAccountLabel() {
		return accountLabel;
	}

	/**
	 * @param accountLabel
	 *            the accountLabel to set
	 */
	public void setAccountLabel(String accountLabel) {
		this.accountLabel = accountLabel;
	}

	/**
	 * @return the currentAccountLabel
	 */
	public String getCurrentAccountLabel() {
		return currentAccountLabel;
	}

	/**
	 * @param currentAccountLabel
	 *            the currentAccountLabel to set
	 */
	public void setCurrentAccountLabel(String currentAccountLabel) {
		this.currentAccountLabel = currentAccountLabel;
	}

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * 
	 * 0:按比例奖励;1: 按金额奖励
	 * 
	 * @return the awardType
	 */
	public int getAwardType() {

		if (awardScale > 0) {
			return 0;
		} else if (awardAccount > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * @param awardType
	 *            the awardType to set
	 */
	public void setAwardType(int awardType) {
		this.awardType = awardType;
	}

	/**
	 * @return the awardScale1
	 */
	public int getAwardScale1() {
		return awardScale1;
	}

	/**
	 * @param awardScale1
	 *            the awardScale1 to set
	 */
	public void setAwardScale1(int awardScale1) {
		this.awardScale1 = awardScale1;
	}

	/**
	 * @return the awardAccount1
	 */
	public double getAwardAccount1() {
		return awardAccount1;
	}

	/**
	 * @param awardAccount1
	 *            the awardAccount1 to set
	 */
	public void setAwardAccount1(double awardAccount1) {
		this.awardAccount1 = awardAccount1;
	}

	/**
	 * @return the awardType1
	 */
	public int getAwardType1() {
		return awardType1;
	}

	/**
	 * @param awardType1
	 *            the awardType1 to set
	 */
	public void setAwardType1(int awardType1) {
		this.awardType1 = awardType1;
	}

	/**
	 * @return the awardScale2
	 */
	public int getAwardScale2() {
		return awardScale2;
	}

	/**
	 * @param awardScale2
	 *            the awardScale2 to set
	 */
	public void setAwardScale2(int awardScale2) {
		this.awardScale2 = awardScale2;
	}

	/**
	 * @return the awardAccount2
	 */
	public double getAwardAccount2() {
		return awardAccount2;
	}

	/**
	 * @param awardAccount2
	 *            the awardAccount2 to set
	 */
	public void setAwardAccount2(double awardAccount2) {
		this.awardAccount2 = awardAccount2;
	}

	/**
	 * @return the awardType2
	 */
	public int getAwardType2() {
		return awardType2;
	}

	/**
	 * @param awardType2
	 *            the awardType2 to set
	 */
	public void setAwardType2(int awardType2) {
		this.awardType2 = awardType2;
	}

	/**
	 * @return the awardScale3
	 */
	public int getAwardScale3() {
		return awardScale3;
	}

	/**
	 * @param awardScale3
	 *            the awardScale3 to set
	 */
	public void setAwardScale3(int awardScale3) {
		this.awardScale3 = awardScale3;
	}

	/**
	 * @return the awardAccount3
	 */
	public double getAwardAccount3() {
		return awardAccount3;
	}

	/**
	 * @param awardAccount3
	 *            the awardAccount3 to set
	 */
	public void setAwardAccount3(double awardAccount3) {
		this.awardAccount3 = awardAccount3;
	}

	/**
	 * @return the awardType3
	 */
	public int getAwardType3() {
		return awardType3;
	}

	/**
	 * @param awardType3
	 *            the awardType3 to set
	 */
	public void setAwardType3(int awardType3) {
		this.awardType3 = awardType3;
	}

	/**
	 * @return the borrowUserId
	 */
	public String getBorrowUserId() {
		return borrowUserId;
	}

	/**
	 * @param borrowUserId
	 *            the borrowUserId to set
	 */
	public void setBorrowUserId(String borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public String getStatusMemo() {

		int status = getStatus();
		if (statusMap.containsKey(status)) {
			return statusMap.get(status);
		}
		return "";

	}

	// /*表的进度 0:筹款中 1: 复审中 2:还款中 3: 已还完 4:流标 5: 撤回 6:初审中; 7:初审失败;8:筹款中
	// ;9:复审中;10:复审失败;11:已还完;*/
	public int getStatus() {

		if (type == 6) {
			if (accountScale >= 100) {
				if (repayAccountWait <= 0) {
					return 3;
				} else {
					return 2;
				}
			} else {
				return 8;
			}
		} else {

			if (cancelStatus == 1) {
				return 5;
			} else {
				if (failedFlg == 1) {
					return 4;
				} else {
					if (borrowStatus < 1) {
						switch (verifyFlg) {
						case "":
							return 9;
						case "0":
							return 10;
						case "1":

						}
					} else {
						if (StringUtils.isEmpty(reverifyFlg)) {
							return 1;
						}
						switch (reverifyFlg) {
						case "":
							return 9;
						case "0":
							return 1;
						case "1":
							if (repayAccountWait <= 0) {
								return 3;
							} else {
								return 2;
							}
						}
					}
				}
			}
		}

		return 8;
	}

	public String getRepayTypeMemo() {
		String result = "";
		switch (repayType) {
		case 1:
			result = "等额本金";
			break;
		case 2:
			result = "等额本息";
			break;
		case 3:
			result = "到期还本付息";
			break;
		case 4:
			result = "按月还息，到期还本";
			break;
		}
		if(10==this.getType())
		{
			return "随时赎回";
		}
		return result;
	}

	public String getBorrowAccountWait() {
		return borrowAccountWait;
	}

	public void setBorrowAccountWait(String borrowAccountWait) {
		this.borrowAccountWait = borrowAccountWait;
	}

	public String getDirectionalPwd() {

		if (StringUtils.isEmpty(directionalPwd)) {
			return "";
		}
		int tmpLength = directionalPwd.length();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < tmpLength; i++) {
			builder.append("*");
		}
		return builder.toString();
	}

	public void setDirectionalPwd(String directionalPwd) {
		this.directionalPwd = directionalPwd;
	}

	public String getUplanBorrowsIsShow() {
		return uplanBorrowsIsShow;
	}

	public void setUplanBorrowsIsShow(String uplanBorrowsIsShow) {
		this.uplanBorrowsIsShow = uplanBorrowsIsShow;
	}

	public String getBorrowFlag() {
		return borrowFlag;
	}

	public void setBorrowFlag(String borrowFlag) {
		this.borrowFlag = borrowFlag;
	}

	public String getVouchersRate() {
		return vouchersRate;
	}

	public void setVouchersRate(String vouchersRate) {
		this.vouchersRate = vouchersRate;
	}

	public List<KeyValuePair> getPrivileges() {
		return privileges;
	}

	// TODO:奖励如果是现金,需要修改
	public String getWanInterest() {

		if (10==this.getType())
			return "0";

		if(StringUtils.isEmpty(period))
			return "0";
		double rate = Double.parseDouble(borrowRate);

		if (awardScale > 0) {
			rate += awardScale;
		}
		return NumberUtils.moneyFormat(String.valueOf((10000 * rate
				* Double.parseDouble(period) / 12 / 100)));
	}

	public String getWanInterestMemo() {

		
		if (awardScale > 0) {
			return "万元收益(含奖励)";
		}
		return "万元收益";
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}

	public String getOriginalBorrowAccount() {
		return originalBorrowAccount;
	}

	public void setOriginalBorrowAccount(String originalBorrowAccount) {
		this.originalBorrowAccount = originalBorrowAccount;
	}

	public String getOriginalBorrowPeriod() {
		return originalBorrowPeriod;
	}

	public void setOriginalBorrowPeriod(String originalBorrowPeriod) {
		this.originalBorrowPeriod = originalBorrowPeriod;
	}

	public BorrowAttestion getAttestion() {
		return attestion;
	}

	public void setAttestion(BorrowAttestion attestion) {
		this.attestion = attestion;
	}

	public String getTransferFlg() {
		return transferFlg;
	}

	public void setTransferFlg(String transferFlg) {
		this.transferFlg = transferFlg;
	}

	public String getOriginalBorrowId() {
		return originalBorrowId;
	}

	public void setOriginalBorrowId(String originalBorrowId) {
		this.originalBorrowId = originalBorrowId;
	}

	public String getNextRecoverTime() {
		return nextRecoverTime;
	}

	public void setNextRecoverTime(String nextRecoverTime) {
		this.nextRecoverTime = nextRecoverTime;
	}

	public List<BorrowRepay> getRepays() {
		if (repays == null) {
			repays = new ArrayList<>();
		}
		return repays;
	}

	public void setRepays(List<BorrowRepay> repays) {
		this.repays = repays;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public String getOriginalBorrowTitle() {
		return originalBorrowTitle;
	}

	public void setOriginalBorrowTitle(String originalBorrowTitle) {
		this.originalBorrowTitle = originalBorrowTitle;
	}

	@Override
	public String toString() {
		return "Borrow [title=" + title + ", privileges=" + privileges
				+ ", attestion=" + attestion + ", nextRecoverTime="
				+ nextRecoverTime + ", repays=" + repays + ", type=" + type
				+ ", isNew=" + isNew + ", awardStatus=" + awardStatus
				+ ", creditLevel=" + creditLevel + ", awardScale=" + awardScale
				+ ", awardAccount=" + awardAccount + ", awardType=" + awardType
				+ ", awardScale1=" + awardScale1 + ", awardAccount1="
				+ awardAccount1 + ", awardType1=" + awardType1
				+ ", awardScale2=" + awardScale2 + ", awardAccount2="
				+ awardAccount2 + ", awardType2=" + awardType2
				+ ", awardScale3=" + awardScale3 + ", awardAccount3="
				+ awardAccount3 + ", awardType3=" + awardType3 + ", isDay="
				+ isDay + ", period=" + period + ", accountScale="
				+ accountScale + ", borrowId=" + borrowId + ", borrowRate="
				+ borrowRate + ", borrowAccount=" + borrowAccount
				+ ", borrowStatus=" + borrowStatus + ", repayType=" + repayType
				+ ", repayTypeMemo=" + repayTypeMemo + ", currentAccount="
				+ currentAccount + ", accountLabel=" + accountLabel
				+ ", currentAccountLabel=" + currentAccountLabel
				+ ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", duration=" + duration + ", borrowUserId=" + borrowUserId
				+ ", status=" + status + ", repayAccountWait="
				+ repayAccountWait + ", cancelStatus=" + cancelStatus
				+ ", failedFlg=" + failedFlg + ", verifyFlg=" + verifyFlg
				+ ", reverifyFlg=" + reverifyFlg + ", borrowAccountWait="
				+ borrowAccountWait + ", directionalPwd=" + directionalPwd
				+ ", vouchersRate=" + vouchersRate + ", uplanBorrowsIsShow="
				+ uplanBorrowsIsShow + ", borrowFlag=" + borrowFlag
				+ ", discountRate=" + discountRate + ", discountAmount="
				+ discountAmount + ", originalBorrowAccount="
				+ originalBorrowAccount + ", originalBorrowPeriod="
				+ originalBorrowPeriod + ", originalBorrowId="
				+ originalBorrowId + ", originalBorrowTitle="
				+ originalBorrowTitle + ", transferFlg=" + transferFlg + "]";
	}

}
