/**
 * 
 */
package com.jujin.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.util.StringUtils;

import com.jujin.common.JsonList;
import com.jujin.entity.account.Account;
import com.jujin.entity.account.AccountCenter;
import com.jujin.entity.account.AccountLog;
import com.jujin.entity.account.EarningsRank;
import com.jujin.entity.account.FriendInvestment;
import com.jujin.entity.account.InviteReward;
import com.jujin.entity.account.Message;
import com.jujin.entity.account.RecoveEntity;
import com.jujin.entity.account.RecoveEntityDetail;
import com.jujin.entity.account.RecoveLog;
import com.jujin.entity.account.RecoveLogItem;
import com.jujin.entity.account.UserBankCard;
import com.jujin.entity.account.UserRoi;
import com.jujin.utils.ExceptionHelper;
import com.jujin.utils.Toolkit;
import com.pro.common.model.Model;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.wicket.loan.common.utils.NumberUtils;
import com.wicket.loan.web.common.bean.UsersOperationLogBean;
import com.wicket.loan.web.person.account.mediator.UserAccountInfoMediator;
import com.wicket.loan.web.person.tender.mediator.UserBorrowTenderMediator;

/**
 * @author 宁
 * 
 */
public class AccountBiz extends BaseBiz {

	@Inject
	private UserAccountInfoMediator mediator;

	private UserBorrowTenderMediator userBorrowTenderMediator=new  UserBorrowTenderMediator();

	/* 用户中心 */
	public AccountCenter getAccountCenter(String userId) {

		SqlSession session = null;
		try {
			session = this.getSession();
			Account account = getAccount(session, userId);
			if (account != null) {
				AccountCenter center = session.selectOne(
						"com.jujin.mapper.QueryAccountCenterInfo", userId);// 分两次查询
				if (center != null) {
					center.setAccount(account);
					return center;
				}
			}
		} finally {
			if (session != null)
				session.close();
		}

		return null;
	}

	public Account getAccount(String userId) {

		SqlSession session = getSession();
		try {
			return getAccount(session, userId);
		} finally {
			if (session != null)
				session.close();
		}
	}

	public Account getAccount(SqlSession session, String userId) {
		Account account = session.selectOne(
				"com.jujin.mapper.QueryAccountInfo", userId);
		return account;
	}

	/* 账户流水 */
	/**
	 * 账户流水 为分页示例
	 * 
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public JsonList<AccountLog> getAccountLog(String userId, int pageIndex,
			int pageSize) {

		/*
		 * Page page = Page.newBuilder(pageIndex, pageSize, ""); //物理分页这个是一定要有的
		 * (对于数据量较小的时候,逻辑分页使用ibatis的RowBound) ThreadLocalUtil.set(page);
		 * List<AccountLog> logs = getSession().selectList(
		 * "com.jujin.mapper.QueryAccountLogByPage", userId);
		 * 
		 * JsonList<AccountLog> list = new JsonList<AccountLog>();
		 * list.addRange(logs); list.setTotal(page.getTotalRecord());
		 * list.setPageSize(page.getPageSize());
		 * list.setPageCount(page.getTotalPage());
		 */
		SqlSession session = null;
		JsonList<AccountLog> list = null;
		try {
			session = this.getSession();
			List<AccountLog> logs = session.selectList(
					"com.jujin.mapper.QueryAccountLog", userId);
			
			if(null!=logs&&logs.size()>0)
			{
				for (int i = 0; i < logs.size(); i++) {
					AccountLog log=logs.get(i);
					
					if(!StringUtils.isEmpty(log.getRemark()))
					{
						log.setRemark(Toolkit.RemoveLink(log.getRemark()));
					}	
				}
			}
			list = GetPagedEntity(logs, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}
		return list;

	}

