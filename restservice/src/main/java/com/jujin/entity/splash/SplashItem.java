package com.jujin.entity.splash;

import org.codehaus.jackson.annotate.JsonIgnore;

public class SplashItem {

	private String img;
	private String memo;
	private String url;
	
	
	private String type;
	
	 
	/**页面显示时间**/
	private int span;
	 
	public String getImg() {
		return img;
	}
	
	
	public void setImg(String img) {
		this.img = img;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUrl() {
		return url;
	}
	
	public int getSpan() {
		return span;
	}
 
	public void setSpan(int span) {
		this.span = span;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	@JsonIgnore
	public String getType() {
		return type;
	}


	@JsonIgnore
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
