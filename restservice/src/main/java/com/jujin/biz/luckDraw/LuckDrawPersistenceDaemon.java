package com.jujin.biz.luckDraw;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jujin.entity.luckDraw.AwardRecord;
import com.jujin.entity.luckDraw.AwardSet;
import com.jujin.entity.luckDraw.UserOddTimes;
import com.jujin.redis.CacheConstants;
import com.jujin.redis.RedisUtil;
import com.jujin.util.xglc.CommonUtil;
import com.jujin.utils.BugUtil;

/**
 * Title: PersistenceBiz
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月13日
 */
public class LuckDrawPersistenceDaemon extends QuartzJobBean{
	protected static final Logger logger = Logger.getLogger(LuckDrawPersistenceDaemon.class);

    private static LuckDrawPersistenceDaemon instance=null;
    
    public static LuckDrawPersistenceDaemon getInstance(){
        if(instance==null){
            synchronized(LuckDrawPersistenceDaemon.class){
                if(instance==null){
                    instance=new LuckDrawPersistenceDaemon();
                }
            }
        }
        return instance;
    }
	
	LuckDrawPersistenceBiz biz = new LuckDrawPersistenceBiz();
	LuckDrawQueryBiz qBiz = new LuckDrawQueryBiz();
	boolean boo = true;
	
	
	
	/**
	 * 判断当前是否为抽奖有效期
	* Title: checkTime
	* Description: 
	* @return
	 */
	private boolean checkTime(){
		boolean boo = true;
		try {
			Date drawBegin = CommonUtil.stringToDate(AwardSet.BEGIN_DATE_DRAW);
			Date drawEnd = CommonUtil.stringToDate(AwardSet.END_DATE_DRAW);
			Date now = new Date();
			if(now.after(drawBegin) && now.before(drawEnd)){
				boo = true;
			}else{
				boo = false;
			}
		} catch (ParseException e) {
			logger.error("判断当前是否为抽奖有效期失败",e);
			RedisUtil.setString(CacheConstants.CACHE_GLOBAL_PERSISTENCE, "fail");
		}
		return boo;
	}
	
	/**
	 * 持久化内存数据
	* Title: persistence
	* Description: 
	* @param allArList
	 */
	private synchronized void persistence(List<AwardRecord> allArList){
		System.out.println("persistence_cache_data,size:" + allArList.size()); 
		try {
			//保存中奖记录
			biz.saveUserAwardRecord(allArList);
			clearCache(allArList);
			List<AwardRecord> coinList = new ArrayList<AwardRecord>();
			List<AwardRecord> ticketList = new ArrayList<AwardRecord>();
			//发放虚拟奖品
			for(AwardRecord ar : allArList){
				if(AwardSet.COIN_CODE_6.equals(ar.getAwardCode())  || AwardSet.COIN_CODE_66.equals(ar.getAwardCode())){
					coinList.add(ar);
				}else if(AwardSet.TICKET_CODE_1.equals(ar.getAwardCode()) || AwardSet.TICKET_CODE_5.equals(ar.getAwardCode())){
					ticketList.add(ar);
				}
			}
			if(coinList.size()>0){
				biz.sendCoin(coinList);
			}
			if(ticketList.size()>0){
				biz.sendTicket(ticketList);
			}
		} catch (Exception e) {
			logger.error("持久化内存数据失败",e);
			BugUtil.sendBugEmail("[2016跨年抽奖]持久化内存数据失败", e);
			RedisUtil.setString(CacheConstants.CACHE_GLOBAL_PERSISTENCE, "fail");
		}
		
	}
	
	/**
	 * 删除缓存里已经持久化过的数据
	* Title: clearCache
	* Description: 
	* @param list
	 */
	@SuppressWarnings("unchecked")
	private void clearCache(List<AwardRecord> list){
		try {
			List<AwardRecord> cacheList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD, AwardRecord.class);
			for(int i = 0;i<cacheList.size();i++){
				for(int j = 0;j<list.size();j++){
					AwardRecord a = cacheList.get(i);
					AwardRecord r = list.get(j);
					if(a.equalsEntity(r)){
						cacheList.remove(a);
					}
				}
			}
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD, cacheList);
		} catch (Exception e) {
			logger.error("删除缓存里已经持久化过的数据失败",e);
			BugUtil.sendBugEmail("[2016跨年抽奖]删除缓存里已经持久化过的数据失败", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {  
			List<AwardRecord> allArList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD, AwardRecord.class);
			if(allArList != null && allArList.size() > 0){
				persistence(allArList);
			}
			resetUotList();
			logger.info("LuckDrawPersistenceDaemon run");
			
        } catch (Exception e) {  
			logger.error("定时任务持久化中奖纪录数据失败",e);
			BugUtil.sendBugEmail("[2016跨年抽奖]定时任务持久化中奖纪录数据失败", e);
        }  
	}

	/**
	 * 每天凌晨重置抽奖次数
	* Title: resetUotList
	* Description:
	 */
	private void resetUotList() {
		if(CommonUtil.isInDate(new Date(), "00:00:00", "00:03:00")){
			List<UserOddTimes> uotList = qBiz.getUserOddTimes();
			RedisUtil.setList(CacheConstants.CACHE_GLOBAL_ALLUSERODDTIMES,uotList);
			logger.info("resetUotList success");
		}
	}



}
