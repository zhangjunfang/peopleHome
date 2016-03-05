/**
 * 
 */
package com.jujin.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.AccountBiz;
import com.jujin.biz.AttestBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.controller.BaseController;
import com.jujin.entity.UserAttestation;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.Account;
import com.jujin.entity.account.AccountCenter;
import com.jujin.entity.account.AccountLog;
import com.jujin.entity.account.Coupon;
import com.jujin.entity.account.EarningsRank;
import com.jujin.entity.account.InviteReward;
import com.jujin.entity.account.Message;
import com.jujin.entity.account.RecoveEntity;
import com.jujin.entity.account.RecoveEntityDetail;
import com.jujin.entity.account.RecoveLog;
import com.jujin.entity.account.UserBankCard;
import com.jujin.entity.account.UserTenderSummary;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.web.borrow.information.mediator.BorrowTenderMediator;
import com.wicket.loan.web.common.bean.UsersOperationLogBean;
import com.wicket.loan.web.person.account.mediator.UserAccountInfoMediator;
import com.wicket.loan.web.person.tender.mediator.UserBorrowTenderMediator;


/**
 * @author 宁
 * 
 */
@Controller
public class AccountControler extends BaseController {

	AccountBiz accountBiz = new AccountBiz();
	
 
	/**
	 * 获取用户中心的首页数据
	 * 
	 * @param userId
	 *            用户id
	 * 
	 * @return 用户中心的数据
	 */
	Object getAccountCenter(String userId) {

		logger.info(String.format("查询[%s]用户中心数据", userId));
		AccountCenter center = null;
		OpEntityResult<AccountCenter> entity = new OpEntityResult<AccountCenter>(
				null);
		try {
			center = accountBiz.getAccountCenter(userId);
			if (center != null) {
				entity.setEntity(center);
				entity.setStatus(true);
			} else {
				entity.setMsg(String.format("获取不到用户[%s]的数据", userId));
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			entity.setMsg(String.format("获取用户[%s]的数据异常,请和客服联系", userId));
			entity.setStatus(false);
		}
		return entity;
	}
	
	
	@RequestMapping(value = "/accountmemo", method = RequestMethod.GET)
	public @ResponseBody
	Object getAccountMemo(@RequestParam(value = "id", required = true) String id) {
		logger.info(String.format("查询[%s]用户中心数据", id));
		OpEntityResult<String> entity = new OpEntityResult<String>(null);
		if (StringUtils.isEmpty(id)) {
			entity.setStatus(false);
			entity.setMsg("数据格式不正确");
			return entity;
		}
		/*
		 * 您目前：
		 * 
		 * 可用余额：%s(元) 待收总额：%s(元) 总资产：%s(元) 总收益：%s(元)
		 * 
		 * <a href="">点击这里继续投资</a>
		 */
		Account center = null;
		String openId = "";
		try {
			openId = DesCodeUtil.decrypt(id);
			if (StringUtils.isEmpty(openId)) {
				entity.setStatus(false);
				entity.setMsg("数据格式不正确");
				return entity;
			}
			WxBindBean bean = accountBiz.QueryWxBindBean(openId);

			if (bean == null) {
				entity.setStatus(true);
				entity.setMsg("该微信号不存在");
				return entity;
			} else if (bean.getBindFlg() == 1) {
				entity.setStatus(true);
				entity.setMsg("该微信号尚未绑定账号");
				return entity;
			}

			String userId = bean.getUserId();
			center = accountBiz.getAccount(userId);

			if (center != null) {
				entity.setStatus(true);
				String msg = "您目前：\n\n";
				msg += String.format("可用余额：%s(元)\n", NumberUtils
						.moneyFormat(center != null ? center.getBalance()
								: "0.00"));
				msg += String.format("聚金宝：%s(元)\n", NumberUtils
						.moneyFormat(center != null ? String.valueOf(center.getJujinbao()): "0.00"));
				msg += String.format("待收总额：%s(元)\n", NumberUtils
						.moneyFormat(center != null ? center.getAwait()
								: "0.00"));
				msg += String.format("冻结金额：%s(元)\n", NumberUtils
						.moneyFormat(center != null ? center.getFrost()
								: "0.00"));
				msg += String.format("回款续投：%s(元)\n", NumberUtils
						.moneyFormat(center != null ? center.getContinueTotal()
								: "0.00"));
				msg += String.format("总资产：%s(元)\n", NumberUtils
						.moneyFormat(center != null ? center.getCapitalTotal()
								: "0.00"));
				msg += String.format("总收益：%s(元)\n\n\n", NumberUtils
						.moneyFormat(center != null ? center.getTotalEarnings()
								: "0.00"));

				msg += String.format("\t<a href=\"%s\">点击这里继续投资</a>\n",
						SystemConfig.MOBILE_ROOT + "#/login?openid="
								+ DesCodeUtil.encrypt(openId));

				entity.setEntity(msg);
			} else {
				entity.setMsg(String.format("获取不到用户[%s]的数据", openId));
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			entity.setMsg(String.format("获取用户[%s]的数据异常,请和客服联系", openId));
			entity.setStatus(false);
		}
		return entity;
	}

	@RequestMapping(value = "/default", method = RequestMethod.GET)
	public @ResponseBody
	Object getAccountCenter(HttpServletRequest request) {

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

		return getAccountCenter(userId);
	}
	
	
	
	/*微信*/
	@RequestMapping(value = "/wdefault", method = RequestMethod.GET)
	public @ResponseBody
	Object getWAccountCenter(HttpServletRequest request) {

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

		return getAccountCenter(userId);

	}

	/**
	 * @param userId
	 *            用户名
	 * @param pi
	 * @param ps
	 * @return 账户资金记录
	 */
	@RequestMapping(value = "/accountlog", method = RequestMethod.GET)
	public @ResponseBody
	Object getAccountLog(int pi, int ps, HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);

		logger.info(String.format("获取用户[%s]的账户流水数据  ", userId));

		JsonList<AccountLog> logs = null;
		try {
			logs = accountBiz.getAccountLog(userId, pi, ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (logs == null)
				logs = new JsonList<AccountLog>();
			logs.setStatus(false);
			logs.setMsg(String.format("获取用户[%s]的账户流水数据异常,请和客服联系", userId));
		}
		return logs;
	}

	/**
	 * 已投项目统计
	 * 
	 * @param userId
	 * @param type
	 * @param pi
	 * @param ps
	 * @return
	 */
	@RequestMapping(value = "/invest", method = RequestMethod.GET)
	public @ResponseBody
	Object getRecoveEntity(int type, int pi, int ps, HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);

		logger.info(String.format("获取用户[%s]的投资项目列表  ", userId));

		JsonList<RecoveEntity> logs = null;
		try {
			logs = accountBiz.getRecoveEntity(userId, type, pi, ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (logs == null)
				logs = new JsonList<RecoveEntity>();
			logs.setMsg(String.format("获取用户[%s]的投资项目列表失败,请和客服联系", userId));
			logs.setStatus(false);
		}
		return logs;
	}

	public @ResponseBody
	Object getRecoveEntityDetail(String borrowId, int type, int pi, int ps,
			HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);

		logger.info(String.format("获取用户[%s]的回款明细  ", userId));

		JsonList<RecoveEntityDetail> logs = null;
		try {
			logs = accountBiz.getRecoveEntityDetail(userId, borrowId, type, pi,
					ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (logs == null)
				logs = new JsonList<RecoveEntityDetail>();
			logs.setMsg(String.format("获取用户[%s]的回款明细失败,请和客服联系", userId));
			logs.setStatus(false);
		}
		return logs;
	}

	/**
	 * 
	 * 
	 * 
	 * @param userId
	 * @param title
	 * @param borrow_type
	 * @param period
	 * @param period_type
	 * @param is_day
	 * @param pi
	 * @param ps
	 * @return 用户回款计划
	 */
	/*
	 * @RequestMapping(value = "/recove", method = RequestMethod.GET) public
	 * 
	 * @ResponseBody Object getRecoveLog(
	 * 
	 * @RequestParam(value = "id", required = true) String userId, String title,
	 * int borrow_type, int period, int period_type, int is_day, int pi, int ps)
	 * { logger.info(String.format("获取用户[%s]的回款计划列表  ", userId)); OpResult
	 * validateResult = validateOpResult(pi, ps); if
	 * (!validateResult.isStatus()) { return validateResult; }
	 * 
	 * JsonList<RecoveLog> logs = null; try { logs =
	 * accountBiz.getRecoveLog(userId, title, borrow_type, period, period_type,
	 * is_day, pi, ps); logs.setStatus(true); } catch (Exception ex) {
	 * logger.error(ExceptionHelper.getExceptionDetail(ex));
	 * 
	 * if (logs == null) logs = new JsonList<RecoveLog>();
	 * logs.setMsg(String.format("获取用户[%s]的回款计划列表失败,请和客服联系", userId));
	 * logs.setStatus(false); } return logs; }
	 */

	@RequestMapping(value = "/recove", method = RequestMethod.GET)
	public @ResponseBody
	Object getRecoveLog(int pi, int ps, String borrowId,
			HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);

		logger.info(String.format("获取用户[%s]的回款计划列表  ", userId));

		JsonList<RecoveLog> logs = null;
		try {
			logs = accountBiz.getRecoveLog(userId, "", borrowId, 0, 0, 0, 0,
					pi, ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (logs == null)
				logs = new JsonList<RecoveLog>();
			logs.setMsg(String.format("获取用户[%s]的回款计划列表失败,请和客服联系", userId));
			logs.setStatus(false);
		}
		return logs;
	}

	/**
	 * 未读消息列表
	 * 
	 * @param userId
	 * @param pi
	 * @param ps
	 * @return 用户回款计划
	 */
	@RequestMapping(value = "/msg", method = RequestMethod.GET)
	public @ResponseBody
	Object getMessages(int pi, int ps, HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}
		String userId = this.getLoginedUserId(request);

		logger.info(String.format("获取用户[%s]的用户消息列表", userId));

		JsonList<Message> logs = null;
		try {
			logs = accountBiz.getMessages(userId, pi, ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (logs == null)
				logs = new JsonList<Message>();
			logs.setMsg(String.format("获取用户[%s]的用户消息列表失败,请和客服联系", userId));
			logs.setStatus(false);
		}
		return logs;
	}

	@RequestMapping(value = "/rank", method = RequestMethod.GET)
	public @ResponseBody
	Object getEarningsRank(HttpServletRequest request) {

		OpEntityResult<EarningsRank> entity = new OpEntityResult<EarningsRank>(
				null);
		if (!onUserIdIsNull(entity, request).isStatus()) {
			return entity;
		}
		String userId = this.getLoginedUserId(request);
		logger.info(String.format("获取用户[%s]的收益排名", userId));

		EarningsRank rank = null;
		try {
			rank = accountBiz.getEarningsRank(userId);
			entity.setEntity(rank);
			entity.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			entity.setStatus(false);
			entity.setMsg(String.format("获取用户[%s]的收益排名失败,请和客服联系", userId));
		}
		return entity;
	}

	/**
	 * 暂时不用
	 * 
	 * @param userId
	 * @param title
	 * @param borrow_type
	 * @param period
	 * @param period_type
	 * @param is_day
	 * @param pi
	 * @param ps
	 * @return
	 */
	@RequestMapping(value = "/hisrecovelog", method = RequestMethod.GET)
	public @ResponseBody
	Object getHisRecoveLog(String title, int borrow_type, int period,
			int period_type, int is_day, int pi, int ps,
			HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);
		logger.info(String.format("获取用户[%s]的历史投标记录列表", userId));

		JsonList<RecoveLog> logs = null;
		try {
			logs = accountBiz.getRecoveLog(userId, title, "", borrow_type,
					period, period_type, is_day, pi, ps);
			logs.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (logs == null)
				logs = new JsonList<RecoveLog>();
			logs.setMsg(String.format("获取用户[%s]的历史投标记录列表失败,请和客服联系", userId));
			logs.setStatus(false);
		}
		return logs;
	}

	@RequestMapping(value = "/bank", method = RequestMethod.GET)
	public @ResponseBody
	Object getBankCard(HttpServletRequest request) {

		OpEntityResult<UserBankCard> entity = new OpEntityResult<UserBankCard>(
				null);
		String userId = "";
		try {

			userId = this.getLoginedUserId(request);

			if (StringUtils.isEmpty(userId)) {
				entity.setStatus(false);
				entity.setMsg(SystemConfig.NO_LOGIN);
				return entity;
			}

			logger.info(String.format("获取用户[%s]绑定的银行卡", userId));
			UserBankCard card = null;

			card = accountBiz.getBankCard(userId);
			entity = new OpEntityResult<UserBankCard>(card);
			entity.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			entity.setStatus(false);
			entity.setMsg(String.format("获取用户[%s]绑定的银行卡失败,请和客服联系", userId));
		}
		return entity;
	}

	@RequestMapping(value = "/invite", method = RequestMethod.GET)
	public @ResponseBody
	Object getStatInvite(int pi, int ps, HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);
		logger.info(String.format("获取用户[%s]的邀请人统计信息奖励列表", userId));

		OpEntityResult<InviteReward> entity = null;
		InviteReward ireward = null;
		try {
			ireward = accountBiz.getStatInvite(userId, pi, ps);
			entity = new OpEntityResult<InviteReward>(ireward);
			entity.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));

