package com.isea533.mybatis.model;

import java.io.Serializable;

public class Pojo implements Serializable {
	
	private static final long serialVersionUID = -2449496714189599556L;
	private String name;
	private String pwd;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pojo {name=");
		builder.append(name);
		builder.append(", pwd=");
		builder.append(pwd);
		builder.append("}");
		return builder.toString();
	}

}
