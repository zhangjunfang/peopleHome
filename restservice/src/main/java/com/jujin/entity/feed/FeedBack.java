package com.jujin.entity.feed;

import java.util.Date;

public class FeedBack {
	//id
	private  int recordid; 
	//发送方
	private String userid;
	//接收方
	private String touserid;
	//用户反馈：0：用户反馈；1：平台反馈用户
	private int msgtype;
	//用户反馈
	private int feedtype;
	//自定义消息类型备注
	private String feedmemo;
	//描述
	private String content;
	//文件列表
	private String file1;
	private String file2;
	private String file3;
	private String file4;
	private String file5;
	private String inserttime;
	
	
	
	public int getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(int msgtype) {
		this.msgtype = msgtype;
	}
	public String getFeedmemo() {
		return feedmemo;
	}
	public void setFeedmemo(String feedmemo) {
		this.feedmemo = feedmemo;
	}
	public String getInserttime() {
		return inserttime;
	}
	public void setInserttime(String inserttime) {
		this.inserttime = inserttime;
	}
	public int getRecordid() {
		return recordid;
	}
	public void setRecordid(int recordid) {
		this.recordid = recordid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTouserid() {
		return touserid;
	}
	public void setTouserid(String touserid) {
		this.touserid = touserid;
	}
	
	public int getFeedtype() {
		return feedtype;
	}
	public void setFeedtype(int feedtype) {
		this.feedtype = feedtype;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFile1() {
		return file1;
	}
	public void setFile1(String file1) {
		this.file1 = file1;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	public String getFile3() {
		return file3;
	}
	public void setFile3(String file3) {
		this.file3 = file3;
	}
	public String getFile4() {
		return file4;
	}
	public void setFile4(String file4) {
		this.file4 = file4;
	}
	public String getFile5() {
		return file5;
	}
	public void setFile5(String file5) {
		this.file5 = file5;
	}
	@Override
	public String toString() {
		return "FeedBack [recordid=" + recordid + ", userid=" + userid
				+ ", touserid=" + touserid + ", msgtype=" + msgtype
				+ ", feedtype=" + feedtype + ", feedmemo=" + feedmemo
				+ ", content=" + content + ", file1=" + file1 + ", file2="
				+ file2 + ", file3=" + file3 + ", file4=" + file4 + ", file5="
				+ file5 + ", inserttime=" + inserttime + "]";
	}
	
	
}
