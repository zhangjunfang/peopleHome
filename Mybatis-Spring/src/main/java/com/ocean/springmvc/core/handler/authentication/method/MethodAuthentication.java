package com.ocean.springmvc.core.handler.authentication.method;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.ocean.springmvc.core.common.Ordered;

/**
 * 针对restful【spring mvc】中的controller中的方法。
 * 是否有权限操作这些方法。
 * 优点：
 *     1.只针对方法
 *     2.排除一些其它资源
 *     3.没有使用AOP 性能相对来说 好一点
 * 
 * 
 * @author ocean
 *
 */
public interface MethodAuthentication extends Serializable,Ordered {

	/**
	 * 
	 * @param object  object是存在于session中的主体  一般指的是当前用户   。
	 *                前提是：必须在session中存储一个name为subject，value为当前操作的主体  一般指的是用户
	 *                详细参见：httpsession.setAttribute(String name, Object value)方法
	 * @param method     method代表被调用的方法
	 * @return  true表示允许  false表示不允许
	 */
	
	boolean isAuthentication(Object object,Method method);
	
}
