/**   
 * @author wangning
 * @date 2015年3月14日 下午7:58:25 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.authorize.AppSession;
import com.jujin.biz.MfaBiz;
import com.jujin.biz.UserBiz;
import com.jujin.biz.security.SecurityBiz;
import com.jujin.biz.xglc.XglcTransactionBiz;
import com.jujin.common.JsonList;
import com.jujin.common.LoginResult;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.OpValueResult;
import com.jujin.common.SystemConfig;
import com.jujin.common.VerifyType;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.account.WithdrawEntity;
import com.jujin.entity.user.UserFriendInterestRank;
import com.jujin.entity.xglc.XglcUser;
import com.jujin.util.xglc.CommonUtil;
import com.jujin.util.xglc.SignUtil;
import com.jujin.utils.ExceptionHelper;
import com.jujin.utils.HttpUtil;
import com.jujin.utils.Toolkit;
import com.mfa.common.notifier.MfaResult;
import com.mfa.constants.notifier.NoticeTypeConstants;
import com.mfa.domain.notifier.UserNotifier;
import com.mfa.util.notifier.MfaUtil;
import com.pro.common.model.Model;
import com.pro.common.model.ModelList;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.FWBeanManager;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.constant.PageParamConstant;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.common.bean.RegistBean;
import com.wicket.loan.web.login.bean.LoginBean;
import com.wicket.loan.web.login.mediator.LoginMediator;
import com.wicket.loan.web.person.withdraw.bean.WithdrawBean;
import com.wicket.loan.web.person.withdraw.mediator.WithdrawMediator;
import com.wicket.loan.web.regist.mediator.RegistMediator;

/**
 * 用户注册和登陆数据 TODO:用户数据关联
 * 
 * @param <LoginResult>
 */
@Controller
public class UserController extends BaseController {

	private static final String NUMBER_PATTERN = "^[0-9]+(.[0-9]{0,2})?$";// 判断小数点后二位的数字的正则表达式
	

	UserBiz biz = new UserBiz();
	MfaBiz mfaBiz = new MfaBiz();
	SecurityBiz securityBiz = new SecurityBiz();

	RegistMediator mediator = FWBeanManager.getBean(RegistMediator.class);

