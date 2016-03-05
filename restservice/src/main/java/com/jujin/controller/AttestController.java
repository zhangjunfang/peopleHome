package com.jujin.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sms.main.SendSmsMessage;
import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.AttestBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.UserAttestation;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.model.Model;
import com.pro.common.model.ModelList;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.common.constant.PageParamConstant;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.common.bean.FileUploadBean;
import com.wicket.loan.web.common.bean.RegistBean;
import com.wicket.loan.web.regist.mediator.RegistMediator;

@Controller
public class AttestController extends BaseController {

	AttestBiz biz = new AttestBiz();
	RegistMediator mediator = FWBeanManager.getBean(RegistMediator.class);

	@RequestMapping(value = "/attestemail", method = RequestMethod.GET)
	public @ResponseBody
	Object attestEmail(
			@RequestParam(value = "email", required = true) String email,
			String code, HttpServletRequest request) {
		OpResult result = new OpResult();

		try {

			if (!this.onUserIdIsNull(result, request).isStatus()) {
				return result;
			}
			String userId = this.getLoginedUserId(request);
			RegistBean bean = new RegistBean();
			bean.setUserId(userId);
			bean.setMailAddress(email.toLowerCase());
			bean.setLoginIp(this.getIpAddr(request));
			bean.setMailType("4");
			bean.setOpType("01");
			bean.setHostUrl("http://www.jujinziben.com");

			if (StringUtils.isEmpty(email)) {
				result.setStatus(false);
				result.setMsg("没有输入邮箱地址，请输入邮箱地址。");
				return result;
			}
			String tmpEmail = email.trim().toLowerCase();
			if (!UserUtils.checkEmail(tmpEmail)) {
				result.setStatus(false);
				result.setMsg("输入的邮箱地址不合法，请重新输入邮箱地址。");
				return result;
			}

			if (!StringUtils.isEmpty(code)) {

				UserAttestation ua = biz.QueryUserAttestation(userId);
				if (code.equals(ua.getEmail())) {
					result.setStatus(false);
					result.setMsg("新邮箱地址输入的内容与当前邮箱地址相同，请重新输入邮箱地址。");
					return result;
				}
				VerifySmsCode(code, ua.getRealMobile(), request, result);
				if (!result.isStatus()) {
					result.setStatus(false);
					result.setMsg("短信验证码不正确请重试");
					return result;
				}
			}
			if (mediator.isExistMail(email)) {
				result.setStatus(false);
				result.setMsg("邮箱已被注册。");
				return result;
			}

			if (mediator.updateEmailVerifyCode(userId, bean)) {
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg("邮箱认证失败，请检查输入是否有误或重试。如仍有问题请与客服联系");
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("邮箱认证失败，请和客服联系");
		}
		return result;
	}

	@RequestMapping(value = "/attestmobile", method = RequestMethod.GET)
	public @ResponseBody
	Object attestMobile(
			@RequestParam(value = "mobile", required = true) String mobile,
			HttpServletRequest request) {
		OpResult result = new OpResult();
		try {

			if (!this.onUserIdIsNull(result, request).isStatus()) {
				return result;
			}

			if (StringUtils.isEmpty(mobile)) {
				result.setStatus(false);
				result.setMsg("没有输入手机号，请输入手机号。");
				return result;
			}
			if (!UserUtils.isMobileNumber(mobile)) {
				result.setStatus(false);
				result.setMsg("不是正确的手机号，请检查。");
				return result;
			}

			if (mediator.isMutilyTel(mobile)) {
				result.setStatus(false);
				result.setMsg("该手机号已被认证，如有疑问请与客服联系。");
				return result;
			}

			String userId = this.getLoginedUserId(request);
			Model<String, Object> model = new Model<String, Object>(
					"APPROVEMOBILE");
			model.addAttribute("USER_ID", this.getLoginedUserId(request));
			model.addAttribute("PHONE_NUMBER", mobile);
			model.addAttribute("INS_USER_ID", userId);
			model.addAttribute("UPD_USER_ID", userId);

			Model<String, Object> modelOperationLog = new Model<String, Object>(
					"INSERTUSERSOPERATIONLOG");
			modelOperationLog.addAttribute("USER_ID", userId);
			modelOperationLog.addAttribute("OPERATION_CODE", "71");
			modelOperationLog.addAttribute("RESULT_FLG", "1");
			modelOperationLog.addAttribute("INS_USER_ID", userId);
			modelOperationLog.addAttribute("INS_IP", this.getIpAddr(request));
			modelOperationLog.addAttribute("OP_TYPE", "01");

			if (mediator.updatePhoneNumber(model, modelOperationLog, userId)) {
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg("手机认证失败，请检查输入是否有误或重试。如仍有问题请与客服联系");
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("手机认证失败，请和客服联系");
		}
		return result;
	}

	@RequestMapping(value = "/attestrealname", method = RequestMethod.GET)
	public @ResponseBody
	Object attestRealName(
			@RequestParam(value = "name", required = true) String realName,
			@RequestParam(value = "card", required = true) String idCard,
			HttpServletRequest request) {

		OpResult result = new OpResult();
		try {
			if (!this.onUserIdIsNull(result, request).isStatus()) {
				return result;
			}

			String userID = this.getLoginedUserId(request);

			if (StringUtils.isEmpty(realName)) {
				result.setMsg("请输入姓名(与身份证保持一致)");
				result.setStatus(false);
				return result;
			}

			if (StringUtils.isEmpty(idCard)) {
				result.setMsg("请输入身份证号");
				result.setStatus(false);
				return result;
			}

			String verifyMsg = UserUtils.checkIdCard(idCard);
			if (!StringUtils.isEmpty(verifyMsg)) {
				result.setMsg(verifyMsg);
				result.setStatus(false);
				return result;
			}

			String checkMsg = inputCheckReal(userID, realName, idCard);
			if (!StringUtils.isEmpty(checkMsg)) {
				result.setMsg(checkMsg);
				result.setStatus(false);
				return result;
			}

			Model<String, Object> model = new Model<String, Object>(
					"APPROVEREALNAME");
			model.addAttribute("USER_ID", userID);
			model.addAttribute("REAL_NAME", realName);
			model.addAttribute("CARD_ID", idCard);
			model.addAttribute("SEX", biz.getGenderByIdCard(idCard));

			FileUploadBean bean1 = new FileUploadBean();
			FileUploadBean bean2 = new FileUploadBean();

			model.addAttribute("INS_USER_ID", userID);
			model.addAttribute("UPD_USER_ID", userID);

			String ipStr = this.getIpAddr(request);

			Model<String, Object> modelOperationLog = new Model<String, Object>(
					"INSERTUSERSOPERATIONLOG");
			modelOperationLog.addAttribute("USER_ID", userID);
			modelOperationLog.addAttribute("OPERATION_CODE", "70");
			modelOperationLog.addAttribute("RESULT_FLG", "1");
			modelOperationLog.addAttribute("INS_USER_ID", userID);
			modelOperationLog.addAttribute("INS_IP", ipStr);
			modelOperationLog.addAttribute("OP_TYPE", "01");

			if (mediator.editRealAttest(model, modelOperationLog, bean1, bean2)) {
				result.setStatus(true);
			} else {
				result.setMsg("实名认证失败，请检查");
				result.setStatus(false);
				return result;
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("实名认证失败，请和客服联系");
		}
		return result;
	}

	@RequestMapping(value = "/atteststatus", method = RequestMethod.GET)
	public @ResponseBody
	Object attestStatus(HttpServletRequest request) {

		OpEntityResult<UserAttestation> result = new OpEntityResult<UserAttestation>(
				null);

		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		String userId = this.getLoginedUserId(request);
		logger.info(String.format(" 查询用戶[%s]认证状态", userId));
		try {
			UserAttestation entity = biz.QueryUserAttestation(userId);
			result.setEntity(entity);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("获取用户认证状态失败，请与客服联系。");
		}
		logger.info(String.format(" 查询用戶[%s]认证状态结束", userId));
		return result;
	}

	@RequestMapping(value = "/verifySmsCode", method = RequestMethod.GET)
	public @ResponseBody
	Object verifySmsCode(
			@RequestParam(value = "mobile", required = true) String tel,
			@RequestParam(value = "code", required = true) String code,
			HttpServletRequest request, HttpServletResponse response) {
		OpResult result = new OpResult();

		try {
			VerifySmsCode(code, tel, request, result);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("短信认证失败，请重试");
		}
		return result;
	}

	@RequestMapping(value = "/vsms", method = RequestMethod.GET)
	public @ResponseBody
	Object verifySms(
			@RequestParam(value = "code", required = true) String code,
			HttpServletRequest request, HttpServletResponse response) {
		OpResult result = new OpResult();

		try {

			if (!this.onUserIdIsNull(result, request).isStatus()) {
				return result;
			}

			String userId = this.getLoginedUserId(request);

			UserAttestation entity = biz.QueryUserAttestation(userId);
			if (1 != entity.getMobileFlg()) {
				result.setStatus(false);
				result.setMsg("请先进行手机认证,操作为更多->认证中心->手机认证。");
			}
			String tel = entity.getRealMobile();
			
			VerifySmsCode(code, tel, request, result);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("短信认证失败，请重试");
		}
		return result;
	}

	private String inputCheckReal(String userID, String realName, String cardId)
			throws ParseException {

		List<String> errList = new ArrayList<String>();
		String result = "";
		if (StringUtils.isEmpty(realName)) {
			errList.add("请填写真实姓名。");
		} else {
			String wDt = UserUtils.checkRealName(realName);
			if (!StringUtils.isEmpty(wDt)) {
				errList.add("真实" + wDt);
			}
		}
		if (StringUtils.isEmpty(cardId)) {
			errList.add("请填写身份证号。");
		} else {
			String strIDCard = UserUtils.checkIdCard(cardId);
			if (!(StringUtils.isEmpty(strIDCard))) {
				errList.add(strIDCard);
			} else {
				ModelList<Model<String, Object>> wDt = mediator
						.selectRealCard(cardId);

				if (wDt != null && wDt.size() > 0) {
					boolean flg = false;
					for (int i = 0; i < wDt.size(); i++) {
						if (!(userID.equals(wDt.get(i)
								.getStringValue("USER_ID")))) {
							flg = true;
							break;
						}
					}
					if (flg) {
						errList.add("该身份证号已被别人进行过实名申请或认证。");
					}
				}
			}
		}

		for (String item : errList) {
			result += item;
		}
		return result;
	}
}
