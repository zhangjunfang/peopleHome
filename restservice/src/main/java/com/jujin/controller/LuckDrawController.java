package com.jujin.controller;

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

import com.jujin.biz.luckDraw.LuckDrawQueryBiz;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.common.OpResult;
import com.jujin.common.SystemConfig;
import com.jujin.entity.luckDraw.Award;
import com.jujin.entity.luckDraw.AwardRecord;
import com.jujin.entity.luckDraw.AwardSet;
import com.jujin.entity.luckDraw.PageData;
import com.jujin.entity.luckDraw.ScrollAwardRecord;
import com.jujin.entity.luckDraw.UserOddTimes;
import com.jujin.entity.luckDraw.WhiteList;
import com.jujin.redis.CacheConstants;
import com.jujin.redis.RedisUtil;
import com.jujin.util.xglc.CommonUtil;
import com.jujin.utils.BugUtil;
import com.jujin.utils.ExceptionHelper;
import com.wicket.loan.common.utils.UserUtils;

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:  
 *                   写字楼里写字间，写字间里程序员；  
 *                   程序人员写程序，又拿程序换酒钱。  
 *                   酒醒只在网上坐，酒醉还来网下眠；  
 *                   酒醉酒醒日复日，网上网下年复年。  
 *                   但愿老死电脑间，不愿鞠躬老板前；  
 *                   奔驰宝马贵者趣，公交自行程序员。  
 *                   别人笑我忒疯癫，我笑自己命太贱；  
 *                   不见满街漂亮妹，哪个归得程序员？  
*/


/**
 * Title: LuckDrawController
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月6日
 */
