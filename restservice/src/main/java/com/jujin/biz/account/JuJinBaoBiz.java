package com.jujin.biz.account;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.BaseBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.borrow.ZhaiQuan;
import com.jujin.entity.interest.InterestBean;
import com.jujin.entity.interest.InterestDetail;

public class JuJinBaoBiz extends BaseBiz {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterestBean getInterestAmount(String userId)
	{
		SqlSession session = null;
		InterestBean result = null;
		try {
			session = this.getSession();
			result = session.selectOne("com.jujin.mapper.getJuJinBaoInterestAmount",
					userId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	 
	public JsonList<InterestDetail>  getJuJinBaoList(String userId,int pi, int ps)
	{
		SqlSession session = null;
		List<InterestDetail> detailList = null;
		JsonList<InterestDetail> result = null;
		try {
			session = this.getSession();
			detailList = session.selectList("com.jujin.mapper.getJuJinBaoInterestList",
					userId);
			result = GetPagedEntity(detailList, pi, ps);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	public JsonList<ZhaiQuan>  getJuJinBaoZhaiQuan(String userId,int pi, int ps)
	{
		SqlSession session = null;
		List<ZhaiQuan> detailList = null;
		JsonList<ZhaiQuan> result = null;
		try {
			session = this.getSession();
			detailList = session.selectList("com.jujin.mapper.getJuJinBaoZhaiQuan",
					userId);
			
			result = GetPagedEntity(detailList, pi, ps);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	

}
