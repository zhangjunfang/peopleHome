package com.jujin.biz;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.interest.InterestBean;
import com.jujin.entity.interest.InterestDetail;

/**
 * Title: InterestBiz 
 * Description: 余额生息Biz 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年7月8日
 */
public class InterestBiz extends JujinBaseBiz {
	
	/**
	 * Title: getInterestList 
	 * Description:获取收益记录 
	 * @return
	 */
	public JsonList<InterestDetail> getInterestList(String userId, int pi, int ps) {
		SqlSession session = null;
		List<InterestDetail> detailList = null;
		JsonList<InterestDetail> result = null;
		try {
			session = this.getSession();
			detailList = session.selectList("com.jujin.mapper.getInterestList",
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
	
	
	/**
	 * Title: getInterestAmount 
	 * Description:获取总收益
	 * @param userId
	 * @return
	 */
	public InterestBean getInterestAmount(String userId) {
		SqlSession session = null;
		InterestBean result = null;
		try {
			session = this.getSession();
			result = session.selectOne("com.jujin.mapper.getInterestAmount",
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
}
