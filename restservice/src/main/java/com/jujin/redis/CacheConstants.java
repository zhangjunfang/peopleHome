package com.jujin.redis;
/**
 * 缓存内容
* <p>Title: CacheConstants.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: jujinziben</p>
* @author zhengshaoxu
* @date 2015年11月10日
* @version 1.0
 */
public class CacheConstants {
	/**
	 * 持久化错误标示
	 */
	public static String CACHE_GLOBAL_PERSISTENCE = "success";
	

	
	/**
	 * 奖品设置
	 */
	public static final String CACHE_GLOBAL_AWARDSET = "awardSet";
	
	/**
	 * 白名单设置
	 */
	public static final String CACHE_GLOBAL_WHITEENABLE = "whiteEnable";
	
	/**
	 * 白名单列表
	 */
	public static final String CACHE_GLOBAL_WHITELIST = "whiteList";
	
	
	
	/**
	 * 大奖池
	 */
	public static final String CACHE_GLOBAL_BIGAWARDPOOL = "bigAwardPool";
	
	
	/**
	 * 需持久化中奖记录
	 */
	public static final String CACHE_GLOBAL_ALLAWARDRECORD = "allAwardRecord";
	
	/**
	 * 每个用户的剩余抽奖次数
	 */
	public static final String CACHE_GLOBAL_ALLUSERODDTIMES = "allUserOddTimes";

	/**
	 * 所有抽奖次数总和
	 */
	public static final String CACHE_GLOBAL_DRAWTIMES = "allDrawTimes";
	
	/**
	 * 用户抽奖次数
	 */
	public static final String CACHE_USER_DRAWTIMES = "DrawTimes";
	
	/**
	 * 用户是否中过大奖
	 */
	public static final String CACHE_USER_HASBIGAWARD = "HasBigAward";
	
	/**
	 * 用户中奖记录
	 */
	public static final String CACHE_USER_AWARDRECORD = "AwardRecord";
	
	/**
	 * 滚动中奖记录
	 */
	public static final String CACHE_SCROLL_AWARDRECORD = "scrollAwardRecord";

	
}
