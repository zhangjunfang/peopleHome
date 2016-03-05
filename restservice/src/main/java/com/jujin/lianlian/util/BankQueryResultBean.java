package com.jujin.lianlian.util;

import java.util.List;

//{"agreement_list":[{"bank_code":"01040000","bank_name":"中国银行","card_no":"8867","card_type":"2","no_agree":"2015050465899307"}],
//"count":"1","ret_code":"0000","ret_msg":"交易成功","sign":"adb558713f82b4b1f3136c711cedbc6f","sign_type":"MD5",
//"user_id":"firetw"}
public class BankQueryResultBean {

	public List<BankCardBean> agreement_list;
	private int count;

	private String ret_code;

	private String ret_msg;

	private String sign;

	private String sign_type;

	private String user_id;
	
	

	public List<BankCardBean> getAgreement_list() {
		return agreement_list;
	}

	public void setAgreement_list(List<BankCardBean> agreement_list) {
		this.agreement_list = agreement_list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getRet_code() {
		return ret_code;
	}

	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}

	public String getRet_msg() {
		return ret_msg;
	}

	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
