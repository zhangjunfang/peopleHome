package com.jujin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.b;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.VerifyCodeBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.common.VerifyType;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.security.PwdBean;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.common.constant.PageParamConstant;
import com.wicket.loan.web.person.password.mediator.UserPasswordMediator;
import com.wicket.loan.web.regist.mediator.RegistMediator;

/**
 * @author 王宁
 * 
 *         修改密码
 * 
 */
@Controller
public class PwdController extends BaseController {

	UserPasswordMediator mediator = FWBeanManager
			.getBean(UserPasswordMediator.class);
	
	VerifyCodeBiz biz=new VerifyCodeBiz();

	@RequestMapping(value = "/changepwd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object changePwd(HttpEntity<PwdBean> entity, HttpServletRequest request) {
		OpEntityResult<String> result = new OpEntityResult<String>("");
		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		String userId = this.getLoginedUserId(request);
		PwdBean bean = entity.getBody();
		if (!validateCommonBean(bean, result, userId, request).isStatus()) {
			return result;
		}
		List<String> list = getConfig(bean);

		String typeMemo = list.get(0);
		String tableName = list.get(1);
		String fieldName = list.get(2);

		logger.info(String.format("用户[%s] 操作类型  [%s]", userId, typeMemo));

		try {
			if (mediator.changePwd(tableName, fieldName, userId, bean.getPwd())) {
				result.setEntity(String.format("%s修改成功。", typeMemo));
				result.setStatus(true);
			} else {
				result.setMsg(String.format("修改%s异常,请和客服联系", typeMemo));
				result.setStatus(false);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setMsg(String.format("修改%s异常,请和客服联系", typeMemo));
			result.setStatus(false);
		}
		return result;
	}

	@RequestMapping(value = "/changepwddirect", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object changePwdDirect(HttpEntity<PwdBean> entity,
			HttpServletRequest request) {
		OpEntityResult<String> result = new OpEntityResult<String>("");
		 
		 
		PwdBean bean = entity.getBody();
		String userId =bean==null?null:bean.getUserId(); //this.getLoginedUserId(request);

		// PwdBean bean, OpResult result,
		// String userId, Boolean isDirect, HttpServletRequest request
		if (!validateCommonBean(bean, result, userId, true, request).isStatus()) {
			return result;
		}
		
		String tel=bean.getTel();
		if(StringUtils.isEmpty(tel))
		{
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
			return result;
		}
		
		String mobile=biz.getUserMobile(userId);
		if(!mobile.equals(tel))
		{
			result.setStatus(false);
			result.setMsg("手机号和绑定手机号不一致");
			return result;
		}
		
		List<String> list = getConfig(bean);

		String typeMemo = list.get(0);
		String tableName = list.get(1);
		String fieldName = list.get(2);

		logger.info(String.format("用户[%s] 操作类型  [%s]", userId, typeMemo));

		try {
			if (mediator.changePwd(tableName, fieldName, userId, bean.getPwd())) {
				result.setEntity(String.format("%s修改成功。", typeMemo));
				result.setStatus(true);
			} else {
				result.setMsg(String.format("修改%s异常,请和客服联系", typeMemo));
				result.setStatus(false);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setMsg(String.format("修改%s异常,请和客服联系", typeMemo));
			result.setStatus(false);
		}
		return result;
	}

	private OpResult validateCommonBean(PwdBean bean, OpResult result,
			String userId, Boolean isDirect, HttpServletRequest request) {
		if (result == null)
			result = new OpResult();
		if (bean == null || (bean.getType() < 0 || bean.getType() > 2)||StringUtils.isEmpty(userId)) {
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
			return result;
		}
		int type = bean.getType();
		if (type == 0) {
			return validateBean(bean, result, userId, isDirect);
		} else if (type == 1) {
			return validateTBean(bean, result, userId, request, isDirect);
		} else if (type == 2) {
			return validateCBean(bean, result, userId, request, isDirect);
		}
		result.setStatus(true);
		return result;
	}

	private OpResult validateCommonBean(PwdBean bean, OpResult result,
			String userId, HttpServletRequest request) {

		return validateCommonBean(bean, result, userId, false, request);
	}

	/**
	 * 
	 * 登陆密码
	 * 
	 * @param bean
	 * @param result
	 * @param userId
	 * @return
	 */
	private OpResult validateBean(PwdBean bean, OpResult result, String userId,
			Boolean isDirect) {

		String oldPwd = bean.getOldPwd();
		String againPwd = bean.getAgainPwd();
		String pwd = bean.getPwd();

		if (!isDirect) {
			if (StringUtils.isEmpty(oldPwd)) {
				result.setMsg("没有输入原始密码，请输入原始密码。");
				result.setStatus(false);
				return result;
			} else {
				if (!mediator.checkPwd("USERS", "USER_PWD", userId,
						bean.getOldPwd())) {
					result.setMsg("原始密码有误，请输入原始密码。");
					result.setStatus(false);
					return result;
				}
			}
		}

		if (StringUtils.isEmpty(pwd)) {
			result.setMsg("没有输入新密码，请输入新登录密码。");
			result.setStatus(false);
			return result;
		} else if ((pwd.length() <= 5) || (pwd.length() > 16)) {
			result.setMsg("新密码长度必须是6-16位");
			result.setStatus(false);
			return result;
		} else if (!mediator.checkUserPwd(userId, pwd, 0)) {
			result.setMsg("修改后密码与交易(提现)密码一致，为了账号资金安全，请重新设置登录密码");
			result.setStatus(false);
			return result;
		}
		// 确认密码验证
		if (StringUtils.isEmpty(againPwd)) {
			result.setMsg("没有输入确认密码，请输入确认密码。");
			result.setStatus(false);
			return result;
		} else if ((againPwd.length() <= 5) || (againPwd.length() > 16)) {
			result.setMsg("确认密码长度必须是6-16位");
			result.setStatus(false);
			return result;
		}
		// 密码一致性验证
		if (!StringUtils.isEmpty(oldPwd) && !StringUtils.isEmpty(pwd)) {
			if (oldPwd.equals(pwd)) {
				result.setMsg("您输入的新密码与原始密码一致，请重新输入新密码。");
				result.setStatus(false);
				return result;
			}
		}
		if (!StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(againPwd)) {
			if (!pwd.equals(againPwd)) {
				result.setMsg("您输入的密码与确认密码不一致，请保持一致。");
				result.setStatus(false);
				return result;
			}
		}
		result.setStatus(true);
		return result;
	}

	/**
	 * 
	 * 交易密码
	 * 
	 * @param bean
	 * @param result
	 * @param userId
	 * @param request
	 * @return
	 */
	private OpResult validateCBean(PwdBean bean, OpResult result,
			String userId, HttpServletRequest request, Boolean isDirect) {

		String oldPwd = bean.getOldPwd();
		String pwd = bean.getPwd();
		String pwdAgain = bean.getPwd();

		if (!isDirect) {
			String verifyCode = bean.getValidateCode();
			if (StringUtils.isEmpty(verifyCode)) {
				result.setStatus(false);
				result.setMsg("没有输入验证码，请输入验证码。");
				return result;
			}
			String cacheVerifyCode = getVerifyCode(VerifyType.USER_VERIFY,
					request);
			// 输入不一致验证
			if (!verifyCode.equals(cacheVerifyCode)) {
				result.setMsg("输入的验证码不正确，请重新输入。");
				result.setStatus(false);
				return result;
			}

			if (StringUtils.isEmpty(oldPwd)) {
				result.setStatus(false);
				result.setMsg("没有输入原始提现密码，请输入原始交易密码。");
				return result;

			} else {
				if (!mediator.checkPwd("USERS_ACCOUNT", "CASH_PWD", userId,
						oldPwd)) {
					result.setStatus(false);
					result.setMsg("原始提现密码有误，请输入原始提现密码。");
					return result;
				}
			}
		}

		if (StringUtils.isEmpty(pwd)) {
			result.setMsg("没有输入新提现密码，请输入新提现密码。");
			result.setStatus(false);
			return result;
		} else if ((pwd.length() <= 5) || (pwd.length() > 16)) {
			result.setMsg("新提现密码长度必须是6-16位");
			result.setStatus(false);
			return result;
		} else if (!mediator.checkUserPwd(userId, pwd, 2)) {
			result.setStatus(false);
			result.setMsg("修改后提现密码与登录密码一致，为了账号资金安全，请重新设置提现密码");
			return result;
		}

		// 确认密码验证
		if (StringUtils.isEmpty(pwdAgain)) {
			result.setMsg("没有输入确认提现密码，请输入确认提现密码。");
			result.setStatus(false);
			return result;
		} else if ((pwdAgain.length() <= 5) || (pwdAgain.length() > 16)) {
			result.setMsg("确认提现密码长度必须是6-16位");
			result.setStatus(false);
			return result;
		}
		// 密码一致性验证
		if (!StringUtils.isEmpty(oldPwd) && !StringUtils.isEmpty(pwd)) {
			if (oldPwd.equals(pwd)) {
				result.setMsg("您输入的提现密码与原始提现密码一致，请重新输入新提现密码。");
				result.setStatus(false);
				return result;
			}
		}
		if (!StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(pwdAgain)) {
			if (!pwd.equals(pwdAgain)) {
				result.setMsg("您输入的提现密码与确认提现密码不一致，请保持一致。");
				result.setStatus(false);
				return result;
			}
		}
		return result;
	}

	private OpResult validateTBean(PwdBean bean, OpResult result,
			String userId, HttpServletRequest request, Boolean isDirect) {

		String oldPwd = bean.getOldPwd();
		String pwd = bean.getPwd();
		String pwdAgain = bean.getPwd();

		if (!isDirect) {
			String verifyCode = bean.getValidateCode();
			if (StringUtils.isEmpty(verifyCode)) {
				result.setStatus(false);
				result.setMsg("没有输入验证码，请输入验证码。");
				return result;
			}

			String tmpuserId = this.getLoginedUserId(request);
			String cacheVerifyCode = getVerifyCode(VerifyType.USER_VERIFY,
					request);

			logger.info(String.format(
					"verifyCode:%s  cacheVerifyCode:%s	tmpuserId:%s	verify:%s",
					verifyCode, cacheVerifyCode, tmpuserId,
					getVerifyCode(VerifyType.USER_VERIFY, request)));
			// 输入不一致验证
			if (!verifyCode.equals(cacheVerifyCode)) {
				result.setMsg("输入的验证码不正确，请重新输入。");
				result.setStatus(false);
				return result;
			}

			if (StringUtils.isEmpty(oldPwd)) {
				result.setMsg("没有输入原始交易密码，请输入原始交易密码。");
				result.setStatus(false);
				return result;
			} else {
				if (!mediator.checkPwd("USERS_ACCOUNT", "TRADE_PWD", userId,
						oldPwd)) {
					result.setMsg("原始交易密码有误，请输入原始交易密码。");
					result.setStatus(false);
					return result;
				}
			}
		}

		if (StringUtils.isEmpty(pwd)) {
			result.setMsg("没有输入新交易密码，请输入新交易密码。");
			result.setStatus(false);
			return result;
		} else if ((pwd.length() <= 5) || (pwd.length() > 16)) {
			result.setMsg("新交易密码长度必须是6-16位");
			result.setStatus(false);
			return result;
		} else if (!mediator.checkUserPwd(userId, pwd, 1)) {
			result.setMsg("修改后交易密码与登录密码一致，为了账号资金安全，请重新设置交易密码");
			result.setStatus(false);
			return result;
		}

		// 确认密码验证
		if (StringUtils.isEmpty(pwdAgain)) {
			result.setMsg("没有输入确认交易密码，请输入确认交易密码。");
			result.setStatus(false);
			return result;
		} else if ((pwdAgain.length() <= 5) || (pwdAgain.length() > 16)) {
			result.setMsg("确认交易密码长度必须是6-16位");
			result.setStatus(false);
			return result;
		}

		// 密码一致性验证
		if (!StringUtils.isEmpty(oldPwd) && !StringUtils.isEmpty(pwd)) {
			if (oldPwd.equals(pwd)) {
				result.setMsg("您输入的交易密码与原始交易密码一致，请重新输入新交易密码。");
				result.setStatus(false);
				return result;
			}
		}
		if (!StringUtils.isEmpty(pwd) && !StringUtils.isEmpty(pwdAgain)) {
			if (!pwd.equals(pwdAgain)) {
				result.setMsg("您输入的交易密码与确认交易密码不一致，请保持一致。");
				result.setStatus(false);
				return result;
			}
		}

		result.setStatus(true);
		// 验证码
		return result;
	}

	/**
	 * 0:类型描述 1：表名 2：字段名
	 * 
	 * @param bean
	 * @return
	 */
	private List<String> getConfig(PwdBean bean) {

		ArrayList<String> list = new ArrayList<String>();
		String typeMemo = "";
		String tableName = "";
		String fieldName = "";

		if (bean != null) {
			switch (bean.getType()) {
			case 0:
				typeMemo = "登陆密码";
				tableName = "USERS";
				fieldName = "USER_PWD";
				break;
			case 1:
				typeMemo = "交易密码";
				tableName = "USERS_ACCOUNT";
				fieldName = "TRADE_PWD";
				break;
			case 2:
				typeMemo = "提现密码";
				tableName = "USERS_ACCOUNT";
				fieldName = "CASH_PWD";
				break;
			default:
				break;
			}
		}

		list.add(typeMemo);
		list.add(tableName);
		list.add(fieldName);
		return list;
	}

}
