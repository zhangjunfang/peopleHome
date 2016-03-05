package com.jujin.controller;

import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.ActivityBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.utils.ExceptionHelper;

/**
 * 聚金资本 在线破亿活动
 * 
 * **/
@Controller
public class ActivityController extends BaseController {

	ActivityBiz biz = new ActivityBiz();

	@RequestMapping(value = "/grantusercoin", method = RequestMethod.GET)
	public @ResponseBody
	Object grantUserCoin(@RequestParam(value = "id", required = true) String id) {

		logger.info(String.format(" 发放用户[%s] 168聚金币", id));
		OpResult result = new OpResult();

		try {

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

	@RequestMapping(value = "/grantuservip", method = RequestMethod.GET)
	public @ResponseBody
	Object grantUserVip(@RequestParam(value = "id", required = true) String id) {

		logger.info(String.format(" 发放用户[%s] 6个月VIP", id));

		OpResult result = new OpResult();

		return result;

	}

	@RequestMapping(value = "/grantcoin", method = RequestMethod.GET)
	public @ResponseBody
	Object grantCoin() {
		OpResult result = new OpResult();
		try {
			biz.grantCoin();
			result.setStatus(true);
		} catch (Exception e) {
			result.setStatus(false);
			String error = ExceptionHelper.getExceptionDetail(e);
			result.setMsg(error);
			logger.error(error);
		}

		return result;

	}

	@RequestMapping(value = "/grantvip", method = RequestMethod.GET)
	public @ResponseBody
	Object grantVip() {
		OpResult result = new OpResult();

		try {
			biz.grantVip();
			result.setStatus(true);
		} catch (Exception e) {
			result.setStatus(false);
			String error = ExceptionHelper.getExceptionDetail(e);
			result.setMsg(error);
			logger.error(error);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/notifycoin", method = RequestMethod.GET)
	public @ResponseBody
	Object notifyCoin(String type) {
		OpResult result = new OpResult();

		try {
			
			SendSmsBean msgBean=new SendSmsBean();
			msgBean.setPhoneNumber("13683815260");
			msgBean.setSendType(SendTypeEnum.MSG);
			msgBean.setBorrowTitle("");
			msgBean.setMoney("");
			msgBean.setUserId("firetw");
			
			
			if(type.equals("0"))
			{
				msgBean.setSmsType(SmsTypeEnum.SMS_JUJIN_COIN_NOTIFY);	
			}
			else if(type.equals("1"))
			{
				msgBean.setSmsType(SmsTypeEnum.SMS_JUJIN_COIN_NOTIFY);	
			}
			 
			logger.info(String.format("Send Msg For [%s]",msgBean.getPhoneNumber()));
			biz.sendMobileMessage(msgBean);
			
			
			
			result.setStatus(true);
		} catch (Exception e) {
			result.setStatus(false);
			String error = ExceptionHelper.getExceptionDetail(e);
			result.setMsg(error);
			logger.error(error);
		}
		return result;
	}
	
	
	@RequestMapping(value = "/notifyvip", method = RequestMethod.GET)
	public @ResponseBody
	Object notifyvip() {
		OpResult result = new OpResult();

		try {
			biz.notifyVip();
			result.setStatus(true);
		} catch (Exception e) {
			result.setStatus(false);
			String error = ExceptionHelper.getExceptionDetail(e);
			result.setMsg(error);
			logger.error(error);
		}
		return result;
	}
	
	
	
	

}
