package com.jujin.biz;

import java.sql.SQLException;
import java.util.List;

import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.jujin.entity.CurrentActivityBean;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.FWBeanManager;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.wicket.loan.common.utils.UserUtils;
import com.wicket.loan.web.borrow.information.mediator.BorrowTenderMediator;
import com.wicket.loan.web.common.bean.CommonUsersOperationAndMsgBean;

public class ActivityBiz extends BaseBiz {

	
	BorrowTenderMediator mediator=FWBeanManager
			.getBean(BorrowTenderMediator.class);
	
	public void grantCoin() throws SQLException {
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction newTransaction = null;

		try {

			session = getSession(false);
			newTransaction = transactionFactory.newTransaction(session
					.getConnection());

			session.insert("com.jujin.mapper.clearTmpUserCoin");
			session.insert("com.jujin.mapper.insertTmpUserCoin");
			session.update("com.jujin.mapper.grantUserCoin");
			session.insert("com.jujin.mapper.grantNewUser");
			session.insert("com.jujin.mapper.insertDetail");

			List<CurrentActivityBean> beans = session
					.selectList("com.jujin.mapper.queryUserCoin");

			
			sendSms(beans,0);
			
			
			newTransaction.commit();
		} catch (Exception ex) {

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

	}

	private void sendSms(List<CurrentActivityBean> beans, int type) {
		try {
			if (beans == null || beans.size() < 1)
				return;
			for(CurrentActivityBean bean:beans)
			{
				SendSmsBean msgBean=new SendSmsBean();
				msgBean.setPhoneNumber(bean.getPhoneNumber());
				msgBean.setSendType(SendTypeEnum.MSG);
				msgBean.setBorrowTitle("");
				msgBean.setMoney("");
				msgBean.setUserId(bean.getUserId());
				
				if(type==0)
				{
					msgBean.setSmsType(SmsTypeEnum.SMS_JUJIN_COIN_NOTIFY);	
				}
				else if(type==1)
				{
					msgBean.setSmsType(SmsTypeEnum.SMS_JUJIN_VIP_NOTIFY);	
				}
				else
				{
					return;
				}
				logger.info(String.format("Send Msg For [%s]",bean.getPhoneNumber()));
				sendMobileMessage(msgBean);
				
				
				
				if(UserUtils.checkEmail(bean.getMailAddress()))
				{
					CommonUsersOperationAndMsgBean comBean=new CommonUsersOperationAndMsgBean();
					comBean.setLoginIp("127.0.0.1");
					comBean.setOperating("T");
					comBean.setOperatorUserId(bean.getUserId());
					comBean.setUserId(bean.getUserId());
					
					
					if(type==0)
					{
						comBean.setOperationCode("401");
						comBean.setContent("感谢您参加聚金资本\"在线破亿\"活动，投资赠送168聚金币已到账，请登录平台查看，祝您投资愉快。");
						comBean.setTitle("聚金币到账通知");
					}
					else
					{
						comBean.setOperationCode("402");
						comBean.setContent("感谢您参加聚金资本\"在线破亿\"活动，满足VIP赠送资格，已获得6个月VIP，请登录平台查看，祝您投资愉快。");
						comBean.setTitle("获取VIP资格通知");
					}
					//mediator.insertUserOperationAndMsgLog(comBean);
				}
			
			}

		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	public void grantVip() throws SQLException {
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction newTransaction = null;
 
		
		try {
			
			session = getSession(false);
			newTransaction = transactionFactory.newTransaction(session
					.getConnection());

			session.insert("com.jujin.mapper.clearTmpUserVip");
			session.insert("com.jujin.mapper.insertTmpUserVip");
			session.update("com.jujin.mapper.grantUserVip1");
			session.update("com.jujin.mapper.grantUserVip2");
			session.insert("com.jujin.mapper.grantNewVip");
			session.insert("com.jujin.mapper.insertVipDetail");
			
	
			List<CurrentActivityBean> beans = session
					.selectList("com.jujin.mapper.queryUserVIP");

			
			sendSms(beans,1);
			newTransaction.commit();	
		} catch (Exception ex) {

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
	}

	public void grantCoin(String userId) {
		SqlSession session = null;
		try {

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}

	}

	public void grantVip(String userId) {
		SqlSession session = null;
		try {

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
	}
	
	
	public void notifyVip()
	{
		SqlSession session = null;
		try	
		{
			session=this.getSession();
			List<CurrentActivityBean> beans = session
					.selectList("com.jujin.mapper.queryUserTmpVIP");
			
			sendSms(beans,1);
			
		}
		catch(Exception ex)
		{
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		
	}
	
	
	
	

}
