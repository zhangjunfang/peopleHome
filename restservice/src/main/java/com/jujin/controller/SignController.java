package com.jujin.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jujin.biz.SignBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.sign.PageSignData;
import com.jujin.entity.sign.SignConfBean;
import com.jujin.entity.sign.SignDetailBean;
import com.jujin.entity.sign.SignResult;
import com.jujin.util.xglc.CommonUtil;
import com.jujin.utils.ExceptionHelper;
import com.wicket.loan.common.utils.NumberUtils;

/**
 * Title: SignController
 * Description: 签到
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月3日
 */
@Controller
@RequestMapping("/sign")
public class SignController extends BaseController {

	SignBiz signMediator = new SignBiz();
	
	/**
	* Title: sign
	* Description: 签到
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/sign", method = RequestMethod.GET)
	public @ResponseBody Object signToday(String token,HttpServletRequest request,HttpServletResponse response){
		OpEntityResult<SignResult> result = new OpEntityResult<SignResult>(null);
		//需判断用户是否登录
		String userId = null;
		if(StringUtils.isEmpty(token)){
			userId = getLoginedUserId(request);
//			userId = request.getParameter("userId");
			if (StringUtils.isEmpty(userId)) {
				result.setStatus(false);
				result.setMsg(SystemConfig.NO_LOGIN);
				return result;
			}
		}else{
			userId = signMediator.getUserIdByToken(token);
		}
		if(userId == null){
			result.setStatus(false);
			result.setMsg("不能识别的用户身份标识");
			return result;
		}
		try {
		    //连续奖励天数
			int continuityRewardDay = 0;
			//连续签到天数
			int continuityDay = 0;
			//最后高奖励金额（最高值）
			String maxReward = "0";
			//本次奖励金额
			String reward = "0";
			//手机号
			String phoneNumber = (String) request.getSession().getAttribute(SystemConfig.PHONE_NUMBER);
			
			Map<String,String>  rewardConfMap=new HashMap<String,String>();
			String maxData = getSignValue(rewardConfMap);
			if(maxData.indexOf("_") != -1){
				continuityRewardDay = Integer.parseInt(maxData.split("_")[0]);
				maxReward = maxData.split("_")[1];
			}
			
			//获取签到聚金币流水
			List<SignDetailBean> signDetailList = signMediator.getSignDetail(userId);
			SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
			List<String> dayList = new ArrayList<String>();
			
			//根据流水判断之前连续签到天数
			for(SignDetailBean detail : signDetailList){
				String signDate = detail.getSignDate();
				String today = sdfdate.format(new Date());
				if(today.equals(signDate)){
					result.setStatus(false);
					//result.setMsg(String.format("用户[%s]今天已签到", userId));
					result.setMsg("用户今天已签到");
					
					return result;
				}
				dayList.add(signDate);
			}
			String yesterday = CommonUtil.getYesterdayStr(sdfdate.format(new Date())) ;
			if(dayList.size()>0){
				while(dayList.contains(yesterday)){
					yesterday = CommonUtil.getYesterdayStr(yesterday);
					continuityDay++;
				}
			}
			
			//根据连续天数获取本次奖励金额
			continuityDay = continuityDay+1;
			if(continuityDay >= continuityRewardDay){
				//按照最高奖励
				reward = maxReward;
			}else{
				//普通奖励
				reward = rewardConfMap.get(String.valueOf(continuityDay));
			}
			//下次签到奖励
			int nextContinuityDay=continuityDay+1;
			String nexSingReward="0";
			if(nextContinuityDay >= continuityRewardDay)
			{
				nexSingReward=maxReward;
			}
			else
			{
				nexSingReward=rewardConfMap.get(String.valueOf(nextContinuityDay));
			}
			//签到
			result = signMediator.signToday(userId,String.valueOf(reward),phoneNumber);
			SignResult sr=null;
			if(null!=result&&null!=(sr=result.getEntity()))
			{
				sr.setNextResult(nexSingReward);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("用户签到失败,请和客服联系");
			return result;
		} 
		return result;
	}
	
	
	
	
	/**
	* Title: getSignRecord
	* Description: 获取签到页数据
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET,produces="application/json;charset=UTF-8")
	public @ResponseBody Object getPageData(String token,HttpServletRequest request,HttpServletResponse response){
		OpEntityResult<PageSignData> result = new OpEntityResult<PageSignData>(null);
		//需判断用户是否登录
		
		String userId = null;
		if(StringUtils.isEmpty(token)){
			userId = getLoginedUserId(request);
//			userId = request.getParameter("userId");
			if (StringUtils.isEmpty(userId)) {
				result.setStatus(false);
				result.setMsg(SystemConfig.NO_LOGIN);
				return result;
			}
		}else{
			userId = signMediator.getUserIdByToken(token);
		}
		if(userId == null){
			result.setStatus(false);
			result.setMsg("不能识别的用户身份标识");
			return result;
		}
		PageSignData pageData= new PageSignData();
		int continuityDay = 0;
		
		try {
			String maxReward = "0";
			int continuityRewardDay = 0;
			//获取奖励规则和最高奖励金额
			List<SignConfBean> list = signMediator.getSignConf();
			Map<String,String> rewardConfMap = new HashMap<String,String>();
			if(list!=null && list.size()>0){
				continuityRewardDay = list.size()-1;
				pageData.setMaxContinuityDay(String.valueOf(continuityRewardDay));
				for(SignConfBean scb:list){
					String name = scb.getName();
					if(name.indexOf("DAY") != -1){
						String day = name.substring(3);
						//最高奖励
						if(day.equals(String.valueOf(continuityRewardDay))){
							maxReward = scb.getValue();
						}
						rewardConfMap.put(day, scb.getValue());
					}
				}
			}
			pageData.setMaxReward(NumberUtils.moneyFormat(maxReward));
			//获取签到聚金币流水
			List<SignDetailBean> signDetailList = signMediator.getSignDetail(userId);
			pageData.setSignDays(String.valueOf(signDetailList.size()));
			SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy-MM-dd");
			List<String> dayList = new ArrayList<String>();
			Double totalReward = 0.0;
			
			
			//根据流水判断之前连续签到天数
			for(SignDetailBean detail : signDetailList){
				String signDate = detail.getSignDate();
				String today = sdfdate.format(new Date());
				if(today.equals(signDate)){
					pageData.setTodayIsSign("1");
				}
				dayList.add(signDate);
				totalReward = totalReward + Double.valueOf(detail.getReward());
			}
			
			
			
			pageData.setTotalReward(NumberUtils.moneyFormat(String.valueOf(totalReward)));
			String yesterday = CommonUtil.getYesterdayStr(sdfdate.format(new Date())) ;
			if(dayList.size()>0){
				while(dayList.contains(yesterday)){
					yesterday = CommonUtil.getYesterdayStr(yesterday);
					continuityDay++;
				}
			}
			if("1".equals(pageData.getTodayIsSign())){
				continuityDay++;
			}
			pageData.setContinuityDay(String.valueOf(continuityDay));
			
			JsonList<SignDetailBean> lastDetailList = signMediator.pagination(userId, 1, 10);
			pageData.setLastSignDetail(lastDetailList);
			
			result.setStatus(true);
			result.setEntity(pageData);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg(String.format("获取用户[%s]的签到数据失败,请和客服联系", userId));
			return result;
		} 
		return result;
	}
	
	
	/**
	 * 
	* Title: paginationSignDetail
	* Description: 分页
	* @param pi
	* @param ps
	* @param request
	* @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public @ResponseBody
	Object paginationSignDetail(String token,int pi, int ps, HttpServletRequest request) {
		OpResult validateResult = validateOpResult(pi, ps);
//		String userId = request.getParameter("userId");
		if (!validateResult.isStatus()) {
			return validateResult;
		}
		JsonList<SignDetailBean> details = new JsonList<SignDetailBean>();;
		//需判断用户是否登录
		String userId = null;
		if(StringUtils.isEmpty(token)){
			userId = getLoginedUserId(request);
			if (StringUtils.isEmpty(userId)) {
				details.setStatus(false);
				details.setMsg(SystemConfig.NO_LOGIN);
				return details;
			}
		}else{
			userId = signMediator.getUserIdByToken(token);
		}
		if(userId == null){
			details.setStatus(false);
			details.setMsg("不能识别的用户身份标识");
			return details;
		}
		try {
			details = signMediator.pagination(userId, pi, ps);
			details.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			details.setMsg(String.format("获取用户[%s]的获奖记录失败,请和客服联系", userId));
			details.setStatus(false);
		}
		return details;
	}
	
	/**
	 * 返回签到的最高奖励(最高奖励连续天数_最高奖励金额)，
	 * 同时以Map的形式返回签到的配置
	 * 注:该方法会修改参数 rewardConfMap
	 * **/
	private String getSignValue(Map<String,String> rewardConfMap)
	{
		int  continuityRewardDay = 0;
		String maxReward="";
		//获取奖励规则和最高奖励金额
		List<SignConfBean> list = signMediator.getSignConf();
		
		if(null==rewardConfMap)
		{
			rewardConfMap = new HashMap<String,String>();	
		}
		
		if(list!=null && list.size()>0){
			continuityRewardDay = list.size()-1;
			for(SignConfBean scb:list){
				String name = scb.getName();
				if(name.indexOf("DAY") != -1){
					String day = name.substring(3);
					//最高奖励
					if(day.equals(String.valueOf(continuityRewardDay))){
						maxReward = scb.getValue();
					}
					rewardConfMap.put(day, scb.getValue());
				}
			}
		}
		return continuityRewardDay+"_"+maxReward;
	}
}
