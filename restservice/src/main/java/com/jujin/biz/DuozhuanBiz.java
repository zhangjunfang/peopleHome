package com.jujin.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.duozhuan.DzBorrowInfo;
import com.jujin.entity.duozhuan.BorrowList;
import com.jujin.entity.duozhuan.Subscribes;

/**
 * 
* <p>Title: DuozhuanBiz.java</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2015</p>
* <p>Company: jujinziben</p>
* @author zhengshaoxu
* @date 2015年12月4日
* @version 1.0
 */
public class DuozhuanBiz extends JujinBaseBiz {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	

	/**
	 * 获取标的详情
	* Title: getBorrowInfo
	* Description: 
	* @return
	 */
	public DzBorrowInfo getBorrowInfo(String borrowId) {
		DzBorrowInfo bi = new DzBorrowInfo();
		List<Subscribes> subList = new ArrayList<Subscribes>();
		SqlSession session = null;
		try {
			session = getSession();
			bi = session.selectOne("com.jujin.duozhuan.mapper.queryBorrowInfo",borrowId);
			if(bi != null){
				subList = session.selectList("com.jujin.duozhuan.mapper.queryTenderFromBorrow",borrowId);
				bi.setSubscribesList(subList);
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return bi;
	}

	/**
	 * 获取列表
	* Title: pagination
	* Description: 
	* @param userId
	* @param pageIndex
	* @param pageSize
	* @return
	 */
	public BorrowList getBorrowUrls(int pageIndex, int pageSize) {
		BorrowList list = new BorrowList();
		List<String> urls = new ArrayList<String>();
		SqlSession session = null;
		try {
			session = this.getSession();
			urls = session.selectList("com.jujin.duozhuan.mapper.queryAllBorrowUrls");
			list = page(urls, pageIndex, pageSize);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}
	
	/**
	 * 分页
	* Title: page
	* Description: 
	* @param entities
	* @param pageIndex
	* @param pageSize
	* @return
	 */
	protected BorrowList page(List<String> entities,int pageIndex, int pageSize) {
		JsonList<String> result = new JsonList<String>();
		BorrowList bl = new BorrowList();
		if(entities == null){
			return bl;
		}
		int startIndex = (pageIndex - 1) * pageSize;
		if (startIndex > entities.size()) {
			result.setMsg("请求的数量超过总数");
			result.setStatus(false);
		} else {
			result.setTotal(entities.size());
			int startPos = (pageIndex - 1) * pageSize;
			int rows = startPos + pageSize;

			if (rows > entities.size()) {
				rows = entities.size();
			}
			for (; startPos < rows; startPos++) {
				result.add(entities.get(startPos));
			}

			int tmpPageCount = result.getTotal() / pageSize;
			result.setPageCount(result.getTotal() % pageSize == 0 ? tmpPageCount
					: ++tmpPageCount);

			
			bl.setCurrentPage(pageIndex);
			bl.setTotalCount(entities.size());
			bl.setTotalPage(tmpPageCount);
			bl.setBorrowList(result.getList());
		}
		return bl;
	}
}
