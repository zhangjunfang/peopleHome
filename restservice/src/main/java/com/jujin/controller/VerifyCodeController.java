package com.jujin.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sms.main.SendSmsMessage;
import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;
import oracle.net.aso.p;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.AttestBiz;
import com.jujin.biz.VerifyCodeBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.common.VerifyType;
import com.jujin.entity.UserAttestation;
import com.jujin.utils.ExceptionHelper;
import com.wicket.loan.common.constant.Config;
import com.wicket.loan.common.utils.UserUtils;

/**
 * @author wangning
 * 
 *         modify by wangning add 短信验证码和短信黑名单
 * 
 */
@Controller
public class VerifyCodeController extends BaseController {

	VerifyCodeBiz biz = new VerifyCodeBiz();

	AttestBiz attestBiz = new AttestBiz();

	private static final String USER_HAS_SMS = "USER_HAS_SMS";

	/**
	 * 获取用户中心的首页数据
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public @ResponseBody
	Object getVerifyImage(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			response.setContentType("textml;charset=UTF-8");
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");

			response.setContentType("image/jpg");
			OutputStream os = response.getOutputStream();
			MakeVerifyCodeService service = MakeVerifyCodeService
					.getVerifyCode(60, 20);
			String code = service.getCode();
			os.write(service.getImage());
			logger.info("生成的验证码：" + code);
			HttpSession session = request.getSession();
			if (session != null) {
				session.setAttribute(SystemConfig.USER_VERIFY, code);
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return null;
	}

	/* 移动端使用的验证码图片 */
	@RequestMapping(value = "/verifyimg", method = RequestMethod.GET)
	public @ResponseBody
	Object getVerifyImageMobile(HttpServletRequest request,
			HttpServletResponse response) {

		String file = "";
		String fileName = "";
		String urlString = "";
		try {

			request.getRequestURI();
			response.setContentType("textml;charset=UTF-8");
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");

			String path = Config.get("VerifyImageDir");
			urlString = Config.get("VerifyImageUrl");
			if (!urlString.endsWith("/")) {
				urlString += "/";
			}
			if (StringUtils.isEmpty(path)) {
				return "动态图片路径不存在";
			}
			File dir = new File(path);
			if (!dir.exists() || !dir.isDirectory()) {
				dir.mkdir();
			}

			UUID uuid = UUID.randomUUID();

			fileName = uuid.toString().toLowerCase().replace("-", "") + ".jpg";

			if (path.endsWith("/")) {
				file = path + fileName;
			} else {
				file = path + "/" + fileName;
			}

			OutputStream os = new FileOutputStream(file);
			MakeVerifyCodeService service = MakeVerifyCodeService
					.getVerifyCode(60, 20);
			String code = service.getCode();
			os.write(service.getImage());
			logger.info("生成的验证码：" + code);
			HttpSession session = request.getSession();
			if (session != null) {
				session.setAttribute(SystemConfig.USER_VERIFY, code);
			}
			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return urlString + fileName;
	}

	@RequestMapping(value = "/queryuserbymobile", method = RequestMethod.GET)
	public @ResponseBody
	Object getUserIdByMobile(
			@RequestParam(value = "tel", required = true) String tel,
			HttpServletRequest request) {
		OpEntityResult<String> result = new OpEntityResult<String>("");

		try {

			List<String> users = biz.getUserByMobile(tel);
			result.setStatus(true);
			if (users == null||users.size()<1) {
				result.setEntity("该手机号在系统中不存在");
				result.setMsg("该手机号在系统中不存在");
			} else if (users.size() > 1) {
				result.setEntity("请输入用户名");
				result.setMsg("请输入用户名");
			} else {
				result.setEntity(users.get(0));
			}
		} catch (Exception e) {
			result.setStatus(false);
			result.setMsg("获取手机号对应的用户名失败，请重试");
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return result;
	}

	@RequestMapping(value = "/smsverifyimg3", method = RequestMethod.GET)
	public @ResponseBody
	Object getVerifyImgSms(
			@RequestParam(value = "tel", required = true) String tel,
			@RequestParam(value = "img", required = true) String imgVerify,
			HttpServletRequest request, HttpServletResponse response) {
		OpResult result = new OpResult();

		if (StringUtils.isEmpty(imgVerify)) {
			result.setMsg("请输入验证码");
			result.setStatus(false);
			return result;
		}

		HttpSession session = request.getSession();
		Object verifyObj = session.getAttribute(SystemConfig.USER_VERIFY);
		String verifyFlg = "";
		if (verifyObj != null) {
			verifyFlg = verifyObj.toString();
		}
		if (!imgVerify.equals(verifyFlg)) {
			result.setMsg("验证码不正确");
			result.setStatus(false);
			return result;
		}
		session.setAttribute(USER_HAS_SMS, null);
		return getVerifySms(tel, request, response);
	}

	/**
	 * 用户是否出现过短信验证失败,如出现登录失败，需要用户输入图片验证码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querysmsstatus", method = RequestMethod.GET)
	public @ResponseBody
	Object QuerySmsStatus(HttpServletRequest request) {

		HttpSession session = request.getSession();
		OpEntityResult<Integer> result = new OpEntityResult<Integer>(0);
		result.setStatus(true);
		if (session != null) {
			Object verifyObj = session.getAttribute(USER_HAS_SMS);
			if (verifyObj != null) {
				result.setEntity(1);
				return result;
			} else {
				result.setEntity(0);
			}
		}
		result.setEntity(0);
		return result;

	}

	/**
	 * 短信验证码
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/smsverify6", method = RequestMethod.GET)
	public @ResponseBody
	Object getVerifySms(
			@RequestParam(value = "tel", required = true) String tel,
			HttpServletRequest request, HttpServletResponse response) {

		OpResult result = new OpResult();

		try {
			HttpSession session = request.getSession();
			if (session == null) {
				result.setStatus(false);
				result.setMsg("错误的请求");
				return result;
			}

			Object verifyOb = session.getAttribute(USER_HAS_SMS);

			if (verifyOb != null) {
				result.setStatus(false);
				result.setMsg("请输入验证码");
				return result;
			}

			if (!UserUtils.isMobileNumber(tel)) {
				result.setStatus(false);
				result.setMsg("请输入正确的手机号");
				return result;
			}
			if (session != null) {
				session.setAttribute(USER_HAS_SMS, "1");
			}

			/*
			 * List<String> users=biz.getUserByMobile(tel);
			 * if(users!=null&&users.size()>0) { result.setStatus(false);
			 * result.setMsg("该手机号已注册，请登录"); return result; }
			 */

			String ipaddress = getIpAddress(request);
			logger.info(String.format("VerifyCodeController.getVerifySms smsverify: %s,tel:%s", ipaddress, tel));

			OpResult checkStatus = CanSendSms(session, tel);
			if (!checkStatus.isStatus()) {
				logger.info(String.format("手机号:%s 已被拦截", tel));
				return checkStatus;
			}

			SendSmsBean smsBean = new SendSmsBean();
			smsBean.setUserId("尊敬的智慧投资人");// 用户昵称
			smsBean.setPhoneNumber(tel);// 号码
			smsBean.setSmsType(SmsTypeEnum.WEIXIN_USER_REGIST);
			smsBean.setSendType(SendTypeEnum.MSG);

			if (biz.sendMobileMessage(smsBean)) {
				result.setStatus(true);
				String verifyCode = smsBean.getVerifyCode();
				verifyCode = tel + "|" + verifyCode;

				logger.info("生成的验证码：" + verifyCode);

				RecordSms(session, tel, verifyCode);
			} else {
				result.setStatus(false);
				result.setMsg("发送失败，请重试");
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return result;
	}
	
	
	/**
	 * 短信验证码
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/usercheck", method = RequestMethod.GET)
	public @ResponseBody
	Object checkUserId(
			@RequestParam(value = "id", required = true) String id,
			HttpServletRequest request, HttpServletResponse response) {

		OpEntityResult<String> result=new  OpEntityResult<String>("");
		try {
			
			boolean isExists=biz.checkUserById(id);
			result.setEntity(isExists?"1":"0");
			result.setStatus(true);
		} catch (Exception e) {
			 logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return result;
	}
	
	
	
	
	/**
	 * 短信验证码
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/mobilesms", method = RequestMethod.GET)
	public @ResponseBody
	Object getVerifyMobileSms(
			@RequestParam(value = "tel", required = true) String tel,
			HttpServletRequest request, HttpServletResponse response) {

		OpResult result = new OpResult();

		try {
			HttpSession session = request.getSession();
			if (session == null) {
				result.setStatus(false);
				result.setMsg("错误的请求");
				return result;
			}

			Object verifyOb = session.getAttribute(USER_HAS_SMS);

			if (verifyOb != null) {
				result.setStatus(false);
				result.setMsg("请输入验证码");
				return result;
			}

			if (!UserUtils.isMobileNumber(tel)) {
				result.setStatus(false);
				result.setMsg("请输入正确的手机号");
				return result;
			}
			if (session != null) {
				session.setAttribute(USER_HAS_SMS, "1");
			}

			/*
			 * List<String> users=biz.getUserByMobile(tel);
			 * if(users!=null&&users.size()>0) { result.setStatus(false);
			 * result.setMsg("该手机号已注册，请登录"); return result; }
			 */

			String ipaddress = getIpAddress(request);
			logger.info(String.format("VerifyCodeController.getVerifySms smsverify: %s,tel:%s", ipaddress, tel));

			OpResult checkStatus = CanSendSms(session, tel);
			if (!checkStatus.isStatus()) {
				logger.info(String.format("手机号:%s 已被拦截", tel));
				return checkStatus;
			}

			SendSmsBean smsBean = new SendSmsBean();
			smsBean.setUserId("尊敬的智慧投资人");// 用户昵称
			smsBean.setPhoneNumber(tel);// 号码
			smsBean.setSmsType(SmsTypeEnum.WEIXIN_USER_REGIST);
			smsBean.setSendType(SendTypeEnum.MSG);

			if (biz.sendMobileMessage(smsBean)) {
				result.setStatus(true);
				String verifyCode = smsBean.getVerifyCode();
				verifyCode = tel + "|" + verifyCode;

				logger.info("生成的验证码：" + verifyCode);

				RecordSms(session, tel, verifyCode);
			} else {
				result.setStatus(false);
				result.setMsg("发送失败，请重试");
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return result;
	}


	/**
	 * 新用户
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 * @throws UnsupportedEncodingException
	 */
	/*
	 * @RequestMapping(value = "/registersms", method = RequestMethod.GET)
	 * public @ResponseBody Object getRegisterSmsVerify(
	 * 
	 * @RequestParam(value = "tel", required = true) String tel,
	 * HttpServletRequest request, HttpServletResponse response) {
	 * 
	 * OpResult result = new OpResult();
	 * 
	 * try {
	 * 
	 * if (!UserUtils.isMobileNumber(tel)) { result.setStatus(false);
	 * result.setMsg("请输入正确的手机号"); return result; }
	 * 
	 * List<String> users=biz.getUserByMobile(tel);
	 * if(users!=null&&users.size()>0) { result.setStatus(false);
	 * result.setMsg("该手机号已注册，请登录"); return result; }
	 * 
	 * String ipaddress = getIpAddr(request);
	 * logger.info(String.format("smsverify: %s,tel:%s", ipaddress, tel));
	 * 
	 * 
	 * HttpSession session = request.getSession(); OpResult checkStatus =
	 * CanSendSms(session, tel); if (!checkStatus.isStatus()) {
	 * logger.info(String.format("手机号:%s 已被拦截", tel)); return checkStatus; }
	 * 
	 * SendSmsBean smsBean = new SendSmsBean(); smsBean.setUserId("尊敬的智慧投资人");//
	 * 用户昵称 smsBean.setPhoneNumber(tel);// 号码
	 * smsBean.setSmsType(SmsTypeEnum.WEIXIN_USER_REGIST);
	 * smsBean.setSendType(SendTypeEnum.MSG);
	 * 
	 * if (biz.sendMobileMessage(smsBean)) { result.setStatus(true); String
	 * verifyCode = smsBean.getVerifyCode(); verifyCode = tel + "|" +
	 * verifyCode;
	 * 
	 * logger.info("生成的验证码：" + verifyCode);
	 * 
	 * RecordSms(session, tel, verifyCode); } else { result.setStatus(false);
	 * result.setMsg("发送失败，请重试"); } } catch (Exception e) {
	 * logger.error(ExceptionHelper.getExceptionDetail(e)); } return result; }
	 */

	private void RecordSms(HttpSession session, String phoneNumber,
			String verifyCode) {
		session.setAttribute(SystemConfig.USER_REGISTER_VERIFY, verifyCode);

		session.setAttribute(SystemConfig.USER_REGISTER_VERIFY_TIME, new Date());

		biz.addSmsVerify(phoneNumber, verifyCode);
	}

	private OpResult CanSendSms(HttpSession session, String phoneNumber) {

		OpResult opentity = new OpResult();

		if (session == null) {
			opentity.setStatus(false);
			opentity.setMsg("数据格式不正确");
			return opentity;
		}

		// 时间校验和黑名单检验 60秒让发一次
		Object cacheObj = session
				.getAttribute(SystemConfig.USER_REGISTER_VERIFY_TIME);

		if (cacheObj != null) {
			Date cacheDate = (Date) cacheObj;
			Date currentDate = new Date();
			if ((currentDate.getTime() - cacheDate.getTime()) < 30000) {
				opentity.setStatus(false);
				opentity.setMsg("短信请求过于频繁,请稍后再试");
				return opentity;
			}
		}

		if (!biz.canSendSms(phoneNumber)) {
			opentity.setStatus(false);
			opentity.setMsg("短信请求过于频繁,请稍后再试");
			return opentity;
		}
		opentity.setStatus(true);
		return opentity;

	}

	/**
	 * 短信验证码
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/sms2", method = RequestMethod.GET)
	public @ResponseBody
	Object getModifyEmailVerifySms(HttpServletRequest request,
			HttpServletResponse response) {

		OpResult result = new OpResult();
		try {
			if (!this.onUserIdIsNull(result, request).isStatus()) {
				return result;
			}

			String userId = this.getLoginedUserId(request);
			UserAttestation entity = attestBiz.QueryUserAttestation(userId);

			HttpSession session = request.getSession();
			OpResult checkStatus = CanSendSms(session, entity.getRealMobile());

			if (!UserUtils.isMobileNumber(entity.getRealMobile())) {
				result.setStatus(false);
				result.setMsg("请输入正确的手机号");
				return result;
			}

			String ipaddress = getIpAddr(request);
			logger.info(String.format("sms: %s,tel:%s", ipaddress,
					entity.getRealMobile()));

			if (!checkStatus.isStatus()) {
				logger.info(String.format("手机号:%s 已被拦截", entity.getRealMobile()));
				return checkStatus;
			}

			SendSmsBean smsBean = new SendSmsBean();
			smsBean.setUserId(entity.getNickName());// 用户昵称
			smsBean.setPhoneNumber(entity.getRealMobile());// 号码
			smsBean.setSmsType(SmsTypeEnum.IDENTITY);
			smsBean.setSendType(SendTypeEnum.MSG);
			if (SendSmsMessage.makeSmsMessage(smsBean)) {
				result.setStatus(true);

				String verifyCode = smsBean.getVerifyCode();
				verifyCode = entity.getRealMobile() + "|" + verifyCode;

				logger.info("生成验证码：" + verifyCode);

				if (session != null) {
					RecordSms(session, entity.getRealMobile(), verifyCode);
				}
			} else {
				result.setStatus(false);
				result.setMsg("发送失败，请重试");
			}

		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return result;
	}

	public synchronized String getInviteUrl(HttpServletRequest request) {
		String userId = this.getLoginedUserId(request);

		if (!StringUtils.isEmpty(userId)) {
			String mobile = biz.getUserMobile(userId);
			if (StringUtils.isEmpty(mobile)) {
				mobile = userId;
			}
			return SystemConfig.MOBILE_ROOT + "#/register?invite=" + mobile;
		}
		return "";
	}

	// 生成二维码
	@RequestMapping(value = "/popularity", method = RequestMethod.GET)
	public @ResponseBody
	Object getPopularityImage(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("popularity执行开始");

		OpResult result = new OpResult();
		try {
			if (!onUserIdIsNull(result, request).isStatus()) {
				logger.info("result==" + result);
				return result;
			}
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");
			String inviteUrl = getInviteUrl(request);
			logger.info("inviteUrl==" + inviteUrl);
			if (!StringUtils.isEmpty(inviteUrl)) {
				ServletOutputStream os = response.getOutputStream();
				os.write(QRCodeUtil.getImage(inviteUrl, true));
				os.close();
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		logger.info("popularity执行结束");
		return null;
	}

	/* 移动端使用的验证码图片 */
	@RequestMapping(value = "/popularitymobile", method = RequestMethod.GET)
	public @ResponseBody
	Object getPopularityMobileImage(String w, String h,
			HttpServletRequest request, HttpServletResponse response) {

		String file = "";
		String fileName = "";
		String urlString = "";
		try {

			String userId = this.getLoginedUserId(request);
			OpResult result = new OpResult();
			if (StringUtils.isEmpty(userId)) {
				result.setStatus(false);
				result.setMsg(SystemConfig.NO_LOGIN);
				return result;
			}

			request.getRequestURI();
			response.setContentType("textml;charset=UTF-8");
			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
			request.setCharacterEncoding("UTF-8");

			String path = Config.get("VerifyImageDir");
			urlString = Config.get("VerifyImageUrl");

			if (!urlString.endsWith("/")) {
				urlString += "/";
			}
			if (StringUtils.isEmpty(path)) {
				return "动态图片路径不存在";
			}
			File dir = new File(path);
			if (!dir.exists() || !dir.isDirectory()) {
				dir.mkdir();
			}

			UUID uuid = UUID.randomUUID();

			fileName = uuid.toString().toLowerCase().replace("-", "") + ".png";

			if (path.endsWith("/")) {
				file = path + fileName;
			} else {
				file = path + "/" + fileName;
			}
			int width = 300;
			int height = 300;

			if (!StringUtils.isEmpty(w)) {
				width = Integer.parseInt(w);
			}
			if (!StringUtils.isEmpty(h)) {
				height = Integer.parseInt(h);
			}
			OutputStream os = new FileOutputStream(file);

			String inviteUrl = getInviteUrl(request);
			logger.info("inviteUrl==" + inviteUrl);
			os.write(QRCodeUtil.getImage(inviteUrl, true, width, height));

			os.flush();
			os.close();
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return urlString + fileName;

	}

	@RequestMapping(value = "/QuerySms", method = RequestMethod.GET)
	public @ResponseBody
	Object QuerySms(String tel, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("QuerySms 执行开始");

		OpResult result = new OpResult();
		try {
			if (!onUserIdIsNull(result, request).isStatus()) {
				logger.info("result==" + result);
				return result;
			}
			List<String> contents = biz.getUserCodeByTel(tel);
			JsonList<String> list = new JsonList<String>();
			list.setStatus(true);
			list.addRange(contents);

			return list;

		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		logger.info("QuerySms 执行结束");
		return null;
	}
	
	@RequestMapping(value = "/QuerySmsAddress", method = RequestMethod.GET)
	public @ResponseBody
	Object QuerySmsAddress(String tel, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("QuerySmsAddress 执行开始");

		OpEntityResult<String> result = new OpEntityResult<String>("");
		try {
			String url=biz.querySmsAddress();
			if(!StringUtils.isEmpty(url))
			{
				result.setEntity(url);
				result.setStatus(true);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		logger.info("QuerySmsAddress 执行结束");
		return result;
	}

}
