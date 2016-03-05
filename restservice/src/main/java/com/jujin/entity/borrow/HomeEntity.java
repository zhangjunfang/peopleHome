package com.jujin.entity.borrow;

import java.util.List;

import com.jujin.common.JsonList;
import com.jujin.common.OpResult;

public class HomeEntity extends OpResult {
	
	
	private List<Borrow> borrows;
	
	private WeiXinEntity entity;
	
	private String phone;

	private String userId;

	public List<Borrow> getBorrows() {
		return borrows;
	}

	public void setBorrows(List<Borrow> borrows) {
		this.borrows = borrows;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public WeiXinEntity getEntity() {
		return entity;
	}

	public void setEntity(WeiXinEntity entity) {
		this.entity = entity;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
