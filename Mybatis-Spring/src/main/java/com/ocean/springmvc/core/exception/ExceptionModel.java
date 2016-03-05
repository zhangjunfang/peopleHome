package com.ocean.springmvc.core.exception;

import java.io.Serializable;

public class ExceptionModel implements Serializable {

	private static final long serialVersionUID = -6281304329601472498L;
	// view address
	private String viewURL;
	// json,xml, javaScript
	private String dataFormat;
	// real view data
	private Object object;
	
	public String getViewURL() {
		return viewURL;
	}
	public void setViewURL(String viewURL) {
		this.viewURL = viewURL;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	@SuppressWarnings("unchecked")
	public <T> T getObject() {
		return (T)object;
	}
	public <T> void setObject(T t) {
		this.object = t;
	}

}