			if (entity == null)
				entity = new OpEntityResult<InviteReward>(null);
			entity.setMsg(String
					.format("获取用户[%s]的邀请人统计信息奖励列表失败,请和客服联系", userId));
			entity.setStatus(false);
		}
		return entity;
	}

	@RequestMapping(value = "/querycoupon", method = RequestMethod.GET)
	public @ResponseBody
	Object queryCoupon(HttpServletRequest request) {

		OpResult validateResult = new OpResult();

		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		OpEntityResult<Coupon> result = new OpEntityResult<Coupon>(null);

		String userId = this.getLoginedUserId(request);
		logger.info(String.format("获取用户[%s]的奖券信息", userId));

		try {
			Coupon entity = accountBiz.QueryCoupon(userId);
			result.setEntity(entity);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}
		return result;
	}
	
	@RequestMapping(value = "/tendersummary", method = RequestMethod.GET)
	public @ResponseBody
	Object tenderSummary(HttpServletRequest request) {

		OpResult validateResult = new OpResult();

		if (!onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		OpEntityResult<UserTenderSummary> result = new OpEntityResult<UserTenderSummary>(null);

		String userId = this.getLoginedUserId(request);
		logger.info(String.format("获取用户[%s]的投资统计信息", userId));

		try {
			UserTenderSummary entity = accountBiz.QueryUserTenderSummary(userId);
			result.setEntity(entity);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
		}
		return result;
	}
	
	
	

	@RequestMapping(value = "/updmsg", method = RequestMethod.GET)
	public @ResponseBody
	Object UpdMsg(HttpServletRequest request) {

		OpResult result = new OpResult();
		
		String userId=this.getLoginedUserId(request);
		if (StringUtils.isEmpty(userId)) {
			if (!this.onUserIdIsNull(result, request).isStatus()) {
				return result;
			}
		}
		try
		{
			accountBiz.UpdMsg(userId);
			result.setStatus(true);
		}
		catch(Exception ex)
		{
			result.setStatus(false);
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg("站内信状态更新失败，请联系客服或重试");
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
