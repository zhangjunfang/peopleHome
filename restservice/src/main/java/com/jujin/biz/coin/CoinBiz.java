package com.jujin.biz.coin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;
import oracle.net.aso.i;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.jujin.biz.JujinBaseBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.pro.common.util.StringUtils;
import com.jujin.entity.coin.CoinChange;
import com.jujin.entity.coin.NotifyBean;
import com.jujin.entity.coin.TccActivityBean;
import com.jujin.entity.coin.TccCoinConfigBean;
import com.jujin.entity.coin.TpaCoinChanceBean;
import com.jujin.entity.coin.TpaUserCoinBean;
import com.jujin.entity.coin.TpaUserCoinDetailBean;

/**
 * Title: DrawAwardBiz Description: 红包Biz Company: jujinziben
 * 
 * @author gaojunfeng
 * @date 2015年5月29日
 */
public class CoinBiz extends JujinBaseBiz {
	// 事务
	private Transaction newTransaction;

	/*
	 * 以下是活动信息表的操作
	 */
	/**
	 * Title: insertTccActivity Description: 活动信息插入
	 * 
	 * @param bean
	 */
	public void insertTccActivity(TccActivityBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.insertTccActivity", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: updateTccActivity Description: 活动信息修改
	 * 
	 * @param bean
	 */
	public void updateTccActivity(TccActivityBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.updateTccActivity", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: getAwardDrawInfoBy Description: 获取活动信息
	 * 
	 * @param userId
	 * @return
	 */
	public TccActivityBean getTccActivityByRecordId(int recordId) {
		SqlSession session = null;
		TccActivityBean bean = null;
		try {
			session = this.getSession();
			bean = session.selectOne(
					"com.jujin.mapper.getTccActivityBeanByRecordId", recordId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * Title: getAllTccActivityBean Description:获取所有活动信息
	 * 
	 * @return
	 */
	public List<TccActivityBean> getAllTccActivityBean() {

		SqlSession session = null;
		List<TccActivityBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.getAllTccActivityBean");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * Title: getAllTccActivityBeanByEnable Description:根据是否有效获取活动信息
	 * 
	 * @return
	 */
	public List<TccActivityBean> getAllTccActivityBeanByEnable(int enable) {

		SqlSession session = null;
		List<TccActivityBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList(
					"com.jujin.mapper.getAllTccActivityBeanByEnable", enable);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/*
	 * 以下是红包键值对配置表的操作
	 */
	/**
	 * Title: insertTccCoinConfig Description: 红包键值对配置信息插入
	 * 
	 * @param bean
	 */
	public void insertTccCoinConfig(TccCoinConfigBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.insertTccCoinConfig", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: updateTccCoinConfig Description: 红包键值对信息修改
	 * 
	 * @param bean
	 */
	public void updateTccCoinConfig(TccCoinConfigBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.updateTccCoinConfig", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: getTccCoinConfigByRecordId Description: 获取红包键值信息
	 * 
	 * @param userId
	 * @return
	 */
	public TccCoinConfigBean getTccCoinConfigByRecordId(int recordId) {
		SqlSession session = null;
		TccCoinConfigBean bean = null;
		try {
			session = this.getSession();
			bean = session.selectOne(
					"com.jujin.mapper.getTccCoinConfigByRecordId", recordId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * Title: getPhoneNumberByRoot Description: 获取当前红包机会的顶级的电话
	 * 
	 * @param userId
	 * @return
	 */
	public String getPhoneNumberByRoot(int recordId) {
		SqlSession session = null;
		String phoneNumber = null;
		try {
			session = this.getSession();
			phoneNumber = session.selectOne(
					"com.jujin.mapper.getPhoneNumberByRoot", recordId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return phoneNumber;
	}

	/**
	 * Title: getTccCoinConfigBeanByGroupId Description:获取只能定groupId下的配置信息
	 * 
	 * @return
	 */
	public List<TccCoinConfigBean> getTccCoinConfigBeanByGroupId(String groupId) {

		SqlSession session = null;
		List<TccCoinConfigBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList(
					"com.jujin.mapper.getTccCoinConfigBeanByGroupId", groupId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * Title: getTccCoinConfigBeanByKey Description:获取指定key下的配置信息
	 * 
	 * @return
	 */
	public List<TccCoinConfigBean> getTccCoinConfigBeanByKey(String key) {

		SqlSession session = null;
		List<TccCoinConfigBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList(
					"com.jujin.mapper.getTccCoinConfigBeanByKey", key);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * Title: getTccCoinConfigBeanByType Description:获取指定type下的配置信息
	 * 
	 * @return
	 */
	public List<TccCoinConfigBean> getTccCoinConfigBeanByType(int type) {

		SqlSession session = null;
		List<TccCoinConfigBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList(
					"com.jujin.mapper.getTccCoinConfigBeanByType", type);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/*
	 * 以下是红包机会表的相关操作
	 */
	/**
	 * Title: insertTpaCoinChance Description: 红包机会信息插入
	 * 
	 * @param bean
	 */
	public void insertTpaCoinChance(TpaCoinChanceBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.insertTpaCoinChance", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: updateTpaCoinChance Description: 红包机会信息修改
	 * 
	 * @param bean
	 */
	public void updateTpaCoinChance(TpaCoinChanceBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.updateTpaCoinChance", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: getTpaCoinChanceByRecordId Description: 获取红包机会信息
	 * 
	 * @param userId
	 * @return
	 */
	public TpaCoinChanceBean getTpaCoinChanceByRecordId(int recordId) {
		SqlSession session = null;
		TpaCoinChanceBean bean = null;
		try {
			session = this.getSession();
			bean = session.selectOne(
					"com.jujin.mapper.getTpaCoinChanceByRecordId", recordId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/*
	 * 以下是红包流水表的相关操作
	 */
	/**
	 * Title: insertTpaUserCoinDetail Description: 红包流水信息插入
	 * 
	 * @param bean
	 */
	public void insertTpaUserCoinDetail(TpaUserCoinDetailBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.insertTpaUserCoinDetail", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: updateTpaUserCoinDetail Description: 红包流水信息修改
	 * 
	 * @param bean
	 */
	public void updateTpaUserCoinDetail(TpaUserCoinDetailBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.updateTpaUserCoinDetail", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: getAllTpaUserCoinDetailBean Description:获取所有红包流水信息
	 * 
	 * @return
	 */
	public List<TpaUserCoinDetailBean> getAllTpaUserCoinDetailByUserId() {

		SqlSession session = null;
		List<TpaUserCoinDetailBean> list = null;
		try {
			session = this.getSession();
			list = session
					.selectList("com.jujin.mapper.getAllTpaUserCoinDetailByUserId");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * Title: getTpaUserCoinDetailCount
	 * Description:获取已抢到的指定recordId红包机会下及recordId对应的root为红包机会的recordID的红包个数
	 * 
	 * @param groupId
	 *            (即对应红包机会表中的recordId)
	 * @param phoneNumber
	 * @return int
	 */
	public int getTpaUserCoinDetailCount(int recordId, String phoneNumber) {
		SqlSession session = null;
		int result = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("RECORD_ID", recordId);
		param.put("PHONE_NUMBER", phoneNumber);
		try {
			session = this.getSession();
			result = session.selectOne(
					"com.jujin.mapper.getTpaUserCoinDetailCount", param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/*
	 * 以下是红包表相关的操作
	 */
	/**
	 * Title: insertTpaUserCoin Description: 红包信息插入
	 * 
	 * @param bean
	 */
	public void insertTpaUserCoin(TpaUserCoinBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.insertTpaUserCoin", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: updateTpaUserCoin Description: 红包信息修改
	 * 
	 * @param bean
	 */
	public void updateTpaUserCoin(TpaUserCoinBean bean) {
		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.updateTpaUserCoin", bean);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * Title: getAllTpaUserCoinDetailBean Description:获取所有红包信息
	 * 
	 * @return
	 */
	public List<TpaUserCoinBean> getAllTpaUserCoinByUserId(String userId) {

		SqlSession session = null;
		List<TpaUserCoinBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList(
					"com.jujin.mapper.getAllTpaUserCoinByUserId", userId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * Title: getAllTpaUserCoinDetailBean Description:获取所有红包信息
	 * 
	 * @return
	 */
	public TpaUserCoinBean getTpaUserCoinByPhoneNumber(String phoneNumber) {

		SqlSession session = null;
		TpaUserCoinBean bean = null;
		try {
			session = this.getSession();
			bean = session
					.selectOne("com.jujin.mapper.getTpaUserCoinByPhoneNumber",
							phoneNumber);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * Title: grabCoin Description: 抢红包
	 * 
	 * @param recordId
	 *            红包机会表中recordId
	 * @return
	 * @throws SQLException
	 */
	public TpaUserCoinDetailBean grabCoin(int recordId, String phoneNumber)
			throws SQLException {
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		newTransaction = null;
		// 红包流水
		TpaUserCoinDetailBean bean1 = null;
		TpaUserCoinDetailBean bean2 = null;
		// 获取红包机会信息
		TpaCoinChanceBean chance = getTpaCoinChanceByRecordId(recordId);
		// 红包
		TpaUserCoinBean tpaUserCoinBean = getTpaUserCoinByPhoneNumber(phoneNumber);
		// 获取红包金额类型
		if (chance != null && chance.getGroupId() != null
				&& chance.getQuantity() > 0) {
			List<TccCoinConfigBean> list = getTccCoinConfigBeanByKey(chance
					.getGroupId());
			// 获取随机金额
			if (list.size() > 0) {
				try {
					session = this.getSession(false);
					newTransaction = transactionFactory.newTransaction(session
							.getConnection());
					// 1：抢到红包用户红包流水
					bean1 = new TpaUserCoinDetailBean();
					double amount = getRandomValue(list);
					bean1.setPhoneNumber(phoneNumber);
					bean1.setBpFlg(0);// 0:收入 1:支出
					bean1.setGroupId(chance.getRecordId());
					bean1.setAmount(amount);
					//chance.getNickName() 
					bean1.setMemo("好友投标"
							+ chance.getBorrowTitle() + "后分享的红包");
					
					//bean1.setMemo("通过好友分享生成自己的红包组");
					
					
					
					bean1.setBorrowId(chance.getBorrowId());

					// 更新红包机会
					chance = session.selectOne(
							"com.jujin.mapper.getTpaCoinChanceByRecordId",
							recordId);
					chance.setQuantity(chance.getQuantity() - 1);
					session.update("com.jujin.mapper.updateTpaCoinChance",
							chance);

					// 插入红包流水
					session.insert("com.jujin.mapper.insertTpaUserCoinDetail",
							bean1);
					if (!phoneNumber.equals(chance.getPhoneNumber())) {
						// 2：有人抢红包，分享红包用户红包流水
						bean2 = new TpaUserCoinDetailBean();
						bean2.setPhoneNumber(chance.getPhoneNumber());
						bean2.setBpFlg(3);// 3:别人抢红包后分享者红包流水
						bean2.setUserId(chance.getUserId());
						bean2.setGroupId(chance.getRecordId());
						bean2.setAmount(bean1.getAmount());
						//bean2.setMemo("电话为" + phoneNumber + "的好友抢红包,红包分享者"
						//		+ chance.getNickName() + "获得的等额奖励");
						
						bean2.setMemo("电话为" + phoneNumber + "的好友抢红包,红包分享者获得的等额奖励");
						
						bean2.setBorrowId(chance.getBorrowId());
						// 插入红包流水
						session.insert(
								"com.jujin.mapper.insertTpaUserCoinDetail",
								bean2);
						// 红包金额统计修改
						TpaUserCoinBean tpaUserCoinBeanTemp = getTpaUserCoinByPhoneNumber(chance
								.getPhoneNumber());
						if (tpaUserCoinBeanTemp != null) {
							tpaUserCoinBeanTemp.setAmount(tpaUserCoinBeanTemp
									.getAmount() + amount);
							session.update(
									"com.jujin.mapper.updateTpaUserCoin",
									tpaUserCoinBeanTemp);
						} else {
							tpaUserCoinBeanTemp = new TpaUserCoinBean();
							tpaUserCoinBeanTemp.setPhoneNumber(chance
									.getPhoneNumber());
							tpaUserCoinBeanTemp.setUserId(chance.getUserId());
							tpaUserCoinBeanTemp.setAmount(amount);
							tpaUserCoinBeanTemp.setFrost(0);
							session.insert(
									"com.jujin.mapper.insertTpaUserCoin",
									tpaUserCoinBeanTemp);
						}
					}
					// 更新红包表数据
					if (tpaUserCoinBean != null) {
						tpaUserCoinBean.setAmount(tpaUserCoinBean.getAmount()
								+ amount);
						session.update("com.jujin.mapper.updateTpaUserCoin",
								tpaUserCoinBean);
					} else {
						tpaUserCoinBean = new TpaUserCoinBean();
						tpaUserCoinBean.setPhoneNumber(phoneNumber);
						tpaUserCoinBean.setAmount(amount);
						tpaUserCoinBean.setFrost(0);
						session.insert("com.jujin.mapper.insertTpaUserCoin",
								tpaUserCoinBean);
					}
					newTransaction.commit();
				} catch (Exception e) {
					newTransaction.rollback();
					bean1 = null;
					logger.error(ExceptionHelper.getExceptionDetail(e));
					e.printStackTrace();
				} finally {
					if (session != null)
						session.close();
				}
				newTransaction.close();
			}
		}
		return bean1;
	}

	/**
	 * Title: getRandomValue Description: 获取红包随机金额
	 * 
	 * @param list
	 * @return
	 */
	public static double getRandomValue(List<TccCoinConfigBean> list) {
		double result = 0;
		double tempNum = Math.random();
		double temp = 0;
		for (TccCoinConfigBean tempBean : list) {
			if (tempNum >= temp && tempNum < temp + tempBean.getPecentValue()) {
				result = Float.parseFloat(tempBean.getValue());
				break;
			}
			temp += tempBean.getPecentValue();
		}
		return result;
	}

	/**
	 * 获取抢到的红包记录 Title: getCoinGrapList Description:
	 * 
	 * @return
	 */
	public JsonList<TpaUserCoinDetailBean> getCoinGrapList(String phoneNumber,
			int pi, int ps) {
		SqlSession session = null;
		List<TpaUserCoinDetailBean> list = null;
		JsonList<TpaUserCoinDetailBean> result = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.getCoinGrapList",
					phoneNumber);
			result = GetPagedEntity(list, pi, ps);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/**
	 * 获取红包的使用记录 Title: getCoinUsedList Description:
	 * 
	 * @return
	 */
	public JsonList<TpaUserCoinDetailBean> getCoinUsedList(String phoneNumber,
			int pi, int ps) {
		SqlSession session = null;
		List<TpaUserCoinDetailBean> list = null;
		JsonList<TpaUserCoinDetailBean> result = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.getCoinUsedList",
					phoneNumber);
			result = GetPagedEntity(list, pi, ps);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/**
	 * 获取过期红包的记录 Title: getCoinOutOfDateList Description:
	 * 
	 * @return
	 */
	public JsonList<TpaUserCoinDetailBean> getCoinOutOfDateList(
			String phoneNumber, int pi, int ps) {
		SqlSession session = null;
		List<TpaUserCoinDetailBean> list = null;
		JsonList<TpaUserCoinDetailBean> result = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.getCoinOutOfDateList",
					phoneNumber);
			result = GetPagedEntity(list, pi, ps);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/**
	 * 获取过期红包金额 Title: getOutOfDateCoin Description:
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public double getOutOfDateCoin(String phoneNumber) {
		SqlSession session = null;
		double result = 0;
		try {
			session = this.getSession();
			result = session.selectOne("com.jujin.mapper.getOutOfDateCoin",
					phoneNumber);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/**
	 * Title: getValidCoinToUse Description:查询当前标投资金额下的可用红包金额
	 * 
	 * @param 参数
	 *            ： phoneNumber：当前投标用户的电话 borrowId：标的id validAmount：用户有效投资金额
	 * @return double
	 */
	public double getValidCoinToUse(String phoneNumber, String borrowId,
			double validAmount) {
		SqlSession session = null;
		double result = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("BORROW_ID", borrowId);
		param.put("VALID_AMOUNT", validAmount);
		try {
			session = this.getSession();
			result = session.selectOne("com.jujin.mapper.getValidCoinToUse",
					param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/**
	 * Title: getTpaUserCoinDetailByPhoneNumberAndBorrowID Description:
	 * 查询指定标ID和指定电话下支出且未用的红包流水
	 * 
	 * @param phoneNumber
	 * @param borrowId
	 * @return
	 */
	public TpaUserCoinDetailBean getTpaUserCoinDetailByPhoneNumberAndBorrowID(
			String phoneNumber, String borrowId) {
		SqlSession session = null;
		TpaUserCoinDetailBean bean = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("BORROW_ID", borrowId);
		try {
			session = this.getSession();
			bean = session
					.selectOne(
							"com.jujin.mapper.getTpaUserCoinDetailByPhoneNumberAndBorrowID",
							param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * Title: getTpaUserCoinDetailRecordId Description:根据recordID获取红包流水
	 * 
	 * @param recordId
	 * @return
	 */
	public TpaUserCoinDetailBean getTpaUserCoinDetailRecordId(int recordId) {
		SqlSession session = null;
		TpaUserCoinDetailBean bean = null;
		try {
			session = this.getSession();
			bean = session.selectOne(
					"com.jujin.mapper.getTpaUserCoinDetailRecordId", recordId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * 获取分享的红包机会（若此红包系列下已有过分享则返回之前的红包机会，若无则返回新的） Title: getShareChance
	 * Description:
	 * 
	 * @param phoneNumber
	 * @param recordId
	 * @return
	 */
	public TpaCoinChanceBean getShareChance(String phoneNumber, int recordId) {
		SqlSession session = null;
		TpaCoinChanceBean bean = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("RECORD_ID", recordId);
		try {
			session = this.getSession();
			bean = session.selectOne("com.jujin.mapper.getShareChance", param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * Title: insertChanceByRoot Description: 分享的红包机会信息插入
	 * 
	 * @param bean
	 */
	public void insertChanceByRoot(int recordId, String phoneNumber) {
		SqlSession session = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("RECORD_ID", recordId);
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.insertChanceByRoot", param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	// **************************以下为新增*******************************//
	/**
	 * Title: getAllTpaUserCoin Description:获取所有红包信息
	 * 
	 * @return
	 */
	public List<TpaUserCoinBean> getAllTpaUserCoin() {

		SqlSession session = null;
		List<TpaUserCoinBean> list = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.getAllTpaUserCoin");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * 跑定时任务，用于处理过期的红包 如果活动过期查询过期的红包金额，修改红包表的金额总数，红包流水表增加一条记录（type=3过期） Title:
	 * runTimerRask Description:
	 * 
	 * @throws SQLException
	 */
	public void runTimerRask() throws SQLException {
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		newTransaction = null;
		try {
			session = this.getSession(false);
			newTransaction = transactionFactory.newTransaction(session
					.getConnection());

			List<TpaUserCoinBean> list = getAllTpaUserCoin();
			for (TpaUserCoinBean bean : list) {
				double result = getOutOfDateCoin(bean.getPhoneNumber());
				if (result > 0) {
					TpaUserCoinBean coinBean = getTpaUserCoinByPhoneNumber(bean
							.getPhoneNumber());
					coinBean.setAmount(coinBean.getAmount() - result);
					session.update("com.jujin.mapper.updateTpaUserCoin",
							coinBean);// 更新红包表数据
					TpaUserCoinDetailBean detailBean = new TpaUserCoinDetailBean();
					detailBean.setPhoneNumber(bean.getPhoneNumber());
					detailBean.setBpFlg(2);// 2：过期
					detailBean.setUserId(bean.getUserId());
					detailBean.setAmount(result);
					detailBean.setMemo("过期红包");
					session.insert("com.jujin.mapper.insertTpaUserCoinDetail",
							detailBean);
				}
			}
			newTransaction.commit();
		} catch (Exception e) {
			newTransaction.rollback();
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		newTransaction.close();
	}

	/**
	 * Title: grabCoin Description: 获取抢到的红包
	 */
	public TpaUserCoinDetailBean getGrapedTpaUserCoinDetail(int recordId,
			String phoneNumber) {
		SqlSession session = null;
		TpaUserCoinDetailBean bean = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("RECORD_ID", recordId);
		try {
			session = this.getSession();
			bean = session.selectOne(
					"com.jujin.mapper.getGrapedTpaUserCoinDetail", param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}
	
	/**
	 * Title: getGrapedTpaUserCoinDetail1 Description: 获取抢到的红包
	 */
	public TpaUserCoinDetailBean queryGrapedTpaUserCoinDetail(int recordId,
			String phoneNumber) {
		SqlSession session = null;
		TpaUserCoinDetailBean bean = null;
		List<TpaUserCoinDetailBean> list = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("PHONE_NUMBER", phoneNumber);
		param.put("RECORD_ID", recordId);
		try {
			session = this.getSession();
			list = session.selectList(
					"com.jujin.mapper.queryGrapedTpaUserCoinDetail", param);
			if(list.size()>0)
			{
				bean = list.get(0);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * Title: getChanceByPnAndAi Description: 根据电话和活动ID查询红包机会
	 * 
	 * @param param
	 */
	public TpaCoinChanceBean getChanceByBiAndAi(String borrowId, int activityId) {
		SqlSession session = null;
		TpaCoinChanceBean bean = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("borrowId", borrowId);
		param.put("activityId", activityId);
		try {
			session = this.getSession();
			bean = session.selectOne("com.jujin.mapper.getChanceByPnAndAi",
					param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}

	/**
	 * add by wangning
	 * 
	 * @param borrowId
	 * @param activityId
	 * @return
	 */
	public List<TpaCoinChanceBean> getChanceByBiAndAis(String borrowId,
			int activityId) {
		SqlSession session = null;
		List<TpaCoinChanceBean> beans = null;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("borrowId", borrowId);
		param.put("activityId", activityId);
		try {
			session = this.getSession();
			beans = session.selectList("com.jujin.mapper.getChanceByPnAndAi",
					param);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return beans;
	}

	/**
	 * Title: insertTpaCoinChance Description: 红包机会信息插入
	 * 
	 * @param bean
	 */
	public void insertTpaCoinChanceByBiAndAi(String borrowId, int activityId) {
		SqlSession session = null;
		List<TpaCoinChanceBean> beans = null;
		try {
			session = this.getSession(true);
			beans = getChanceByBiAndAis(borrowId, activityId);
			if (beans != null && beans.size() > 0) {
				for (TpaCoinChanceBean bean : beans) {
					session.insert("com.jujin.mapper.insertTpaCoinChance", bean);
				}
			} else {
				logger.error("红包机会生成失败！");
			}
			List<TpaCoinChanceBean> grabCoinChanceBeans = session.selectList(
					"com.jujin.mapper.getChanceByActivity",
					String.valueOf(activityId));

			// 系统自动抢一次红包
			if (grabCoinChanceBeans != null & grabCoinChanceBeans.size() > 0) {

				for (TpaCoinChanceBean bean : grabCoinChanceBeans) {
					grabCoin(bean.getRecordId(), bean.getPhoneNumber());
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}

	/**
	 * 查询红包机会
	 * 
	 * @param userId
	 * @param phoneNumber
	 */
	public JsonList<CoinChange> QueryCoinChance(String userId,
			String phoneNumber, int pi, int ps) {
		SqlSession session = null;
		JsonList<CoinChange> result = null;
		try {
			session = getSession();
			HashMap<String, String> map = new HashMap<String, String>();

			if (!StringUtils.isEmpty(userId)) {
				map.put("USER_ID", userId);
			}
			if (!StringUtils.isEmpty(phoneNumber)) {
				map.put("PHONE_NUMBER", phoneNumber);
			}
			List<CoinChange> list = session.selectList(
					"com.jujin.mapper.QueryCoinChance", map);

			result = GetPagedEntity(list, pi, ps);

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;

	}

	public OpEntityResult<CoinChange> QueryCoinChanceByRoot(int id,
			String phoneNumber) {

		SqlSession session = null;
		OpEntityResult<CoinChange> result = null;
		try {
			session = getSession();
			HashMap<String, String> map = new HashMap<String, String>();

			map.put("RECORD_ID", String.valueOf(id));
			map.put("PHONE_NUMBER", phoneNumber);

			CoinChange entity = session.selectOne(
					"com.jujin.mapper.QueryCoinChanceByRoot", map);
			
			logger.info(String.format("CoinChange [%s]",entity.toString()));

			double totalAmount=0;
			Object amountObj=session.selectOne("com.jujin.mapper.getTpaUserCoinDetailAmount", map);
			if(amountObj!=null)
			{
				totalAmount=(double)amountObj;
			}
			if(null!=entity)
			{
				entity.setGetAmount(totalAmount);
			}
			
			result = new OpEntityResult<CoinChange>(entity);
			result.setStatus(true);
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			result = new OpEntityResult<CoinChange>(null);
			result.setStatus(true);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return result;
	}
	
	
	
	public String QueryUserCoin(String phoneNumber)
	{
		SqlSession session = null;
		String result="";
		try {
			session = getSession();
			
			result= session.selectOne(
					"com.jujin.mapper.QueryUserCoin", phoneNumber);
		}
		catch(Exception ex)
		{
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		finally
		{
			if(session != null){
				session.close();
			}
		}
		return result;
	}

	public void notifyUser(String borrowId, int activityId) {

		SqlSession session = null;
		List<NotifyBean> beans = null;
		try {
			session = getSession();

			Map<String, String> param = new HashMap<String, String>();
			param.put("ACTIVITY_ID", String.valueOf(activityId));
			param.put("BORROW_ID", borrowId);

			beans = session.selectList("com.jujin.mapper.getNotifyBean", param);

			TccActivityBean activityBean = session
					.selectOne("com.jujin.mapper.getTccActivityBeanByRecordId",
							activityId);

			for (NotifyBean bean : beans) {
				String phoneNumber = bean.getPhoneNumber();
				String amount = bean.getAmount();
				String userId = bean.getUserId();

				logger.info(String.format(
						"phoneNumber [%s] amount [%s] userId [%s]",
						phoneNumber, amount, userId));

				SendSmsBean smsBean = new SendSmsBean();
				smsBean.setSendType(SendTypeEnum.MSG);
				smsBean.setSmsType(SmsTypeEnum.SMS_ANNIVERSARY_NOTIFY);
				smsBean.setPhoneNumber(phoneNumber);

				smsBean.setUserId(userId);
				smsBean.setTime(activityBean.getName());
				smsBean.setBorrowTitle(amount);
				smsBean.setVerifyCode("http://dwz.cn/SxhiL");

				sendMobileMessage(smsBean);
			}

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
