package com.jujin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.AwardBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.entity.account.AccountLog;
import com.jujin.entity.award.AwardInfo;
import com.jujin.entity.award.AwardResultInfo;
import com.jujin.entity.award.UserAwardInfo;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.web.borrow.information.mediator.BorrowTenderMediator;

/**
 * Title: AwardController Description: 抽奖相关的controller Company: jujinziben
 * 
 * @author gaojunfeng
 * @date 2015年4月17日
 */
@Controller
public class AwardController extends BaseController {

	AwardBiz awardBiz = new AwardBiz();

	/**
	 * Title: getAwardResultInfo Description: 抽奖
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/drawAward", method = RequestMethod.GET)
	public @ResponseBody
	Object getAwardResultInfo(String userId, HttpServletRequest request) {

		OpEntityResult<AwardResultInfo> result = new OpEntityResult<AwardResultInfo>(
				null);

		if (StringUtils.isEmpty(userId)) {
			if (!onUserIdIsNull(result, request).isStatus()) {
				return result;
			}
			userId = this.getLoginedUserId(request);
		}

		logger.info(String.format("[%s]抽奖", userId));

		try {
			AwardInfo awardInfo = awardBiz.drawAward(userId);
			if (awardInfo != null) {
				logger.info(String.format("[%s]抽到奖品信息" + awardInfo.toString(),
						userId));
				AwardResultInfo item = new AwardResultInfo();
				item.setCountDrawNumTD(awardBiz.getDrawTimesOfToday(userId));
				item.setOddTimes(awardBiz.getAwardDrawInfoByUserId(userId)
						.getOddTimes());
				item.setAwardCode(awardInfo.getAwardCode());
				result.setEntity(item);
				result.setStatus(true);
			} else {
				logger.info(String.format("[%s]抽奖次数已用完！", userId));
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]抽奖异常,请与客服联系", userId));
		}
		return result;
	}

	@RequestMapping(value = "/ua", method = RequestMethod.GET)
	public @ResponseBody
	Object updateAccount(
			@RequestParam(value = "userId", required = true) String userId,
			double amount, HttpServletRequest request) {
		logger.info(String.format(" 更新 %s 的账户信息", userId));
		OpResult result = new OpResult();
		try {
			awardBiz.updateUserAccount(userId, this.getIpAddr(request), amount);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg("更新账户信息失败，请重试");
		}
		return result;
	}

	/**
	 * 每日分享成功第一次会有增加一次抽奖机会
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sharesuccess", method = RequestMethod.GET)
	public @ResponseBody
	Object shareSuccess(
			@RequestParam(value = "userId", required = true) String userId,
			HttpServletRequest request) {
		OpResult result = new OpResult();
		try {
			//logger.info("用户[%s] 微信分享成功,更新中奖次数");
			//awardBiz.updateDrawCountByShare(userId);
			//result.setStatus(true);
		} catch (Exception ex) {
			logger.error("用户[%s] 微信分享成功,更新中奖次数失败");
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
		}
		return result;
	}

	/**
	 * @param userId
	 *            用户名
	 * @param pi
	 * @param ps
	 * @return 账户资金记录
	 */
	@RequestMapping(value = "/awardinfo", method = RequestMethod.GET)
	public @ResponseBody
	Object getUserAwardInfo(int pi, int ps, HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);

		logger.info(String.format("获取用户[%s]中奖数据  ", userId));

		JsonList<UserAwardInfo> logs = null;
		try {
			logs = awardBiz.getUserAwardInfo(userId, pi, ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (logs == null)
				logs = new JsonList<UserAwardInfo>();
			logs.setStatus(false);
			logs.setMsg(String.format("获取用户[%s]的中奖数据异常,请和客服联系", userId));
		}
		return logs;
	}
}
