package com.jujin.biz;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.feed.FeedBack;
import com.jujin.entity.feed.FeedType;

public class FeedbackBiz extends BaseBiz{
	
	public  JsonList<FeedType> QueryUserFeedType(){
		SqlSession session = null;
		List<FeedType> entities = null;
		JsonList<FeedType> result=null;
		try{
			session = this.getSession();
			entities=session.selectList("com.jujin.mapper.QueryUserFeedType");
			result=new JsonList<FeedType>();
			result.addRange(entities);
		}
		catch(Exception ex)
		{
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		finally{
			if(session!=null){
				session.close();
			}
		}
		return result;
	}
	
	/*用户反馈列表*/
	public JsonList<FeedBack> QuerygetFeedByUser(String userid,int pi,int px){
		JsonList<FeedBack> list = null;
		SqlSession session = null;
		try{
			session = this.getSession();
			List<FeedBack> logs = session.selectList("com.jujin.mapper.QuerygetFeedByUser",userid);
			list = GetPagedEntity(logs, pi, px);
		}finally{
			if(session!=null)
				session.close();
		}
		return list;
	}
	
	public void addFeedback(FeedBack feedItem){
	 
		SqlSession session = null;
		try{
			
			session = getSession(true);
			logger.info("新增");
			session.insert("com.jujin.mapper.InsertUserfeedback",feedItem);
			logger.info("提交");
			session.commit();
			
		}catch(Exception e){
			throw e;
		}finally{
			if(session!=null)
			{
				session.rollback();
				session.close();
			}
		}
	}
}
