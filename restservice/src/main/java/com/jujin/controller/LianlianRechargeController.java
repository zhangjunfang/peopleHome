package com.jujin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import org.springframework.stereotype.Controller;

import com.pro.common.model.Model;
import com.pro.common.util.DateUtils;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.StringUtils;
//import com.wicket.loan.common.constant.PageParamConst;
import com.wicket.loan.common.constant.PageParamConstant;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.web.person.recharge.bean.RechargeBean;
import com.wicket.loan.web.person.recharge.mediator.RechargeMediator;



@Controller
public class LianlianRechargeController extends BaseController{
	
	
	protected RechargeMediator mediator;
	
	
	protected String bankConfig = "";
	protected String config = "";
	protected String rechargeAmount = "";
	protected String feeAmount = "";
	protected String balanceAmount = "";
	protected String rechargeBankName = "";
	protected String bankId = "";
	protected String merCode = "";
	protected String merKey = "";
	protected String paycd = "";
	protected String currencyType = "";
	protected String billNoAutoStr = "";
	protected String merId = "";
	protected String recharge_bank_id="";//TODO:参数
	
	
 
	
	protected void getRechargeType() {
		Model<String, Object> bankInfo = mediator.getOnlineBankInfoById(recharge_bank_id);
		this.rechargeBankName = bankInfo.getStringValue("BANK_NAME");
		this.config = bankInfo.getStringValue("MER_CONFIG");
		this.bankConfig = bankInfo.getStringValue("BANK_CONFIG");
	}
	
	protected String getBillNoNew() {
		// 订单号
		String billDate = DateUtils.convertDate2String(DateUtils.getSystemDate(), DateUtils.DATE_TIME);
		Random random = new Random();
		String billNoStr = billDate + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9)
				+ random.nextInt(9);
		while (!mediator.isRightOrder(billNoStr)) {
			billNoStr = billDate + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9)
					+ random.nextInt(9);
		}
		return billNoStr;
	}
	
	protected RechargeBean createConditionBean(int... params) {
		RechargeBean saveBean = new RechargeBean();
		/*saveBean.setUserId(appSession.getStringValue(PageParamConstant.LOGIN_ID));

		if ("1".equals(getParameter(PageParamConst.RECHARGE_CONTINUE))) {
			saveBean.setOrderId(getParameter(PageParamConst.RECHARGE_ORDER_ID));
			saveBean.setContinueOrderId(this.billNoAutoStr);
		} else {
			saveBean.setOrderId(billNoAutoStr);
		}
		saveBean.setRechargeStatus("1");
		saveBean.setRechargeAmount(this.rechargeAmount);
		saveBean.setFeeAmount(this.feeAmount);
		saveBean.setBalanceAmount(this.balanceAmount);
		saveBean.setPayment(this.rechargeBankName);
		saveBean.setRtnValue("1");
		saveBean.setRechargeType("0");
		saveBean.setRemark("用户账号线上充值");
		saveBean.setInsIp(super.requestContext.getClientIp());
		saveBean.setInsUserId(appSession.getStringValue(PageParamConstant.LOGIN_ID));
		saveBean.setUpdUserId(appSession.getStringValue(PageParamConstant.LOGIN_ID));
		saveBean.setBankId(getParameter(PageParamConst.RECHARGE_BANK_ID));
		saveBean.setRechargeContinue(getParameter(PageParamConst.RECHARGE_CONTINUE));
		String rechargeAmountStr = rechargeAmount;
		if (params != null && params.length > 0) {
			double operatorAmount = NumberUtils.parseDouble(this.rechargeAmount);
			operatorAmount = operatorAmount * params[0];
			rechargeAmountStr = NumberUtils.subZeroAndDot(StringUtils.objToString(operatorAmount));
		}
		saveBean.setRechargeValidateKey(saveBean.getOrderId() + rechargeAmountStr + currencyType);*/

		return saveBean;
	}

	// 手续费
	protected String getFeeAmountByMediatot() {
		return mediator.getFeeAmountForRecharge(this.rechargeAmount);
	}

	// 到账金额
	protected String getBalance(String feeStr) {
		return StringUtils.objToString(NumberUtils.parseDouble(this.rechargeAmount) - NumberUtils.parseDouble(feeStr));
	}

	protected String getRechargeValidateKey(String billNo) {
		return DesCodeUtil.encrypt(billNo);
	}
	
	protected void setInitValue() {
		this.rechargeAmount =""; //NumberUtils.decimalFormat(getParameter(PageParamConst.RECHARGE_AMOUNT));
		this.feeAmount = getFeeAmountByMediatot();
		this.balanceAmount = getBalance(this.feeAmount);
		getRechargeType();
		if (!StringUtils.isEmpty(this.config)) {
			String[] paramMer = this.config.split("&");
			for (String keyValue : paramMer) {
				if (!StringUtils.isEmpty(keyValue)) {
					String[] conf = keyValue.split("=");
					if ("Mer_code".equals(conf[0])) {
						merCode = conf[1];
					}
					if ("Mer_key".equals(conf[0])) {
						merKey = conf[1];
					}
					if ("pay_cd".equals(conf[0])) {
						paycd = conf[1];
					}
					if ("Mer_id".equals(conf[0])) {
						merId = conf[1];
					}
				}
			}
		}

		if (!StringUtils.isEmpty(this.bankConfig)) {
			String[] paramMer = this.bankConfig.split("&");
			for (String keyValue : paramMer) {
				if (!StringUtils.isEmpty(keyValue)) {
					String[] conf = keyValue.split("=");
					if ("bank_id".equals(conf[0])) {
						bankId = conf[1];
					}
					if ("Mer_code".equals(conf[0])) {
						merCode = conf[1];
					}
					if ("Mer_key".equals(conf[0])) {
						merKey = conf[1];
					}

					if ("Mer_id".equals(conf[0])) {
						merId = conf[1];
					}
				}
			}
		}
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public String getRechargeBankName() {
		return rechargeBankName;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

    protected String urlEncode(String args) {
    	if (StringUtils.isEmpty(args)) return "";
        String result;
        try {
			result=URLEncoder.encode(args,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
        return result;
    }

	 
	
	


}
