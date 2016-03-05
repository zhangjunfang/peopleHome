package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.UserBiz;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.Account;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.StringUtils;
import com.wicket.loan.common.utils.NumberUtils;


/*
 * 微信相关的操作,绑定是在登录步骤完成的
 *
 * */
@Controller
public class WeiXinController extends BaseController {

	
	UserBiz biz = new UserBiz();
	
	@RequestMapping(value = "/wxstatus", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryWeiXinBindStatus(
			@RequestParam(value = "id", required = true) String weiXinId) {

		OpEntityResult<Boolean> result = new OpEntityResult<Boolean>(null);
		try {
			weiXinId = DesCodeUtil.decrypt(weiXinId);
			WxBindBean bean = biz.QueryWxBindBean(weiXinId);
			if (bean != null && bean.getBindFlg() == 0) {
				result.setEntity(true);
			} else {
				result.setEntity(false);
			}
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("绑定过程中出现错误，请与客服联系");
		}
		return result;
	}
	
	@RequestMapping(value = "/updatewx", method = RequestMethod.GET)
	public @ResponseBody
	Object updateWx(
			@RequestParam(value = "id", required = true) String weiXinId,
			int state, HttpServletRequest request) {

		OpResult result = new OpResult();
		if (StringUtils.isEmpty(weiXinId)) {
			result.setMsg("错误的数据格式");
			result.setStatus(false);
			return result;
		}

		if (!(state == 0 || state == 1)) {
			result.setMsg("状态错误");
			result.setStatus(false);
			return result;
		}

		try {
			weiXinId = DesCodeUtil.decrypt(weiXinId);
			WxBindBean bean = new WxBindBean();
			bean.setBindFlg(state);
			bean.setWeiXinId(weiXinId);
			biz.UpdateWxBindBean(bean);

			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("绑定过程中发生错误，请与客服联系");
		}
		return result;
	}
	
}
