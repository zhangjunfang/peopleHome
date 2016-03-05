/**   
 * @author wangning
 * @date 2015年3月6日 上午9:57:59 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.controller;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jujin.biz.AttestBiz;
import com.jujin.biz.HomeBiz;
import com.jujin.biz.TicketBiz;
import com.jujin.common.Constants;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.UserAccountInfo;
import com.jujin.entity.UserAttestation;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.borrow.Borrow;
import com.jujin.entity.borrow.BorrowInfo;
import com.jujin.entity.borrow.BorrowInfoWithAccount;
import com.jujin.entity.borrow.BorrowInvestIndicate;
import com.jujin.entity.borrow.HomeEntity;
import com.jujin.entity.borrow.KeyValuePair;
import com.jujin.entity.borrow.TenderBean;
import com.jujin.entity.borrow.TenderLog;
import com.jujin.entity.borrow.WeiXinEntity;
import com.jujin.entity.ticket.Ticket;
import com.jujin.entity.xglc.XglcUserTender;
import com.jujin.util.xglc.SignUtil;
import com.jujin.utils.ExceptionHelper;
import com.jujin.utils.HttpTookit;
import com.jujin.utils.HttpUtil;
import com.mfa.util.notifier.MfaUtil;
import com.pro.common.model.Model;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.borrow.information.bean.BorrowTenderBean;
import com.wicket.loan.web.borrow.information.mediator.BorrowTenderMediator;
import com.wicket.loan.web.common.bean.UsersOperationLogBean;
import com.wicket.loan.web.person.account.mediator.UserAccountInfoMediator;

/**
 * 
 */
@Controller
public class HomeControler extends BaseController {

	AttestBiz attestbiz = new AttestBiz();
	HomeBiz biz = new HomeBiz();

	// update by wangning 投资奖励保留两位小数
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.0#");
	TicketBiz ticketBiz = new TicketBiz();

	BorrowTenderMediator mediator = FWBeanManager
			.getBean(BorrowTenderMediator.class);

	UserAccountInfoMediator uaMediator = FWBeanManager
			.getBean(UserAccountInfoMediator.class);

	// 可用的标信息
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public @ResponseBody
	Object getExpressBorrow(HttpServletRequest request) {

		logger.info(String.format("获取标的简约信息  "));

		JsonList<Borrow> logs = null;
		try {
			logs = biz.getExpressBorrow();
			logs.setStatus(true);

			String userId = this.getLoginedUserId(request);
			if (!StringUtils.isEmpty(userId)) {
				logs.setLoginstatus(1);
			} else {
				logs.setLoginstatus(0);
			}

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (logs == null)
				logs = new JsonList<Borrow>();
			logs.setStatus(false);
			logs.setMsg(String.format("获取标的简约信息异常,请与客服联系"));
		}
		return logs;
	}

	@RequestMapping(value = "/whome", method = RequestMethod.GET)
	public @ResponseBody
	Object getHomeBorrow(HttpServletRequest request) {
		logger.info(String.format(" 首页标的信息 "));
		HomeEntity entity = new HomeEntity();
		try {

			List<Borrow> borrows = biz.getHomeBorrow();
			entity.setBorrows(borrows);

			String userId = this.getLoginedUserId(request);

			try {
				String url = "http://m.jujinziben.com/m/jujin/";
				url = "http://m.jujinziben.com"
						+ "/WeiXinServer/ShareServlet?url=" + url; // encodeURIComponent(userUrl);
				String jsonStr = HttpTookit.doGet(url);
				JSONObject obj = JSONObject.parseObject(jsonStr);
				WeiXinEntity wxEntity = (WeiXinEntity) JSONObject.toJavaObject(
						obj, WeiXinEntity.class);

				entity.setEntity(wxEntity);
			} catch (Exception e) {
				logger.error(ExceptionHelper.getExceptionDetail(e));
			}

			if (!StringUtils.isEmpty(userId)) {
				entity.setLoginstatus(1);
				entity.setUserId(userId);
				UserAttestation ua = attestbiz.QueryUserAttestation(userId);
				if (ua.getMobileFlg() == 1) {
					entity.setPhone(ua.getRealMobile());
				}
			} else {
				entity.setLoginstatus(0);
			}
			entity.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			entity.setStatus(false);
			entity.setMsg(String.format("获取首页标的信息异常,请与客服联系"));
		}
		return entity;
	}

