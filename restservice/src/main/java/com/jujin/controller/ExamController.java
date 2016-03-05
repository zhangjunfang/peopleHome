package com.jujin.controller;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.jujin.biz.ExamBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.exam.ExamBean;
import com.jujin.exam.ExamUser;
import com.jujin.utils.ExceptionHelper;

/**
 * @author wangning
 * 
 * 
 * 
 */
@Controller
public class ExamController extends BaseController {

	ExamBiz biz = new ExamBiz();

	/**
	 * 参与者的个数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPartCount", method = RequestMethod.GET)
	public @ResponseBody
	Object getPartCount() {

		OpEntityResult<Integer> result = new OpEntityResult<Integer>(0);
		try	
		{
			result.setEntity(biz.getPartCount());
			result.setStatus(true);
		}
		catch(Exception e)
		{
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}
		return result;
	}
	
	@RequestMapping(value = "/getTopPartUser", method = RequestMethod.GET)
	public @ResponseBody
	Object getTopPartUser() {
		JsonList<ExamUser> result=new  JsonList<ExamUser>();
	
		try	
		{
			result.addRange(biz.getTopPartUser());
			result.setStatus(true);
		}
		catch(Exception e)
		{
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}
		return result;
	}

	/**
	 * 获取用户成绩
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getUserScore", method = RequestMethod.GET)
	public @ResponseBody
	Object getUserScore(@RequestParam(value = "id", required = true) String id) {

		OpEntityResult<ExamUser> result = new OpEntityResult<ExamUser>(null);
		try {
			ExamUser user = biz.getUserScore(id);
			if (user!=null) {
				result.setEntity(user);
			} else {
				result.setMsg("未参加");
			}
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}

		return result;
	}

	@RequestMapping(value = "/submitUseScore", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object submitUserScore(HttpEntity<ExamUser> entity) {

		
		ExamUser bean=entity.getBody();
		OpEntityResult<Integer> result = new OpEntityResult<Integer>(0);
		
		if(bean==null||StringUtils.isEmpty(bean.getOpenId())||bean.getScore()<0)
		{
			 result.setMsg("错误的数据格式");
			 result.setStatus(false);
			 return result;
		}
		try {
			
			ExamUser user = biz.getUserScore(bean.getOpenId());
			if(user!=null)
			{
				result.setStatus(false);
				return result;
			}
			if(null==bean.getHeadUrl())
			{
				bean.setHeadUrl("");
			}
			if(null==bean.getNickName())
			{
				bean.setNickName("");
			}
			if(null==bean.getHeadUrl())
			{
				bean.setHeadUrl("");
			}
			
			result.setEntity(biz.submitUserScore(bean));
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}
		return result;
	}

	@RequestMapping(value = "/shareSuccess", method = RequestMethod.GET)
	public @ResponseBody
	Object shareSuccess(String id) {
		OpEntityResult<Integer> result = new OpEntityResult<Integer>(0);
		try {
			biz.shareSuccess(id);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}
		return result;
	}

}
