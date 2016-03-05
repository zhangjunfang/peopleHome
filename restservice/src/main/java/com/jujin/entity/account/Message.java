/**   
 * @author wangning
 * @date 2015年2月10日 上午9:41:57 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.account;

/**
 * 站内信
 */
public class Message {

	/* 发件人昵称 */
	private String fromNickName;
	/* 发件人用户名 */
	private String fromUserId;

	/* 收件人昵称 */
	private String toNickName;
	/* 收件人用户名 */
	private String toUserId;

	/* 标题 */
	private String title;

	/* 内容,需要前端支持html的格式呈现 */
	private String content;

	/* 发送时间 */
	public String sendTime;

	/**
	 * @return the fromNickName
	 */
	public String getFromNickName() {
		return fromNickName;
	}

	/**
	 * @param fromNickName
	 *            the fromNickName to set
	 */
	public void setFromNickName(String fromNickName) {
		this.fromNickName = fromNickName;
	}

	/**
	 * @return the fromUserId
	 */
	public String getFromUserId() {
		return fromUserId;
	}

	/**
	 * @param fromUserId
	 *            the fromUserId to set
	 */
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	/**
	 * @return the toNickName
	 */
	public String getToNickName() {
		return toNickName;
	}

	/**
	 * @param toNickName
	 *            the toNickName to set
	 */
	public void setToNickName(String toNickName) {
		this.toNickName = toNickName;
	}

	/**
	 * @return the toUserId
	 */
	public String getToUserId() {
		return toUserId;
	}

	/**
	 * @param toUserId
	 *            the toUserId to set
	 */
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sendTime
	 */
	public String getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
