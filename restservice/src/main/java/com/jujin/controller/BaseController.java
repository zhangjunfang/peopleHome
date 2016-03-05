package com.jujin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jujin.authorize.AppSession;
import com.jujin.authorize.CookieTool;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.common.VerifyType;
import com.jujin.entity.User;
import com.jujin.entity.account.Account;
import com.jujin.utils.ExceptionHelper;

import javax.servlet.http.Cookie;

/*
 * 控制器基类
 * */
public class BaseController {

	protected static final Logger logger = Logger
			.getLogger(BaseController.class);

	protected static final Map<String, AppSession> sessions = new HashMap<String, AppSession>();

	protected String onAuthenticateUser() {

		return onAuthenticateUser(0);
	}

	protected AppSession getAppSession() {
		String token = getToken();
		if (StringUtils.isNotEmpty(token)) {
			return sessions.get(token);
		}
		return null;
	}

	protected String getVerifyCode(VerifyType type, HttpServletRequest request) {
		if (null == request)
			return "";

		HttpSession session = request.getSession();
		Object tmpObj = null;
		if (session != null) {
			switch (type) {
			case USER_VERIFY:
				tmpObj = session.getAttribute(SystemConfig.USER_VERIFY);
				break;
			case USER_REGISTER_VERIFY:
				tmpObj = session
						.getAttribute(SystemConfig.USER_REGISTER_VERIFY);
				break;
			default:
				break;
			}
		}
		if (tmpObj != null) {
			return tmpObj.toString().trim().toLowerCase();
		}
		return "";
	}

	protected String getInvite(HttpServletRequest request) {
		if (null == request)
			return "";

		HttpSession session = request.getSession();
		if (session == null)
			return "";
		Object tmpObj = null;
		tmpObj = session.getAttribute(SystemConfig.INVITE_USER);
		if (tmpObj == null)
			return "";
		return tmpObj.toString();
	}

	protected AppSession getAppSession(HttpServletRequest request) {

		if (request == null)
			return null;
		HttpSession session = request.getSession();

		if (session == null)
			return null;

		AppSession cache = (AppSession) session
				.getAttribute(SystemConfig.USERNAME_KEY);
		if (cache == null)
			return null;
		return cache;
	}

	/**
	 * 获取已经登录的用户名
	 * 
	 * @return
	 */
	protected String getLoginedUserId(HttpServletRequest request) {
		com.jujin.authorize.AppSession as = getAppSession(request);

		if (as != null) {
			String userId = as.getStringValue(SystemConfig.USER_ID);
			return userId;
		} else {
			return null;
		}

	}

	protected OpResult validateOpResult(int pageIndex, int pageSize) {
		OpResult result = new OpResult();
		if (pageIndex < 1) {
			result.setMsg("页数必须大于0");
			result.setStatus(false);
			return result;
		} else if (pageSize < 0) {
			result.setMsg("每页条数必须大于0");
			result.setStatus(false);
			return result;
		}
		result.setStatus(true);
		return result;

	}

