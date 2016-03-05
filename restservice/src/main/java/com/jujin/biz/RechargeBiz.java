package com.jujin.biz;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.jujin.entity.UserIdentityCard;
import com.jujin.entity.account.UserBankCard;
import com.jujin.entity.pay.lianlian.Md5Algorithm;
import com.jujin.entity.pay.lianlian.MobilePaymentInfo;
import com.jujin.entity.pay.lianlian.PartnerConfig;
import com.jujin.entity.pay.lianlian.PaymentInfo;
import com.jujin.entity.pay.lianlian.RSAUtil;
import com.jujin.entity.pay.lianlian.RiskItem;
import com.jujin.entity.recharge.BankCardEntity;
import com.jujin.entity.recharge.RechargeEntity;
import com.jujin.entity.recharge.RechargeParam;
import com.jujin.lianlian.util.DateUtil;
import com.jujin.lianlian.util.FuncUtils;
import com.jujin.utils.ExceptionHelper;
import com.mfa.common.notifier.MfaResult;
import com.mfa.constants.notifier.NoticeTypeConstants;
import com.mfa.domain.notifier.BankcardOperateNotifier;
import com.mfa.util.notifier.MfaUtil;
import com.pro.common.model.Model;
import com.pro.common.model.ModelList;
import com.pro.common.util.DateUtils;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.FWBeanManager;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.web.person.recharge.bean.RechargeBean;
import com.wicket.loan.web.person.recharge.mediator.RechargeMediator;

public class RechargeBiz extends BaseBiz {

	RechargeMediator mediator = FWBeanManager.getBean(RechargeMediator.class);

	public String getBillNoNew() {
		// 订单号
		String billDate = DateUtils.convertDate2String(
				DateUtils.getSystemDate(), DateUtils.DATE_TIME);
		Random random = new Random();
		String billNoStr = billDate + random.nextInt(9) + random.nextInt(9)
				+ random.nextInt(9) + random.nextInt(9) + random.nextInt(9)
				+ random.nextInt(9);
		while (!mediator.isRightOrder(billNoStr)) {
			billNoStr = billDate + random.nextInt(9) + random.nextInt(9)
					+ random.nextInt(9) + random.nextInt(9) + random.nextInt(9)
					+ random.nextInt(9);
		}
		return billNoStr;
	}

