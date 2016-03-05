/**   
 * @author wangning
 * @date 2015年2月11日 下午2:34:05 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity;

import org.springframework.util.StringUtils;

import com.jujin.common.SystemConfig;

/**
 * 用户和用户ID
 */
public class UserNickName {

	private String userId;

	private String nickName;

	/* 用户头像对应的图片 */
	private String userHeadImage;

	private String typeId;

	/* 是否VIP 0:不是;1:是 */
	private int vipFlg;

	/* VIP名称 */
	private String vipName;

	/* VIP级别对应的图片 */
	private String typeImageFileId;
	
	private String phoneNumber;

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the userHeadImage
	 */
	public String getUserHeadImage() {
		if (!StringUtils.isEmpty(userHeadImage)) {
			return SystemConfig.getRoot() + userHeadImage;
		}
		return userHeadImage;
	}

	/**
	 * @param userHeadImage
	 *            the userHeadImage to set
	 */
	public void setUserHeadImage(String userHeadImage) {
		this.userHeadImage = userHeadImage;
	}

	/**
	 * @return the vipFlg
	 */
	public int getVipFlg() {
		return vipFlg;
	}

	/**
	 * @param vipFlg
	 *            the vipFlg to set
	 */
	public void setVipFlg(int vipFlg) {
		this.vipFlg = vipFlg;
	}

	/**
	 * @return the vipName
	 */
	public String getVipName() {
		return vipName;
	}

	/**
	 * @param vipName
	 *            the vipName to set
	 */
	public void setVipName(String vipName) {
		this.vipName = vipName;
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
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the typeImageFileId
	 */
	public String getTypeImageFileId() {
		
		if (!StringUtils.isEmpty(typeImageFileId)) {
			return SystemConfig.getRoot() + typeImageFileId;
		}
		return typeImageFileId;
	}

	/**
	 * @param typeImageFileId
	 *            the typeImageFileId to set
	 */
	public void setTypeImageFileId(String typeImageFileId) {
		this.typeImageFileId = typeImageFileId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
