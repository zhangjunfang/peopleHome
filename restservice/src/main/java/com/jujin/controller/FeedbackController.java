package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.FeedbackBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.account.WithdrawEntity;
import com.jujin.entity.feed.FeedBack;
import com.jujin.entity.feed.FeedType;
import com.pro.common.model.Model;
import com.pro.common.model.ModelList;
import com.pro.common.util.StringUtils;

@Controller
public class FeedbackController extends BaseController {

	FeedbackBiz biz = new FeedbackBiz();

	/**
	 * 反馈类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/feedtype", method = RequestMethod.GET)
	public @ResponseBody
	Object getfeedtype() {

		logger.info("获取用户反馈类型");
		JsonList<FeedType> types = null;
		try {
			types = biz.QueryUserFeedType();
			types.setStatus(true);

		} catch (Exception e) {

			logger.error(ExceptionHelper.getExceptionDetail(e));
		}
		return types;
	}

	/**
	 * 反馈
	 */
	@RequestMapping(value = "/feedback", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object InsertUserfeedback(HttpEntity<FeedBack> entity,
			HttpServletRequest request) {
		OpResult result = new OpResult();

		if (!this.onUserIdIsNull(result, request).isStatus()) {
			return result;
		}
		
		String userid = this.getLoginedUserId(request);
		
		logger.info("feedback" + (StringUtils.isEmpty(userid) ? "" : userid));

		 
		if (entity == null) {
			result.setMsg("用户反馈错误的数据类型");
			return result;
		}
		FeedBack item = entity.getBody();
		if (item == null) {
			result.setMsg("用户反馈错误的数据类型");
			return result;
		}
		logger.info(item);
		// 校验一些必填字段
		 
		String touserid = item.getTouserid();
		if (touserid == null) {
			result.setMsg("接收方不能为空");
			return result;
		}
		int msgtype = item.getMsgtype();
		if (msgtype != 0 && msgtype != 1) {
			result.setMsg("用户反馈类型错误");
			return result;
		}

		// FeedBack back = new FeedBack();
		// back.setUserid(userid);
		// back.setTouserid(touserid);
		// back.setMsgtype(msgtype);
		// back.setFeedtype(item.getFeedtype());
		// back.setContent(item.getContent());
		// back.setFeedmemo(item.getFeedmemo());

		try {
			item.setUserid(userid);
			biz.addFeedback(item);
			result.setStatus(true);
		} catch (Exception ex) {
			result.setStatus(false);
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg("反馈提交失败，请重试或与客服联系");
		}
		
		return result;
	}

	/**
	 * 用户反馈列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/feedbyuser", method = RequestMethod.GET)
	public @ResponseBody
	Object QuerygetFeedByUser(int pi, int ps, HttpServletRequest request) {
		OpResult validateResult = validateOpResult(pi, ps);
		
		
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		
		if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}
		String userid = this.getLoginedUserId(request);
		
		logger.info("获取用户反馈列表");

		JsonList<FeedBack> logs = null;
		try {
			logs = biz.QuerygetFeedByUser(userid, pi, ps);
			logs.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));

			if (logs == null)
				logs = new JsonList<FeedBack>();
			logs.setMsg("获取用户反馈列表失败，请和客服联系");
			logs.setStatus(false);
		}
		return logs;

	}

}
