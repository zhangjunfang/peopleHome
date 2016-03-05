package com.jujin.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.exam.ExamBean;
import com.jujin.exam.ExamUser;

public class ExamBiz extends BaseBiz {

	public int getPartCount() {

		SqlSession session = null;
		int partCount = 0;
		try {
			session = getSession();
			partCount = session.selectOne("com.jujin.mapper.QueryPartCount");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		return partCount;
	}

	public ExamUser getUserScore(String userId) {
		SqlSession session = null;
		ExamUser user = null;
		try {
			session = getSession();
			user = session.selectOne("com.jujin.mapper.QueryUserScore", userId);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		return user;
	}

	public List<ExamUser> getTopPartUser() {
		SqlSession session = null;
		List<ExamUser> result = null;
		try {
			session = getSession();
			result = session.selectList("com.jujin.mapper.QueryTopPartUser");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		return result;
	}

	public int submitUserScore(ExamUser bean) {
		SqlSession session = null;
		int reward = 0;
		try {
			session = getSession(true);
			int score = bean.getScore();
			session.insert("com.jujin.mapper.InsertExamSum", bean);
			/*
			 * 80 80：5 90：10 100：20
			 */
			if (score > 80 && score <= 90) {
				reward = 5;
			} else if (score >=90 && score < 100) {
				reward = 10;
			} else if (score == 100) {
				reward = 20;
			}

			ExamBean rewardBean = new ExamBean();
			rewardBean.setId(bean.getOpenId());
			rewardBean.setScore(reward);
			session.insert("com.jujin.mapper.InsertExamReward", rewardBean);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		return reward;
	}

	public void shareSuccess(String id) {
		SqlSession session = null;

		try {
			session = getSession(true);

			ExamUser bean = new ExamUser();
			bean.setOpenId(id);
			bean.setShareReward(10);

			session.update("com.jujin.mapper.UpdateShareReward", bean);
			session.update("com.jujin.mapper.UpdateShareStatus", id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(session != null){
				session.close();
			}
		}
	}

}
