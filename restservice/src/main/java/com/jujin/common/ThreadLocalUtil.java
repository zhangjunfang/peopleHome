/**   
 * @author wangning
 * @date 2015年2月27日 下午8:05:19 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.common;

/**
 * 
 */
public class ThreadLocalUtil {

	private static final ThreadLocal<Page> session = new ThreadLocal<Page>();

	public static Page current() {
		Page s = (Page) session.get();
		// 如果Session还没有打开，则新开一个Session
		if (s == null) {
			System.out.println(Thread.currentThread().getName()
					+ " Session is null");
			session.set(s); // 将新开的Session保存到线程局部变量中
		}
		return s;
	}

	public static void set(Page item) {
		session.set(item);
	}

}
