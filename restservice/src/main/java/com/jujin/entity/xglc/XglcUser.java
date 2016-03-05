package com.jujin.entity.xglc;

import java.util.Date;

public class XglcUser {

		private String userId;
		private String accountKey;
		private Date expireTime;
		
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getAccountKey() {
			return accountKey;
		}
		public void setAccountKey(String accountKey) {
			this.accountKey = accountKey;
		}
		public Date getExpireTime() {
			return expireTime;
		}
		public void setExpireTime(Date expireTime) {
			this.expireTime = expireTime;
		}
		
		
		
}