@Controller
@RequestMapping("/luckdraw")
public class LuckDrawController extends BaseController {
	LuckDrawQueryBiz ldMediator = new LuckDrawQueryBiz();
	private static String sync = "lock";
	private boolean whiteEnable = false;//白名单是否开启
	/**
	 * 
	* Title: luckDraw
	* Description: 抽奖
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/draw", method = RequestMethod.GET)
	public @ResponseBody Object luckDraw(HttpServletRequest request,HttpServletResponse response){
		OpEntityResult<Award> result = new OpEntityResult<Award>(null);
		//需判断用户是否登录
		String userId = request.getParameter("userId");
		if(userId == null){
			userId = getLoginedUserId(request);
		}
		if (StringUtils.isEmpty(userId)) {
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		
		
		int myDrawTimes = Integer.parseInt(ldMediator.loadUserOddTimes(userId));
		if("success".equals(CacheConstants.CACHE_GLOBAL_PERSISTENCE)){
			if(myDrawTimes > 0){
				String phoneNumber = (String) request.getSession().getAttribute(SystemConfig.PHONE_NUMBER);
				String nickName = null;
				List<UserOddTimes> uotList = ldMediator.loadGlobalOddTimes();
				
				for(UserOddTimes uot : uotList){
					if(userId.equals(uot.getUserId())){
						nickName = uot.getNickName();
						phoneNumber = uot.getPhoneNumber();
					}
				}
				if(StringUtils.isEmpty(nickName))
				{
					result.setStatus(false);
					result.setMsg("您的剩余抽奖次数为0");
					return result;
				}
				result= doDraw(userId,phoneNumber,UserUtils.strToConceal(nickName));
			}else{
				result.setStatus(false);
				result.setMsg("您的剩余抽奖次数为0");
			}
		}else{
			result.setStatus(false);
			result.setMsg("抽奖失败，请联系客服人员!");
			BugUtil.sendBugEmail("[2016跨年抽奖]持久化错误标识为false",new Exception());
		}
		
		return result;
	}
	
	
	
	
	
	/**
	 * 
	* Title: getOddTimes
	* Description: 获取用户页面数据
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/my", method = RequestMethod.GET)
	public @ResponseBody Object getMyPageData(HttpServletRequest request,HttpServletResponse response){
		OpEntityResult<PageData> result = new OpEntityResult<PageData>(null);
		int myDrawTimes = 0;//我的抽奖次数
		PageData pd = new PageData();//页面数据
		
		
		//需判断用户是否登录
		String userId = request.getParameter("userId");
		if(userId == null){
			userId = getLoginedUserId(request);
		}
		if (StringUtils.isEmpty(userId)) {
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		pd.setUserId(userId);
		try {
			//缓存初始化数据
			ldMediator.initCacheData(userId);
			
			//获取抽奖次数
			String myDrawTimesObj = ldMediator.loadUserOddTimes(userId);
			if(myDrawTimesObj == null){
				myDrawTimesObj = "0";
			}
			if(myDrawTimesObj != null){
				myDrawTimes = Integer.parseInt(myDrawTimesObj) ;
			}
			pd.setOddTimes(myDrawTimes);
			//获取中奖记录(最近10条)
			List<AwardRecord> awardRecordList = ldMediator.loadUserAwardRecord(userId);
			if(awardRecordList == null){
				awardRecordList = ldMediator.pagination(userId, 1, 10).getList();
			}
			if(awardRecordList.size()>10){
				List<AwardRecord> lastList = awardRecordList.subList(0, 10);
				pd.setAwardList(lastList);
			}else{
				List<AwardRecord> lastList = awardRecordList.subList(0, awardRecordList.size());
				pd.setAwardList(lastList);
			}
			
			result.setEntity(pd);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg(String.format("获取用户[%s]抽奖信息失败,请和客服联系", userId));
			BugUtil.sendBugEmail(String.format("[2016跨年抽奖]获取用户[%s]抽奖信息失败,请和客服联系", userId),e);
		}
		return result;
	}
	

	
	/**
	 * 获取滚动中奖记录
	* Title: getScrollAwardRecord
	* Description: 
	* @param request
	* @param response
	* @return
	 */
	@RequestMapping(value = "/scroll", method = RequestMethod.GET)
	public @ResponseBody Object getScrollAwardRecord(HttpServletRequest request,HttpServletResponse response){
		OpEntityResult<List<ScrollAwardRecord>> result = new OpEntityResult<List<ScrollAwardRecord>>(null);
		try {
			List<ScrollAwardRecord> allAwardRecord = ldMediator.loadScrollAwardRecord();
			for(ScrollAwardRecord sar : allAwardRecord){
				if(sar.getWinDate().length() > 12){
					String temp = sar.getWinDate();
					sar.setWinDate(temp.substring(11, 19));
				}
//				String tempName = sar.getNickName();
//				sar.setNickName(UserUtils.strToConceal(tempName));
			}
			if(allAwardRecord.size()<20){
				result.setEntity(allAwardRecord);
			}else{
				result.setEntity(allAwardRecord.subList(0, 20));
			}
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("获取滚动中奖记录失败,请和客服联系");
			BugUtil.sendBugEmail("[2016跨年抽奖]获取滚动中奖记录失败",e);
		}
		return result;
	}
	
	
	/**
	 * 
	* Title: paginationUserAward
	* Description: 分页
	* @param pi
	* @param ps
	* @param request
	* @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public @ResponseBody Object paginationUserAward(int pi, int ps, HttpServletRequest request) {
		OpResult validateResult = validateOpResult(pi, ps);

		if (!validateResult.isStatus()) {
			return validateResult;
		}
		JsonList<AwardRecord> details = new JsonList<AwardRecord>();
		//需判断用户是否登录
		String userId = request.getParameter("userId");
		if(userId == null){
			userId = getLoginedUserId(request);
		}
		if (StringUtils.isEmpty(userId)) {
			details.setStatus(false);
			details.setMsg(SystemConfig.NO_LOGIN);
			return details;
		}
		try {
			details = ldMediator.pagination(userId, pi, ps);
			details.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			details.setMsg(String.format("获取用户[%s]的中奖记录失败,请和客服联系", userId));
			details.setStatus(false);
			BugUtil.sendBugEmail("[2016跨年抽奖]获取用户"+userId+"中奖记录失败",ex);
		}
		return details;
	}
	
	/**
	 * 为用户中心提供抽奖次数数据
	* Title: getAccountData
	* Description: 
	* @param request
	* @return
	 */
	@RequestMapping(value = "/times", method = RequestMethod.GET)
	public @ResponseBody Object getAccountData(HttpServletRequest request) {
		OpEntityResult<Map<String,String>> result = new OpEntityResult<Map<String,String>>(null);
		//需判断用户是否登录
		String userId = request.getParameter("userId");
		if(userId == null){
			userId = getLoginedUserId(request);
		}
		if (StringUtils.isEmpty(userId)) {
			result.setStatus(false);
			result.setMsg(SystemConfig.NO_LOGIN);
			return result;
		}
		try {
			Map<String,String> map = new HashMap<String, String>();
			String oddTimes = ldMediator.loadUserOddTimes(userId);
			String usedTimes = "0";
			List<AwardRecord> list = ldMediator.loadUserAwardRecord(userId);
			if(list != null && list.size()>0){
				usedTimes = String.valueOf(list.size());
			}
			String allTimes = String.valueOf(Integer.parseInt(usedTimes) + Integer.parseInt(oddTimes));
			map.put("usedTimes", usedTimes);
			map.put("oddTimes", oddTimes);
			map.put("allTimes", allTimes);
			result.setEntity(map);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result.setMsg(String.format("获取用户[%s]的中奖记录失败,请和客服联系", userId));
			result.setStatus(false);
			BugUtil.sendBugEmail("[2016跨年抽奖]获取用户"+userId+"中奖记录失败",ex);
		}
		return result;
	}

