package com.jujin.biz;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import net.sms.main.enums.SmsTypeEnum;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.JsonList;
import com.jujin.entity.award.AwardCodeEnum;
import com.jujin.entity.award.AwardDrawInfo;
import com.jujin.entity.award.AwardInfo;
import com.jujin.entity.award.AwardWinInfo;
import com.jujin.entity.award.UserAwardInfo;
import com.jujin.entity.award.UtilEnum;
import com.jujin.thread.SmsRunnable;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.db.Exception.DbException;
import com.pro.common.util.FWBeanManager;
import com.wicket.loan.common.constant.Config;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.borrow.information.mediator.BorrowTenderMediator;
import com.wicket.loan.web.common.bean.BorrowVentureBean;
import com.wicket.loan.web.common.bean.UsersAccountBean;
import com.wicket.loan.web.common.mediator.CommonAdminMediator;

/**
 * Title: DrawAwardBiz Description: 抽奖 Company: jujinziben
 * 
 * @author gaojunfeng
 * @date 2015年4月17日
 */
public class AwardBiz extends BaseBiz {

	UserAwardMediator mediator = new UserAwardMediator(); // FWBeanManager.getBean(UserAwardMediator.class);

	/**
	 * Title: getAward Description:获取所有奖品信息
	 * 
	 * @return
	 */
	public List<AwardInfo> getAward() {

		SqlSession session = null;
		List<AwardInfo> list = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.QueryAwardInfo");
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * Title: getAwardDrawInfoBy Description: 获取用户抽奖情况信息
	 * 
	 * @param userId
	 * @return
	 */
	public AwardDrawInfo getAwardDrawInfoByUserId(String userId) {

		SqlSession session = null;
		AwardDrawInfo info = null;
		try {
			session = this.getSession();
			info = session.selectOne(
					"com.jujin.mapper.QueryAwardDrawInfoByUserId", userId);
		} finally {
			if (session != null)
				session.close();
		}

		return info;
	}

	/**
	 * 
	 * Title: updateAwardDrawInfo Description: 更新指定用户的抽奖信息
	 * 
	 * @param bean
	 */
	public void updateAwardDrawInfo(AwardDrawInfo bean) {

		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.UpdateAwardDrawInfo", bean);
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * 
	 * Title: insertAwardWinInfo Description: 用户中奖信息插入
	 * 
	 * @param bean
	 */
	public void insertAwardWinInfo(AwardWinInfo bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.InsertAwardWinInfo", bean);
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * 
	 * Title: getAwardDrawInfoCountOfVIP Description: 查询指定用户的VIP奖品信息
	 * 
	 * @param userId
	 * @return
	 */
	public int getAwardDrawInfoCountOfVIP(String userId) {
		SqlSession session = null;
		int count = 0;
		try {
			session = this.getSession();
			count = session.selectOne(
					"com.jujin.mapper.QueryAwardDrawInfoCountOfVIP", userId);
		} finally {
			if (session != null)
				session.close();
		}
		return count;
	}

	/**
	 * 用户抽奖信息插入(首次插入数据抽奖次数默认为1) Title: insertAwardDrawInfo Description:
	 * 
	 * @param userId
	 */
	public void insertAwardDrawInfo(String userId) {
		SqlSession session = null;
		try {
			session = getSession(true);
			session.insert("com.jujin.mapper.InsertAwardDrawInfo", userId);
		} finally {
			if (session != null)
				session.close();
		}

	}

	/**
	 * 
	 * Title: getDrawTimesOfToday Description: 获取今日抽奖次数
	 * 
	 * @param userId
	 * @return
	 */
	public int getDrawTimesOfToday(String userId) {
		SqlSession session = null;
		int count = 0;
		try {
			session = getSession();
			count = session.selectOne("com.jujin.mapper.QueryDrawTimesOfToday",
					userId);
		} finally {
			if (session != null)
				session.close();
		}
		return count;
	}

	/**
	 * 
	 * Title: countAwardDrawInfoByUserId Description: 统计用户抽奖信息
	 * 
	 * @param bean
	 */
	public void countAwardDrawInfoByUserId(String userId) {
		SqlSession session = null;
		try {
			session = getSession(true);
			session.update("com.jujin.mapper.CountAwardDrawInfoByUserId",
					userId);
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * 抽奖
	 */
	public AwardInfo drawAward(String userId) {
		// 1：第一次抽奖：用户抽奖信息表插入一条记录，不查询VIP次数
		// 2：不是第一次抽奖：
		// 当天首次登录，当天抽奖次数小于1
		// 定义此次 抽奖结果
		AwardInfo resultBean = null;
		// 如果是第一次抽奖，先插入用户抽奖信息记录
		AwardDrawInfo awardDrawInfo = getAwardDrawInfoByUserId(userId);
		// 获取所有奖品信息
		List<AwardInfo> list = getAward();
		// 12个月VIP奖品信息
		AwardInfo vipAwardInfo = getVipAwardInfo(list);
		int random;// 定义 随机数
		if (awardDrawInfo == null) {
			insertAwardDrawInfo(userId);
		} else// 统计当前用户可用抽奖次数
		{
			countAwardDrawInfoByUserId(userId);
		}
		// 重新获取当前用户的抽奖信息
		awardDrawInfo = getAwardDrawInfoByUserId(userId);
		// 抽奖次数大于0
		if (awardDrawInfo != null && awardDrawInfo.getOddTimes() > 0) {
			// 获取用户抽到的VIP次数
			int vipCount = getAwardDrawInfoCountOfVIP(userId);
			if (vipAwardInfo != null ? (vipCount >= vipAwardInfo.getMaxTimes())
					: false) {
				random = (int) (Math.random() * (UtilEnum.PECENTS.getIndex() - (vipAwardInfo
						.getAwardPercentEnd() - vipAwardInfo
						.getAwardPercentFrom())));// 去掉VIP的百分比
			} else {
				random = (int) (Math.random() * (UtilEnum.PECENTS.getIndex()));// 去掉VIP的百分比
			}
			// 根据随机数判断结果所在区间获取抽奖结果
			for (AwardInfo info : list) {
				if (random >= info.getAwardPercentFrom()
						&& random < info.getAwardPercentEnd()) {
					resultBean = info;
					break;
				}
			}
			// 更新用户抽奖信息
			if (AwardCodeEnum.VIPCODE.getIndex() != resultBean.getAwardCode()) {
				awardDrawInfo.setOddTimes(awardDrawInfo.getOddTimes() - 1);
				updateAwardDrawInfo(awardDrawInfo);
			}
			// 插入用户中奖纪录
			AwardWinInfo awardWinInfo = new AwardWinInfo();
			awardWinInfo.setUserId(userId);
			awardWinInfo.setAwardCode(resultBean.getAwardCode());
			insertAwardWinInfo(awardWinInfo);
		}
		return resultBean;
	}

	/**
	 * Title: getVipAwardInfo Description:获取VIP奖品信息
	 * 
	 * @param list
	 * @return
	 */
	private AwardInfo getVipAwardInfo(List<AwardInfo> list) {
		for (AwardInfo info : list) {
			if (info.getAwardCode() == AwardCodeEnum.VIPCODE.getIndex()) {
				return info;
			}
		}
		return null;
	}

	/**
	 * add by wangn 20150426
	 * 
	 * @param userId
	 */
	public void updateDrawCountByShare(String userId) {

		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.UpdateDrawCountByShare", userId);
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * add by wangning 2015-04-22
	 * 
	 * @param userId
	 * @param ip
	 * @param money
	 */
	public boolean updateUserAccount(String userId, String ip, double money) {

		boolean isSuccess = mediator.updateUserAccount(userId, ip, money);
		if (isSuccess) {
			SmsRunnable worker = new SmsRunnable(this, userId,
					NumberUtils.moneyFormat(String.valueOf(money)) + "元红包");
			Pool.execute(worker);
		}
		return isSuccess;
	}

	public JsonList<UserAwardInfo> getUserAwardInfo(String userId, int pi,
			int ps) {

		SqlSession session = null;
		JsonList<UserAwardInfo> list = null;
		try {
			session = this.getSession();

			List<UserAwardInfo> logs = session.selectList(
					"com.jujin.mapper.QueryUserAwardInfo", userId);
			list = GetPagedEntity(logs, pi, ps);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

}
