package com.jujin.entity.xglc.transaction;

import java.text.ParseException;
import java.util.Date;

import com.jujin.util.xglc.CommonUtil;

/**
 * Title: LoginInfoDTO
 * Description: 登录返回信息(西瓜理财)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class LoginInfoDTO {
	/**
	 * 是否已登录
	 */
	private boolean isLoginSucceed = false;
	/**
	 * 用户身份识别Key
	 */
	private String userAccessKey;
	/**
	 * 用户身份key到期时间
	 */
	private String expireTime = null;
	
	public boolean isLoginSucceed() {
		return isLoginSucceed;
	}
	public void setLoginSucceed(boolean isLoginSucceed) {
		this.isLoginSucceed = isLoginSucceed;
	}
	public String getUserAccessKey() {
		return userAccessKey;
	}
	public void setUserAccessKey(String userAccessKey) {
		this.userAccessKey = userAccessKey;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	
	/**
	 * 是否过期
	* Title: isExpire
	* Description: 
	* @return
	* @throws ParseException
	 */
	public boolean checkExpire() throws ParseException{
		Date expireDate = CommonUtil.stringToDate(expireTime);
		if(expireDate.before(new Date())){
			return true;
		}else{
			return false;
		}
	}
	
	
}
