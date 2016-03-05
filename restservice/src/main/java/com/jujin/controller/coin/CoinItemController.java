package com.jujin.controller.coin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.coin.CoinItemBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.common.SystemConfig;
import com.jujin.controller.BaseController;
import com.jujin.entity.coin.CoinItem;

@Controller
public class CoinItemController extends BaseController {

	CoinItemBiz biz = new CoinItemBiz();

	@RequestMapping(value = "/coinoverdue", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryUserCoinOverdue(HttpServletRequest request) {

		JsonList<CoinItem> result = null;
		String userId = this.getLoginedUserId(request);

		if (StringUtils.isEmpty(userId)) {

			result = new JsonList<CoinItem>();
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		logger.info(String.format("获取用户 [%s] 的聚金币过期记录 ",userId));
		try {
			result = biz.QueryUserCoinOverdue(userId);
			if (result != null)
				result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			if(result==null)
			{
				result=new  JsonList<CoinItem>();
				result.setStatus(false);
				result.setMsg("获取用户聚金币过期记录失败,请重试");
			}
		}
		return result;
	}

	@RequestMapping(value = "/coinget", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryUserCoinGet(HttpServletRequest request) {


		JsonList<CoinItem> result = null;
		String userId = this.getLoginedUserId(request);

		if (StringUtils.isEmpty(userId)) {

			result = new JsonList<CoinItem>();
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		logger.info(String.format("获取用户 [%s] 的聚金币获取记录 ",userId));
		try {
			result = biz.QueryUserCoinGet(userId);
			if (result != null)
				result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			if(result==null)
			{
				result=new  JsonList<CoinItem>();
				result.setStatus(false);
				result.setMsg("获取用户聚金币获取记录失败,请重试");
			}
		}
		return result;

	}

	@RequestMapping(value = "/coinuse", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryUserCoinUse(HttpServletRequest request) {

		JsonList<CoinItem> result = null;
		String userId = this.getLoginedUserId(request);

		if (StringUtils.isEmpty(userId)) {

			result = new JsonList<CoinItem>();
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		logger.info(String.format("获取用户 [%s] 的聚金币使用记录 ",userId));
		try {
			result = biz.QueryUserCoinUse(userId);
			if (result != null)
				result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			if(result==null)
			{
				result=new  JsonList<CoinItem>();
				result.setStatus(false);
				result.setMsg("获取用户聚金币使用记录失败,请重试");
			}
		}
		return result;

	}

}
