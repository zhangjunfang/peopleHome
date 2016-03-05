package com.jujin.controller.coin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.AttestBiz;
import com.jujin.common.CoinOpEntityResult;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.controller.BaseController;
import com.jujin.entity.UserAttestation;
import com.jujin.entity.borrow.Borrow;
import com.jujin.utils.ExceptionHelper;
import com.wicket.loan.common.utils.UserUtils;
import com.jujin.entity.coin.CoinChange;
import com.jujin.entity.coin.TccActivityBean;
import com.jujin.entity.coin.TpaCoinChanceBean;
import com.jujin.entity.coin.TpaUserCoinDetailBean;
import com.jujin.biz.coin.CoinBiz;

import cryptix.jce.provider.cipher.Null;

/**
 * Title: CoinController Description: 红包相关的controller Company: jujinziben
 * 
 * @author gaojunfeng
 * @date 2015年6月1日
 */
@Controller
public class CoinController extends BaseController {

	CoinBiz coinBiz = new CoinBiz();
	AttestBiz attestBiz = new AttestBiz();

	/**
	 * Title: getTccActivityByRecordId Description: 获取指定活动信息
	 * 
	 * @param recordId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTccActivityByRecordId", method = RequestMethod.GET)
	public @ResponseBody
	Object getTccActivityByRecordId(
			@RequestParam(value = "recordId", required = true) int recordId,
			HttpServletRequest request) {
		OpEntityResult<TccActivityBean> result = new OpEntityResult<TccActivityBean>(
				null);
		if (StringUtils.isEmpty(recordId)) {
			return result;
		}
		logger.info(String.format("[%s]获取活动信息", recordId));
		try {
			TccActivityBean bean = coinBiz.getTccActivityByRecordId(recordId);
			if (bean != null) {
				logger.info(String.format("[%s]活动信息" + bean.toString(),
						recordId));
				result.setEntity(bean);
				result.setStatus(true);
			} else {
				logger.info(String.format("[%s]查询无结果！", recordId));
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]查询异常", recordId));
		}
		return result;
	}

	/**
	 * 获取所有活动 Title: getAllTccActivityBean Description:
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getAllTccActivityBean", method = RequestMethod.GET)
	public @ResponseBody
	Object getAllTccActivityBean(HttpServletRequest request) {

		logger.info(String.format("获取所有活动信息  "));

		List<TccActivityBean> list = null;
		JsonList<TccActivityBean> result = null;
		try {
			list = coinBiz.getAllTccActivityBean();
			result = new JsonList<TccActivityBean>();
			result.addRange(list);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if (result == null)
				result = new JsonList<TccActivityBean>();
			result.setStatus(false);
			result.setMsg(String.format("获取活动信息异常"));
		}
		return result;
	}

	/**
	 * Title: getTccActivityByRecordId Description: 抢红包
	 * 
	 * @param recordId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getcoin", method = RequestMethod.GET)
	public @ResponseBody
	Object getcoin(
			@RequestParam(value = "recordId", required = true) int recordId,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
			@RequestParam(value = "sharephone", required = true) String sharephone,
			HttpServletRequest request) {
		CoinOpEntityResult<TpaUserCoinDetailBean> result = new CoinOpEntityResult<TpaUserCoinDetailBean>(
				null);
		if (StringUtils.isEmpty(sharephone)) {
			result.setStatus(false);
			result.setMsg("错误的数据格式");
			return result;
		}
		TpaCoinChanceBean mapBean = coinBiz.getShareChance(sharephone,recordId);

		int tempRecordId = recordId;
		if (null != mapBean) {
			recordId = mapBean.getRecordId();
		}

		String msg = null;
		// 红包状态
		String coinStates = null;
		// 红包对应的活动
		TccActivityBean tccActivityBean = null;
		// 抢到的红包
		TpaUserCoinDetailBean bean = null;
		if (StringUtils.isEmpty(recordId)) {
			return result;
		}
		
		if (!StringUtils.isEmpty(phoneNumber)
				&& UserUtils.isMobileNumber(phoneNumber)) {
			result.setTotalAmount(coinBiz.QueryUserCoin(phoneNumber));
		}
		logger.info(String.format(" 原始ID[%s]  转换过的ID [%s]抢红包", tempRecordId,
				recordId));
		try {
			TpaCoinChanceBean chance = coinBiz
					.getTpaCoinChanceByRecordId(recordId);
			if (chance == null || chance.getEnable() == 0)// 红包机会无效
			{
				coinStates = "002";
				msg = "对不起当前红包机会无效";
			} else {
				tccActivityBean = coinBiz.getTccActivityByRecordId(chance
						.getActivityId());
				if (new Date().after(tccActivityBean.getEndTime())) {
					coinStates = "003";
					msg = "对不起当前红包对应的活动已过期";
				} else if (new Date().after(tccActivityBean.getCoinEndTime())) {
					coinStates = "004";
					msg = "对不起当前抢红包时间已过期";
				} else {
					if (coinBiz
							.getTpaUserCoinDetailCount(recordId, phoneNumber) > 0) {
						coinStates = "005";
						msg = "对不起当前红包您已抢过";
						bean = coinBiz.getGrapedTpaUserCoinDetail(recordId,
								phoneNumber);
					} else {
						if (chance.getQuantity() > 0)//
						{
							coinStates = "001";
							bean = coinBiz.grabCoin(recordId, phoneNumber);
							msg = "电话为" + phoneNumber + "的用户抢到面额为"
									+ bean.getAmount() + "的红包";
						} else {
							coinStates = "006";
							msg = "对不起当前红包已抢完";
						}
					}
				}
			}

		

			result.setCoinStates(coinStates);
			result.setEntity(bean);
			result.setMsg(msg);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]抢红包异常", recordId));
		}
		return result;
	}

	/**
	 * Title: getTccActivityByRecordId Description: 抢红包
	 * 
	 * @param recordId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCoinChance", method = RequestMethod.GET)
	public @ResponseBody
	Object getCoinChance(
			@RequestParam(value = "recordId", required = true) int recordId,
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			@RequestParam(value = "sharephone", required = false) String sharephone,
			HttpServletRequest request) {
		CoinOpEntityResult<TpaUserCoinDetailBean> result = new CoinOpEntityResult<TpaUserCoinDetailBean>(
				null);
		String msg = null;
		// 红包状态
		String coinStates = null;
		// 红包对应的活动
		TccActivityBean tccActivityBean = null;

		TpaCoinChanceBean mapBean = coinBiz.getShareChance(sharephone,recordId);

		int tempRecordId = recordId;
		if (null != mapBean) {
			recordId = mapBean.getRecordId();
		}

		// 抢到的红包
		TpaUserCoinDetailBean bean = null;
		if (StringUtils.isEmpty(recordId)) {
			return result;
		}
		logger.info(String.format("原始ID[%s]  转换过的ID [%s]",tempRecordId,recordId));
		
		if (!StringUtils.isEmpty(phoneNumber)
				&& UserUtils.isMobileNumber(phoneNumber)) {
			result.setTotalAmount(coinBiz.QueryUserCoin(phoneNumber));
		}
		
		try {
			TpaCoinChanceBean chance = coinBiz
					.getTpaCoinChanceByRecordId(recordId);
			if (chance == null || chance.getEnable() == 0)// 红包机会无效
			{
				coinStates = "002";
				msg = "对不起当前红包机会无效";
			} else {
				tccActivityBean = coinBiz.getTccActivityByRecordId(chance
						.getActivityId());
				if (new Date().after(tccActivityBean.getEndTime())) {
					coinStates = "003";
					msg = "对不起当前红包对应的活动已过期";
				} else {
					if (!StringUtils.isEmpty(phoneNumber)) {
						bean = coinBiz.queryGrapedTpaUserCoinDetail(recordId,
								phoneNumber);
						if (bean != null) {
							coinStates = "005";
							msg = "对不起当前红包您已抢过";
							result.setCoinStates(coinStates);
							result.setEntity(bean);
							result.setMsg(msg);
							result.setStatus(true);
							return result;
						}
					}
					if (new Date().after(tccActivityBean.getCoinEndTime())) {
						coinStates = "004";
						msg = "对不起当前抢红包时间已过期";
					} else if (chance.getQuantity() < 1)// 红包是否可用
					{
						coinStates = "006";
						msg = "对不起当前红包已抢完";
					} else {
						coinStates = "001";
						msg = "当前红包可抢";
					}
				}
				result.setCoinStates(coinStates);
				result.setEntity(bean);
				result.setMsg(msg);
				result.setStatus(true);
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]红包组查询异常", recordId));
		}
		return result;
	}

	/**
	 * Title: shareChance Description: 分享红包
	 * 
	 * @param recordId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/shareChance", method = RequestMethod.GET)
	public @ResponseBody
	Object shareChance(
			@RequestParam(value = "recordId", required = true) int recordId,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
			HttpServletRequest request) {
		
		
		logger.info(String.format("recordId:[%s] phoneNumber:[%s] ", recordId,phoneNumber));
		
		OpEntityResult<TpaCoinChanceBean> result = new OpEntityResult<TpaCoinChanceBean>(
				null);
		String msg = null;
		// 当前红包机会
		TpaCoinChanceBean chanceBeanNow = coinBiz
				.getTpaCoinChanceByRecordId(recordId);
		// 要分享的红包机会
		TpaCoinChanceBean chanceBean = null;
		// 活动bean
		TccActivityBean tccActivityBean = null;
		if (StringUtils.isEmpty(recordId) || StringUtils.isEmpty(phoneNumber)) {
			return result;
		}
		logger.info(String.format("[%s]分享红包", recordId));
		try {
			if (chanceBeanNow != null) {
				tccActivityBean = coinBiz
						.getTccActivityByRecordId(chanceBeanNow.getActivityId());
				if (new Date().after(tccActivityBean.getEndTime())) {
					msg = "对不起当前活动已过期";
				} else if (new Date().after(tccActivityBean.getCoinEndTime())) {
					msg = "对不起当前红包分享时间已过期";
				} else {
					chanceBean = coinBiz.getShareChance(phoneNumber, recordId);
					if (chanceBean == null) {
						coinBiz.insertChanceByRoot(recordId, phoneNumber);
						chanceBean = coinBiz.getShareChance(phoneNumber,
								recordId);
						msg = "之前未分享过";
					} else {
						msg = "之前分享过";
					}
				}
			} else {
				msg = "链接无效";
			}
			result.setEntity(chanceBean);
			result.setMsg(msg);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setStatus(false);
			result.setMsg(String.format("[%s]红包分享异常", recordId));
		}
		return result;
	}

	/**
	 * Title: getGraphCoinList Description: 获取指定电话的用户抢到的红包
	 * 
	 * @param phoneNumber
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getGraphCoinList", method = RequestMethod.GET)
	public @ResponseBody
	Object getGraphCoinList(
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			int pi, int ps, HttpServletRequest request) {
		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}

		if (StringUtils.isEmpty(phoneNumber)) {
			if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
				return validateResult;
			}

			String userId = this.getLoginedUserId(request);
			UserAttestation entity = attestBiz.QueryUserAttestation(userId);
			phoneNumber = entity.getRealMobile();
		}

		JsonList<TpaUserCoinDetailBean> list = null;
		if (!UserUtils.isMobileNumber(phoneNumber)) {
			list = new JsonList<TpaUserCoinDetailBean>();
			list.setStatus(false);
			list.setMsg(String.format("请输入正确的手机号"));
			return list;
		}

		logger.info(String.format("[%s]抢到的红包", phoneNumber));
		try {
			list = coinBiz.getCoinGrapList(phoneNumber, pi, ps);
			list.setStatus(true);
			list.setMsg(String.format("[%s]红包查询正常", phoneNumber));
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			list.setStatus(false);
			list.setMsg(String.format("[%s]红包查询异常", phoneNumber));
		}
		return list;
	}

	/**
	 * Title: getUsedCoinList Description: 获取指定电话的用户已用的红包
	 * 
	 * @param phoneNumber
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUsedCoinList", method = RequestMethod.GET)
	public @ResponseBody
	Object getUsedCoinList(
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			int pi, int ps, HttpServletRequest request) {
		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}

		if (StringUtils.isEmpty(phoneNumber)) {
			if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
				return validateResult;
			}
			String userId = this.getLoginedUserId(request);
			UserAttestation entity = attestBiz.QueryUserAttestation(userId);
			phoneNumber = entity.getRealMobile();
		}

		JsonList<TpaUserCoinDetailBean> list = null;
		if (!UserUtils.isMobileNumber(phoneNumber)) {
			list = new JsonList<TpaUserCoinDetailBean>();
			list.setStatus(false);
			list.setMsg(String.format("请输入正确的手机号"));
			return list;
		}

		logger.info(String.format("[%s]已用的红包", phoneNumber));
		try {
			list = coinBiz.getCoinUsedList(phoneNumber, pi, ps);
			list.setStatus(true);
			list.setMsg(String.format("[%s]已用红包查询正常", phoneNumber));
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			list.setStatus(false);
			list.setMsg(String.format("[%s]已用红包查询异常", phoneNumber));
		}
		return list;
	}

	/**
	 * Title: getOutOfDateCoinList Description: 获取指定电话的用户已过期的红包
	 * 
	 * @param phoneNumber
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOutOfDateCoinList", method = RequestMethod.GET)
	public @ResponseBody
	Object getOutOfDateCoinList(
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			int pi, int ps, HttpServletRequest request) {
		
		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}

		if (StringUtils.isEmpty(phoneNumber)) {
			if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
				return validateResult;
			}

			String userId = this.getLoginedUserId(request);
			UserAttestation entity = attestBiz.QueryUserAttestation(userId);
			phoneNumber = entity.getRealMobile();
		}

		JsonList<TpaUserCoinDetailBean> list = null;
		if (!UserUtils.isMobileNumber(phoneNumber)) {
			list = new JsonList<TpaUserCoinDetailBean>();
			list.setStatus(false);
			list.setMsg(String.format("请输入正确的手机号"));
			return list;
		}

		logger.info(String.format("[%s]过期的红包", phoneNumber));
		try {
			list = coinBiz.getCoinOutOfDateList(phoneNumber, pi, ps);
			list.setStatus(true);
			list.setMsg(String.format("[%s]过期红包查询正常", phoneNumber));
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			list.setStatus(false);
			list.setMsg(String.format("[%s]过期红包查询异常", phoneNumber));
		}
		return list;
	}

	/**
	 * Title: QueryCoinChance Description: 获取指定用户的红包机会列表
	 * 
	 * @param phoneNumber
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryCoinChance", method = RequestMethod.GET)
	public @ResponseBody
	Object QueryCoinChance(
			@RequestParam(value = "phoneNumber", required = false) String phoneNumber,
			int pi, int ps, HttpServletRequest request) {

		OpResult validateResult = validateOpResult(pi, ps);
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		if (!this.onUserIdIsNull(validateResult, request).isStatus()) {
			return validateResult;
		}

		String userId = this.getLoginedUserId(request);
		JsonList<CoinChange> list = null;
		list = coinBiz.QueryCoinChance(userId, phoneNumber, pi, ps);
		if (list == null) {
			list = new JsonList<CoinChange>();
			list.setStatus(true);
		}
		return list;
	}

	/**
	 * Title: QueryCoinChance Description: 耽搁红包机会详情
	 * 
	 * @param phoneNumber
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryCoinChanceByRoot", method = RequestMethod.GET)
	public @ResponseBody
	Object queryCoinChanceByRoot(
			@RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
			HttpServletRequest request) {

		OpEntityResult<CoinChange> result = null;
		result = coinBiz.QueryCoinChanceByRoot(id, phoneNumber);
		if (result == null) {
			result = new OpEntityResult<CoinChange>(null);
			result.setStatus(true);
		}
		return result;
	}

	@RequestMapping(value = "/addChanceByBiAndAi", method = RequestMethod.GET)
	public @ResponseBody
	Object addChanceByBiAndAi(String borrowId, int activityId) {
		OpResult result = new OpResult();
		try {
			coinBiz.insertTpaCoinChanceByBiAndAi(borrowId, activityId);
			result.setStatus(true);

		} catch (Exception ex) {
			result.setStatus(false);
			result.setMsg("生成红包机会出错");
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}

		return result;
	}

	@RequestMapping(value = "/notify", method = RequestMethod.GET)
	public @ResponseBody
	Object notify(String borrowId, int activityId) {
		OpResult result = new OpResult();
		try {
			coinBiz.notifyUser(borrowId, activityId);
			result.setStatus(true);

		} catch (Exception ex) {
			result.setStatus(false);
			result.setMsg("发送通知短信");
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}

		return result;
	}
	@RequestMapping(value = "/queryUserCoin", method = RequestMethod.GET)
	public @ResponseBody
	Object queryUserCoin(String phoneNumber) {
		
		OpEntityResult<String> result = new OpEntityResult<String>("");
		try {
			if(StringUtils.isEmpty(phoneNumber))
			{
				return result;
			}
			result.setEntity(coinBiz.QueryUserCoin(phoneNumber));
			
			result.setStatus(true);

		} catch (Exception ex) {
			result.setStatus(false);
			result.setMsg("获取用户聚金币异常");
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}

		return result;
	}
	
	
	

}
