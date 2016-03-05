/**   
 * @author wangning
 * @date 2015年2月12日 下午5:01:19 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.authorize;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

import com.pro.common.util.ConvUtil;

/**
 * 存放用户个人数据
 */
public class AppSession implements Serializable {

	private static final long serialVersionUID = -2883508516879892767L;

	private HashMap<String, Object> sessionMap = new HashMap<String, Object>();
	private Date activeTime;

	public AppSession() {
		activeTime = new Date();
	}

	/**
	 * @return the activeTime
	 */
	public Date getActiveTime() {
		return activeTime;
	}

	/**
	 * @param activeTime
	 *            the activeTime to set
	 */
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public void set(String paramString, Object paramObject) {
		this.sessionMap.put(paramString, paramObject);
	}

	public Object get(String paramString) {
		return this.sessionMap.get(paramString);
	}

	public String getStringValue(String paramString) {
		return ConvUtil.convToString(this.sessionMap.get(paramString));
	}
}
