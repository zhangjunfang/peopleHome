package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jujin.biz.common.CommonBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.OpResult;
import com.jujin.controller.auto.AutoInvestController;
import com.jujin.controller.common.CommonController;
import com.jujin.entity.DispatcherObject;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.account.UserBankCard;
import com.jujin.entity.account.WithdrawEntity;
import com.jujin.entity.auto.AutoInvestSetting;
import com.jujin.entity.borrow.TenderBean;
import com.jujin.entity.common.DeviceLocInfo;
import com.jujin.entity.recharge.RechargeParam;
import com.jujin.entity.security.PwdBean;
import com.pro.common.util.StringUtils;

@Controller
public class DispatcherController extends BaseController {

	UserController userController = new UserController();
	HomeControler homeController = new HomeControler();
	PwdController pwdController = new PwdController();
	RechargeController rechargeController = new RechargeController();
	CommonController commonController=new CommonController();
	AutoInvestController autoController=new AutoInvestController();

	//移动端POST需要这么东西
	@RequestMapping(value = "/dispatcher", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public @ResponseBody
	Object Dispatcher(String handler, String content, HttpServletRequest request) {
		
		logger.info(String.format("HANDLER: %s  Content: %s", handler,content));
		
		
		if (StringUtils.isEmpty(handler)) {
			return new OpResult();
		}
		try {
			switch (handler.toLowerCase()) {
			case "login":
				return userController.login(
						new HttpEntity<RegisterEntity>(JSON.parseObject(
								content, RegisterEntity.class)), request, true);
			case "tend":
				return homeController.tenderBorrow(new HttpEntity<TenderBean>(
						JSON.parseObject(content, TenderBean.class)), request);
			case "changepwd":
				return pwdController.changePwd(
						new HttpEntity<PwdBean>(JSON.parseObject(content,
								PwdBean.class)), request);
			case "changepwddirect":
				return pwdController.changePwdDirect(new HttpEntity<PwdBean>(
						JSON.parseObject(content, PwdBean.class)), request);
			case "recharge":
				return rechargeController.recharge(
						new HttpEntity<RechargeParam>(JSON.parseObject(content,
								RechargeParam.class)), request);
			case "bindcard":
				return rechargeController.BindBankCard(
						new HttpEntity<UserBankCard>(JSON.parseObject(content,
								UserBankCard.class)), request);
			case "register":
				return userController.register(new HttpEntity<RegisterEntity>(
						JSON.parseObject(content, RegisterEntity.class)),
						request);
			case "withdraw":
				return userController.withdraw(new HttpEntity<WithdrawEntity>(
						JSON.parseObject(content, WithdrawEntity.class)),
						request);
			case "mobilerecharge":
				return rechargeController.mobilerecharge(
						new HttpEntity<RechargeParam>(JSON.parseObject(content,
								RechargeParam.class)), request);
			case "setting/autosetting"://自动投标
				return autoController.addOrModifyAutoSetting(new HttpEntity<AutoInvestSetting>(JSON.parseObject(content, AutoInvestSetting.class)),request);//HttpEntity<AutoInvestSetting>
			case "postdeviceinfo"://位置信息
				return  commonController.Postdeviceinfo(new HttpEntity<DeviceLocInfo>(JSON.parseObject(content, DeviceLocInfo.class)),
						 request);
			default:
				break;
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}

		return new OpResult();
	}

}
