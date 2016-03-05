package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import net.sms.main.SendSmsMessage;
import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.common.OpResult;
import com.jujin.entity.MessageBean;
import com.jujin.utils.ExceptionHelper;

@Controller
public class CustomerMsgController extends BaseController {

	@RequestMapping(value = "/customermsg", method = RequestMethod.POST)
	public @ResponseBody
	Object getAccountMemo(HttpEntity<MessageBean> entity, HttpServletRequest request) {
		
		OpResult result = new OpResult();
		if (entity == null) {
			return onEntityIsNull(result, request, "数据格式不正确，请检查");
		}
		
		MessageBean bean = entity.getBody();
		if (bean == null) {
			result.setMsg("数据格式不正确，请检查");
			result.setStatus(false);
			return result;
		}  
		
		String msg=String.format("用户[%s],手机[%s],内容[%s]", bean.getUserId(),bean.getPhoneNumber(),bean.getMessage());
		logger.info(msg);
		try {

			SendSmsBean smsBean=new SendSmsBean();
			smsBean.setPhoneNumber(bean.getPhoneNumber());
			smsBean.setContents(bean.getMessage());
			smsBean.setUserId(bean.getUserId());
			smsBean.setSendType(SendTypeEnum.MSG);
			smsBean.setSmsType(SmsTypeEnum.CUSTOMER_MESSAGE);
			
			
			 SendSmsMessage.makeSmsMessage(smsBean);
			result.setMsg(msg);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		return result;
	}

}
