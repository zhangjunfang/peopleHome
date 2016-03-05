package com.citic.risk.entity;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * 再类上用 @JsonAutoDetect(JsonMethod.NONE) 
 属性上用  @JsonProperty 
 没有加 @JsonProperty的就隐藏了 
 * */
public class User {

	private String name;
	private String age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
}
