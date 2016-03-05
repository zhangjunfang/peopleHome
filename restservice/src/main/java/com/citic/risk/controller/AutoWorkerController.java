package com.citic.risk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.UserBiz;
import com.jujin.common.OpResult;
import com.jujin.controller.BaseController;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.FWBeanManager;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.common.bean.RegistBean;
import com.wicket.loan.web.regist.mediator.RegistMediator;

@Controller
public class AutoWorkerController extends BaseController {

	UserBiz biz = new UserBiz();
	RegistMediator mediator = FWBeanManager.getBean(RegistMediator.class);

	 
	@RequestMapping(value = "/myregister", method = RequestMethod.GET)
	public @ResponseBody
	Object myregister(String tel, String pwd, HttpServletRequest request) {

		OpResult result = new OpResult();
		if(StringUtils.isEmpty(tel)||!UserUtils.isMobileNumber(tel)||StringUtils.isEmpty(pwd))
		{
			result.setMsg("错误的数据格式");
			result.setStatus(false);
			return result;
		}

		RegistBean bean = new RegistBean();
		bean.setUserId(biz.GetSingleUserId(tel));

		logger.info("GetSingleUserId:" + bean.getUserId());
		bean.setUserPwd(DesCodeUtil.encrypt(pwd));
		bean.setMobileNumber(tel);
		bean.setNickName(tel);
		bean.setLoginIp(this.getIpAddr(request));
		bean.setSysMobile(true);

		bean.setInviteUserId("");
		bean.setMailAddress("");
		bean.setMailType("0");
		bean.setOpType("01");

		logger.info("执行注册");
		if (mediator.registUser(bean)) {
			result.setStatus(true);
			logger.info("执行注册完毕");
		} else {
			result.setStatus(false);
			result.setMsg("注册失败,请重试");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/currentuser", method = RequestMethod.GET)
	public @ResponseBody
	Object currentUser(HttpServletRequest request) {

		int result=0;
		try	
		{
			result=biz.GetCurrentUser();
		}
		catch(Exception ex)
		{
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		return result;
	}

}
