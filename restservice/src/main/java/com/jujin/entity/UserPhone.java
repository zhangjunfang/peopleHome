/**   
 * @author wangning
 * @date 2015年2月11日 下午3:17:54 
 * @version V1.0   
 * @Description:
 */
package com.jujin.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户手机号
 */
public class UserPhone extends UserNickName {

	/* 移动手机 */
	private String mobilephome;

	/**
	 * @return the mobilephome
	 */
	public String getMobilephome() {
		return mobilephome;
	}

	/**
	 * @param mobilephome the mobilephome to set
	 */
	public void setMobilephome(String mobilephome) {
		this.mobilephome = mobilephome;
	}

}
