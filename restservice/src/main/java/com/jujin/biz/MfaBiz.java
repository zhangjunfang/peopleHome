/**   
 * @author wangning
 * @date 2015年2月12日 上午9:26:01 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.biz;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.jujin.common.ExceptionHelper;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.util.xglc.CommonUtil;


/**
 * 
* <p>Title: MfaBiz.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: jujinziben</p>
* @author zhengshaoxu
* @date 2015年12月24日
* @version 1.0
 */
public class MfaBiz extends BaseBiz {

	public boolean bindUserInfo(RegisterEntity entity,String type)  {
		boolean boo = false;
		SqlSession session = null;
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Transaction newTransaction = null;
		try {
			session = getSession(false);
			newTransaction = transactionFactory.newTransaction(session.getConnection());
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", entity.getUserName());
			map.put("platCode", entity.getChannel());
			map.put("bindType", type);
			map.put("platToken", CommonUtil.getUUID());
			map.put("insDate", CommonUtil.dateToString(new Date()));
			map.put("platUsername", entity.getPlatUsername());
			int	rs = session.insert("com.jujin.mfa.mapper.bindUserInfo",map);
			newTransaction.commit();
			boo = rs == 1?true:false;
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			try {
				newTransaction.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (session != null){
				session.close();
			}
			if(newTransaction != null){
				try {
					newTransaction.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return boo;
	}

	
	
}