	/**
	 * 抽奖业务
	* Title: doDraw
	* Description: 
	* @param userId
	 */
	private OpEntityResult<Award> doDraw(String userId,String phoneNumber,String nickName){
		OpEntityResult<Award> result = new OpEntityResult<Award>(null);
		try {
			synchronized (sync){
				List<AwardSet> setList = ldMediator.loadAwardSet();

				
				//如果抽奖人数超过一半，则提升大奖中奖概率
				int allDrawTimes = Integer.parseInt(ldMediator.loadTotalOddTimes());
				List<Award> bigAwardPool = ldMediator.loadBigAwardPool();
				if(allDrawTimes < bigAwardPool.size()*2){
					ldMediator.changeChance();
				}
				
				//概率抽奖
				Award award = ldMediator.getAward(setList);
				
				//白名单过滤
				award = filterWhite(award, userId, setList);
				
				//获取用户是否已经中过大奖
				String hasBigAward = ldMediator.loadUserHasBigAward(userId);
				//判断该奖品是否为大奖 && 判断奖池是否有该奖品  && 该用户是否已经中过大奖 
				if("1".equals(award.getIsBigAward())){
					if(ldMediator.hasAward(bigAwardPool, award) && "0".equals(hasBigAward)){
						modifyCache(bigAwardPool, award, userId,nickName, phoneNumber, allDrawTimes, true);
					}else{
						award = randomDrawLuckyAward(award, setList);
						modifyCache(bigAwardPool, award, userId,nickName, phoneNumber, allDrawTimes, false);
					}
				}else{
					modifyCache(bigAwardPool, award, userId,nickName, phoneNumber, allDrawTimes, false);
				}
				
				result.setEntity(award);
				result.setStatus(true);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg(String.format("用户[%s]抽奖失败,请和客服联系", userId));
			BugUtil.sendBugEmail("[2016跨年抽奖]用户"+userId+"抽奖失败",e);
		}
		return result;
	}
	
	/**
	 * 白名单过滤
	* Title: filterWhite
	* Description: 是否开启白名单，如果开启，则为该名单中人员指定奖品，不开启则概率抽奖
	* @return
	 */
	private Award filterWhite(Award award,String userId,List<AwardSet> setList){
		String whiteEnableStr = ldMediator.loadWhiteEnable();
		if("1".equals(whiteEnableStr)){
			whiteEnable = true;
		}
		if(whiteEnable){
			List<WhiteList> whiteList = ldMediator.loadWhiteList();
			for(WhiteList w : whiteList){
				if(userId.equals(w.getUserId())){
					for(AwardSet as : setList){
						if(as.getAwardCode().equals(w.getAwardCode()) ){
							award = new Award(as.getId(),as.getAwardCode(), as.getAwardMsg(),as.getQuantity(), "1".equals(as.getMaxTimes()) ? "1" : "0");
						}
					}
				}
			}
		}
		return award;
	}
	
	/**
	 * 抽奖后修改缓存
	* Title: modifyCache
	* Description:
	 */
	private void modifyCache(List<Award> bigAwardPool,Award award,String userId,String nickName,String phoneNumber,int allDrawTimes,boolean isBigAward){
		if(isBigAward){
			//修改大奖池
			bigAwardPool = ldMediator.removeAward(bigAwardPool, award);
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_BIGAWARDPOOL, bigAwardPool);
			
			//修改是否中大奖
			RedisUtil.setString(userId + CacheConstants.CACHE_USER_HASBIGAWARD, "1");
		}
		
		//修改所有中奖记录
		List<AwardRecord> allAwardRecordList = ldMediator.loadAllAwardRecord();
		allAwardRecordList.add(new AwardRecord(award,userId,phoneNumber));
		RedisUtil.setList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD, allAwardRecordList);
		//修改每个用户剩余抽奖次数 TODO
		
		
		//修改总剩余抽奖次数
		allDrawTimes = allDrawTimes - 1;
		RedisUtil.setString(CacheConstants.CACHE_GLOBAL_DRAWTIMES,String.valueOf(allDrawTimes));
		//修改我的剩余抽奖次数
		String myDrawTimesStr = ldMediator.loadUserOddTimes(userId);
		int myDrawTimes = Integer.parseInt(myDrawTimesStr);
		RedisUtil.setString(userId + CacheConstants.CACHE_USER_DRAWTIMES,String.valueOf(myDrawTimes-1));
		
