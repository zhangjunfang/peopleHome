package com.jujin.biz.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.Session;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.BaseBiz;
import com.jujin.common.Constants;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.common.SystemConfig;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.auto.AutoInvestBean;
import com.jujin.entity.auto.AutoInvestRecord;
import com.jujin.entity.auto.AutoInvestSetting;
import com.jujin.entity.borrow.Borrow;
import com.jujin.entity.security.ClientTicket;
import com.jujin.utils.Toolkit;
import com.pro.common.util.DesCodeUtil;

public class SecurityBiz extends BaseBiz {

	/* 移动端登录标志 */
	public RegisterEntity QueryMobileTicket(String ticket) {
		SqlSession session = null;
		RegisterEntity bean = null;

		try {
			session = this.getSession(true);

			bean = session
					.selectOne("com.jujin.mapper.QueryUserTicket", ticket);
			if (bean != null&&bean.getIscheck()==1) {
				// 更新时间
				session.insert("com.jujin.mapper.InsertUserTicket", ticket);

				String newTicket = Toolkit.getUUID();
				Map<String, String> map = new HashMap<>();
				map.put("TICKETNEW", newTicket);
				map.put("TICKET", ticket);

				bean.setValidateCode(newTicket);
				session.update("com.jujin.mapper.UpdateUserTicket", map);
				session.commit();
			}
			else
			{
				logger.info(String.format("Ticket:%s 尚在有效期，暂不更新", ticket));
			}
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			session.rollback();
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return bean;
	}

	public String GenMobileTicket(String userId) {
		SqlSession session = null;
		String newTicket="";

		try {
			session = this.getSession(true);
			newTicket= DesCodeUtil.encrypt(Toolkit.getUUID());
		
			Map<String, String> map = new HashMap<>();
			map.put("USERID", userId);
			map.put("TICKET", newTicket);
			
			// 更新时间
			session.insert("com.jujin.mapper.InsertNewUserTicketLog", map);
			ClientTicket clientTicket= session.selectOne("com.jujin.mapper.QueryNewUserTicket", userId);
			if(null!=clientTicket)
			{
				session.update("com.jujin.mapper.UpdateUserTicketByUser", map);
			}
			else {
				session.insert("com.jujin.mapper.InsertNewUserTicket", map);
			}
			session.commit();
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			session.rollback();
		} finally {
			if (null != session) {
				session.close();
			}
		}
		return newTicket;
	}
}