	/**
	 * 返回带基础参数的支付信息
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public PaymentInfo getPayInfo(String orderId, boolean isMobile)
			throws UnsupportedEncodingException {
		PaymentInfo entity = new PaymentInfo();

		entity.setBusi_partner(PartnerConfig.BUSI_PARTNER);
		entity.setDt_order(DateUtil.getCurrentDateTimeStr1());
		entity.setInfo_order(PartnerConfig.NAMES_GOODS);
		entity.setName_goods(PartnerConfig.NAMES_GOODS);
		entity.setNotify_url(PartnerConfig.NOTIFY_URL);
		entity.setOid_partner(PartnerConfig.OID_PARTNER);
		entity.setSign_type(PartnerConfig.SIGN_TYPE);
		if (isMobile) {
			entity.setUrl_return(PartnerConfig.URL_RETURN_MOBILE);
		} else {
			entity.setUrl_return(PartnerConfig.URL_RETURN);
		}

		entity.setOid_partner(PartnerConfig.OID_PARTNER);
		entity.setValid_order("10080");
		entity.setApp_request(PartnerConfig.APP_REQUEST);
		entity.setVersion("1.1");
		return entity;
	}

	/**
	 * 返回带基础参数的支付信息
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public MobilePaymentInfo getMobilePayInfo(String orderId, boolean isMobile)
			throws UnsupportedEncodingException {
		MobilePaymentInfo entity = new MobilePaymentInfo();

		entity.setBusi_partner(PartnerConfig.BUSI_PARTNER);
		entity.setDt_order(DateUtil.getCurrentDateTimeStr1());
		entity.setInfo_order(PartnerConfig.NAMES_GOODS);
		entity.setName_goods(PartnerConfig.NAMES_GOODS);
		entity.setNotify_url(PartnerConfig.NOTIFY_URL);
		entity.setOid_partner(PartnerConfig.OID_PARTNER);
		entity.setSign_type(PartnerConfig.SIGN_TYPE);
		entity.setOid_partner(PartnerConfig.OID_PARTNER);
		entity.setValid_order("10080");

		return entity;
	}

	public boolean onlineRecharge(String userId, String bankId,
			String rechargeAmount, String rechargeOrderId,
			String rechargeContinue, String ip, String newOrderId,
			int... params) {

		RechargeEntity bean = new RechargeEntity();
		bean.setRechargeAmount(rechargeAmount);
		bean.setBankId(bankId);
		bean.setUserId(userId);
		bean.setOrderId(newOrderId);
		setInitValue(bean);
		return mediator.onlineRecharge(createConditionBean(bean,
				rechargeContinue, rechargeOrderId, ip, params));
	}

	/**
	 * type:0为微信端充值 type:1移动端充值
	 * **/
	public String getPaymentInfoStr(MobilePaymentInfo info,
			UserIdentityCard identity, int type)
			throws UnsupportedEncodingException {

		RiskItem item = new RiskItem();

		// TODO:如果是已经认证过的卡
		if (identity != null) {
			item.setFrms_ware_category("2009");
			item.setUser_info_mercht_userno(info.getUser_id());
			item.setUser_info_id_no(identity.getCardId());
			item.setUser_info_full_name(identity.getRealName());
			item.setUser_info_id_type("0");
			item.setUser_info_identify_state("1");
			item.setUser_info_identify_type("1");
			item.setUser_info_dt_register(identity.getUserInfoDtRegister());

			info.setNo_agree(identity.getNoAgree());
		}

		String risk_item = JSON.toJSONString(item);
		info.setRisk_item(risk_item);

		// java.net.URLEncoder.encode("中国", "utf-8");
		String acctName = info.getAcct_name();// URLEncoder.encode(info.getAcct_name(),
												// encode);
		// String bgColor = info.getBg_color();
		String busiPartner = info.getBusi_partner();
		String cardNo = info.getCard_no();
		String dtOrder = info.getDt_order();
		String idNo = info.getId_no();
		String idType = info.getId_type();
		String infoOrder = info.getInfo_order();// URLEncoder.encode(info.getInfo_order(),
												// encode);
		String moneyOrder = info.getMoney_order();
		String nameGoods = info.getName_goods();// URLEncoder.encode(info.getName_goods(),
												// encode);
		String noAgree = info.getNo_agree();
		String noOrder = info.getNo_order();
		String notifyUrl = info.getNotify_url(); // URLEncoder.encode(info.getNotify_url(),
													// encode);
		String oidPartner = info.getOid_partner();
		String riskItem = info.getRisk_item();
		String signType = info.getSign_type();

		String appRequest = "";
		String urlReturn = "";
		String version = "";

		if (type == 0) {
			appRequest = ((PaymentInfo) info).getApp_request();
			urlReturn = ((PaymentInfo) info).getUrl_return(); // URLEncoder.encode(info.getUrl_return(),encode);
			version = ((PaymentInfo) info).getVersion();
		}

		String userId = info.getUser_id();
		String validOrder = info.getValid_order();

		StringBuffer strBuf = new StringBuffer();
		
		if (type == 0) {
			if (!FuncUtils.isNull(acctName)) {

				strBuf.append("acct_name=");
				strBuf.append(acctName);
				strBuf.append("&app_request=" + appRequest);
			} else {
				strBuf.append("app_request=" + appRequest);
			}
		}
		/*
		 * if (!FuncUtils.isNull(bgColor)) { strBuf.append("&bg_color=");
		 * strBuf.append(bgColor); }
		 */
		if(strBuf.toString().length()>1)
		{
			strBuf.append("&busi_partner=");
		}
		else
		{
			strBuf.append("busi_partner=");
		}
		
		strBuf.append(busiPartner);
		if (type == 0) {
			if (!FuncUtils.isNull(cardNo)) {
				strBuf.append("&card_no=");
				strBuf.append(cardNo);
			}
		}
		strBuf.append("&dt_order=");
		strBuf.append(dtOrder);

		if (type == 0) {
			if (!FuncUtils.isNull(idNo)) {
				strBuf.append("&id_no=");
				strBuf.append(idNo);
			}
		}

		if (type == 0) {
			if (!FuncUtils.isNull(idType)) {
				strBuf.append("&id_type=");
				strBuf.append(idType);
			}
		}
		if (!FuncUtils.isNull(infoOrder)) {
			strBuf.append("&info_order=");
			strBuf.append(infoOrder);
		}
		strBuf.append("&money_order=");
		strBuf.append(moneyOrder);
		if (!FuncUtils.isNull(nameGoods)) {
			strBuf.append("&name_goods=");
			strBuf.append(nameGoods);
		}
		if (type == 0) {
			if (!FuncUtils.isNull(noAgree)) {
				strBuf.append("&no_agree=");
				strBuf.append(noAgree);
			}
		}

		strBuf.append("&no_order=");
		strBuf.append(noOrder);
		strBuf.append("&notify_url=");
		strBuf.append(notifyUrl);
		strBuf.append("&oid_partner=");
		strBuf.append(oidPartner);
		if (!FuncUtils.isNull(riskItem)) {
			strBuf.append("&risk_item=");
			strBuf.append(riskItem);
		}
		strBuf.append("&sign_type=");
		strBuf.append(signType);

		if (type == 0) {
			if (!FuncUtils.isNull(urlReturn)) {
				strBuf.append("&url_return=");
				strBuf.append(urlReturn);
			}
		}
		if (type == 0) {
			if (!FuncUtils.isNull(userId)) {
				strBuf.append("&user_id=");
				strBuf.append(userId);
			}
		}
		if (!FuncUtils.isNull(validOrder)) {
			strBuf.append("&valid_order=");
			strBuf.append(validOrder);
		}
		String sign_src = strBuf.toString();
		if (sign_src.startsWith("&")) {
			sign_src = sign_src.substring(1);
		}
		String sign = "";

		if ("RSA".equals(signType)) {
			sign = RSAUtil.sign(PartnerConfig.TRADER_PRI_KEY, sign_src);
		} else {

			if (type == 0) {
				sign_src += "&version=" + version;
			}
			sign_src += "&key=" + PartnerConfig.MD5_KEY;

			logger.info(sign_src);
			sign = Md5Algorithm.getInstance().md5Digest(
					sign_src.getBytes("utf-8"));
		}

		info.setSign(sign);
		String req_data = JSON.toJSONString(info);
		return req_data;

	}