	// 可用的标信息
	@RequestMapping(value = "/borrowindicate", method = RequestMethod.GET)
	public @ResponseBody
	Object getBorrowIndicate(HttpServletRequest request) {

		logger.info(String.format("获取标的投资状态信息  "));
		JsonList<BorrowInvestIndicate> result = new JsonList<BorrowInvestIndicate>();
		List<BorrowInvestIndicate> status = null;
		try {
			status = biz.getBorrowStatus();
			if (null != status) {
				result.addRange(status);
			}
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("获取标的投资状态信息,请与客服联系"));
		}
		return result;
	}

	/**
	 * 根据标类型获取标的信息
	 * 
	 * @param type
	 *            0:新专享,1:散标列表,2:聚金U选
	 * @param pi
	 * @param ps
	 * @return
	 */
	@RequestMapping(value = "/borrow", method = RequestMethod.GET)
	public @ResponseBody
	Object getBorrowsByType(
			@RequestParam(value = "id", required = true) int type, int pi,
			int ps) {
		logger.info(String.format("获取类型为[%s]的标信息  ", type));
		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}

		JsonList<Borrow> borrows = null;
		if (type < 0 || type > 2) {
			if (borrows == null)
				borrows = new JsonList<Borrow>();
			borrows.setStatus(false);
			borrows.setMsg(String.format("0:债权转让,1:散标列表,2:聚金U选, 标的类型不正确,请重新输入"));
			return borrows;
		}
		try {
			borrows = biz.getBorrowsByType(type, pi, ps);
			borrows.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (borrows == null)
				borrows = new JsonList<Borrow>();
			borrows.setStatus(false);
			borrows.setMsg(String.format("获取类型为[%s]的标信息异常,请与客服联系", type));
		}
		return borrows;
	}

	// 标的详细信息查看
	@RequestMapping(value = "/borrowinfo", method = RequestMethod.GET)
	public @ResponseBody
	Object getBorrowInfo(
			@RequestParam(value = "id", required = true) String borrowId,
			HttpServletRequest request) {
		logger.info(String.format("获取标 [%s] 的详细信息   ", borrowId));

		OpEntityResult<BorrowInfoWithAccount> entity = null;
		try {
			BorrowInfo info = biz.getBorrowInfo(borrowId);
			BorrowInfoWithAccount bia = new BorrowInfoWithAccount();
			bia.setBorrow(info);

			String userId = this.getLoginedUserId(request);
			if (!StringUtils.isEmpty(userId)) {
				UserAccountInfo accountInfo = biz.GetUserAccountInfo(userId);
				if (accountInfo != null) {
					bia.setAccount(accountInfo);
				}
			}
			entity = new OpEntityResult<BorrowInfoWithAccount>(bia);
			entity.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (entity == null) {
				entity = new OpEntityResult<BorrowInfoWithAccount>(null);
			}
			entity.setStatus(false);
			entity.setMsg(String.format("获取标 [%s] 的详细信息 失败,请与客服联系 ", borrowId));
		}
		return entity;
	}

	// 标的详细信息查看
	@RequestMapping(value = "/tender", method = RequestMethod.GET)
	public @ResponseBody
	Object getTenderLogByBorrowId(
			@RequestParam(value = "id", required = true) String borrowId,
			int pi, int ps) {
		logger.info(String.format("获取标 [%s] 的投标记录信息   ", borrowId));
		JsonList<TenderLog> logs = null;
		try {
			logs = biz.getTenderLogByBorrowId(borrowId, pi, ps);
			if (logs != null)
				logs.setStatus(true);
			else {
				logs = new JsonList<TenderLog>();
				logs.setStatus(true);
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (logs == null) {
				logs = new JsonList<TenderLog>();
			}
			logs.setStatus(false);
			logs.setMsg(String.format("获取标 [%s] 的投标记录 失败,请与客服联系 ", borrowId));
		}
		return logs;
	}

	// 投标方法
	@RequestMapping(value = "/tend", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object tenderBorrow(HttpEntity<TenderBean> entity,
			HttpServletRequest request) {

		OpResult result = new OpResult();

		TenderBean tbBean = null;

		if (entity == null || (tbBean = entity.getBody()) == null) {
			result.setMsg("提交数据格式有误,请检查");
			result.setStatus(false);
			return result;
		}
		String userId = this.getLoginedUserId(request);
		if (StringUtils.isEmpty(userId)) {
			return onEntityIsNull(result, request, SystemConfig.NO_LOGIN);
		}
		Model<String, Object> userAccountInfo = mediator
				.getUserAccountInfo(userId);
		if (null == userAccountInfo) {
			result.setStatus(false);
			result.setMsg("指定的用户不存在或已注销");
			return result;
		}
		tbBean.setUserId(userId);
		String borrowId = tbBean.getBorrowId();

		String tenderMsg = String.format(
				"用户  [%s] 对标 [%s] 投资 [%s]元  加息券 [%s] 聚金币[%s]",
				tbBean.getUserId(), tbBean.getBorrowId(),
				tbBean.getTenderAccount(), tbBean.getTicketId(),
				tbBean.getCoin());

		logger.info(tenderMsg);

		String ticketId = tbBean.getTicketId();
		Ticket ticket = null;
		if (!StringUtils.isEmpty(ticketId)) {
			ticket = ticketBiz.QueryTicket(userId, ticketId);
			if (ticket == null) {
				result.setStatus(false);
				result.setMsg("您选择的加息券已失效或过期");
				return result;
			}
		}

		boolean isTransfer = biz.QueryBorrowTransfer(borrowId);
		Model<String, Object> borrowInfo = null;
		if (isTransfer) {
			borrowInfo = mediator.selectTransferBorrowInfo(borrowId);
		} else {
			borrowInfo = mediator.selectBorrowInfo(borrowId);
		}

		/*
		 * private BorrowTenderBean createConditionBean() { double tenderMoney =
		 * NumberUtils
		 * .parseDouble(UserUtils.clearMoneyDecimal(txtTenderAccount.getValue
		 * ())); BorrowTenderBean bean = new BorrowTenderBean();
		 * bean.setIsBuyTransferFlg("1");//投资债权转让
		 * bean.setUserId(appSession.getStringValue
		 * (PageParamConstant.LOGIN_ID));
		 * bean.setBorrowId(StringUtils.objToString
		 * (DesCodeUtil.decrypt(getParameter
		 * (DesCodeUtil.encrypt("borrow_id")))));
		 * bean.setTenderAmount(tenderMoney);
		 * bean.setLoginIp(requestContext.getClientIp());
		 * bean.setTransferAccount(calcCapitalFromTransAmount(tenderMoney));
		 * 
		 * return bean; }
		 */

		BorrowTenderBean bean = new BorrowTenderBean();
		bean.setUserId(userId);
		bean.setBorrowId(tbBean.getBorrowId());
		bean.setTenderAmount(Double.valueOf(UserUtils.clearMoneyDecimal(tbBean
				.getTenderAccount())));

		if (isTransfer) {
			bean.setIsBuyTransferFlg("1");// 投资债权转让
			double tran_capital = borrowInfo.getDoubleValue("TRANSFER_CAPITAL");// 转让本金
			double borrow_amount = borrowInfo.getDoubleValue("BORROW_ACCOUNT");// 转让价格
			double discount_amount = borrowInfo
					.getDoubleValue("DISCOUNT_AMOUNT");// 折让金额
			bean.setTransferAccount(calcCapitalFromTransAmount(tran_capital,
					borrow_amount, bean.getTenderAmount()));
		} else {
			double tenderAccount = NumberUtils.parseDouble(tbBean
					.getTenderAccount());
			double vouRate = NumberUtils.parseDouble(borrowInfo
					.getStringValue("VOUCHERS_RATE"));

			double maxCoin = NumberUtils.parseDouble(userAccountInfo
					.getStringValue("COIN"));

			double useCoin = tbBean.getCoin();

			if (tenderAccount * vouRate > maxCoin) {
				maxCoin = tenderAccount * vouRate;
				maxCoin = Double.parseDouble(df.format(maxCoin));// 保留两位小数
			}
			if (maxCoin < useCoin) {
				result.setStatus(false);
				result.setMsg("使用的聚金币大于可用金额");
				return result;
			}
			bean.setCoinAmount(useCoin);
		}

		// 检测是否超过最大投标次数
		if (mediator.checkInvestMaxTimes(bean)) {
			result.setMsg("对不起您已经达到本标的个人最大投标次数。请您选择其他标进行投标。");
			result.setStatus(false);
			logger.warn(tenderMsg + " 数据校验未通过");
			return result;
		}
		String borrowFlag = borrowInfo.getStringValue("BORROW_FLAG");
		if ("1".equals(borrowFlag)) {
			if (!mediator.checkIsNew(bean)) {
				result.setMsg("对不起，新专享是针对新用户，您不符合此要求，请您选择其他标进行投标。");
				result.setStatus(false);
				logger.warn(tenderMsg + " 数据校验未通过");
				return result;
			}
		}
		if ("1".equals(borrowFlag)) {
			if (bean.getTenderAmount() > 1000) {

				logger.warn(tenderMsg + " 超出新专项额度");
				result.setMsg("对不起，新手专享项目，单笔限额1000元，请核对投标金额。");
				return result;
			}
		}

		List<String> errList = inputCheck(borrowInfo, userId,
				tbBean.getDirectionalPWD(), tbBean.getTenderAccount(),
				userAccountInfo);

		if (errList != null && errList.size() > 0) {

			StringBuilder errMsgs = new StringBuilder();
			for (String errMsg : errList) {
				errMsgs.append(errMsg);
			}
			result.setMsg(errMsgs.toString());
			result.setStatus(false);

			logger.warn(tenderMsg + " 数据校验未通过");
			return result;
		}
		bean.setOpType("01");

		if (ticket != null) {
			bean.setTicketId(ticket.getTicketId());
			bean.setTicketCount("1");
			bean.setTicketRate(ticket.getTicketValue());
		}
		// 聚金宝不使用聚金币和聚金券
		// 债权转让的时候不适用聚金币和聚金券

		if (borrowInfo.getStringValue("BORROW_TYPE").equals("10")
				|| borrowInfo.getStringValue("TRANSFER_FLG").equals("1")) {
			bean.setTicketId(null);
			bean.setTicketId(null);
			bean.setTicketRate(0);
			bean.setCoinAmount(0);
		}

		String xglcNotifyUrl = "";
		if (mediator.editBorrowTender(bean)) {
			result.setStatus(true);
//			if ("1".equals(tbBean.getIsXglc())) {
//				xglcNotifyUrl = NotifyXglc(request.getSession(), userId,
//						borrowId, "success");
//				System.out.println("XGLC-TEND-SUC:" + xglcNotifyUrl);
//			}
			
			getCallback(tbBean.getUserId(), "1", "tender");
			
			logger.info(tenderMsg + " 投资成功！");
		} else {
//			if ("1".equals(tbBean.getIsXglc())) {
//				xglcNotifyUrl = NotifyXglc(request.getSession(), userId,
//						borrowId, "fail");
//				System.out.println("XGLC-TEND-FAIL:" + xglcNotifyUrl);
//			}
			getCallback(tbBean.getUserId(), "0", "tender");
			logger.warn(tenderMsg + " 投标过程中产生未知错误,请核查账户余额是否扣除,若扣除请与客服管理人员联系。");
			result.setMsg("投标过程中产生未知错误,请核查账户余额是否扣除,若扣除请与客服管理人员联系。");
		}

		logger.info(String.format("IsXglc: %s;xglcNotifyUrl:%s",
				tbBean.getIsXglc(), xglcNotifyUrl));
		result.setReturnUrl(xglcNotifyUrl);
		try {
			String url = String
					.format("https://www.jujinziben.com/tzjnotifyservice?id=%s&userid=%s",
							borrowId, userId);
			logger.info(url);
			String contentString = HttpTookit.doGet(url);
			logger.info(contentString);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		return result;
	}

	private String getCallback(String userId, String status,String type) {
		String platCode = mediator.checkIsThirdUser(userId);
		if (platCode == null) {
			logger.info("普通用户取消回调");
			return "";
		}
		String retUrl = "";
		Map<String, String> hmap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("channel", platCode);
		map.put("userId", userId);
		map.put("status", status);
		retUrl = HttpUtil.get(MfaUtil.CALLBACK_URL,
				hmap, map);
		if ("ERROR".equals(retUrl.substring(0, 5))) {
			logger.error("回调失败:" + retUrl);
			retUrl = "";
		}
		return retUrl;
	}

	/**
	 * 西瓜理财只在移动端呈现
	 * **/
	private String NotifyXglc(HttpSession session, String userId,
			String borrowId, String status) {
		try {
			String returnUrl = (String) session
					.getAttribute(Constants.XGLC_RETURN_URL);
			String orderSn = (String) session
					.getAttribute(Constants.XGLC_XG_ORDER_SN);

			String orderId = "fail";
			if ("success".equals(status)) {
				orderId = biz.getTenderSeq(userId, borrowId);
			}
			String signStr = orderId + status + orderSn + SignUtil.signKey;
			String sign = SignUtil.encryptMD5(signStr);

			XglcUserTender record = new XglcUserTender();
			record.setBorrowId(borrowId);
			record.setUserId(userId);
			record.setXgOrderSn(orderSn);
			record.setUserBorId(orderId);

			biz.AddXglcUserRecord(record);
			// 签名
			return returnUrl + "?orderId="
					+ URLEncoder.encode(orderId, "utf-8")
					+ "&tradeResultStatus="
					+ URLEncoder.encode(status, "utf-8") + "&xgOrderSn="
					+ URLEncoder.encode(orderSn, "utf-8") + "&sign="
					+ URLEncoder.encode(sign, "utf-8");

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		return "";
	}

	private double calcCapitalFromTransAmount(double tran_capital,
			double borrow_amount, double transferAmount) {

		double capital;
		// 包含本金 / 全部债权的本金 = 投资金额 / 全部债权总金额
		capital = tran_capital * transferAmount / borrow_amount;
		// // 当前日期
		// String nowDate = DateUtils.getSystemYYYYMMDD();
		// // 本期剩余天数
		// double lastDays = NumberUtils.calcDoubleSub(30.0D,
		// UserUtils.getDaysBetween2Date(nowDate, this.currentRecoverTime));
		//
		// // 债权本金 = 购买金额 / (1 - 折让比例 + 剩余天数的利率)
		// // 折让比例
		// double discountRate = NumberUtils.calcDoubleDiv(this.discount_amount,
		// this.tran_capital);
		// // 应收利息比例
		// double investRate =
		// NumberUtils.calcDoubleDiv(NumberUtils.calcDoubleMul(rate, lastDays),
		// 360.0D, 9);
		// // 转让金额比例
		// double transferRate = NumberUtils.calcDoubleAdd(investRate,
		// NumberUtils.calcDoubleSub(1.0D, discountRate));
		// double capital = NumberUtils.calcDoubleDiv(transferAmount,
		// transferRate);
		// double capital = transferAmount / (1.0D - (this.discount_amount /
		// this.tran_capital) + rate * lastDays / 360.0D);
		capital = NumberUtils.decimalConverter(capital, 2,
				BigDecimal.ROUND_HALF_UP);

		return capital;
	}

	private List<String> inputCheck(Model<String, Object> borrowModel,
			String userId, String directPwd, String tenderAccount,
			Model<String, Object> userAccountInfo) {

		List<String> errList = new ArrayList<>();
		if (borrowModel.isEmpty()) {
			errList.add("此借款标不存在，请选择其他借款标或与客服联系。");
			return errList;
		}

		double minAmount = NumberUtils.parseDouble(borrowModel
				.getStringValue("TENDER_ACCOUNT_MIN"));
		double maxAmount = NumberUtils.parseDouble(borrowModel
				.getStringValue("TENDER_ACCOUNT_MAX"));
		String directionalPWD = borrowModel.getStringValue("DIRECTIONAL_PWD");

		if ("1".equals(borrowModel.getStringValue("IS_DAY"))) {
			// 待收总额
			double dueinAmount = NumberUtils.parseDouble(borrowModel
					.getStringValue("DUEIN_AMOUNT"));
			double checkDueinAmount = 0;
			// 投资总额
			double investTotalAmount = NumberUtils.parseDouble(borrowModel
					.getStringValue("INVEST_TOTAL_AMOUNT"));
			double checkInvestTotalAmount = 0;

			Model<String, Object> modelCheck = mediator
					.checkTenderUserAmount(userId);

			if (!modelCheck.isEmpty()) {
				checkDueinAmount = NumberUtils.parseDouble(modelCheck
						.getStringValue("AWAIT"));
				checkInvestTotalAmount = NumberUtils.parseDouble(modelCheck
						.getStringValue("TOTAL_INVEST"));
			}

			if (dueinAmount > 0) {
				if (dueinAmount > checkDueinAmount) {
					errList.add("借款方对投资人待收总额进行设定，您无法对此标进行投资");
				}
			}

			if (investTotalAmount > 0) {
				if (investTotalAmount > checkInvestTotalAmount) {
					errList.add("借款方对投资人投资总额进行设定，您无法对此标进行投资");
				}
			}
		}
		// 定向密码
		if (!StringUtils.isEmpty(borrowModel.getStringValue("DIRECTIONAL_PWD"))) {
			if (!directionalPWD.equals(directPwd)) {
				errList.add("输入的定向密码不正确，请与借款人联系获取定向密码。");
			}
		}
		// 投资金额
		if (StringUtils.isEmpty(tenderAccount)) {
			errList.add("没有输入投资金额，请输入投资金额。");
		} else if (!UserUtils.isDecimalNumber(UserUtils
				.clearMoneyDecimal(tenderAccount))) {
			errList.add("输入的投资金额必须是数字，请确认后重新输入。");
		} else if (NumberUtils.parseDouble(UserUtils
				.clearMoneyDecimal(tenderAccount)) < minAmount) {
			errList.add("输入的投资金额小于借款者指定的最小投资金额，请确认后重新输入。");
		}
		// 已投资金额取得
		double tenderAmount = mediator.getTenderTotalAmount(userId,
				borrowModel.getStringValue("BORROW_ID"));
		if (maxAmount > 0
				&& (NumberUtils.parseDouble(UserUtils
						.clearMoneyDecimal(tenderAccount)) + tenderAmount) > maxAmount) {
			if (0 == tenderAmount) {
				errList.add("您的投资总额大于借款者指定的最大总投资金额，请确认后重新输入。");
			} else {
				errList.add("您已投资￥"
						+ tenderAmount
						+ ",您的剩下的最大投资总额￥"
						+ (NumberUtils.parseDouble(UserUtils
								.clearMoneyDecimal(tenderAccount)) - tenderAmount)
						+ "，请确认后重新输入。");
			}

		}

		logger.info("BALANCE：" + userAccountInfo.getStringValue("BALANCE"));
		logger.info("CONTINUE_TOTAL"
				+ userAccountInfo.getStringValue("CONTINUE_TOTAL"));
		logger.info("tenderAccount"
				+ UserUtils.clearMoneyDecimal(tenderAccount));

		if ((NumberUtils.parseDouble(userAccountInfo.getStringValue("BALANCE")) + NumberUtils
				.parseDouble(userAccountInfo.getStringValue("CONTINUE_TOTAL"))) < NumberUtils
				.parseDouble(UserUtils.clearMoneyDecimal(tenderAccount))) {
			errList.add("账户余额不足,请先充值后进行投标。");
		}
		if (!"0".equals(NumberUtils.convToZero(tenderAccount))) {
			if (mediator.getTenderValidAccont(
					borrowModel.getStringValue("BORROW_ACCOUNT_WAIT"),
					UserUtils.clearMoneyDecimal(tenderAccount)) == 0) {
				errList.add("此借款标已满标,无法进行投标。");
			}
		}
		return errList;
	}

	/**
	 * 
	 * 获取用户资金信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accountinfo", method = RequestMethod.GET)
	public @ResponseBody
	Object getAccountInfo(HttpServletRequest request) {

		OpEntityResult<UserAccountInfo> result = new OpEntityResult<UserAccountInfo>(
				null);
		onUserIdIsNull(result, request);
		if (!result.isStatus()) {
			return result;
		}

		String userId = this.getLoginedUserId(request);
		logger.info(String.format(" 用户[%s]账户资金信息  ", userId));
		UserAccountInfo entity = null;

		try {
			entity = biz.GetUserAccountInfo(userId);
			result.setEntity(entity);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("账户资金信息异常,请与客服联系"));
		}
		return result;
	}

	/**
	 * 
	 * U计划债权明细
	 * 
	 * @param borrowId
	 * @param pi
	 * @param ps
	 * @return
	 */
	@RequestMapping(value = "/uplantenderlog", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryUplanLog(
			@RequestParam(value = "id", required = true) String borrowId,
			int pi, int ps) {

		logger.info(String.format("获取聚金优选 [%s] 的债权信息   ", borrowId));
		JsonList<TenderLog> logs = null;

		try {
			logs = biz.QueryUplanLog(borrowId, pi, ps);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (logs == null) {
				logs = new JsonList<TenderLog>();
				logs.setStatus(false);
				logs.setMsg("获取债权信息失败，请重试");
			}
		}
		return logs;

	}

	// 加入余额生息
	@RequestMapping(value = "/joininterest", method = RequestMethod.GET)
	public @ResponseBody
	Object joinInterest(HttpServletRequest request) {

		OpResult result = new OpResult();
		if (request == null) {
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
			return result;
		}
		String userId = this.getLoginedUserId(request);

		if (StringUtils.isEmpty(userId)) {
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		try {
			UserAttestation entity = attestbiz.QueryUserAttestation(userId);
			if (entity.getIdFlg() == 1) {
				UsersOperationLogBean bean = new UsersOperationLogBean();
				bean.setInsUserId(userId);
				bean.setContent(String.format("用户 %s 开通余额生息", userId));
				bean.setOperating("U");
				bean.setOperationCode("201");
				bean.setInsIp(this.getIpAddr(request));
				bean.setUserId(userId);
				bean.setOpType("01");
				boolean status = uaMediator.joinInterest(bean);
				if (status) {
					result.setStatus(true);
				} else {
					result.setStatus(false);
					result.setMsg("开通余额生息出现错误，请重试或与客服联系。");
				}
			} else {
				result.setStatus(false);
				result.setMsg("NOATTESTIDENTITY");// 未实名
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("开通余额生息出现错误，请重试或与客服联系。");
		}
		return result;
	}

	@RequestMapping(value = "/jujinbaoconfig", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryJuJinBaoConfig(HttpServletRequest request) {

		JsonList<KeyValuePair> result = null;
		try {

			result = biz.QueryJuJinBaoConfig();
			if (result != null) {
				result.setStatus(true);
			}

		} catch (Exception ex) {

			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("获取聚金宝配置出现错误，请重试或与客服联系。");
		}
		return result;

	}

}
