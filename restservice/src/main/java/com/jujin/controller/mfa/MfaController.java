//package com.jujin.controller.mfa;
//import java.sql.SQLException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jujin.authorize.AppSession;
//import com.jujin.biz.MfaBiz;
//import com.jujin.biz.UserBiz;
//import com.jujin.biz.security.SecurityBiz;
//import com.jujin.common.LoginResult;
//import com.jujin.common.OpResult;
//import com.jujin.common.SystemConfig;
//import com.jujin.controller.BaseController;
//import com.jujin.entity.mfa.RegisterBean;
//import com.jujin.util.xglc.CommonUtil;
//import com.jujin.utils.ExceptionHelper;
//import com.pro.common.util.DesCodeUtil;
//import com.pro.common.util.FWBeanManager;
//import com.pro.common.util.StringUtils;
//import com.wicket.loan.common.constant.PageParamConstant;
//import com.wicket.loan.web.common.bean.RegistBean;
//import com.wicket.loan.web.login.bean.LoginBean;
//import com.wicket.loan.web.login.mediator.LoginMediator;
//import com.wicket.loan.web.regist.mediator.RegistMediator;
//
///**
// * 
//* <p>Title: MfaController.java</p>
//* <p>Description: </p>
//* <p>Copyright: Copyright (c) 2015</p>
//* <p>Company: jujinziben</p>
//* @author zhengshaoxu
//* @date 2015年12月24日
//* @version 1.0
// */
//@Controller
//@RequestMapping("/mfaservice")
//public class MfaController extends BaseController {
//	UserBiz biz = new UserBiz();
//	SecurityBiz securityBiz = new SecurityBiz();
//
//	
//	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Object mfaLogin(HttpEntity<RegisterBean> entity,HttpServletRequest request) {
//		return login(entity, request);
//	}
//
//	public Object login(HttpEntity<RegisterBean> entity,HttpServletRequest request) {
//		LoginResult result = new LoginResult();
//		if (entity == null) {
//			return onEntityIsNull(result, request, "用户登录提交数据有误");
//		}
//		RegisterBean reg = entity.getBody();
//		if (reg == null) {
//			result.setMsg("数据格式不正确，请检查");
//			result.setStatus(false);
//			return result;
//		} else {
//			HttpSession session = request.getSession();
//			validate(reg, result, session);
//		}
//
//		if (!result.isStatus()) {
//			return result;
//		}
//		HttpSession session = request.getSession();
//		logger.info(String.format(" 用戶[%s]登录", reg.getUsername()));
//
//		String msg = "";
//		try {
//			LoginMediator mediator = FWBeanManager.getBean(LoginMediator.class);
//			LoginBean bean = new LoginBean();
//			bean.setUserId(reg.getUsername());
//			bean.setUserPwd(reg.getPwd());
//
//			bean.setLoginIp(this.getIpAddr(request));
//			bean.setOpType("01");
//
//			String userToken = reg.getUsername();
//			if (StringUtils.checkValidityEmail(reg.getUsername())) {
//				bean.setMailAddress(userToken);
//				bean.setUserId(null);
//			} else {
//				bean.setUserId(userToken);
//			}
//
//			msg = mediator.isLogin(bean, session);
//			if (StringUtils.isEmpty(msg)) {
//				result.setStatus(true);
//				AppSession as = new AppSession();
//				as.set(SystemConfig.USER_ID,mediator.getAppSession().get(PageParamConstant.LOGIN_ID));// 添加个人信息
//				session.setAttribute(SystemConfig.USERNAME_KEY, as);
//				session.removeAttribute(SystemConfig.USER_LOGIN_VERIFY);
//				result.setNickName((String) mediator.getAppSession().get(PageParamConstant.NICK_NAME));
//				session.setAttribute(SystemConfig.APP_SESSION,mediator.getAppSession());
//				return result;
//			} else {
//				result.setMsg(msg);
//				result.setStatus(false);
//
//				session.setAttribute(SystemConfig.USER_LOGIN_VERIFY, "1");
//				return result;
//			}
//		} catch (Exception ex) {
//			logger.error(ExceptionHelper.getExceptionDetail(ex));
//			result.setMsg("登录失败，请重试");
//			result.setStatus(false);
//		}
//		return result;
//	}
//
//	private Object validate(RegisterBean bean, OpResult result, HttpSession session) {
//		if (bean.isHasPwd() && StringUtils.isEmpty(bean.getPwd())) {
//			result.setMsg("请输入密码");
//			result.setStatus(false);
//			return result;
//		}
//
//		result.setStatus(true);
//		return result;
//	}
//	
//	@RequestMapping(value = "/reg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody Object mfaRegister(HttpEntity<RegisterBean> entity,HttpServletRequest request) {
//		String pwdGenerator = null;
//		RegisterBean reg = null;
//		OpResult result = new OpResult();
//		if (entity == null || (reg = entity.getBody()) == null) {
//			return onEntityIsNull(request, "用户注册提交的数据有误");
//		} else {
//			validate(reg, result, request.getSession());
//			if (!result.isStatus()) {
//				return result;
//			}
//			String userId = reg.getUsername();
//			String tel = reg.getTel();
//			String email = reg.getEmail();
//			
//			logger.info("开始注册");
//			RegistMediator mediator =  FWBeanManager.getBean(RegistMediator.class);
//			if (tel != null && mediator.isExistsTel(tel)) {
//				result.setMsg("该手机号已经被注册！");
//				result.setStatus(false);
//				return result;
//			}
//			//如果该账号存在则前面加上平台标识
//			if (userId != null && mediator.isExistUser(userId)) {
//				userId = reg.getPlatCode().toLowerCase()+"_"+userId;
//				if(mediator.isExistUser(userId)){
//					result.setMsg("该账号已经被注册！");
//					result.setStatus(false);
//					return result;
//				}
//			}
//			if (email != null && mediator.isExistMail(email)) {
//				result.setMsg("该邮箱已经被注册！");
//				result.setStatus(false);
//				return result;
//			}
//			
//			if(userId == null){
//				if(tel == null){
//					result.setMsg("账号和手机号至少要填写一个！");
//					result.setStatus(false);
//					return result;
//				}else{
//					userId = biz.GetSingleUserId(tel);
//				}
//			}
//			
//			reg.setUsername(userId);
//			
//			RegistBean bean = new RegistBean();
//			bean.setUserId(userId);
//			if(reg.isHasPwd()){
//				bean.setUserPwd(DesCodeUtil.encrypt(reg.getPwd()));
//			}else{
//				pwdGenerator = String.valueOf(CommonUtil.getRandom(111111, 999999));
//				bean.setUserPwd(DesCodeUtil.encrypt(pwdGenerator));
//			}
//			bean.setMobileNumber(tel);
//			bean.setNickName(userId);
//			bean.setLoginIp(this.getIpAddr(request));
//			if(tel != null){
//				bean.setSysMobile(true);
//			}else{
//				bean.setSysMobile(false);
//			}
//			
//			if(email == null){
//				email = "";
//			}
//			if(tel == null){
//				tel = "";
//			}
//			bean.setInviteUserId("");
//			bean.setMailAddress(email);
//			bean.setMailType("0");
//			bean.setOpType("01");
//				
//			logger.info("执行注册");
//			if (mediator.registUser(bean)) {
//				try {
//					if(bindPlatRegInfo(reg)){
//						result.setStatus(true);
//						sendPwd(reg);
//						logger.info("执行注册完毕");
//						logger.info("执行登陆");
//						login(entity, request);
//						logger.info("执行登录结束");
//						request.getSession().setAttribute(SystemConfig.USER_VERIFY,null);
//						request.getSession().setAttribute("USER_HAS_SMS", null);
//					}else{
//						result.setStatus(false);
//						result.setMsg("绑定第三方平台失败,请重试");
//					}
//				} catch (SQLException e) {
//					logger.error("用户注册"+reg.getUsername()+"绑定第三方平台失败", e);
//					result.setStatus(false);
//					result.setMsg("绑定第三方平台失败,请重试");
//				}
//			}else {
//				result.setStatus(false);
//				result.setMsg("注册失败,请重试");
//			}
//			return result;
//		}
//	}
//
//	
//	/**
//	 * 绑定第三方平台
//	* Title: bindPlatRegInfo
//	* Description: 
//	* @param bean
//	* @return
//	 * @throws SQLException 
//	 */
//	private boolean bindPlatRegInfo(RegisterBean bean) throws SQLException{
//		MfaBiz biz = new MfaBiz();
//		return biz.bindUserInfo(bean);
//	}
//	
//	/**
//	 * 发送密码提示:短信/邮箱(暂只支持短信)
//	* Title: sendPwdSms
//	* Description: 发送短信通知 :恭喜您通过{0}注册成为聚金资本用户,您的用户名是{1}，初始登录、交易、提现密码{2}，请尽快修改。
//	* @param userId
//	* @param phoneNumber
//	* @param pwd
//	 */
//    private void sendPwd(RegisterBean bean) {
//    	if(bean.getTel() != null){
//    		//TODO 
//    	}
//    }
//    
//}
