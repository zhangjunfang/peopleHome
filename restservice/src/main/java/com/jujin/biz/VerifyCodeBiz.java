package com.jujin.biz;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.utils.ExceptionHelper;
import com.pro.common.util.StringUtils;

public class VerifyCodeBiz extends BaseBiz {

	public String getUserMobile(String userId) {

		SqlSession session = null;
		String mobile = "";
		try {
			session = this.getSession();
			mobile = session.selectOne("com.jujin.mapper.QueryUserMobile",
					userId);
		} finally {
			if (session != null)
				session.close();
		}
		return mobile;
	}

	public List<String> getUserByMobile(String tel) {
		SqlSession session = null;
		List<String> mobiles = null;
		try {
			session = this.getSession();
			mobiles = session.selectList(
					"com.jujin.mapper.QueryUserIdByMobile", tel);
		} finally {
			if (session != null)
				session.close();
		}
		return mobiles;
	}

	public List<String> getUserCodeByTel(String tel) {
		SqlSession session = null;
		List<String> mobiles = null;
		try {
			session = this.getSession();
			mobiles = session
					.selectList("com.jujin.mapper.QueryCodeByTel", tel);
		} finally {
			if (session != null)
				session.close();
		}
		return mobiles;
	}

	// QueryCanSendSms
	/**
	 * 
	 * 是否可发送短信
	 * 
	 * @param tel
	 * @return
	 */
	public boolean canSendSms(String tel) {
		SqlSession session = null;
		boolean result = true;
		try {
			session = this.getSession();
			String status = session.selectOne(
					"com.jujin.mapper.QueryCanSendSms", tel);
			if (StringUtils.isEmpty(status) || "0".equals(status)) {
				result = false;
			}
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public void addSmsVerify(String tel, String verifyCode) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("PHONE_NUMBER", tel);
		map.put("VERIFY_CODE", verifyCode);

		SqlSession session = null;
		try {
			session = getSession(true);
			session.insert("com.jujin.mapper.InsertSmsVerify", map);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if(session != null){
				session.close();
			}
		}
	}

	public String querySmsAddress() {
		SqlSession session = null;
		String result = "";
		try {
			session = getSession();
			result = session.selectOne("com.jujin.mapper.QuerySmsAddress", "");
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if(session != null){
				session.close();
			}
		}
		return result;
	}

	public boolean checkUserById(String id) {
		
		SqlSession session = null;
		String result = "";
		try {
			session = getSession();
			result = session.selectOne("com.jujin.mapper.CheckUserById", id);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if(session != null){
				session.close();
			}
		}
		return Integer.valueOf(result)>0;
	}

}
