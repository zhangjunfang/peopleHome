package com.jujin.controller.security;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.e;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.UserBiz;
import com.jujin.biz.security.SecurityBiz;
import com.jujin.common.LoginResult;
import com.jujin.common.OpResult;
import com.jujin.controller.BaseController;
import com.jujin.controller.UserController;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.StringUtils;



/**
 * 移动端验证票据实现自动登陆
 * */
@Controller
public class SecurityController extends BaseController {
	
	
	UserController userController=new  UserController();
	UserBiz biz = new UserBiz();
	SecurityBiz securityBiz=new SecurityBiz();
	
	/**
	 * 
	 * 从微信登录
	 * 
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginfromwx", method = RequestMethod.GET)
	public @ResponseBody
	Object loginFromWeiXin(
			@RequestParam(value = "id", required = true) String weiXinId,
			HttpServletRequest request) {

		OpResult result = new OpResult();
		if (StringUtils.isEmpty(weiXinId)) {
			result.setMsg("错误的数据格式");
			result.setStatus(false);
			return result;
		}

		try {

			weiXinId = DesCodeUtil.decrypt(weiXinId);
			WxBindBean bean = biz.QueryWxBindBean(weiXinId);
			if (bean == null) {
				result.setStatus(true);
				result.setMsg("该微信号不存在");
				return result;
			}
			if (bean.getBindFlg() == 1) {
				result.setStatus(true);
				result.setMsg("该微信号尚未绑定账号");
				return result;
			}
			RegisterEntity entity = biz.QueryUserPwd(bean.getUserId());
			if (entity != null) {
				entity.setPwd(DesCodeUtil.decrypt(entity.getPwd()));
				
				HttpEntity<RegisterEntity> he = new HttpEntity<RegisterEntity>(
						entity);
				return userController.login(he, request, false);// 主要是为了要登录记录什么的
			} else {
				result.setMsg("微信号不正确");
				result.setStatus(false);
				return result;
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg("自动登录过程出现错误，请与客服联系");
			result.setStatus(false);
		}
		return result;
	}
	
	
	/**
	 * 
	 * 从移动端登录
	 * 
	 * @param entity
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginfrommobile", method = RequestMethod.GET)
	public @ResponseBody
	Object loginFromMobile(
			@RequestParam(value = "id", required = true) String ticket,
			HttpServletRequest request) {

		OpResult result = new OpResult();
		if (StringUtils.isEmpty(ticket)) {
			result.setMsg("错误的数据格式");
			result.setStatus(false);
			return result;
		}

		try {

			RegisterEntity entity = securityBiz.QueryMobileTicket(ticket);
			if (entity != null) {
				
				entity.setPwd(DesCodeUtil.decrypt(entity.getPwd()));
				HttpEntity<RegisterEntity> he = new HttpEntity<RegisterEntity>(
						entity);
				
				logger.info(entity);
				logger.info("USER_ID:"+entity.getUserName()+"     PWD:"+entity.getPwd());
				LoginResult tmpResult=(LoginResult)userController.login(he, request, false);// 主要是为了要登录记录什么的
				
				
				if(tmpResult!=null&&StringUtils.isEmpty(tmpResult.getTicket()))
				{
					tmpResult.setTicket(ticket);
				}
				return tmpResult;
				
			} else {
				result.setMsg("票据不存在或已过期");
				result.setStatus(false);
				return result;
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg("移动客户端自动登录过程出现错误，请与客服联系");
			result.setStatus(false);
		}
		return result;
	}



}
