package com.jujin.entity.xglc.transaction;
/**
 * Title: MessageInfoDTO
 * Description: 账户消息（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class MessageInfoDTO {


	/**
	 * 消息ID
	 */
	private String messageId;
	/**
	 * 消息类型
	 */
	private String messageType;
	/**
	 * 消息标题
	 */
	private String messageTitle;
	/**
	 * 消息内容
	 */
	private String messageContent;
	/**
	 * 消息发送时间(日期格式，yyyy-mm-dd hh:MM:ss)
	 */
	private String  publishTime;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	
	
}