	private WithdrawMediator withDrwamediator = FWBeanManager
			.getBean(WithdrawMediator.class);

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object login(HttpEntity<RegisterEntity> entity, HttpServletRequest request) {
		return login(entity, request, true);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public @ResponseBody
	Object logout(HttpServletRequest request) {

		OpResult result = new OpResult();

		HttpSession session = request.getSession();
		Enumeration<String> keyEnumer = session.getAttributeNames();
		for (Enumeration<String> tmp = keyEnumer; tmp.hasMoreElements();) {
			session.removeAttribute(tmp.nextElement());
		}
		result.setStatus(true);
		return result;
	}

	public Object login(HttpEntity<RegisterEntity> entity,
			HttpServletRequest request, boolean verifyFlg) {

		LoginResult result = new LoginResult();
		if (entity == null) {
			return onEntityIsNull(result, request, "用户登录提交数据有误");
		}
		RegisterEntity register = entity.getBody();
		if (register == null) {
			result.setMsg("数据格式不正确，请检查");
			result.setStatus(false);
			return result;
		} else {
			HttpSession session = request.getSession();
			validate(register, result, 1, session);
		}

		if (!result.isStatus()) {
			return result;
		}
		HttpSession session = request.getSession();
		logger.info(String.format(" 用戶[%s]登录", register.getUserName()));

		String msg = "";
		try {

			Object verifyObj = session
					.getAttribute(SystemConfig.USER_LOGIN_VERIFY);
			if (verifyObj != null && verifyObj == "1") {

				String verifyCode = this.getVerifyCode(VerifyType.USER_VERIFY,
						request);

				if (!(!StringUtils.isEmpty(verifyCode) && verifyCode
						.equals(register.getValidateCode().trim().toLowerCase()))) {
					result.setMsg("验证码错误,请重新输入");
					result.setStatus(false);
					return result;
				}
			}

			LoginMediator mediator = FWBeanManager.getBean(LoginMediator.class);

			LoginBean bean = new LoginBean();
			bean.setUserId(register.getUserName());
			bean.setUserPwd(register.getPwd());

			bean.setLoginIp(this.getIpAddr(request));
			bean.setOpType("01");

			String userToken = register.getUserName();
			if (com.pro.common.util.StringUtils.checkValidityEmail(register
					.getUserName())) {
				bean.setMailAddress(userToken);
				bean.setUserId(null);
			} else {
				bean.setUserId(userToken);
			}

			if (!StringUtils.isEmpty(userToken)
					&& StringUtils.isAllDigit(userToken)
					&& userToken.length() == 11
					&& mediator.getPhoneCount(userToken) > 1) {

				// 登陆错误，画面表示错误消息
				// 错误消息内容设定
				result.setMsg("您输入的手机号对应多个账号,请使用用户名登录");
				result.setStatus(false);
				return result;
			}

			msg = mediator.isLogin(bean, session);
			if (StringUtils.isEmpty(msg)) {

				result.setStatus(true);

				// 获取登录票据
				if (!StringUtils.isEmpty(register.getOpType())
						&& !"01".equals(register.getOpType())) {
					logger.info("REGISTER: 获取票据");
					result.setTicket(securityBiz.GenMobileTicket(mediator
							.getAppSession().get(PageParamConstant.LOGIN_ID)
							.toString()));
				}
				if (!StringUtils.isEmpty(register.getOpenId()))// 绑定微信
				{
					BindWeiXin(
							register,
							mediator.getAppSession()
									.get(PageParamConstant.LOGIN_ID).toString());
				}

				// if ("1".equals(register.getIsXglc())
				// && !StringUtils.isEmpty(register.getReturnUrl())) {
				// try {
				// String notifyUrl = NotifyXglc(mediator.getAppSession()
				// .get(PageParamConstant.LOGIN_ID).toString(),
				// register.getReturnUrl(), 2, true, true);
				// System.out.println("XGLC-LOGIN:" + notifyUrl);
				// result.setReturnUrl(notifyUrl);
				// } catch (Exception e) {
				// logger.error(ExceptionHelper.getExceptionDetail(e));
				// }
				// }

				AppSession as = new AppSession();
				as.set(SystemConfig.USER_ID,
						mediator.getAppSession()
								.get(PageParamConstant.LOGIN_ID));// 添加个人信息
				session.setAttribute(SystemConfig.USERNAME_KEY, as);
				session.removeAttribute(SystemConfig.USER_LOGIN_VERIFY);

				result.setNickName((String) mediator.getAppSession().get(
						PageParamConstant.NICK_NAME));

				session.setAttribute(SystemConfig.APP_SESSION,
						mediator.getAppSession());

				if (register.getChannel() != null) {
					logger.info("login-channel："+register.getChannel()+",进入第三方回调处理");
					if(register.getPlatUsername() != null){
						if("BSY".equals(register.getChannel())){
							//判断是否已经手机认证过
							String username = mediator.getRealUsername(register.getUserName());
							if(username == null){
								result.setMsg("该手机号尚未注册或认证!");
								result.setStatus(false);
								return result;
							}
							logger.info("login-platUsername："+register.getPlatUsername()+",进入重复检测处理");
							String platCode = mediator.checkIsThirdUser(username);
							if(!register.getChannel().equals(platCode)){
								logger.info("login-platCode："+platCode+",该用户未绑定，先绑定后回调");
								register.setUserName(username);
								boolean boo = mfaBiz.bindUserInfo(register,"2");
								if (!boo) {
									logger.error("绑定第三方失败:" + username);
								}else{
									pushMfa4login(username);
								}
							}else{
								logger.info("login-platCode："+platCode+",该用户已绑定，直接回调");
							}
							String retUrl = getCallback(register, "1","login");
							result.setReturnUrl(retUrl);
						}
					}
				}
				return result;
			} else {
				result.setMsg(msg);
				result.setStatus(false);

				session.setAttribute(SystemConfig.USER_LOGIN_VERIFY, "1");

				if (register.getChannel() != null) {
					if("BSY".equals(register.getChannel())){
						logger.info("进入BSY回调");
						String retUrl = getCallback(register, "0","login");
						result.setReturnUrl(retUrl);
					}
				}

				return result;
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg("登录失败，请重试");
			result.setStatus(false);
		}

		return result;
	}

	private Object validate(RegisterEntity register, OpResult result, int type,
			HttpSession session, boolean vFlg) {
		String info = type == 1 ? "请输入用户名/手机号/邮箱" : "请输入手机号";
		if (StringUtils.isEmpty(register.getUserName())) {
			result.setMsg(info);
			result.setStatus(false);
			return result;
		}
		if (StringUtils.isEmpty(register.getPwd())) {
			result.setMsg("请输入密码");
			result.setStatus(false);
			return result;
		}

		Object verifyObj = session.getAttribute(SystemConfig.USER_LOGIN_VERIFY);
		String verifyFlg = "";
		if (verifyObj != null) {
			verifyFlg = verifyObj.toString();
		}
		if (vFlg && verifyFlg == "1"
				&& StringUtils.isEmpty(register.getValidateCode())) {
			result.setMsg("请输入验证码");
			result.setStatus(false);
			return result;
		}
		if (type != 1 && !register.isAgree()) {
			result.setMsg("请同意相关协议");
			result.setStatus(false);
			return result;
		}
		result.setStatus(true);
		return result;
	}

	private Object validate(RegisterEntity register, OpResult result, int type,
			HttpSession session) {
		return validate(register, result, type, session, true);
	}

	// TODO:手机端注册流程为:手机号/密码->成功进入投标界面,提现时绑定邮箱和实名认证
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object register(HttpEntity<RegisterEntity> entity,
			HttpServletRequest request) {

		RegisterEntity re = null;
		OpResult result = new OpResult();
		if (entity == null || (re = entity.getBody()) == null) {
			return onEntityIsNull(request, "用户注册提交的数据有误");
		} else {
			validate(re, result, 0, request.getSession());

			if (!result.isStatus()) {
				return result;
			}
			String tel = re.getUserName();
			if (!StringUtils.isEmpty(UserUtils.checkTel(tel))) {
				result.setStatus(false);
				result.setMsg("为了您获得更好的投资体验，移动端仅支持手机号注册");
				return result;
			}
			if (!VerifySmsCode(re.getValidateCode(), tel, request, result)
					.isStatus()) {
				return result;
			}
			logger.info("开始注册");

			if (mediator.isExistsTel(tel)) {
				result.setMsg("该手机号已经被注册！");
				result.setStatus(false);
				return result;
			}

			logger.info("检查推荐人");

			String invite = null;
			if (!StringUtils.isEmpty(re.getInvite())) {
				invite = biz.getUserIdFromInvite(re.getInvite()); // re.getInvite();
				if (StringUtils.isEmpty(invite)) {
					result.setMsg("推荐人手机号/用户名不存在！");
					result.setStatus(false);
					return result;
				}
			}

			RegistBean bean = new RegistBean();
			bean.setUserId(biz.GetSingleUserId(tel));

			logger.info("GetSingleUserId:" + bean.getUserId());
			bean.setUserPwd(DesCodeUtil.encrypt(re.getPwd()));
			bean.setMobileNumber(tel);
			bean.setNickName(tel);
			bean.setLoginIp(this.getIpAddr(request));
			bean.setSysMobile(true);

			bean.setInviteUserId(invite);
			bean.setMailAddress("");
			bean.setMailType("0");
			bean.setOpType("01");

			re.setUserName(bean.getUserId());

			logger.info("执行注册");
			String status = "1";
			if (mediator.registUser(bean)) {
				result.setStatus(true);
				status = "1";
				// 西瓜理财
				if ("1".equals(re.getIsXglc())) {
					try {
						bindXglc(bean.getUserId());
					} catch (Exception e) {
						logger.error(ExceptionHelper.getExceptionDetail(e));
					}
				}
				
				// MFA
				if (re.getChannel() != null) {
					boolean boo = mfaBiz.bindUserInfo(re,"1");
					if (boo) {
						if("BSY".equals(re.getChannel())){
							String retUrl = getCallback(re, status,"register");
							result.setReturnUrl(retUrl);
						}
					} else {
						logger.error("绑定第三方失败:" + re.getUserName());
					}
				}
				pushMfa4reg(re.getUserName());

				logger.info("执行注册完毕");
				BindWeiXin(re, bean.getUserId());
				logger.info("执行登陆");
				login(entity, request);
				logger.info("执行登录结束");

				request.getSession().setAttribute(SystemConfig.USER_VERIFY,
						null);
				request.getSession().setAttribute("USER_HAS_SMS", null);

			} else {
				status = "0";
				result.setStatus(false);
				result.setMsg("注册失败,请重试");
			}

			return result;
		}
	}
	
	// 推送至MFA TODO
	private void pushMfa4reg(String userId) {
		try {
			logger.info("进入MFA推送");
			// 判断是否为第三方用户
			String platCode = mediator.checkIsThirdUser(userId);
			if (platCode == null) {
				logger.info("普通用户取消推送");
				return;
			}
			UserNotifier un = new UserNotifier();
			un.setUserId(userId);
			MfaResult result = MfaUtil.push(
					NoticeTypeConstants.REGISTER.getType(),userId, un);
			if (result.isStatus()) {
				logger.info("注册后推送用户[" + userId + "]信息到MFA成功");
			} else {
				logger.error("注册后推送用户[" + userId + "]信息到MFA失败，错误:"
						+ result.getMsg());
			}
		} catch (Exception e) {
			logger.error("注册后推送用户[" + userId + "]信息到MFA失败", e);
		}
	}
	
	// 推送至MFA TODO
	private void pushMfa4login(String userId) {
		try {
			// 判断是否为第三方用户
			String platCode = mediator.checkIsThirdUser(userId);
			if (platCode == null) {
				return;
			}
			UserNotifier un = new UserNotifier();
			un.setUserId(userId);
			MfaResult result = MfaUtil.push(NoticeTypeConstants.LOGIN.getType(),userId, un);
			if (result.isStatus()) {
				logger.info("登录后推送用户[" + userId + "]信息到MFA成功");
			} else {
				logger.error("登录后推送用户[" + userId + "]信息到MFA失败，错误:"
						+ result.getMsg());
			}
		} catch (Exception e) {
			logger.error("登录后推送用户[" + userId + "]信息到MFA失败", e);
		}
	}

	private String getCallback(RegisterEntity re, String status,String type) {
		String retUrl = "";
		Map<String, String> hmap = new HashMap<String, String>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", type);
		map.put("channel", re.getChannel());
		map.put("userId", re.getUserName());
		map.put("status", status);
		retUrl = HttpUtil.get(MfaUtil.CALLBACK_URL,
				hmap, map);
		if("".equals(retUrl)){
			return retUrl;
		}
		if ("ERROR".equals(retUrl.substring(0, 5))) {
			logger.error("回调失败:" + retUrl);
			retUrl = "";
		}
		return retUrl;
	}

	/**
	 * 绑定西瓜理财（监控用） 20160105 zsx Title: bindXglc Description:
	 * 
	 * @param userId
	 */
	private void bindXglc(String userId) {
		logger.info("开始绑定XGLC用户");
		String userAccessKey = CommonUtil.getUUID();

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 1);
		date = calendar.getTime();

		XglcUser xglcUser = new XglcUser();
		xglcUser.setAccountKey(userAccessKey);
		xglcUser.setExpireTime(date);
		xglcUser.setUserId(userId);
		if (biz.AddXglcUser(xglcUser)) {
			logger.info("XGLC用户绑定成功");
		} else {
			logger.error("XGLC用户绑定失败");
		}

	}

	/**
	 * 生成URL返回前台 Title: NotifyXglc Description:
	 * 
	 * @param userId
	 * @param returnUrl
	 * @param operaType
	 *            1-注册，2-登录,3-老用户绑定西瓜理财
	 * @param isRegisted
	 * @param isLoginSucceed
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String NotifyXglc(String userId, String returnUrl, int operaType,
			boolean isRegisted, boolean isLoginSucceed)
			throws UnsupportedEncodingException {
		logger.info("进入西瓜理财URL回调");
		String userAccessKey = "";
		if (operaType == 1) {
			userAccessKey = CommonUtil.getUUID();
		} else {
			XglcTransactionBiz biz = new XglcTransactionBiz();
			userAccessKey = biz.getUserAccessKeyByUserId(userId);
			// 老用户绑定
			if (userAccessKey == null) {
				userAccessKey = CommonUtil.getUUID();
				operaType = 3;
			}
		}

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, 1);
		date = calendar.getTime();
		String dateString = Toolkit.FormatDate(date);
		String paramAccessKey = SignUtil.encryptAccessKey(userAccessKey);
		String signStr = userId + dateString + isLoginSucceed + isRegisted
				+ userAccessKey + SignUtil.signKey;
		String sign = SignUtil.encryptMD5(signStr);

		String retUrl = returnUrl + "?isRegisted="
				+ URLEncoder.encode(String.valueOf(isRegisted), "utf-8")
				+ "&isLoginSucceed="
				+ URLEncoder.encode(String.valueOf(isLoginSucceed), "utf-8")
				+ "&account=" + URLEncoder.encode(userId, "utf-8")
				+ "&userAccessKey="
				+ URLEncoder.encode(paramAccessKey, "utf-8") + "&expireTime="
				+ URLEncoder.encode(Toolkit.FormatDate(date), "utf-8")
				+ "&sign=" + URLEncoder.encode(sign, "utf-8");

		logger.info("回调地址:" + retUrl);

		if (operaType != 2) {
			logger.info("XGLC进行用户绑定");
			XglcUser xglcUser = new XglcUser();
			xglcUser.setAccountKey(userAccessKey);
			xglcUser.setExpireTime(date);
			xglcUser.setUserId(userId);
			if (biz.AddXglcUser(xglcUser)) {
				logger.info("XGLC用户绑定成功");
				return retUrl;
			}
			logger.info("XGLC用户绑定失败");
		} else {
			return retUrl;
		}

		return "";
	}

	/**
	 * 
	 * 用户首次注册时会绑定，或者通过登录的参数来绑定
	 * 
	 * @param re
	 * @param userId
	 */
	private void BindWeiXin(RegisterEntity re, String userId) {
		String openId = re.getOpenId();
		if (!StringUtils.isEmpty(openId)) {

			openId = DesCodeUtil.decrypt(openId);
			WxBindBean bean = biz.QueryWxBindBean(openId);
			if (bean == null) {
				logger.info("绑定微信号：" + openId + " userId:" + userId);
				bean = new WxBindBean();
				bean.setBindFlg(0);
				bean.setUserId(userId);
				bean.setWeiXinId(openId);

				biz.InsertWxBindBean(bean);
				logger.info("绑定微信号结束：" + openId + " userId:" + userId);
			} else if (bean.getBindFlg() == 1) {
				bean.setBindFlg(0);
				biz.UpdateWxBindBean(bean);
			}
		}
	}

	// 检查手机号是否存在
	@RequestMapping(value = "/isTelExists", method = RequestMethod.GET)
	public @ResponseBody
	Object isTelExists(String tel) {
		RegistMediator mediator = FWBeanManager.getBean(RegistMediator.class);

		OpValueResult<Boolean> result = new OpValueResult<Boolean>();
		try {
			boolean isExists = mediator.isExistsTel(tel);
			result.setStatus(true);
			result.setValue(isExists);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("操作失败,请重试");
		}
		return result;

	}

	@RequestMapping(value = "/getWithDrawInfo", method = RequestMethod.GET)
	public @ResponseBody
	Object getWithDrawIfno(HttpServletRequest request) {
		OpEntityResult<WithdrawEntity> entity = new OpEntityResult<WithdrawEntity>(
				null);
		if (request == null) {
			entity.setStatus(false);
			entity.setMsg(SystemConfig.ERROR_FORMAT);
			return entity;
		}

		try {
			String userId = this.getLoginedUserId(request);

			if (StringUtils.isEmpty(userId)) {

				entity.setStatus(false);
				entity.setMsg(SystemConfig.NO_LOGIN);
				return entity;
			}

			ModelList<Model<String, Object>> accountInfoList = this.withDrwamediator
					.getAccountInfo(userId);

			if (accountInfoList == null || accountInfoList.size() == 0) {
				entity.setStatus(false);
				entity.setMsg("尚未绑定银行卡，请先绑定银行卡");
				return entity;
			}
			Model<String, Object> map = accountInfoList.get(0);

			// 账户总额
			String accountTotal = StringUtils.objToString(map
					.get("ACCOUNT_TOTAL"));
			// 可用余额
			String balanceCash = "0";
			balanceCash = StringUtils.objToString(map.get("BALANCE"));

			// 可提现金额
			String withdrawMoney = withDrwamediator.getWithdrawMoney(userId,
					balanceCash, accountTotal);

			String nickName = StringUtils.objToString(map.get("REAL_NAME"));
			if (!StringUtils.isEmpty(nickName)) {
				StringBuilder str = new StringBuilder();
				for (int i = 1; i < nickName.length(); i++) {
					str.append("*");
				}

				nickName = nickName.substring(0, 1).concat(str.toString());
			}

			WithdrawEntity bean = new WithdrawEntity();
			bean.setNickName(nickName);
			bean.setMoney(withdrawMoney);// 提现金额

			String cardCode = StringUtils.objToString(map.get("CARD_ID"));
			String bankName = "";
			if (!StringUtils.isEmpty(cardCode)) {
				cardCode = cardCode.substring(0, 4) + "****"
						+ cardCode.substring(cardCode.length() - 3);
				bankName = StringUtils.objToString(map.get("PROVINCE"))
						+ StringUtils.objToString(map.get("CITY"))
						+ StringUtils.objToString(map.get("BANK_NAME"))
						+ StringUtils.objToString(map.get("BRANCK"));

				bean.setCardCode(cardCode);
				bean.setBankName(bankName);
			}

			entity.setStatus(true);
			entity.setEntity(bean);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			entity.setStatus(false);
			entity.setMsg("获取账户提现金额失败，请与客服联系。");
		}
		return entity;
	}

	private String getFormatAmount(String money) {
		if (StringUtils.isEmpty(money))
			return "";

		String withdrawAmount = money.replaceAll(",", "");// 把134,123处理为
		return withdrawAmount;
	}

	public OpResult ValidateWithdrawEntity(HttpEntity<WithdrawEntity> he,
			HttpServletRequest request) {
		OpResult result = new OpResult();
		WithdrawEntity entity = null;
		if (request == null || he == null || (entity = he.getBody()) == null) {
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
			return result;
		}
		if (StringUtils.isEmpty(entity.getMoney())) {
			result.setStatus(false);
			result.setMsg("请输入提现金额");
			return result;
		}
		String withdrawAmount = getFormatAmount(entity.getMoney());

		if (UserUtils.isDecimalNumber(withdrawAmount)
				&& Double.parseDouble(withdrawAmount) < 100) {
			result.setStatus(false);
			result.setMsg("提现金额必须是数字,且提现金额必须大于100元");
			return result;
		}
		result.setStatus(true);
		return result;

	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object withdraw(HttpEntity<WithdrawEntity> he, HttpServletRequest request) {

		OpResult result = ValidateWithdrawEntity(he, request);
		if (!result.isStatus()) {
			return result;
		}
		WithdrawEntity entity = he.getBody();

		try {
			String userId = this.getLoginedUserId(request);

			if (StringUtils.isEmpty(userId)) {

				result.setStatus(false);
				result.setMsg(SystemConfig.NO_LOGIN);
				return result;
			}
			ModelList<Model<String, Object>> wDt = withDrwamediator
					.getAccountInfo(userId);

			if (wDt != null && wDt.size() > 0) {

				Model<String, Object> map = wDt.get(0);

				String cardCode = StringUtils.objToString(map.get("CARD_ID"));
				String bankName = "";
				String bankId = "";
				if (!StringUtils.isEmpty(cardCode)) {
					bankName = StringUtils.objToString(map.get("PROVINCE"))
							+ StringUtils.objToString(map.get("CITY"))
							+ StringUtils.objToString(map.get("BANK_NAME"))
							+ StringUtils.objToString(map.get("BRANCK"));

					bankId = StringUtils.objToString(map.get("BANK_ID"));
				}

				if (StringUtils.isEmpty(cardCode)
						|| StringUtils.isEmpty(bankName)) {
					result.setStatus(false);
					result.setMsg("请先绑定银行卡");
					return result;
				}

				if (!withDrwamediator.checkUserPasswordCash(userId)) {
					result.setStatus(false);
					result.setMsg("未设置提现密码或与登录密码一致，为了账号资金安全，建议马上修改");
					return result;
				}
				if (!withDrwamediator.checkAccount(userId,
						DesCodeUtil.encrypt(entity.getCashPwd()))) {
					result.setStatus(false);
					result.setMsg("取现密码不正确！");
					return result;
				}

				// 账户总额
				String accountTotal = StringUtils.objToString(wDt.get(0).get(
						"ACCOUNT_TOTAL"));
				// 可用余额
				String balanceCash = "0";
				balanceCash = StringUtils
						.objToString(wDt.get(0).get("BALANCE"));

				// 可提现金额
				String withdrawMoney = withDrwamediator.getWithdrawMoney(
						userId, balanceCash, accountTotal);

				String withdrawAmount = getFormatAmount(entity.getMoney());

				String feeAmount = withDrwamediator.getFeeAmountForWithdraw(
						withdrawAmount, userId, withdrawMoney);
				String realAmount = StringUtils.objToString(NumberUtils
						.parseDouble(withdrawAmount)
						- NumberUtils.parseDouble(feeAmount));

				WithdrawBean saveBean = new WithdrawBean();
				saveBean.setUserId(userId);
				saveBean.setUpdUserId(userId);
				saveBean.setCashAmount(withdrawAmount);
				saveBean.setRealCash(realAmount);
				saveBean.setFeeCash(feeAmount);
				saveBean.setInsIp(this.getIpAddr(request));

				saveBean.setInsUserId(userId);
				saveBean.setUpdUserId(userId);

				saveBean.setCardId(cardCode);
				saveBean.setBankId(bankId);
				saveBean.setOpType("01");

				double maxWithdrawAmount = withDrwamediator
						.getMaxWithdrawAmount(userId);
				if (maxWithdrawAmount != 0) {
					if (maxWithdrawAmount < NumberUtils
							.parseDouble(withdrawAmount)) {
						result.setStatus(false);
						result.setMsg("取现金额不能超过"
								+ NumberUtils.decimalFormat(String
										.valueOf(maxWithdrawAmount))
								+ "元，请重新输入！");
						return result;
					}
				}

				if (NumberUtils.parseDouble(withdrawAmount) > NumberUtils
						.parseDouble(withdrawMoney)) {

					result.setStatus(false);
					result.setMsg("取现金额不能大于您的可提现金额(您的可提现金额发生变化导致)，请重新输入提现金额！");
					return result;

				} else if (withDrwamediator.doWithdrawCash(saveBean)) {
					result.setStatus(true);
					return result;// 只有这一个出口出去的才算成功！
				} else {
					result.setStatus(false);
					result.setMsg("提现申请过程失败，请与客服联系。");
					return result;
				}
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("提现申请过程失败，请与客服联系。");
		}
		result.setStatus(false);
		return result;
	}

	@RequestMapping(value = "/friendsrank", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryUserFriendInterestRank(HttpServletRequest request) {
		JsonList<UserFriendInterestRank> result = new JsonList<UserFriendInterestRank>();

		if (!onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		String userId = this.getLoginedUserId(request);

		try {

			List<UserFriendInterestRank> lists = biz
					.QueryUserFriendInterestRank(userId);
			result.addRange(lists);
			result.setStatus(true);

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			result.setStatus(false);
			result.setMsg("获取好友排名失败，请重试或与客服联系");
		}
		return result;
	}

	/**
	 * 用户是否出现过登录失败,如出现登录失败，需要用户输入图片验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryloginstatus", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryLoginStatus(HttpServletRequest request) {

		HttpSession session = request.getSession();
		OpEntityResult<Integer> result = new OpEntityResult<Integer>(0);
		result.setStatus(true);
		if (session != null) {
			Object verifyObj = session
					.getAttribute(SystemConfig.USER_LOGIN_VERIFY);
			if (verifyObj != null) {
				result.setEntity(Integer.valueOf(verifyObj.toString()));
				return result;
			}
		}
		result.setEntity(0);
		return result;

	}

}
