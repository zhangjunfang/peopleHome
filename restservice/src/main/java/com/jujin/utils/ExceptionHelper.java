/**   
 * @author wangning
 * @date 2015年2月13日 下午6:34:32 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.utils;

/**
 * 
 */
public class ExceptionHelper {
	/**
	 * 获取exception详情信息
	 * 
	 * @param e
	 *            Excetipn type
	 * @return String type
	 */
	public static String getExceptionDetail(Exception e) {

		StringBuffer msg = new StringBuffer("null");

		if (e != null) {
			msg = new StringBuffer("");

			String message = e.toString();

			int length = e.getStackTrace().length;

			if (length > 0) {

				msg.append(message + "\n");

				for (int i = 0; i < length; i++) {

					msg.append("\t" + e.getStackTrace()[i] + "\n");

				}
			} else {

				msg.append(message);
			}

		}
		return msg.toString();

	}

}
