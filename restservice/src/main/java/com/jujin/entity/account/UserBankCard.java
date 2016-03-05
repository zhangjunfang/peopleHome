/**   
 * @author wangning
 * @date 2015年2月28日 下午2:57:08 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.entity.account;

import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.UserUtils;

/**
 * 用户银行卡信息
 */
public class UserBankCard {

	private String userId;
	private String nickName;
	private String cardId;
	// 开户行
	private String branck;

	private int province;
	private String provinceName;

	private int city;
	private String cityName;
	private String bankName;

	private String bankCode;

	private int id;

	/**
	 * 卡的类型0:充值绑定的卡,1：提现绑定的卡
	 */
	private int type;

	private String validateCode;

	private static HashMap<String, String> bankTypeMap = new HashMap<String, String>();

	static {

		/*
		 * 62 中国建设银行 59 中国交通银行 71 招商银行 78 中国工商银行 79 广发银行 80 华夏银行 81 平安银行 82 浦发银行
		 * 83 兴业银行 84 光大银行 85 民生银行 86 农业银行 87 中国银行 88 邮政 89 中信银行 90 中国银行
		 */

		bankTypeMap.put("62", "CCB");
		bankTypeMap.put("59", "BOC");
		bankTypeMap.put("71", "CMB");
		bankTypeMap.put("78", "ICBC");
		bankTypeMap.put("79", "GDB");
		bankTypeMap.put("80", "HXB");
		bankTypeMap.put("81", "PINGAN");
		bankTypeMap.put("82", "SPDB");
		bankTypeMap.put("83", "CIB");
		bankTypeMap.put("84", "CEB");
		bankTypeMap.put("85", "CMBC");
		bankTypeMap.put("86", "ABC");
		bankTypeMap.put("87", "BOC");
		bankTypeMap.put("88", "PSBC");
		bankTypeMap.put("89", "CITIC");
		bankTypeMap.put("90", "BOC");

	};

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return UserUtils.realNameToConceal(this.userId);
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return UserUtils.strCardToConceal(this.cardId);
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the branck
	 */
	public String getBranck() {
		return branck;
	}

	/**
	 * @param branck
	 *            the branck to set
	 */
	public void setBranck(String branck) {
		this.branck = branck;
	}

	/**
	 * @return the province
	 */
	public int getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(int province) {
		this.province = province;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName
	 *            the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the city
	 */
	public int getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(int city) {
		this.city = city;
	}

	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 *            the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankType() {
		if (bankTypeMap.containsKey(this.bankCode)) {
			return bankTypeMap.get(this.bankCode);
		}
		return "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore
	public String getRealUserId() {
		return this.userId;
	}

	@JsonIgnore
	public String getRealCardId() {
		return this.cardId;
	}

	@JsonIgnore
	public boolean isValidate() {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(cardId)
				|| StringUtils.isEmpty(branck) || province == 0 || city == 0
				|| StringUtils.isEmpty(bankCode)) {
			return false;
		}
		return true;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	/**
	 * 卡的类型0:充值绑定的卡,1：提现绑定的卡
	 */
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