	private String getToken() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		String action = "";
		String token = "";
		if (request != null) {
			Cookie cookie = CookieTool.getCookieByName(request, "token");
			if (cookie != null) {
				// token = cookie.getValue();
			}
		}
		// return token;
		return SystemConfig.USERNAME_KEY;
	}

	public String getIpAddr(HttpServletRequest paramHttpServletRequest) {
		String str = paramHttpServletRequest.getHeader("x-forwarded-for");
		if ((str == null) || (str.length() == 0)
				|| ("unknown".equalsIgnoreCase(str))) {
			str = paramHttpServletRequest.getHeader("Proxy-Client-IP");
		}
		if ((str == null) || (str.length() == 0)
				|| ("unknown".equalsIgnoreCase(str))) {
			str = paramHttpServletRequest.getHeader("WL-Proxy-Client-IP");
		}
		if ((str == null) || (str.length() == 0)
				|| ("unknown".equalsIgnoreCase(str))) {
			str = paramHttpServletRequest.getRemoteAddr();

			String host = paramHttpServletRequest.getRemoteHost();
			if (!StringUtils.isEmpty(host)) {
				logger.info(host);
			}
		}
		
		 if(!StringUtils.isEmpty(str)&&str.length()>30)
         {
			 str=str.substring(0, 30);
         }
		return str;
	}
	
	  /** 
     * 获取当前网络ip 
     * @param request 
     * @return 
     */  
    public String getIpAddress(HttpServletRequest request){  
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }
            if(!StringUtils.isEmpty(ipAddress)&&ipAddress.length()>30)
            {
            	ipAddress=ipAddress.substring(0, 30);
            }
            return ipAddress;   
    }  


	protected OpResult onEntityIsNull(OpResult result,
			HttpServletRequest request, String msg) {
		if (result == null) {
			result = new OpResult();
		}
		if (request == null) {
			result.setStatus(false);
			result.setMsg(SystemConfig.ERROR_FORMAT);
			return result;
		}
		result.setStatus(true);
		return result;
	}

	protected OpResult onEntityIsNull(HttpServletRequest request, String msg) {
		return onEntityIsNull(null, request, msg);
	}

	protected OpResult onUserIdIsNull(OpResult result,
			HttpServletRequest request) {

		OpResult orResult = onEntityIsNull(result, request,
				SystemConfig.ERROR_FORMAT);
		if (orResult.isStatus()) {
			String userId = this.getLoginedUserId(request);
			if (StringUtils.isEmpty(userId)) {
				orResult.setStatus(false);
				orResult.setMsg(SystemConfig.NO_LOGIN);
			} else {
				orResult.setStatus(true);
			}
		}
		return orResult;
	}

	/**
	 * 验证用户名和密码
	 * 
	 * @param userid
	 * @param password
	 * @param type
	 * @return
	 */
	protected AppSession authenticate(String userid, String password, int type) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();

		AppSession session = new AppSession();
		request.getSession().setAttribute(SystemConfig.USERNAME_KEY, session);

		return session;
	}

	/**
	 * 
	 * 
	 * @param flg
	 */
	protected String onAuthenticateUser(int flg) {
		String token = getToken();
		String action = "";
		if (!StringUtils.isEmpty(token)) {
			AppSession appsession = sessions.get(token);// 查找不到用户表示用户有可能已经被注销
			if (appsession == null) {
				switch (flg) {
				case 0:
					action = SystemConfig.LOGIN;
					break;
				case 1:// 登录并跳转自个人中心
					action = SystemConfig.LOGINACCOUNT;
					break;
				case 2:
					action = SystemConfig.LOGINBORROW;
					break;
				default:
					action = SystemConfig.LOGIN;
					break;
				}
			}
		}
		return action;
	}

	protected OpResult VerifySmsCode(String code, String tel,
			HttpServletRequest request, OpResult result) {
		if (result == null) {
			OpResult tmpResult = new OpResult();
			tmpResult.setStatus(false);
			return tmpResult;
		}
		String verifyCode = code;
		if (StringUtils.isEmpty(verifyCode)) {
			result.setStatus(false);
			result.setMsg("请输入验证码");
			return result;
		}
		verifyCode = verifyCode.trim().toLowerCase();
		logger.info("USER_REGISTER_VERIFY:"
				+ this.getVerifyCode(VerifyType.USER_REGISTER_VERIFY, request));

		String waitVerifyCode = this.getVerifyCode(
				VerifyType.USER_REGISTER_VERIFY, request);

		String[] tmpArrays = waitVerifyCode.split("\\|");
		if (tmpArrays.length < 2) {
			result.setStatus(false);
			result.setMsg("操作异常，请重试");
			return result;
		}

		String tmpTel = tmpArrays[0];
		waitVerifyCode = tmpArrays[1];

		logger.info("tmpTel:" + tmpTel + " Tel:" + tel);
		if (!tmpTel.equals(tel)) {
			result.setStatus(false);
			result.setMsg("验证手机不正确");
			return result;
		}
		logger.info("verifyCode:" + verifyCode + "waitVerifyCode:"
				+ waitVerifyCode);

		if (!verifyCode.equals(waitVerifyCode)) {
			result.setStatus(false);
			result.setMsg("验证码不正确");
			return result;
		}
		result.setStatus(true);
		return result;
	}

}
