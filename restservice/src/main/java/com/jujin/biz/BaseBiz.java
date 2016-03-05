package com.jujin.biz;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sms.main.SendSmsMessage;
import net.sms.main.bean.SendSmsBean;
import net.sms.main.enums.SendTypeEnum;
import net.sms.main.enums.SmsTypeEnum;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citic.risk.entity.User;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.Account;
import com.pro.common.util.StringUtils;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import com.wicket.loan.common.utils.NumberUtils;

public abstract class BaseBiz extends JujinBaseBiz {

	protected SqlSessionFactory sqlSessionFactory;

	public static final ExecutorService Pool = Executors.newCachedThreadPool();
	/** 日志实例 */
	protected static final Logger logger = Logger.getLogger(BaseBiz.class);

	protected Object lockObject = new Object();

	public BaseBiz() {
		try {
			InitIbatis();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void InitIbatis() throws IOException {
		String resource = "mybatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public WxBindBean QueryWxBindBean(String openId) {
		SqlSession session = null;
		WxBindBean bean = null;
		try {
			session = this.getSession();
			bean = session.selectOne(
					"com.jujin.mapper.QueryUserWeiXinBindInfo", openId);
		} finally {
			if (session != null)
				session.close();
		}

		return bean;
	}
	
	
	
	/**
	 * 内部分页时使用该方法
	 * 
	 * @param entities
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	protected <Entity> List<Entity> GetListPagedEntity(List<Entity> entities,
			int pageIndex, int pageSize) throws Exception {

		if (entities == null)
			return new ArrayList<Entity>();

		List<Entity> result = new ArrayList<Entity>();
		int startIndex = (pageIndex - 1) * pageSize;
		if (pageIndex <= 0) {
			throw new Exception("页数必须大于0");
		} else if (pageSize <= 0) {
			throw new Exception("每页显示数量必须大于0");
		} else if (startIndex > entities.size()) {
			throw new Exception("请求的数量超过总数");
		} else {
			int startPos = (pageIndex - 1) * pageSize;
			int rows = startPos + pageSize;

			System.out.println("startPos:" + startPos);
			System.out.println("rows:" + rows);
			for (; startPos < rows; startPos++) {
				result.add(entities.get(startPos));
			}
		}
		return result;
	}

	/**
	 * @return
	 */
	protected SqlSession getSession() {
		return getSession(false);
	}

	protected SqlSession getSession(boolean autoCommit) {

		if (sqlSessionFactory == null) {
			try {
				InitIbatis();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return sqlSessionFactory.openSession(autoCommit);
	}

	public boolean sendMobileMessage(SendSmsBean bean) {
		return SendSmsMessage.makeSmsMessage(bean);
	}

	public boolean sendMobileMessage(String userId, String phoneNumber,
			String contents, String money, SendTypeEnum sendType,
			SmsTypeEnum smsType) {
		SendSmsBean bean = new SendSmsBean();
		bean.setUserId(userId);
		bean.setPhoneNumber(phoneNumber);
		bean.setSendType(sendType);
		bean.setSmsType(smsType);
		bean.setContents(contents);
		bean.setMoney(NumberUtils.moneyFormat(StringUtils.objToString(money)));
		return SendSmsMessage.makeSmsMessage(bean);
	}

	public boolean sendMobileMessage(String userId, String phoneNumber,
			String contents, SendTypeEnum sendType, SmsTypeEnum smsType) {
		return sendMobileMessage(userId, phoneNumber, contents, "0", sendType,
				smsType);
	}

	public boolean sendMobileMessage(String userId, String phoneNumber,
			String contents, SmsTypeEnum smsType) {
		return sendMobileMessage(userId, phoneNumber, contents, "0",
				SendTypeEnum.MSG, smsType);
	}

	public boolean sendMobileMessage(String userId, String phoneNumber,
			SmsTypeEnum smsType) {
		return sendMobileMessage(userId, phoneNumber, "", "0",
				SendTypeEnum.MSG, smsType);
	}

}
