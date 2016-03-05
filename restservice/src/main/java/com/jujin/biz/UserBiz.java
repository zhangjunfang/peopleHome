package com.jujin.biz;

import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.ExceptionHelper;
import com.jujin.entity.WxBindBean;
import com.jujin.entity.account.RegisterEntity;
import com.jujin.entity.user.UserFriendInterestRank;
import com.jujin.entity.xglc.XglcUser;

public class UserBiz extends BaseBiz {

	/** serialVersionUID*/
	private static final long serialVersionUID = 6391030655127159744L;

	static String[] NUMBERS = { "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9" };

	static String[] CHARS = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "E", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public void InsertWxBindBean(WxBindBean bean) {

		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.InsertWxBindBean", bean);
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void UpdateWxBindBean(WxBindBean bean) {

		SqlSession session = null;
		try {
			session = this.getSession(true);
			session.update("com.jujin.mapper.UpdateWxBindBean", bean);
		} finally {
			if (session != null)
				session.close();
		}
	}
	
	public int GetCurrentUser()
	{
		int result=0;
		SqlSession session = null;
		try {
			session = this.getSession();
			result=session.selectOne("com.jujin.mapper.QueryCurrentUser");
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public RegisterEntity QueryUserPwd(String userId) {

		SqlSession session = null;
		RegisterEntity entity = null;
		try {
			session = this.getSession();
			entity = session.selectOne("com.jujin.mapper.QueryUserPwd", userId);
		} finally {
			if (session != null)
				session.close();
		}
		return entity;
	}

	public List<UserFriendInterestRank> QueryUserFriendInterestRank(
			String userId) {

		SqlSession session = null;
		List<UserFriendInterestRank> list = null;
		try {
			session = this.getSession();
			list = session.selectList("com.jujin.mapper.QueryUserFriendRank",
					userId);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	public String getUserIdFromInvite(String inviteId) {

		SqlSession session = null;
		try {
			session = this.getSession();
			List<String> list = session.selectList(
					"com.jujin.mapper.QueryUserIdByInviteId", inviteId);

			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return "";
		} finally {
			if (session != null)
				session.close();
		}

	}

	/**
	 * 
	 * 根据手机号生成用户的唯一ID
	 * 
	 * @param tel
	 * @return
	 */
	public String GetSingleUserId(String tel) {

		SqlSession session = null;
		try {
			session = this.getSession();
			List<String> list = session.selectList(
					"com.jujin.mapper.QueryUserLikeId", tel);
			if (list == null || list.size() < 1) {
				return tel;
			}
			Random random = new Random();

			// 有重复的用户名
			String userId = tel + "_";

			while (true) {
				String tmp = "";
				if (random.nextDouble() >= 0.5) {
					tmp = NUMBERS[random.nextInt(NUMBERS.length)];
				} else {
					tmp = CHARS[random.nextInt(CHARS.length)];
				}
				userId += tmp;

				Boolean isContains = false;
				for (String item : list) {
					if (userId.equals(item)) {
						isContains = true;
					}
				}
				if (!isContains) {
					return userId;
				}
			}
		} finally {
			if (session != null)
				session.close();
		}
	}

	public boolean AddXglcUser(XglcUser xglcUser) {
		 
		SqlSession session=null;
		boolean result=false;
		try {
			session=this.getSession();
			session.insert("com.jujin.mapper.InsertXglcUser",xglcUser);
			session.commit();
			result=true;
		}  
		catch(Exception ex)
		{
			logger.error(ExceptionHelper.getExceptionDetail(ex));
			if(session!=null)
			{
				session.rollback();
			}
		}
		finally{
			if (session != null){
				session.close();
			}
		}
		return result;
		
	}
}
