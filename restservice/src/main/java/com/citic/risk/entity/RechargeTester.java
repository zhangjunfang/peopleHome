package com.citic.risk.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jujin.biz.RechargeBiz;
import com.jujin.entity.pay.lianlian.PartnerConfig;
import com.jujin.entity.pay.lianlian.PayResult;
import com.jujin.lianlian.util.YinTongUtil;

public class RechargeTester {

	public static void main(String[] args) {

		// RechargeBiz biz = new RechargeBiz();

		// biz.onlineRecharge("firetw", "96", "500", "", "0", "192.168.0.66");

		/*
		 * { "oid_partner":"201504201000291503",
		 * "dt_order":"20150505113127450427", "no_order":"20150505113127450427",
		 * "oid_paybill":"2013051613121201", "money_order":"1",
		 * "result_pay":"SUCCESS", "settle_date":"20150504",
		 * "info_order":"聚金资本快捷充值", "pay_type":"2", "bank_code":"01020000",
		 * "sign_type":"MD5", "sign":
		 * "ZPZULntRpJwFmGNIVKwjLEF2Tze7bqs60rxQ22CqT5J1UlvGo575QK9z/+p+7E9cOoRoWzqR6xHZ6WVv3dloyGKDR0btvrdqPgUAoeaX/YOWzTh00vwcQ+HBtXE+vPTfAqjCTxiiSJEOY7ATCF1q7iP3sfQxhS0nDUug1LP3OLk="
		 * }
		 */

		PayResult result = new PayResult();
		result.setOid_partner("201504201000291503");
		result.setDt_order("20150505113127");
		result.setNo_order("20150505113127450427");
		result.setMoney_order("1");
		result.setResult_pay("SUCCESS");
		result.setSettle_date("20150504");
		result.setInfo_order("聚金资本快捷充值");
		result.setPay_type("2");
		result.setBank_code("01020000");
		result.setSign("MD5");
		String result1 = YinTongUtil.addSign(
				JSONObject.parseObject(JSON.toJSONString(result)), "",
				PartnerConfig.OID_PARTNER);

		// result.no_order("firetw");

		System.out.println(result1);
		System.out.println("completed");
	}
}