	/**
	 * @param para
	 * @param userId
	 * @param type
	 *            :0为微信,1:手机
	 * 
	 *            提交给连连支付需要的参数列表
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getPaymentString(RechargeParam para, String userId,
			String orderId) throws UnsupportedEncodingException {

		PaymentInfo info = this.getPayInfo(orderId, para.getType() == 1);
		info.setNo_order(orderId);
		info.setUser_id(userId);
		info.setMoney_order(para.getRechargeAmount());
		info.setDt_order(DateUtil.getCurrentDateTimeStr1());

		UserIdentityCard identity = getUserIdentityCard(userId,
				para.getCardId(), "");

		info.setCard_no(identity.getBankCardId());
		info.setAcct_name(identity.getRealName());
		info.setId_no(identity.getCardId());

		info.setId_type("0");
		info.setInfo_order(String.format("%s,%s", userId,
				identity.getBankCardId()));
		String reqStr = this.getPaymentInfoStr(info, identity, 0);
		return reqStr;
	}

	/**
	 * @param para
	 * @param userId
	 * @param type
	 *            :0为微信,1:手机
	 * 
	 *            提交给连连支付需要的参数列表
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getMobilePaymentString(RechargeParam para, String userId,
			String orderId) throws UnsupportedEncodingException {

		MobilePaymentInfo info = this.getMobilePayInfo(orderId,
				para.getType() == 1);
		info.setNo_order(orderId);
		info.setUser_id(userId);
		info.setMoney_order(para.getRechargeAmount());
		info.setDt_order(DateUtil.getCurrentDateTimeStr1());

		UserIdentityCard identity = getUserIdentityCard(userId,
				para.getCardId(), "");

		info.setCard_no(identity.getBankCardId());
		info.setAcct_name(identity.getRealName());
		info.setId_no(identity.getCardId());

		info.setId_type("0");
		info.setInfo_order(String.format("%s,%s", userId,
				identity.getBankCardId()));

		String reqStr = this.getPaymentInfoStr(info, identity, para.getType());
		return reqStr;
	}

	/**
	 * 此处通过USERS_ACCOUNT_BANK_MOBILE 表出数据，此处数据可以一对多 提现银行卡为一对一
	 * 
	 * @param userId
	 * @return
	 */
	public BankCardEntity getBankCardEntity(String userId) {
		SqlSession session = null;
		BankCardEntity result = null;
		try {

			session = this.getSession();
			result = new BankCardEntity();

			int count = session.selectOne(
					"com.jujin.mapper.QueryApproveRealName", userId);

			if (count > 0) {
				result.setRealName(true);
			} else {
				result.setRealName(false);
				return result;
			}

			List<UserBankCard> cards = session.selectList(
					"com.jujin.mapper.QueryUserBindCard", userId);
			result.setCards(cards);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	public String BindBankCardMobile(UserBankCard card) {

		SqlSession session = null;
		String result = "";
		try {
			session = this.getSession(true);
			if (card.getType() == 0) {
				result = BindCardRecharge(card, session);
			} else {
				result = BindCardWithdraw(card, session);
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result = "绑定卡的过程中发生错误，请重试";
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}

	/**
	 * 绑定充值卡
	 * 
	 * @param card
	 */
	private String BindCardRecharge(UserBankCard card, SqlSession session) {

		String result = "";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", card.getRealUserId());
		map.put("cardId", card.getRealCardId());
		int count = session
				.selectOne("com.jujin.mapper.QueryIsBindMobile", map);
		if (count > 0) {
			return "此卡已经绑定过";
		}
		session.insert("com.jujin.mapper.InsertUserBankCard", card);
		count = session.selectOne("com.jujin.mapper.QueryIsBindNormal", map);// QueryIsBindNormal
		// 判断是否在users_account_bank
		if (count == 0) {
			session.insert("com.jujin.mapper.InsertUserBankCardNormal", card);
		}
		pushMfa(card.getRealUserId(), card.getRealCardId());
		return result;
	}
	
	// 推送至MFA TODO
	private void pushMfa(String userId,String cardId) {
		try {
			//判断是否为第三方用户
			String platCode = mediator.checkIsThirdUser(userId);
			if (platCode == null) {
				return;
			}
			BankcardOperateNotifier un = new BankcardOperateNotifier();
			un.setUserId(userId);
			un.setBankcard(cardId);
			MfaResult result = MfaUtil.push(NoticeTypeConstants.BANKCARD_OPERATE_CHANGE.getType(),userId, un);
			if (result.isStatus()) {
				logger.info("绑卡后推送用户[" + userId + "]的绑卡信息到MFA成功");
			} else {
				logger.error("绑卡后推送用户[" + userId + "]的绑卡信息到MFA失败，错误:" + result.getMsg());
			}
		} catch (Exception e) {
			logger.error("绑卡后推送用户[" + userId + "]的绑卡信息到MFA失败", e);
		}
	}

	private String BindCardWithdraw(UserBankCard card, SqlSession session) {
		String result = "";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userId", card.getRealUserId());
		map.put("cardId", card.getRealCardId());
		int count = session
				.selectOne("com.jujin.mapper.QueryIsBindNormal", map);// QueryIsBindNormal

		if (count == 0) {
			session.insert("com.jujin.mapper.InsertUserBankCardNormal", card);
			pushMfa(card.getRealUserId(), card.getRealCardId());
		} else {
			return "此卡已经绑定过";
		}
		return result;
	}

	public void UpdateBankNoAgree(String userId, String cardId, String noAgree) {
		SqlSession session = null;
		try {
			session = this.getSession(true);

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("cardId", cardId);
			map.put("noAgree", noAgree);

			session.update("com.jujin.mapper.UpdateCardNoAgree", map);
			logger.info("更新 noAgree:" + noAgree);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public UserIdentityCard getUserIdentityCard(String userId, int recordId,
			String cardId) {

		SqlSession session = null;
		UserIdentityCard entity = null;
		try {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			if (recordId != 0)
				map.put("recordId", String.valueOf(recordId));
			if (!StringUtils.isEmpty(cardId))
				map.put("cardId", cardId);

			session = this.getSession();
			entity = session.selectOne(
					"com.jujin.mapper.QueryUserIdentityInfo", map);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return entity;

	}

	// 手续费
	protected String getFeeAmountByMediatot(String rechargeAmount) {
		return mediator.getFeeAmountForRecharge(rechargeAmount);
	}

	// 到账金额
	protected String getBalance(String feeStr, String rechargeAmount) {
		return StringUtils.objToString(NumberUtils.parseDouble(rechargeAmount)
				- NumberUtils.parseDouble(feeStr));
	}

	protected String getRechargeValidateKey(String billNo) {
		return DesCodeUtil.encrypt(billNo);
	}

	protected void getRechargeType(RechargeEntity entity) {

		Model<String, Object> bankInfo = getOnlineMobileBankInfoById(entity
				.getBankId());

		entity.setRechargeBankName(bankInfo.getStringValue("BANK_NAME"));
		entity.setConfig(bankInfo.getStringValue("MER_CONFIG"));
		entity.setBankConfig(bankInfo.getStringValue("BANK_CONFIG"));

	}

	protected void setInitValue(RechargeEntity entity) {

		entity.setFeeAmount(getFeeAmountByMediatot(entity.getRechargeAmount()));
		entity.setBalanceAmount(getBalance(entity.getFeeAmount(),
				entity.getRechargeAmount()));

		getRechargeType(entity);

		String config = entity.getConfig();
		String bankConfig = entity.getBankConfig();
		if (!StringUtils.isEmpty(config)) {
			String[] paramMer = config.split("&");
			for (String keyValue : paramMer) {
				if (!StringUtils.isEmpty(keyValue)) {
					String[] conf = keyValue.split("=");
					if ("Mer_code".equals(conf[0])) {
						entity.setMerCode(conf[1]);
					}
					if ("Mer_key".equals(conf[0])) {
						entity.setMerKey(conf[1]);
					}
					if ("pay_cd".equals(conf[0])) {
						entity.setPaycd(conf[1]);
					}
					if ("Mer_id".equals(conf[0])) {
						entity.setMerId(conf[1]);
					}
				}
			}
		}

		if (!StringUtils.isEmpty(bankConfig)) {
			String[] paramMer = bankConfig.split("&");
			for (String keyValue : paramMer) {
				if (!StringUtils.isEmpty(keyValue)) {
					String[] conf = keyValue.split("=");
					// if ("bank_id".equals(conf[0])) {
					// bankId = conf[1];
					// }
					if ("Mer_code".equals(conf[0])) {
						entity.setMerCode(conf[1]);
					}
					if ("Mer_key".equals(conf[0])) {
						entity.setMerKey(conf[1]);
					}

					if ("Mer_id".equals(conf[0])) {
						entity.setMerId(conf[1]);
					}
				}
			}
		}
	}

	protected RechargeBean createConditionBean(RechargeEntity bean,
			String rechargeOrderId, String rechargeContinue, String ip,
			int... params) {
		RechargeBean saveBean = new RechargeBean();

		String userId = bean.getUserId();

		saveBean.setUserId(userId);

		String rechargeAmount = bean.getRechargeAmount();
		String feeAmount = bean.getFeeAmount();
		String balanceAmount = bean.getBalanceAmount();
		String rechargeBankName = bean.getRechargeBankName();
		String bankId = bean.getBankId();
		String currencyType = "RMB";
		String billNoAutoStr = bean.getOrderId();

		if ("1".equals(rechargeContinue)) {
			saveBean.setOrderId(rechargeOrderId);
			saveBean.setContinueOrderId(billNoAutoStr);
		} else {
			saveBean.setOrderId(billNoAutoStr);
		}
		saveBean.setRechargeStatus("1");
		saveBean.setRechargeAmount(rechargeAmount);
		saveBean.setFeeAmount(feeAmount);
		saveBean.setBalanceAmount(balanceAmount);
		saveBean.setPayment(rechargeBankName);
		saveBean.setRtnValue("1");
		saveBean.setRechargeType("0");
		saveBean.setRemark("用户账号线上充值");
		saveBean.setInsIp(ip);
		saveBean.setInsUserId(userId);
		saveBean.setUpdUserId(userId);
		saveBean.setBankId(bankId);
		saveBean.setRechargeContinue(rechargeContinue);
		String rechargeAmountStr = rechargeAmount;
		if (params != null && params.length > 0) {
			double operatorAmount = NumberUtils.parseDouble(rechargeAmount);
			operatorAmount = operatorAmount * params[0];
			rechargeAmountStr = NumberUtils.subZeroAndDot(StringUtils
					.objToString(operatorAmount));
		}
		saveBean.setRechargeValidateKey(saveBean.getOrderId()
				+ rechargeAmountStr + currencyType);

		return saveBean;
	}

	private Model<String, Object> getOnlineMobileBankInfoById(String bankId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T2.NAME || '-' || T.NAME AS BANK_NAME, ");
		sql.append(" T2.CONFIG AS MER_CONFIG, ");
		sql.append(" T.CONFIG AS BANK_CONFIG ");
		sql.append("  FROM PAYMENT_MOBILE T ");
		sql.append("  LEFT JOIN PAYMENT_MOBILE T2 ON T.PARENT_ID = T2.ID ");
		sql.append(" WHERE T.ID = ").append(bankId);
		ModelList<Model<String, Object>> resultList = mediator.executeQuery(sql
				.toString());
		if (resultList != null && resultList.size() > 0) {
			return resultList.get(0);
		} else {
			return new Model<>();
		}
	}

	public void freeCard(String cardId, int type) {

		SqlSession session = null;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			session = this.getSession(true);
			map.put("userId", cardId);
			map.put("cardId", String.valueOf(type));
			if (type == 0) {
				session.update("com.jujin.mapper.FreeRechargeCard", map);
			} else {
				session.update("com.jujin.mapper.FreeWithDrawCard", map);
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
