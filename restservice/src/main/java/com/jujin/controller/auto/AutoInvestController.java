package com.jujin.controller.auto;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.auto.AutoInvestBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.controller.BaseController;
import com.jujin.entity.account.AccountLog;
import com.jujin.entity.auto.AutoInvestBean;
import com.jujin.entity.auto.AutoInvestRecord;
import com.jujin.entity.auto.AutoInvestSetting;
import com.jujin.utils.ExceptionHelper;

/**
 * 
 * 自动投标设置
 * 
 * **/
@Controller
public class AutoInvestController extends BaseController {

	AutoInvestBiz biz = new AutoInvestBiz();

	@RequestMapping(value = "/setting/auto", method = RequestMethod.GET)
	public @ResponseBody
	Object getAutoSetting(HttpServletRequest request) {

		OpEntityResult<AutoInvestBean> result = new OpEntityResult<AutoInvestBean>(
				null);
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
		
		
		

		logger.info(String.format("查询 [%s]用户 自动投标设置", userId));
		try {

			AutoInvestBean bean = biz.getAutoInvestBean(userId);
			if (bean != null) {
				result.setEntity(bean);
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg("获取自动投标设置异常，请重试或与客服联系");
			}
		} catch (Exception e) {
			logger.error("获取用户自动投标设置异常："
					+ ExceptionHelper.getExceptionDetail(e));
			result.setMsg("获取自动投标设置异常，请重试或与客服联系");
			result.setStatus(false);
		}
		return result;
	}

	@RequestMapping(value = "/setting/statuschanged", method = RequestMethod.GET)
	public @ResponseBody
	Object updateAutoSetting(int id, int status) {
		OpResult result = new OpResult();
		try {
			if (biz.updateAutoSetting(id, status)) {
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg("自动投标设置异常，请重试或与客服联系");
			}
		} catch (Exception e) {
			logger.info(String.format(" SEQ [%s], USING_FLG [%s]", id, status)
					+ ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("获取自动投标设置异常，请重试或与客服联系");
		}
		return result;
	}
	
	@RequestMapping(value = "/setting/delautoinvest", method = RequestMethod.GET)
	public @ResponseBody
	Object deleteAutoSetting(int id, HttpServletRequest request) {
		
		OpResult result=new OpResult();
		
		try {
			String userId = this.getLoginedUserId(request);
			if (StringUtils.isEmpty(userId)) {
				result.setStatus(false);
				result.setMsg(SystemConfig.NO_LOGIN);
				return result;
			}
			biz.DeleteAutoSetting(id);
			result.setStatus(true);
		} catch (Exception ex) {
			 logger.error(ExceptionHelper.getExceptionDetail(ex));
			 result.setMsg("删除自动投标设置异常");
		}
		return result;
	}
	
	@RequestMapping(value = "/setting/getsetting", method = RequestMethod.GET)
	public @ResponseBody
	Object getAutoSetting(int pi, int ps, HttpServletRequest request) {

		JsonList<AutoInvestSetting> result = new JsonList<AutoInvestSetting>();
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
			result = biz.getAutoSetting(userId, pi, ps);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			result.setStatus(false);
			result.setMsg(String.format("获取用户  [%s] 的自动投标设置异常,请和客服联系", userId));
		}
		return result;

	}

	@RequestMapping(value = "/setting/autorecord", method = RequestMethod.GET)
	public @ResponseBody
	Object getAutoRecord(int pi, int ps, HttpServletRequest request) {

		JsonList<AutoInvestRecord> result = new JsonList<AutoInvestRecord>();
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
			result = biz.getAutoRecord(userId, pi, ps);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			
			result.setStatus(false);
			result.setMsg(String.format("获取用户  [%s] 的自动投标设置异常,请和客服联系", userId));
		}
		return result;

	}

	@RequestMapping(value = "/setting/autosetting", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object addOrModifyAutoSetting(HttpEntity<AutoInvestSetting> settings,
			HttpServletRequest request) {

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
		if(settings==null||settings.getBody()==null)
		{
			result.setStatus(false);
			result.setMsg("错误的数据类型");
			return result;
		}
		AutoInvestSetting bean=settings.getBody();
		
		if(bean.getPeriodEnd()==0)
		{
			bean.setPeriodEnd(12);
		}
		bean.setUserId(userId);
	
		try {
			if (biz.addOrModifyAutoSetting(bean)) {
				result.setStatus(true);
			} else {
				result.setStatus(false);
				result.setMsg("自动投标设置异常,请重试或与客服联系");
			}

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			result.setStatus(false);
			result.setMsg("自动投标设置异常,请重试或与客服联系");
		}
		return result;
	}

}
