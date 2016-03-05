package com.jujin.entity.xglc.transaction;
/**
 * Title: RegInfo
 * Description: 注册返回数据(西瓜理财)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class RegInfoDTO {
	
	/**
	 * 是否已完成注册
	 */
	private boolean isRegisted;
	/**
	 * 用户登录平台的账号
	 */
	private String account;
	/**
	 * 用户身份识别Key
	 */
	private String userAccessKey;
	/**
	 * 用户身份key到期时间
	 */
	private String expireTime;
	
	public boolean isRegisted() {
		return isRegisted;
	}
	public void setRegisted(boolean isRegisted) {
		this.isRegisted = isRegisted;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	
	

}
