/**   
 * @author wangning
 * @date 2015年3月6日 下午5:15:19 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.entity.borrow;

import java.util.Date;
import java.util.List;

import com.cn.wicket.common.common.component.ChoiceListValues;
import com.cn.wicket.common.common.component.ChoicesData;
import com.pro.common.util.DateUtils;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;

/**
 * 用户收入信息
 */
public class UserIncomeInfo {

	/* 用户姓名 */
	private String name;

	/* 性别 1:男;2:女 */
	private String gender;

	/* 年龄 */
	private String cardId;

	/* 文件程度 */
	private String education;

	/* 每月收入 */
	private String income;

	/* 婚否 */
	private String marry;

	/* 社保 */
	private String socialSecurity;

	/* 住房条件 */
	private String house;

	/* 是否购车 */
	private String car;

	/**
	 * @return the name
	 */
	public String getName() {
		return UserUtils.realNameToConceal(name);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return choiceListDisplay(gender,"SEX_LIST");
	}

	/**
	 * @param gender
	 *            the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the education
	 */
	public String getEducation() {
		return choiceListDisplay(education,"EDUCATION_LIST");
	}

	/**
	 * @param education
	 *            the education to set
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * @return the income
	 */
	public String getIncome() {
		return choiceListDisplay(income,"MONTH_INCOME_LIST");
	}

	/**
	 * @param income
	 *            the income to set
	 */
	public void setIncome(String income) {
		this.income = income;
	}

	/**
	 * @return the marry
	 */
	public String getMarry() {
		return choiceListDisplay(marry,"MARRY_LIST");
	}

	/**
	 * @param marry
	 *            the marry to set
	 */
	public void setMarry(String marry) {
		this.marry = marry;
	}

	/**
	 * @return the socialSecurity
	 */
	public String getSocialSecurity() {
		return choiceListDisplay(socialSecurity,"SOCIAL_SECURITY");
	}

	/**
	 * @param socialSecurity
	 *            the socialSecurity to set
	 */
	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	/**
	 * @return the house
	 */
	public String getHouse() {
		return choiceListDisplay(house,"HOUSE_LIST");
	}

	/**
	 * @param house
	 *            the house to set
	 */
	public void setHouse(String house) {
		this.house = house;
	}

	/**
	 * @return the car
	 */
	public String getCar() {
		return choiceListDisplay(car,"CAR_LIST");
	}

	/**
	 * @param car
	 *            the car to set
	 */
	public void setCar(String car) {
		this.car = car;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserIncomeInfo [name=" + name + ", gender=" + gender + ", age="
				+ getAge() + ", education=" + education + ", income=" + income
				+ ", marry=" + marry + ", socialSecurity=" + socialSecurity
				+ ", house=" + house + ", car=" + car + "]";
	}
	
	private String choiceListDisplay(Object obj, String strList) {
		String str = "";
		List<ChoiceListValues> list = new ChoicesData(strList).getChoicesList();
		for (int i = 0; i < list.size(); i++) {
			if (StringUtils.trim(StringUtils.objToString(obj)).equals(StringUtils.objToString(list.get(i).getValue()))) {
				str = StringUtils.objToString(list.get(i).getDisplay());
				break;
			}
		}
		return str;
	}

	private String getAge() {

		String nowYear = DateUtils.getYear(new Date());
		String cardID = this.cardId;
		String s = cardID;
		int leh = s.length();
		if (leh != 18 && leh != 15) {
			return "0";
		} else {
			if (leh == 18) {
				String dates = s.substring(6, 10);
				return StringUtils.objToString(NumberUtils
						.parseInteger(nowYear)
						- NumberUtils.parseInteger(dates));

			} else {
				String dates = "19" + s.substring(6, 8);
				return StringUtils.objToString(NumberUtils
						.parseInteger(nowYear)
						- NumberUtils.parseInteger(dates));
			}
		}
	}

	/**
	 * @return the cardId
	 */
	public String getCardId() {
		return "";
	}

	/**
	 * @param cardId
	 *            the cardId to set
	 */
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

}