		//保存我的中奖记录
		List<AwardRecord> myAwardList = ldMediator.loadUserAwardRecord(userId);
		myAwardList.add(0,new AwardRecord(award,userId,phoneNumber));
		RedisUtil.setList(userId + CacheConstants.CACHE_USER_AWARDRECORD, myAwardList);
		
		//修改滚动中奖记录
		List<ScrollAwardRecord> scrollList = ldMediator.loadScrollAwardRecord();
		scrollList.add(0,new ScrollAwardRecord(userId,nickName,phoneNumber,award.getAwardMsg(),CommonUtil.dateToString(new Date())));
		RedisUtil.setList(CacheConstants.CACHE_SCROLL_AWARDRECORD, scrollList);
	}
	
	/**
	 * 随机抽取幸运奖
	* Title: randomDrawLuckyAward
	* Description: 
	* @return
	 */
	private Award randomDrawLuckyAward(Award award,List<AwardSet> setList){
		//随机发放幸运奖（根据奖品code随机生成,每次抽奖活动需要修改此处的code范围值） TODO
		int randomCode = CommonUtil.getRandom(1, 4);
		String awardCode = String.valueOf(randomCode);
		for(AwardSet as : setList){
			if(awardCode.equals(as.getAwardCode()) ){
				award = new Award(as.getId(),as.getAwardCode(), as.getAwardMsg(),as.getQuantity(), "1".equals(as.getMaxTimes()) ? "1" : "0");
			}
		}
		return award;
	}
}
