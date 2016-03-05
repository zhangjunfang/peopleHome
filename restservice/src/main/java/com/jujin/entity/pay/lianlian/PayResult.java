package com.jujin.entity.pay.lianlian;

/**
 * @author wangning 支付之后连连返回的支付结果
 * 
 */
public class PayResult {

	private String oid_partner; // 是 商户编号是在连支付平台
	private String sign_type; // 是 RSA 或者 MD5
	private String sign; // 是 RSA加密签名
	private String dt_order;// 是 商户订单时间
	private String no_order; // 是 商户系统唯一订单号
	private String oid_paybill; // 是 连支付单号
	private String money_order; // 是 交易金额
	private String result_pay; // 支付结果 SUCCESS 为成功
	private String settle_date; // 清算日期
	private String info_order; // 订单描述
	private String pay_type; // 支付方式 2：快捷支付（借记卡） 3：快捷支付（信用卡）
	private String bank_code; // 银行编号
	private String no_agree;//支付成功的签约号

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
	

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getOid_paybill() {
		return oid_paybill;
	}

	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getResult_pay() {
		return result_pay;
	}

	public void setResult_pay(String result_pay) {
		this.result_pay = result_pay;
	}

	public String getSettle_date() {
		return settle_date;
	}

	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}

	public String getInfo_order() {
		return info_order;
	}

	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	public String getNo_agree() {
		return no_agree;
	}

	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}

}
