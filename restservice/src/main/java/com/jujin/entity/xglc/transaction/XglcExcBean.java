package com.jujin.entity.xglc.transaction;
/**
 * Title: ExceptionBean
 * Description: 返回错误信息（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月23日
 */
public class XglcExcBean {

	private String message;
	
	private String info;
	
	public XglcExcBean(String message,String info){
		this.message = message;
		this.info = info;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
