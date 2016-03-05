package com.jujin.biz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.util.StringUtils;

import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.entity.account.Message;
import com.jujin.entity.coin.TpaUserCoinBean;
import com.jujin.entity.coin.TpaUserCoinDetailBean;
import com.jujin.entity.sign.SignConfBean;
import com.jujin.entity.sign.SignDetailBean;
import com.jujin.entity.sign.SignResult;
import com.wicket.loan.common.utils.NumberUtils;

/**
 * Title: SignBiz 
 * Description: 签到 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年11月3日
 */
public class SignBiz extends JujinBaseBiz {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * Title: sendSms Description: 发送短信通知
	 * 
	 * @param userId
	 * @param phoneNumber
	 * @param coin
	 */
	private void sendSms(String userId, String phoneNumber, String coin) {
		try {
			SendSmsBean msgBean = new SendSmsBean();
			msgBean.setPhoneNumber(phoneNumber);
			msgBean.setSendType(SendTypeEnum.MSG);
			msgBean.setBorrowTitle("");
			msgBean.setMoney("");
			msgBean.setUserId(userId);
			msgBean.setSmsType(SmsTypeEnum.SMS_JUJIN_SIGN_NOTIFY);
			msgBean.setParam1(coin);
			logger.info(String.format("Send Msg For [%s]", phoneNumber));
			sendMobileMessage(msgBean);
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	/**
	 * 
	* Title: getSignConf
	* Description: 获取签到配置
	* @return
	 */
	public List<SignConfBean> getSignConf() {
		List<SignConfBean> list = new ArrayList<SignConfBean>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.sign.mapper.getSignConf");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * 
	* Title: saveSignConf
	* Description: 保存签到配置
	* @param confMap
	* @return
	* @throws SQLException
	 */
	public OpEntityResult<Integer> saveSignConf(Map<String, String> confMap) throws SQLException {
		OpEntityResult<Integer> result = new OpEntityResult<Integer>(0);
		SqlSession session = null;
		int rs = 0;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction newTransaction = null;
		try {
			session = getSession(false);
			newTransaction = transactionFactory.newTransaction(session.getConnection());
			List<SignConfBean> list = MapToList(confMap);
			if(list.size()>0) {
				session.delete("com.jujin.sign.mapper.clearSignConf"); 
				rs = session.insert("com.jujin.sign.mapper.saveSignConf",list);
			}
			newTransaction.commit();
			result.setEntity(rs);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			newTransaction.rollback();
			result.setStatus(false);
		} finally {
			if (session != null){
				session.close();
			}
			if(newTransaction != null){
				newTransaction.close();
			}
		}
		return result;
		
	}

	private List<SignConfBean> MapToList(Map<String, String> confMap) {
		List<SignConfBean> list = new ArrayList<SignConfBean>();
		Set<String> keySet = confMap.keySet();
		Iterator<String> it = keySet.iterator();
		while(it.hasNext()){
			String key = it.next();
			String value = confMap.get(key);
			SignConfBean scb = new SignConfBean();
			if(key.equals("expiryDate")){
				scb.setDescription("聚金币有效期");
				scb.setKeyName("聚金币有效期");
				scb.setName("EXPIRY_DATED");
				scb.setValue(value);
			}else{
				scb.setDescription("连续签到第"+key+"天奖励规则");
				scb.setKeyName("第"+key+"天");
				scb.setName("DAY"+key);
				scb.setValue(value);
			}
			list.add(scb);
		}
		return list;
	}

	/**
	 * 
	* Title: getSignDetail
	* Description: 获取最近10次签到流水
	* @param userId
	* @return
	 */
	public List<SignDetailBean> getSignDetail(String userId) {
		List<SignDetailBean> list = new ArrayList<SignDetailBean>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList("com.jujin.sign.mapper.getAllSignDetail",userId);
			for(SignDetailBean sdb : list){
				String reward = sdb.getReward();
				sdb.setReward(NumberUtils.moneyFormat(reward));
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	/**
	 * 签到
	* Title: signToday
	* Description: 
	* @param userId
	* @param reward
	* @param phoneNumber
	* @return
	* @throws SQLException
	 */
	public OpEntityResult<SignResult> signToday(String userId,String reward,String phoneNumber) throws SQLException {
		OpEntityResult<SignResult> result = new OpEntityResult<SignResult>(null);
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction newTransaction = null;
		try {
			session = getSession(false);
			newTransaction = transactionFactory.newTransaction(session.getConnection());
			// 发放聚金币
			TpaUserCoinBean coinBean = session.selectOne("com.jujin.sign.mapper.getTpaUserCoinByUserId", userId);
			if(coinBean != null){
				double amount = coinBean.getAmount();
				coinBean.setAmount(amount + Double.valueOf(reward));
				session.update("com.jujin.sign.mapper.updateTpaUserCoin", coinBean);
			}else{
				coinBean = new TpaUserCoinBean();
				coinBean.setAmount(Double.valueOf(reward));
				coinBean.setFrost(0);
				coinBean.setUserId(userId);
				coinBean.setPhoneNumber(phoneNumber);
				session.insert("com.jujin.sign.mapper.insertTpaUserCoin", coinBean);
			}
			// 4.保存聚金币发放流水
			TpaUserCoinDetailBean coinDetailBean = new TpaUserCoinDetailBean();
			coinDetailBean.setPhoneNumber(phoneNumber);
			coinDetailBean.setUserId(userId);
			coinDetailBean.setAmount(Double.valueOf(reward));
			coinDetailBean.setMemo("签到成功获取聚金币"+reward+"元");
			session.insert("com.jujin.sign.mapper.insertTpaUserCoinDetail", coinDetailBean);
			// 5.短信通知用户
			if(!StringUtils.isEmpty(phoneNumber)){
				sendSms(userId, phoneNumber, reward);
			}
			
			SignResult sr=new SignResult();
			sr.setResult(reward);
		
			result.setEntity(sr);
			result.setStatus(true);
			newTransaction.commit();
		} catch (Exception ex) {
			result.setStatus(false);
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			newTransaction.rollback();
		} finally {
			if (session != null){
				session.close();
			}
			if(newTransaction != null){
				newTransaction.close();
			}
		}
		return result;
	}
	
	/**
	 * 获取用户名
	* Title: getUserIdByToken
	* Description: 
	* @param token
	* @return
	 */
	public String getUserIdByToken(String token){
		String userId = null;
		SqlSession session = null;
		try {
			session = getSession();
			userId = session.selectOne("com.jujin.sign.mapper.getUserId",token);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return userId;
	}

	/**
	 * 分页
	* Title: pagination
	* Description: 
	* @param userId
	* @param pageIndex
	* @param pageSize
	* @return
	 */
	public JsonList<SignDetailBean> pagination(String userId, int pageIndex, int pageSize) {
		JsonList<SignDetailBean> list = new JsonList<SignDetailBean>();
		SqlSession session = null;
		try {
			session = this.getSession();
			List<SignDetailBean> details = session.selectList("com.jujin.sign.mapper.getAllSignDetail", userId);
			for(SignDetailBean sdb : details){
				String reward = sdb.getReward();
				sdb.setReward(NumberUtils.moneyFormat(reward));
			}
			list = GetPagedEntity(details, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}
}
