package com.jujin.biz.luckDraw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.JujinBaseBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.entity.luckDraw.Award;
import com.jujin.entity.luckDraw.AwardRecord;
import com.jujin.entity.luckDraw.AwardSet;
import com.jujin.entity.luckDraw.BorrowRecord;
import com.jujin.entity.luckDraw.ScrollAwardRecord;
import com.jujin.entity.luckDraw.UserOddTimes;
import com.jujin.entity.luckDraw.WhiteList;
import com.jujin.redis.CacheConstants;
import com.jujin.redis.RedisUtil;
import com.jujin.utils.BugUtil;

/**
 * Title: LuckDrawBiz
 * Description: 抽奖业务
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月6日
 */
public class LuckDrawQueryBiz extends JujinBaseBiz{

	/** serialVersionUID*/
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * 获取抽奖配置
	* Title: getAwardConf
	* Description: 
	* @return
	 */
	public OpEntityResult<List<AwardSet>> getAwardConf(){
		OpEntityResult<List<AwardSet>> result = new OpEntityResult<List<AwardSet>>(null);
		List<AwardSet> list = new ArrayList<AwardSet>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.luckDraw.mapper.getConf");
			result.setEntity(list);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result.setStatus(false);
			result.setMsg("获取奖励配置出错");
			BugUtil.sendBugEmail("[2016跨年抽奖]获取奖品配置出错", e);
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	
	
	/**
	 * 获取某个用户中奖纪录（分页）
	* Title: paginationUserAwardRecord
	* Description: 
	* @return
	 */
	public JsonList<AwardRecord> pagination(String userId, int pageIndex, int pageSize){
		JsonList<AwardRecord> list = new JsonList<AwardRecord>();
		SqlSession session = null;
		try {
			session = getSession();
			List<AwardRecord> records = session.selectList("com.jujin.luckDraw.mapper.getUserAwardRecord",userId);
			list = GetPagedEntity(records, pageIndex, pageSize);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			BugUtil.sendBugEmail("[2016跨年抽奖]获取某个用户中奖纪录（分页）出错", e);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}
	
	/**
	 * 获取滚动中奖信息(20条)
	* Title: getBigAwardRecord
	* Description: 
	* @return
	 */
	public List<ScrollAwardRecord> getScrollAwardRecord(){
		List<ScrollAwardRecord> list = new ArrayList<ScrollAwardRecord>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.luckDraw.mapper.getScrollAwardRecord");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			BugUtil.sendBugEmail("[2016跨年抽奖]获取滚动中奖信息(20条)出错", e);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}
	
	
	/**
	 * 获取所有用户剩余抽奖次数
	* Title: getUserDrawTimes
	* Description: 
	* @return
	 */
	public List<UserOddTimes> getUserOddTimes(){
		List<UserOddTimes> list = new ArrayList<UserOddTimes>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.luckDraw.mapper.getUserOddTimes");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			BugUtil.sendBugEmail("[2016跨年抽奖]获取所有用户剩余抽奖次数出错", e);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * 提升概率
	* Title: changeChance
	* Description:
	 */
	@SuppressWarnings("unchecked")
	public void changeChance(){
		List<AwardSet> awardSetList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_AWARDSET,AwardSet.class);
		for(AwardSet as : awardSetList){
			if("1".equals(as.getMaxTimes())){
				if(as.getChance() != 1){
					as.setChance(1);
				}
			}
		}
		RedisUtil.setList(CacheConstants.CACHE_GLOBAL_AWARDSET, awardSetList);
	}
	

	/**
	 * 根据概率随机获取奖品
	* Title: getAward
	* Description: 
	* @param awardSet
	* @return
	 */
	public Award getAward(List<AwardSet> awardSet) {
		Double[] chances = new Double[awardSet.size()];		
		Award[] awards = new Award[awardSet.size()];
		Award award = new Award();
		//概率之和
		double s = 0;
		for(int i = 0;i < awardSet.size();i++){
			AwardSet as = awardSet.get(i);
			s = s + as.getChance();
			chances[i] = as.getChance();
			awards[i] = new Award(as.getId(),as.getAwardCode(), as.getAwardMsg(),as.getQuantity(), "1".equals(as.getMaxTimes()) ? "1" : "0");
		}
		//抽奖
		double r = Math.random();
		double v = r * s;
		
		double a = 0;
		for(int i = 0;i < awardSet.size();i++){
			if(a < v && v < a+chances[i]){
				award = awards[i];
			}
			a = a+chances[i];
		}
		return award;
	}
	
	/**
	 * 将有数量限制的奖项组成奖品池（大奖奖池）
	* Title: uniqueBigAward
	* Description: 
	* @param awardPool
	* @param myAwardList
	* @param award
	* @return
	 */
	public static List<Award> getBigAwardPool(List<AwardSet> awardSet){
		List<Award> bigAwardPool = new ArrayList<Award>();
		for(AwardSet as : awardSet){
			if(Integer.parseInt(as.getMaxTimes()) > 0){
				for(int i = 0;i<Integer.parseInt(as.getTotalCount());i++){
					bigAwardPool.add(new Award(as.getId(),as.getAwardCode(), as.getAwardMsg(),as.getQuantity(), "1".equals(as.getMaxTimes()) ? "1" : "0"));
				}
			}
		}
		return bigAwardPool;
	}
	
	/**
	 * 删除对象
	* Title: removeAward
	* Description: 
	* @param list
	* @param award
	* @return
	 */
	public List<Award> removeAward(List<Award> list,Award award){
		ConcurrentLinkedDeque<Award> qList = new ConcurrentLinkedDeque<>(list);
		if(hasAward(list,award)){
			for(Award a : qList){
				if(a.equalsAward(award)){
					list.remove(a);
					break;
				}
			}
		}
		List<Award> aList = new ArrayList<>(list);
		return aList;
	}
	
	/**
	 * 判断是否包含对象
	* Title: hasAward
	* Description: 
	* @param list
	* @param award
	* @return
	 */
	public boolean hasAward(List<Award> list,Award award){
		boolean boo = false;
		for(Award a : list){
			if(a.equalsAward(award)){
				boo = true; 
			}
		}
		return boo;
	}
	
	
	
	
	
	


	/**
	 * 获取活动期间的用户投标记录
	* Title: getUserBorrowRecord
	* Description: 
	* @param userId
	* @return
	 */
	public List<BorrowRecord> getUserBorrowRecord(String userId) {
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.luckDraw.mapper.getUserBorrowRecord",userId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			BugUtil.sendBugEmail("[2016跨年抽奖]获取活动期间的用户投标记录出错", e);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}
	
	/******************************加载缓存**********************************/
	
	
	/**
	 * 加载奖品设置
	* Title: loadAwardSet
	* Description: 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<AwardSet> loadAwardSet(){
		List<AwardSet> awardSetList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_AWARDSET,AwardSet.class);
		if(awardSetList == null){
			awardSetList = getAwardConf().getEntity();
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_AWARDSET, awardSetList);
		}
		return awardSetList;
	}
	
	/**
	 * 加载大奖池
	* Title: loadBigAwardPool
	* Description: 
	* @param loadAwardSet
	* @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Award> loadBigAwardPool(){
		List<AwardSet> loadAwardSet = loadAwardSet();
		List<Award> bigAwardPool = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_BIGAWARDPOOL,Award.class);
		if(bigAwardPool == null){
			bigAwardPool = getBigAwardPool(loadAwardSet);
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_BIGAWARDPOOL, bigAwardPool);
		}
		return bigAwardPool;
	}
	
	/**
	 * 加载全局剩余抽奖次数(所有用户)
	* Title: loadGlobalOddTimes
	* Description: 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserOddTimes> loadGlobalOddTimes(){
		List<UserOddTimes> userOddlist = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_ALLUSERODDTIMES, UserOddTimes.class);
		if(userOddlist == null){
			userOddlist = getUserOddTimes();
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_ALLUSERODDTIMES, userOddlist);
		}
		return userOddlist;
	}
	
	/**
	 * 加载剩余抽奖次数总和
	* Title: loadTotalOddTimes
	* Description: 
	* @return
	 */
	public String loadTotalOddTimes(){
		String temp = RedisUtil.getString(CacheConstants.CACHE_GLOBAL_DRAWTIMES);
		if(temp == null){
			int allOddTimes = 0;
			List<UserOddTimes> userOddlistTemp = loadGlobalOddTimes();
			for(UserOddTimes uot : userOddlistTemp){
				allOddTimes = allOddTimes + Integer.parseInt(uot.getOddTimes()); 
			}
			temp = String.valueOf(allOddTimes);
			RedisUtil.setString(CacheConstants.CACHE_GLOBAL_DRAWTIMES, temp);
		}
		return temp;
	}
	
	/**
	 * 加载所有中奖记录
	* Title: loadAllAwardRecord
	* Description: 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<AwardRecord> loadAllAwardRecord(){
		List<AwardRecord> allAwardRecordList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD,AwardRecord.class);
		if(allAwardRecordList == null){
			allAwardRecordList = new ArrayList<AwardRecord>();
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD, allAwardRecordList);
		}
		return allAwardRecordList;
	}
	
	
	/**
	 * 加载用户中奖纪录
	* Title: loadUserAwardRecord
	* Description: 
	* @param userId
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<AwardRecord> loadUserAwardRecord(String userId){
		List<AwardRecord> userAwardRecordList = RedisUtil.getList(userId + CacheConstants.CACHE_USER_AWARDRECORD,AwardRecord.class);
		if(userAwardRecordList == null){
			userAwardRecordList = pagination(userId,1,9999).getList();
			RedisUtil.setList(userId + CacheConstants.CACHE_USER_AWARDRECORD, userAwardRecordList);
		}
		return userAwardRecordList;
	}
	
	/**
	 * 加载用户是否中过大奖
	* Title: loadUserHasBigAward
	* Description: 
	* @return
	 */
	public String loadUserHasBigAward(String userId){
		String hasBigAward = "0";
		List<AwardRecord> userAwardRecordList = loadUserAwardRecord(userId);
		for(AwardRecord ar : userAwardRecordList){
			if("1".equals(ar.getIsBigAward())){
				hasBigAward = "1";
			}
		}
		RedisUtil.setString(userId + CacheConstants.CACHE_USER_HASBIGAWARD, hasBigAward);
		return hasBigAward;
	}
	
	/**
	 * 加载用户剩余抽奖次数
	* Title: loadUserOddTimes
	* Description: 
	* @param userId
	* @return
	 */
	public String loadUserOddTimes(String userId){
		String oddTimes = RedisUtil.getString(userId + CacheConstants.CACHE_USER_DRAWTIMES);
		if(oddTimes == null){
			List<UserOddTimes> globalOddTimes = loadGlobalOddTimes();
			for(UserOddTimes uot : globalOddTimes){
				if(userId.equals(uot.getUserId())){
					oddTimes = uot.getOddTimes();
				}
			}
		}
		if(oddTimes == null){
			oddTimes = "0";
		}
		return oddTimes;
	}
	
	/**
	 * 加载白名单开启状态
	* Title: loadWhiteEnable
	* Description: 
	* @return
	 */
	public String loadWhiteEnable(){
		String whiteEnable = RedisUtil.getString(CacheConstants.CACHE_GLOBAL_WHITEENABLE);
		return whiteEnable;
	}
	
	/**
	 * 加载白名单列表
	* Title: loadWhiteList
	* Description: 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<WhiteList> loadWhiteList() {
		List<WhiteList> whiteList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_WHITELIST,WhiteList.class);
		return whiteList;
	}
	
	/**
	 * 加载滚动中奖记录
	* Title: loadScrollAwardRecord
	* Description: 
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<ScrollAwardRecord> loadScrollAwardRecord(){
		List<ScrollAwardRecord> scrollAwardRecord = RedisUtil.getList(CacheConstants.CACHE_SCROLL_AWARDRECORD,ScrollAwardRecord.class);
		if(scrollAwardRecord == null){
			scrollAwardRecord = getScrollAwardRecord();
			RedisUtil.setList(CacheConstants.CACHE_SCROLL_AWARDRECORD, scrollAwardRecord);
		}
		return scrollAwardRecord;
	}
	
	/**
	 * 初始化缓存数据
	* Title: initCacheData
	* Description: 
	* @param userId
	 */
	public void initCacheData(String userId){
		//缓存奖品设置
		loadAwardSet();
		
		//缓存大奖池
		loadBigAwardPool();
		
		//缓存所有中奖记录
		loadAllAwardRecord();
		
		//缓存每个用户剩余抽奖次数
		loadGlobalOddTimes();
		
		//缓存总剩余抽奖次数
		loadTotalOddTimes();

		//缓存用户中奖记录
		loadUserAwardRecord(userId);
		
		//缓存用户是否中过大奖
		loadUserHasBigAward(userId);
		
		//缓存用户剩余抽奖次数
		loadUserOddTimes(userId);
		
		//缓存白名单是否开启
		loadWhiteList();
		
		//缓存滚动记录
		loadScrollAwardRecord();
	}







	
	
}
