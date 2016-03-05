package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException;
import org.apache.poi.ss.formula.eval.OperandResolver;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jujin.biz.AttestBiz;
import com.jujin.biz.RechargeBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.common.VerifyType;
import com.jujin.entity.UserAttestation;
import com.jujin.entity.account.UserBankCard;
import com.jujin.entity.pay.lianlian.MobilePaymentInfo;
import com.jujin.entity.pay.lianlian.PartnerConfig;
import com.jujin.entity.pay.lianlian.PayResult;
import com.jujin.entity.recharge.BankCardEntity;
import com.jujin.entity.recharge.RechargeParam;
//import com.jujin.lianlian.util.YinTongUtil;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.FWBeanManager;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.person.recharge.mediator.RechargeMediator;

@Controller
public class RechargeController extends BaseController {

	RechargeMediator mediator = FWBeanManager.getBean(RechargeMediator.class);
	RechargeBiz biz = new RechargeBiz();

	AttestBiz attestBiz = new AttestBiz();

	// 返回用户绑定的银行卡号
	@RequestMapping(value = "/channel", method = RequestMethod.GET)
	public @ResponseBody
	Object channel(HttpServletRequest request) {

		OpEntityResult<BankCardEntity> result = new OpEntityResult<BankCardEntity>(
				null);

		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		String userId = this.getLoginedUserId(request);
		try {

			result.setEntity(biz.getBankCardEntity(userId));
			result.setStatus(true);

		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("获取用户绑定银行卡信息失败，请与客服联系");
		}
		return result;
	}

	@RequestMapping(value = "/freecard", method = RequestMethod.GET)
	public @ResponseBody
	Object freeCard(
			@RequestParam(value = "cardId", required = true) String cardId,
			@RequestParam(value = "type", required = true) int type,
			HttpServletRequest request) {
		OpResult result = new OpResult();

		try {
			if (StringUtils.isEmpty(cardId) || (!(type == 0 || type == 1))) {
				result.setStatus(false);
				result.setMsg(SystemConfig.ERROR_FORMAT);
				return result;
			}
			biz.freeCard(cardId, type);

		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("解除绑定失败,请与客服联系");
		}
		return result;
	}

	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public @ResponseBody
	Object recharge(HttpEntity<RechargeParam> entity, HttpServletRequest request) {

		OpEntityResult<String> result = new OpEntityResult<String>("");
		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		RechargeParam para = entity.getBody();

		String userId = this.getLoginedUserId(request);
		if (!validateCommonBean(para, result).isStatus()) {
			return result;
		}

		try {
			String newOrderId = biz.getBillNoNew();
			String reqStr = biz.getPaymentString(para, userId, newOrderId);
			logger.info(String.format("提交的参数:[%s]", reqStr));

			boolean status = biz.onlineRecharge(userId, para.getBankId(),
					para.getRechargeAmount(), para.getRechargeOrderId(),
					para.getRechargeContinue(), this.getIpAddr(request),
					newOrderId);
			result.setStatus(status);

			if (status) {
				logger.info(String.format("UserID:[%s] 生成投资记录成功", userId));
				result.setEntity(reqStr);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("在线充值失败，请重试。");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/mobilerecharge", method = RequestMethod.POST)
	public @ResponseBody
	Object mobilerecharge(HttpEntity<RechargeParam> entity, HttpServletRequest request) {

		OpEntityResult<String> result = new OpEntityResult<String>("");
		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		RechargeParam para = entity.getBody();

		String userId = this.getLoginedUserId(request);
		if (!validateCommonBean(para, result).isStatus()) {
			return result;
		}

		try {
			String newOrderId = biz.getBillNoNew();
			String reqStr = biz.getMobilePaymentString(para, userId, newOrderId);
			logger.info(String.format("提交的参数:[%s]", reqStr));

			boolean status = biz.onlineRecharge(userId, para.getBankId(),
					para.getRechargeAmount(), para.getRechargeOrderId(),
					para.getRechargeContinue(), this.getIpAddr(request),
					newOrderId);
			result.setStatus(status);

			if (status) {
				logger.info(String.format("UserID:[%s] 生成投资记录成功", userId));
				result.setEntity(reqStr);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("在线充值失败，请重试。");
		}
		return result;
	} 

	private OpResult validateCommonBean(RechargeParam para, OpResult result) {
		if (result == null) {
			result = new OpResult();
			result.setStatus(false);
		}
		if (para == null
				|| NumberUtils.parseDouble(NumberUtils.decimalFormat(para
						.getRechargeAmount())) < 0
				|| StringUtils.isEmpty(para.getBankId())) {
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
		}
		result.setStatus(true);
		return result;
	}

	private OpResult validateBindCardBean(UserBankCard card, OpResult result) {
		if (result == null) {
			result = new OpResult();
			result.setStatus(false);
		}
		if (card == null || !card.isValidate()) {
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
			return result;
		}
		result.setStatus(true);
		return result;
	}

	@RequestMapping(value = "/bindcard", method = RequestMethod.POST)
	public @ResponseBody
	Object BindBankCard(HttpEntity<UserBankCard> entity,
			HttpServletRequest request) {
		OpResult result = new OpResult();
		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}

		UserBankCard card = entity.getBody();
		if (card != null) {
			card.setUserId(this.getLoginedUserId(request));
		}
		if (!validateBindCardBean(card, result).isStatus()) {
			return result;
		}

		String validateCode = card.getValidateCode();

		String userId = card.getRealUserId();

		UserAttestation uaEntity = attestBiz.QueryUserAttestation(userId);
		if (uaEntity == null) {
			logger.info(String.format("BindBankCard 用户:[%s] 未进行认证", userId));
		} else {
			logger.info(String.format(
					"BindBankCard 用户:[%s],getMobileFlg:[%s],Tel:[%s]", userId,
					uaEntity.getMobileFlg(), uaEntity.getRealMobile()));
		}
		if (uaEntity == null || StringUtils.isEmpty(userId)
				|| 1 != uaEntity.getMobileFlg()
				|| StringUtils.isEmpty(uaEntity.getRealMobile())) {
			result.setStatus(false);
			result.setMsg("请先进行手机认证,操作为更多->认证中心->手机认证。");
			return result;
		}

		if (StringUtils.isEmpty(validateCode)
				|| (!VerifySmsCode(validateCode, uaEntity.getRealMobile(),
						request, result).isStatus())) {
			result.setStatus(false);
			result.setMsg("验证码输入错误");
			return result;
		}

		logger.info(String.format(" 用户：[%s] 绑定银行卡:[%s]", card.getUserId(),
				card.getCardId()));
		try {

			String msg = biz.BindBankCardMobile(card);
			if (StringUtils.isEmpty(msg)) {

				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg(msg);
			}

		} catch (Exception e) {
			logger.error(e);
			result.setStatus(false);
			result.setMsg("绑定过程中出现错误，请重试");
		}
		return result;
	}

}
