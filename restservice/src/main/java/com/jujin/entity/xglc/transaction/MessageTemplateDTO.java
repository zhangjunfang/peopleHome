package com.jujin.entity.xglc.transaction;

import java.util.List;

/**
 * Title: MessageTemplateDTO
 * Description: 消息容器（西瓜理财）
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class MessageTemplateDTO {
	/**
	 * 消息数量
	 */
	private int messageCount;
	/**
	 * 消息列表
	 */
	private List<MessageInfoDTO> messageList;
	
	public MessageTemplateDTO(List<MessageInfoDTO> messageList){
		this.messageCount = messageList.size();
		this.messageList = messageList;
	}
	
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public List<MessageInfoDTO> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<MessageInfoDTO> messageList) {
		this.messageList = messageList;
	}
	
	
	
	
}