	/* 已投项目 正在回款中 0:正在回款中;1:投标中;2:已结束 */
	public JsonList<RecoveEntity> getRecoveEntity(String userId, int type,
			int pageIndex, int pageSize) {

		SqlSession session = null;
		if (type < 0 || type > 2) {
			throw new RuntimeException("已投项目 类型不正确");
		}

		String key = "com.jujin.mapper.QueryRecoveEntity";
		switch (type) {
		case 0:
			key = "com.jujin.mapper.QueryRecoveEntity";
			break;
		case 1:
			key = "com.jujin.mapper.QueryReadyRecoveEntity";
			break;
		case 2:
			key = "com.jujin.mapper.QueryGoingRecoveEntity";
			break;
		default:
			break;
		}
		JsonList<RecoveEntity> list = null;
		try {
			session = this.getSession();
			List<RecoveEntity> logs = session.selectList(key, userId);

			list = GetPagedEntity(logs, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	/* 项目还款明细 */
	public JsonList<RecoveEntityDetail> getRecoveEntityDetail(String userId,
			String borrowId, int type, int pageIndex, int pageSize) {

		if (type < 0 || type > 1) {
			throw new RuntimeException("还款明细  类型不正确");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);

		if (!StringUtils.isEmpty(borrowId)) {
			map.put("borrow_id", borrowId);
		} else {
			map.put("type", String.valueOf(type));
		}

		JsonList<RecoveEntityDetail> list = null;
		SqlSession session = null;
		try {
			session = this.getSession();

			List<RecoveEntityDetail> logs = session.selectList(
					"com.jujin.mapper.QueryRecoveEntityDetail", map);

			list = GetPagedEntity(logs, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	/* 回款计划 暂不启用 */
	public JsonList<RecoveLog> getRecoveLog(String userId, String title,
			String borrowId, int borrowType, int period, int periodType,
			int isDay, int pageIndex, int pageSize, int recover_flg) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_id", userId);
		if (!StringUtils.isEmpty(title))
			map.put("title", borrowType);
		if (borrowType != 0)
			map.put("borror_type", borrowType);
		if (period != 0)
			map.put("period", period);
		if (periodType != 0)
			map.put("period_type", period);
		if (isDay == 1)
			map.put("is_day", 1);
		if (!StringUtils.isEmpty(borrowId)) {
			map.put("borrow_id", borrowId);
		}

		map.put("recover_flg", recover_flg);

		SqlSession session = null;
		JsonList<RecoveLog> list = null;
		try {
			session = this.getSession();
			List<RecoveLogItem> logs = session.selectList(
					"com.jujin.mapper.QueryRecoveLogItem", map);
			// 没有C#的Linq
			RecoveLog entity = null;
			List<RecoveLog> lists = new ArrayList<RecoveLog>();
			for (RecoveLogItem item : logs) {
				if (entity == null
						|| (!entity.getBorrowId().equals(item.getBorrowId()))) {
					entity = new RecoveLog();
					entity.setBorowTitle(item.getBorowTitle());
					entity.setBorrower(item.getBorrower());
					entity.setBorrowId(item.getBorrowId());
					entity.addRecord(item);
					entity.setAddRate(item.getAddRate());
					lists.add(entity);
				} else if (entity.getBorrowId().equals(item.getBorrowId())) {
					entity.addRecord(item);
				}
			}
			list = GetPagedEntity(lists, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}

		return list;
	}

	/* 回款计划 */
	public JsonList<RecoveLog> getRecoveLog(String userId, String title,
			String borrowId, int borrowType, int period, int periodType,
			int isDay, int pageIndex, int pageSize) {

		return getRecoveLog(userId, title, borrowId, borrowType, period,
				periodType, isDay, pageIndex, pageSize, 0);

	}

	/* 我的消息 */
	public JsonList<Message> getMessages(String userId, int pageIndex,
			int pageSize) {

		JsonList<Message> list = null;
		SqlSession session = null;
		try {
			session = this.getSession();
			List<Message> logs = session.selectList(
					"com.jujin.mapper.QueryMessage", userId);
			
			if(logs!=null&&logs.size()>0)
			{
				for (int i = 0; i < logs.size(); i++) {
					Message msg=logs.get(i);
					if(!StringUtils.isEmpty(msg.getContent()))
					{
						msg.setContent(Toolkit.RemoveLink(msg.getContent()));
					}
				}
			}

			list = GetPagedEntity(logs, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/* 收益排名 */
	/*
	 * public EarningsRank getEarningsRank(String userId) {
	 * 
	 * List<UserRoi> list = getSession().selectList(
	 * "com.jujin.mapper.QueryUserRoi", userId);
	 * 
	 * double totalIntervest = 0;
	 * 
	 * EarningsRank erEntity = new EarningsRank();
	 * 
	 * Account accountInfo = getSession().selectOne(
	 * "com.jujin.mapper.QueryAccountInfo", userId);
	 * 
	 * erEntity.setAccount(accountInfo);
	 * 
	 * List<DataPoint> dpList1 = erEntity.getEarnPoints(); List<DataPoint>
	 * dpList2 = erEntity.getCompPoints1(); List<DataPoint> dpList3 =
	 * erEntity.getCompPoints2();
	 * 
	 * // 投资收益 for (UserRoi entity : list) {
	 * 
	 * totalIntervest += entity.getRecoverInterest1();
	 * 
	 * DataPoint dp = new DataPoint(); dp.setLabelCn(entity.getRecoverTime());
	 * dp.setMemo("聚金资本"); dp.setValue(entity.getRecoverInterest1());
	 * 
	 * DataPoint dp1 = new DataPoint(); dp1.setLabelCn(entity.getRecoverTime());
	 * dp1.setMemo("余额宝"); dp1.setValue(entity.getRecoverInterest2());
	 * 
	 * DataPoint dp2 = new DataPoint(); dp2.setLabelCn(entity.getRecoverTime());
	 * dp2.setMemo("银行活期存款"); dp2.setValue(entity.getRecoverInterest3());
	 * 
	 * dpList1.add(dp); dpList2.add(dp1); dpList3.add(dp2); }
	 * 
	 * System.out.println("list.size:"+list.size());
	 * System.out.println("list.size:"+dpList1.size());
	 * System.out.println("list.size:"+dpList2.size());
	 * System.out.println("list.size:"+dpList3.size());
	 * erEntity.setTotalIntervest(totalIntervest); return erEntity; }
	 */

	public EarningsRank getEarningsRank(String userId) {

		List<UserRoi> list = getSession().selectList(
				"com.jujin.mapper.QueryUserRoi", userId);

		double totalIntervest = 0;
		double totalIntervest1 = 0;
		double totalIntervest2 = 0;

		EarningsRank erEntity = new EarningsRank();

		SqlSession session = null;
		try {
			session = this.getSession();

			Account accountInfo = session.selectOne(
					"com.jujin.mapper.QueryAccountInfo", userId);

			erEntity.setAccount(accountInfo);

			List<Double> dpList1 = erEntity.getPoints();
			List<Double> dpList2 = erEntity.getPoints1();
			List<Double> dpList3 = erEntity.getPoints2();

			List<String> labels = erEntity.getLabels();

			// 投资收益
			for (UserRoi entity : list) {

				totalIntervest += entity.getRecoverInterest1();
				totalIntervest1 += entity.getRecoverInterest2();
				totalIntervest2 += entity.getRecoverInterest3();

				labels.add(entity.getRecoverTime());
				dpList1.add(entity.getRecoverInterest1());
				dpList2.add(entity.getRecoverInterest2());
				dpList3.add(entity.getRecoverInterest3());

				/*
				 * DataPoint dp = new DataPoint();
				 * dp.setLabelCn(entity.getRecoverTime()); dp.setMemo("聚金资本");
				 * dp.setValue(entity.getRecoverInterest1());
				 * 
				 * DataPoint dp1 = new DataPoint();
				 * dp1.setLabelCn(entity.getRecoverTime()); dp1.setMemo("余额宝");
				 * dp1.setValue(entity.getRecoverInterest2());
				 * 
				 * DataPoint dp2 = new DataPoint();
				 * dp2.setLabelCn(entity.getRecoverTime());
				 * dp2.setMemo("银行活期存款");
				 * dp2.setValue(entity.getRecoverInterest3());
				 * 
				 * dpList1.add(dp); dpList2.add(dp1); dpList3.add(dp2);
				 */
			}
			erEntity.setTotalIntervest(totalIntervest);
			erEntity.setTotalIntervest1(totalIntervest1);
			erEntity.setTotalIntervest2(totalIntervest2);
		} finally {
			if (session != null)
				session.close();
		}
		return erEntity;
	}

	/* 历史投标记录 */
	public JsonList<RecoveLog> getHisRecoveLog(String userId, String title,
			String borrowId, int borrowType, int period, int periodType,
			int isDay, int pageIndex, int pageSize) {

		return getRecoveLog(userId, title, borrowId, borrowType, period,
				periodType, isDay, pageIndex, pageSize, 1);
	}

	/* 用户绑定银行卡的信息,目前系统只有一个账号 */
	public UserBankCard getBankCard(String userId) {

		SqlSession session = null;
		try {
			session = this.getSession();
			List<UserBankCard> cards = session.selectList(
					"com.jujin.mapper.QueryUserBankCard", userId);
			if (cards != null && cards.size() > 0) {
				return cards.get(0);
			}
		} finally {
			if (session != null)
				session.close();
		}

		return null;
	}

	/* 邀请人统计信息奖励 */
	public InviteReward getStatInvite(String userId, int pageIndex, int pageSize) {

		InviteReward reward = null;
		/*
		 * String memo = getSession()
		 * .selectOne("com.jujin.mapper.QueryRewardMemo");
		 */
		String memo = "";
		SqlSession session = null;
		try {
			session = this.getSession();
			List<FriendInvestment> list = session.selectList(
					"com.jujin.mapper.QueryFriendInvestment", userId);

			JsonList<FriendInvestment> jsonList = GetPagedEntity(list,
					pageIndex, pageSize);
			reward = new InviteReward();
			reward.setInvets(jsonList);
			reward.setMemo(memo);
		} finally {
			if (session != null)
				session.close();
		}
		return reward;
	}

	public com.jujin.entity.account.Coupon QueryCoupon(String userId) {
		SqlSession session = null;

		com.jujin.entity.account.Coupon result = null;
		try {
			session = this.getSession();
			result = session.selectOne("com.jujin.mapper.QueryCoupon", userId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result = null;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return result;
	}

	public void joinInterest(UsersOperationLogBean bean) {

	}

	public com.jujin.entity.account.UserTenderSummary QueryUserTenderSummary(
			String userId) {

		com.jujin.entity.account.UserTenderSummary result = new com.jujin.entity.account.UserTenderSummary();

		double overAccount = 0;// 逾期投资
		double tenderAccount = 0;// 投资总额
		double rewardAccount = 0;// 投资奖励
		double tenderInterest = 0;// 已利息
		double overdueInteres = 0; // 已赚罚金

		Model<String, Object> model = this.userBorrowTenderMediator
				.selectInvestCount(userId);
		if (!model.isEmpty()) {
			// 使用聚金券 增加金额 统计 2015-04-27 add
			double addAccountAll = model.getDoubleValue("ADDACCOUNT");
			double addAccountYes = model.getDoubleValue("ADDACCOUNTYES");
			double addAccountWait = addAccountAll - addAccountYes;
			double tenderAccountWait = model
					.getDoubleValue("RECOVER_ACCOUNT_WAIT");// 待收金额
			double tenderInterstYes = model
					.getDoubleValue("RECOVER_ACCOUNT_INTEREST_YES");// 已赚利息
			double tenderAccountYes = model
					.getDoubleValue("RECOVER_ACCOUNT_YES");// 已回收本息

			// 加
			tenderAccountWait += addAccountWait;
			tenderInterstYes += addAccountYes;
			tenderAccountYes += addAccountYes;

			result.setTenderAccount(NumberUtils.moneyFormat(model
					.getStringValue("TENDERING_ACCOUNT")));
			result.setRepayEndCapital(NumberUtils.moneyFormat(model
					.getStringValue("RECOVEREND_ACCOUNT")));
			result.setRepayingAccount(NumberUtils.moneyFormat(model
					.getStringValue("RECOVERING_ACCOUNT")));

			overAccount = NumberUtils.parseDouble(model
					.getStringValue("OVERDUE_ACCOUNT_TENDER"));
			result.setOverAccount(NumberUtils.moneyFormat(model
					.getStringValue("OVERDUE_ACCOUNT_TENDER")));

			result.setRepayWaitAccount(NumberUtils
					.moneyFormat(tenderAccountWait + "")
					+ "/"
					+ NumberUtils.parseInteger(model
							.getStringValue("RECOVER_ACCOUNT_WAIT_TIMES"))
					+ "期");

			result.setRepayEndAccount(NumberUtils.moneyFormat(tenderAccountYes
					+ "")
					+ "/" + model.getIntValue("RECOVER_TIMES") + "期");

			tenderAccount = NumberUtils.parseDouble(model
					.getStringValue("VALID_ACCOUNT_TENDER"));
			tenderInterest = NumberUtils.parseDouble(model
					.getStringValue("RECOVER_ACCOUNT_INTEREST_YES"));

			result.setTenderAccount(NumberUtils.moneyFormat(model
					.getStringValue("VALID_ACCOUNT_TENDER")));
			result.setIncomeInterest(NumberUtils.moneyFormat(String
					.valueOf(tenderInterstYes)));

			overdueInteres = NumberUtils.parseDouble(model
					.getStringValue("OVERDUE_INTEREST"));
			result.setIncomePenalty("￥"
					+ NumberUtils.moneyFormat(model
							.getStringValue("OVERDUE_INTEREST")));

			rewardAccount = NumberUtils.parseDouble(model
					.getStringValue("REWARDMONEY"));
			result.setIncomeReward(NumberUtils.moneyFormat(model
					.getStringValue("REWARDMONEY")));
			result.setSiteAdvance(NumberUtils.moneyFormat(model
					.getStringValue("WEB_MAT_ACCOUNT")));
		} else {

			result.setTenderingAccount(NumberUtils.moneyFormat("0"));
			result.setRepayingAccount(NumberUtils.moneyFormat("0"));
			result.setRepayEndCapital(NumberUtils.moneyFormat("0"));
			result.setOverAccount(NumberUtils.moneyFormat("0"));
			result.setRepayWaitAccount(NumberUtils.moneyFormat("0") + "/0期");
			result.setRepayEndAccount(NumberUtils.moneyFormat("0") + "/0期");
			result.setTenderAccount(NumberUtils.moneyFormat("0"));
			result.setIncomeInterest(NumberUtils.moneyFormat("0"));
			result.setIncomePenalty(NumberUtils.moneyFormat("0"));
			result.setIncomeReward(NumberUtils.moneyFormat("0"));
			result.setSiteAdvance(NumberUtils.moneyFormat("0"));
		}

		// 坏账率 逾期投资/投资总额
		// 平均收益率 (已赚奖励+已赚罚息+已赚利息)/投资总额
		if (tenderAccount == 0) {
			result.setBadRate("0.00%");
			result.setAverageIncome("0.00%");
		} else {
			double scale = overAccount / tenderAccount;
			result.setBadRate(NumberUtils.decimalConverter(scale * 100, 2,
					BigDecimal.ROUND_HALF_UP) + "%");
			scale = (tenderInterest + overdueInteres + rewardAccount)
					/ tenderAccount;
			result.setAverageIncome(NumberUtils.decimalConverter(scale * 100,
					2, BigDecimal.ROUND_HALF_UP) + "%");
		}
		
		return result;
	}

	public void UpdMsg(String userId) {
		 
		SqlSession session=null;
		try {
			session=this.getSession(true);
			session.update("com.jujin.mapper.UpdateUserMessage",userId);
		}  
		finally
		{
			 if(session!=null)
			 {
				 session.close();
			 }
		}
	}

}