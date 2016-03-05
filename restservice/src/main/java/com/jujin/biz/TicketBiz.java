package com.jujin.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.transformation.SortedList;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.JsonList;
import com.jujin.entity.ticket.Ticket;
import com.jujin.utils.ExceptionHelper;

/**
 * 
 * 
 * 红包和加息券
 * 
 * @author wangning
 * 
 */
public class TicketBiz extends BaseBiz {

	/**
	 * 
	 * 查询加息券信息 type:0：未使用;1:已使用；2：已过期
	 * 
	 * @param userId
	 * @param type
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public JsonList<Ticket> QueryTicketInfo(String userId, int type,
			int pageIndex, int pageSize) {
		SqlSession session = null;

		JsonList<Ticket> result = null;
		try {
			session = this.getSession();

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			if (type == 3) {
				map.put("type", "3");
			}
			else if(type==2)
			{
				map.put("type", "2");
			}
			else if(type==1||type==0)
			{
				map.put("type", "1");
			}
			
			
			List<Ticket> list = session.selectList(
					"com.jujin.mapper.QueryTicketInfo", map);

			List<Ticket> tmpList = new ArrayList<Ticket>();

			for (Ticket ticket : list) {
				for (int i = 0; i < ticket.getTypeCount(type); i++) {

					Ticket tmpTicket = ticket.Clone();
					tmpList.add(tmpTicket);
				}
			}
			result = GetPagedEntity(tmpList, pageIndex, pageSize);
			result.setStatus(true);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			if (result == null) {
				result = new JsonList<Ticket>();
			}
			result.setStatus(false);
			result.setMsg("获取聚金券失败，请重试。");
		} finally {
			if(session != null){
				session.close();
			}
		}
		return result;
	}

	public Ticket QueryTicket(String userId, String ticketId) {

		SqlSession session = null;

		Ticket result = null;
		try {
			session = this.getSession();

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("ticketId", ticketId);

			result = session.selectOne("com.jujin.mapper.QueryTicketInfo", map);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			result = null;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return result;
	}

}
