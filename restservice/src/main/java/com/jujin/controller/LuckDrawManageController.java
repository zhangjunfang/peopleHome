package com.jujin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.jujin.biz.luckDraw.LuckDrawQueryBiz;
import com.jujin.entity.luckDraw.Award;
import com.jujin.entity.luckDraw.AwardRecord;
import com.jujin.entity.luckDraw.AwardSet;
import com.jujin.entity.luckDraw.BorrowRecord;
import com.jujin.entity.luckDraw.ScrollAwardRecord;
import com.jujin.entity.luckDraw.UserOddTimes;
import com.jujin.entity.luckDraw.WhiteList;
import com.jujin.redis.CacheConstants;
import com.jujin.redis.RedisUtil;
import com.jujin.util.xglc.CommonUtil;
/**
 * Title: LuckDrawManageController
 * Description: 抽奖管理页面
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月6日
 */
@Controller
@RequestMapping("/ldm")
public class LuckDrawManageController extends BaseController {
	LuckDrawQueryBiz ldMediator = new LuckDrawQueryBiz();
	
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView toUserView(HttpServletRequest request,HttpServletResponse response){
		String auth = request.getParameter("auth");
		if("xxbcoder".equals(auth)){
			ModelAndView view = new ModelAndView("ldm/user");
			return view;
		}else{
			ModelAndView view = new ModelAndView("ldm/auth");
			return view;
		}
	}
	
	@RequestMapping(value = "/global", method = RequestMethod.GET)
	public ModelAndView toGlobalView(HttpServletRequest request,HttpServletResponse response){
		String auth = request.getParameter("auth");
		if("xxbcoder".equals(auth)){
			ModelAndView view = new ModelAndView("ldm/global");
			return view;
		}else{
			ModelAndView view = new ModelAndView("ldm/auth");
			return view;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadUserData", method = RequestMethod.GET)
	public void getUserPageData(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		String userId = request.getParameter("userId");
		String yesDayStr = CommonUtil.getYesterdayStr(CommonUtil.dateToString(new Date()));
		
		int yesAddTimes = 0;
		//获取活动期间投标记录
		List<BorrowRecord> brList = ldMediator.getUserBorrowRecord(userId);
		for(BorrowRecord br : brList){
			if(yesDayStr.equals(br.getTdate())){
				yesAddTimes++;
			}
		}
		
		int allTimes = 0;
		//获取中奖记录
		List<AwardRecord> arList = RedisUtil.getList(userId + CacheConstants.CACHE_USER_AWARDRECORD, AwardRecord.class);
		if(arList == null){
			arList = ldMediator.pagination(userId, 1, 9999).getList();
		}
		allTimes = brList.size();
//		int oddTimes = allTimes - arList.size();
		String oddTimes = ldMediator.loadUserOddTimes(userId);
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("allTimes", allTimes);
		map.put("yesAddTimes", yesAddTimes);
		map.put("oddTimes", oddTimes);
		map.put("brList", brList);
		map.put("arList", arList);
		response.getWriter().print(JSON.toJSONString(map));
	}
	
	@RequestMapping(value = "/loadGlobalData", method = RequestMethod.GET)
	public void loadGlobalData(HttpServletRequest request,HttpServletResponse response) throws ParseException, IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		
		String persistenceFlag = CacheConstants.CACHE_GLOBAL_PERSISTENCE;
		List<AwardSet> asList = ldMediator.loadAwardSet();
		String whiteListEnable = ldMediator.loadWhiteEnable();
		List<WhiteList> wlList = ldMediator.loadWhiteList();
		List<Award> bigList = ldMediator.loadBigAwardPool();
		List<AwardRecord> persistenceRecord = ldMediator.loadAllAwardRecord();
		List<UserOddTimes> uotList = ldMediator.loadGlobalOddTimes();
		String oddTimesTotal = ldMediator.loadTotalOddTimes();
		
		map.put("pFlag", persistenceFlag);
		map.put("wlEnbale", whiteListEnable);
		map.put("totalTimes", oddTimesTotal);
		
		map.put("pRecord", persistenceRecord);
		map.put("asList", asList);
		map.put("bigList", bigList);
		map.put("wlList", wlList);
		map.put("uotList", uotList);
		
		response.getWriter().print(JSON.toJSONString(map));
	}
	
	
}
