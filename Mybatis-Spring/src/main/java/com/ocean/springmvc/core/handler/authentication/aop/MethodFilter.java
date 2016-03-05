package com.ocean.springmvc.core.handler.authentication.aop;

import java.io.Serializable;

import com.ocean.springmvc.core.common.Ordered;

/**
 * 
 * @author ocean
 *
 */
public interface MethodFilter extends Ordered ,Serializable{
	/**
	 * 
	 * @param subject  主体
	 * @param methodSignature  方法签名
	 * @param actualParameter  方法实参
	 * @return
	 */
	boolean isAuthentication(Object subject, String methodSignature,
			Object[] actualParameter);

}
