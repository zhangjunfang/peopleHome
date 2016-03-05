package com.jujin.biz.luckDraw;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.jujin.entity.luckDraw.AwardRecord;
import com.jujin.entity.luckDraw.AwardSet;
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
public class LuckDrawPersistenceService extends Observable implements Runnable{
	protected static final Logger logger = Logger.getLogger(LuckDrawPersistenceService.class);

    private static LuckDrawPersistenceService instance=null;
    
    public static LuckDrawPersistenceService getInstance(){
        if(instance==null){
            synchronized(LuckDrawPersistenceService.class){
                if(instance==null){
                    instance=new LuckDrawPersistenceService();
                }
            }
        }
        return instance;
    }
	
	LuckDrawPersistenceBiz biz = new LuckDrawPersistenceBiz();
	boolean boo = true;
	
    // 此方法一经调用，立马可以通知观察者，在本例中是监听线程
    public void doBusiness() {
        if (true) {
            super.setChanged();
        }
        notifyObservers();
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(checkTime()){
			try {  
				List<AwardRecord> allArList = RedisUtil.getList(CacheConstants.CACHE_GLOBAL_ALLAWARDRECORD, AwardRecord.class);
				if(allArList != null && allArList.size() > 0){
					persistence(allArList);
				}
                Thread.sleep(1000 * 60); //每隔60s执行一次  
                System.out.println("Thread[LuckDrawPersistence]——runing:" + CommonUtil.dateToString(new Date())); 
            } catch (Exception e) {  
                e.printStackTrace();
                doBusiness();
                break;
            }  
		}
	}
	
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
			CacheConstants.CACHE_GLOBAL_PERSISTENCE = "fail";
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
			CacheConstants.CACHE_GLOBAL_PERSISTENCE = "fail";
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



}
