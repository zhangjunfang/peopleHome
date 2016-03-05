package com.jujin.biz.xglc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.JujinBaseBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.borrow.Borrow;
import com.jujin.entity.xglc.transaction.AccountBasicInfoDTO;
import com.jujin.entity.xglc.transaction.DebtInfoDTO;
import com.jujin.entity.xglc.transaction.LastRecoverInfo;
import com.jujin.entity.xglc.transaction.LoginInfoDTO;
import com.jujin.entity.xglc.transaction.OrderInfoDTO;
import com.jujin.entity.xglc.transaction.OrderTemplateDTO;
import com.jujin.entity.xglc.transaction.RecoverDTO;
import com.pro.common.util.DesCodeUtil;
import com.pro.common.util.StringUtils;
import com.wicket.loan.web.login.bean.LoginBean;

/**
 * Title: XglcProductBiz Description: Company: jujinziben
 * 
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class XglcTransactionBiz extends JujinBaseBiz {
	private static final long serialVersionUID = 1L;

	public LoginInfoDTO login(String account, String password) {
		LoginInfoDTO dto = new LoginInfoDTO();
		SqlSession session = null;
		try {
			session = getSession();
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("account", account);
			paramMap.put("password", DesCodeUtil.encrypt(password));
			dto = session.selectOne("com.jujin.xglc.mapper.memberLogin",
					paramMap);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return dto;
	}

	public AccountBasicInfoDTO accountBasicStatus(String userAccessKey) {
		AccountBasicInfoDTO dto = new AccountBasicInfoDTO();
		SqlSession session = null;
		try {
			session = getSession();
			dto = session.selectOne("com.jujin.xglc.mapper.accountBasicStatus",
					userAccessKey);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return dto;
	}


	public LoginBean getLoginBeanByAccessKey(String accountKey) {
		LoginBean bean = null;
		SqlSession session = null;
		try {
			session = getSession();
			bean = session
					.selectOne("com.jujin.xglc.mapper.getLoginBeanByAccessKey",
							accountKey);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return bean;
	}
	
	public String getUserIdByAccessKey(String userAccessKey) {
		String userId = null;
		SqlSession session = null;
		try {
			session = getSession();
			userId = session
					.selectOne("com.jujin.xglc.mapper.getUserIdByAccessKey",userAccessKey);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return userId;
	}

	public List<OrderInfoDTO> getAccountOrderList(String userAccessKey,
			String lastOrderTime, String pageIndex, String pageSize, OrderTemplateDTO dto) {
		JsonList<OrderInfoDTO> jsonlist = new JsonList<OrderInfoDTO>();
		List<OrderInfoDTO> list = new ArrayList<OrderInfoDTO>();
		SqlSession session = null;
		try {
			session = getSession();
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userAccessKey", userAccessKey);
			paramMap.put("lastOrderTime", lastOrderTime);
			list = session.selectList(
					"com.jujin.xglc.mapper.getAccountOrderList", paramMap);
			dto.setTotalCount(list.size());
			jsonlist = GetPagedEntity(list, Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return jsonlist.getList();
	}

	public List<RecoverDTO> getOrderRecoverList(String orderId) {
		List<RecoverDTO> list = new ArrayList<RecoverDTO>();
		SqlSession session = null;
		try {
			session = getSession();
			list = session.selectList(
					"com.jujin.xglc.mapper.getOrderRecoverList", orderId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	public List<DebtInfoDTO> getAccountDebtInfoList(
			String orderIdList) {
		List<DebtInfoDTO> infolist = new ArrayList<DebtInfoDTO>();
		SqlSession session = null;
		try {
			session = getSession();
			String[] orderIds = orderIdList.split(",");
			List<String> list = Arrays.asList(orderIds);
			infolist = session.selectList("com.jujin.xglc.mapper.getAccountDebtInfoList", list);
			for (DebtInfoDTO di : infolist) {
				LastRecoverInfo lri = session.selectOne("com.jujin.xglc.mapper.getLastRecover", di.getDebtId());
				if(lri != null){
					di.setLastPaidCaptial(lri.getLastPaidCaptial());
					di.setLastPaidDate(lri.getLastPaidDate());
					di.setLastPaidInterest(lri.getLastPaidInterest());
				}else{
					di.setLastPaidCaptial(0.0);
					di.setLastPaidDate("");
					di.setLastPaidInterest(0.0);
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return infolist;
	}

	public String QueryXglcUser(String id) {

		SqlSession session = null;
		String result = null;
		try {
			session = getSession();
			result = session.selectOne("com.jujin.mapper.QueryUserName",
					id);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public String QueryXglcBorrow(String productCode) {
		
		
		SqlSession session = null;
		Borrow borrow = null;
		String result=null;
		try {
			session = getSession();
			borrow = session.selectOne("com.jujin.mapper.QueryXglcBorrow",productCode);
			
			if(borrow!=null)
			{
				int borrowType=borrow.getType();
				String borrowId=borrow.getBorrowId();
				String tranFlg=borrow.getTransferFlg();
				if("0".equals(tranFlg)||StringUtils.isEmpty(borrowId))
				{
					switch (borrowType) {
					case 2:
					case 8:
						result="borrowinfo?id="+borrowId;
						break;
					case 7:
						result="uplaninfo?id="+borrowId+"&plan_kind="+borrow.getBorrowFlag();
						break;
					case 10:
						result="jujinbao?id="+borrowId;
						break;
					default:
						result="borrowinfo?=id"+borrowId;
						break;
					}
				}
				else if("1".equals(tranFlg))
				{
					result="transfer?id="+borrowId;
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public String getUserAccessKeyByUserId(String userId) {
		SqlSession session = null;
		String result = null;
		try {
			session = getSession();
			result = session.selectOne("com.jujin.xglc.mapper.getUserAccessKeyByUserId",
					userId);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}
	
	

}
