package com.citic.risk.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.json.JSON;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.jujin.biz.AwardBiz;
import com.jujin.entity.account.UserBankCard;
import com.jujin.entity.award.AwardInfo;
import com.jujin.lianlian.util.BankQueryResultBean;
import com.pro.common.db.jdbc.DbContext;
import com.pro.common.model.Model;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.web.common.bean.BorrowVentureBean;
import com.wicket.loan.web.regist.mediator.RegistMediator;

public class AccountTestController {

	public static void main(String[] args) throws Exception {
		// AwardBiz biz=new AwardBiz();

		// AwardBiz biz=new AwardBiz();

		// biz.drawAward("firetw");

		// SELECT * FROM users_account WHERE user_id='firetw'
		System.out.println("begin");
		String tmpTrash = com.pro.common.util.DesCodeUtil.encrypt("123456");
		String tmp2 = com.pro.common.util.DesCodeUtil
				.decrypt("03D4A8039041CDA17153EEF73E998178");

		System.out.println(tmpTrash);
		System.out.println(tmp2);

		String text = "{\"agreement_list\":[{\"bank_code\":\"01040000\",\"bank_name\":\"中国银行\",\"card_no\":\"8867\",\"card_type\":\"2\",\"no_agree\":\"2015050465899307\"}],\"count\":\"1\",\"ret_code\":\"0000\",\"ret_msg\":\"交易成功\",\"sign\":\"adb558713f82b4b1f3136c711cedbc6f\",\"sign_type\":\"MD5\",\"user_id\":\"firetw\"}";
		Object bean = com.alibaba.fastjson.JSON
				.parse("{\"agreement_list\":[{\"bank_code\":\"01040000\",\"bank_name\":\"中国银行\",\"card_no\":\"8867\",\"card_type\":\"2\",\"no_agree\":\"2015050465899307\"}],\"count\":\"1\",\"ret_code\":\"0000\",\"ret_msg\":\"交易成功\",\"sign\":\"adb558713f82b4b1f3136c711cedbc6f\",\"sign_type\":\"MD5\",\"user_id\":\"firetw\"}");

		// JSONObject.parse(text)
		// System.out.println(bean.get);
		// JSONObject obj = new
		// JSONObject().fromObject(jsonStr);//将json字符串转换为json对象
		// Person jb =
		// (Person)JSONObject.toBean(obj,Person.class);//将建json对象转换为Person对象

		JSONObject objBean = com.alibaba.fastjson.JSON.parseObject(text);

		System.out.println(objBean);

		// objBean.toJavaObject(json, clazz)
		BankQueryResultBean obj = JSONObject.toJavaObject(objBean,
				BankQueryResultBean.class);

		System.out.println(obj.getRet_code());
		System.out.println(obj.getUser_id());
		System.out.println(obj.getAgreement_list().get(0).getNo_agree());

		System.out.println("completed");

		/*
		 * RegistMediator mediator =
		 * FWBeanManager.getBean(RegistMediator.class);
		 * 
		 * if(mediator==null) { System.out.println("mediator is null "); }
		 * System.out.println(mediator.selectMaxFileID());
		 * 
		 * biz.updateUserAccount("firetw", "127.0.0.1", 5, mediator);
		 */

		/*
		 * String url = "jdbc:oracle:thin:@//192.168.0.18/DEVDEV"; //
		 * "jdbc:oracle:thin:@//10.217.3.29:1521/NPMDBGCP"
		 * ;//"jdbc:mysql://10.217.3.29:1521/NPMDBGCP"; String username =
		 * "loanadmin"; String password = "123"; Connection conn=null;
		 * 
		 * try { conn = DriverManager.getConnection(url, username, password); }
		 * catch (SQLException se) { System.out.println("数据库连接失败！");
		 * se.printStackTrace(); }
		 * 
		 * BorrowVentureBean vbBean = new BorrowVentureBean();
		 * vbBean.setAmount(5);
		 * vbBean.setContent(String.format("聚金资本 抽奖活动支出[%s]元,幸运中奖人[%s]", 5,
		 * "firetw")); vbBean.setIeFlg(0); vbBean.setLoginIp("192.168.0.160");
		 * vbBean.setOperatorUserId("聚金资本"); vbBean.setVentureType("0");
		 * vbBean.setUserId("firetw");
		 * 
		 * 
		 * Model<String, Object> modelOperationLog = new Model<String, Object>(
		 * "INSERTBORROWVENTURELOG");
		 * modelOperationLog.addAttribute("IE_FLG",vbBean.getIeFlg());
		 * modelOperationLog.addAttribute("AMOUNT", vbBean.getAmount());
		 * modelOperationLog.addAttribute("CONTENT", vbBean.getContent());
		 * modelOperationLog.addAttribute("USER_ID",vbBean.getUserId());
		 * modelOperationLog.addAttribute("VENTURE_TYPE",
		 * vbBean.getVentureType());
		 * modelOperationLog.addAttribute("INS_IP",vbBean.getLoginIp());
		 * modelOperationLog
		 * .addAttribute("INS_USER_ID",vbBean.getOperatorUserId());
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * StringBuilder builder=new StringBuilder();
		 * builder.append(String.format("IE_FLG:[%s]", 0));
		 * builder.append(String.format("AMOUNT:[%s]", "AMOUNT"));
		 * builder.append(String.format("CONTENT:[%s]", "CONTENT"));
		 * builder.append(String.format("USER_ID:[%s]", "USER_ID"));
		 * builder.append(String.format("VENTURE_TYPE:[%s]", "VENTURE_TYPE"));
		 * builder.append(String.format("INS_IP:[%s]", "INS_IP"));
		 * builder.append(String.format("INS_USER_ID:[%s]", "INS_USER_ID"));
		 * 
		 * 
		 * Object[] objs= modelOperationLog.toArrayAttributes();
		 * 
		 * 
		 * DbContext dbContext=new DbContext();
		 * 
		 * dbContext.executeInsert(conn, modelOperationLog);
		 * 
		 * 
		 * for(Object obj:objs) { System.out.println(obj); }
		 * 
		 * System.out.println(modelOperationLog.getTable());
		 * 
		 * 
		 * System.out.println("completed");
		 */

	}

}
