/**
 * 
 */
package com.jujin.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.jujin.common.ExceptionHelper;
import com.jujin.common.JsonList;
import com.jujin.entity.UserAccountInfo;
import com.jujin.entity.borrow.Borrow;
import com.jujin.entity.borrow.BorrowAttestationItem;
import com.jujin.entity.borrow.BorrowAttestion;
import com.jujin.entity.borrow.BorrowInfo;
import com.jujin.entity.borrow.BorrowInfoItem;
import com.jujin.entity.borrow.BorrowInvestIndicate;
import com.jujin.entity.borrow.BorrowRepay;
import com.jujin.entity.borrow.BorrowTransfer;
import com.jujin.entity.borrow.KeyValuePair;
import com.jujin.entity.borrow.TenderLog;
import com.jujin.entity.borrow.UserIncomeInfo;
import com.jujin.entity.xglc.XglcUserTender;
import com.pro.common.util.StringUtils;

/**
 * @author 宁
 *
 */
/**
 * 
 */
public class HomeBiz extends BaseBiz {

	/** serialVersionUID*/
	private static final long serialVersionUID = -8421246457385814605L;
	java.text.DecimalFormat df = new java.text.DecimalFormat("0.0#");

	/**
	 * @return 当前可用标
	 */
	public JsonList<Borrow> getExpressBorrow() {

		SqlSession session = null;
		JsonList<Borrow> result = null;
		try {
			session = this.getSession();

			List<Borrow> borrows = session
					.selectList("com.jujin.mapper.QueryExpressBorrow");
			result = new JsonList<Borrow>();
			result.addRange(borrows);

		} finally {
			if (session != null)
				session.close();
		}

		return result;
	}

	public List<BorrowInvestIndicate> getBorrowStatus() {
		SqlSession session = null;
		List<BorrowInvestIndicate> result = null;

		try {
			session = this.getSession();
			result = session.selectList("com.jujin.mapper.QueryBorrowStatus");
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return result;
	}

	public List<Borrow> getHomeBorrow() {

		SqlSession session = null;
		List<Borrow> result = null;
		try {
			session = this.getSession();
			List<Borrow> borrows = session
					.selectList("com.jujin.mapper.QueryHomeBorrow");

			if (null != borrows) {
				for (int index = 0; index < borrows.size(); index++) {

					Borrow borrow = borrows.get(index);

					KeyValuePair pair = new KeyValuePair();
					pair.setName("聚金币抵扣(投资金额)");

					pair.setValue(df.format(Double.parseDouble(borrow
							.getVouchersRate()) * 100) + "%");

					borrow.getPrivileges().add(pair);
				}
			}
			result = borrows;

		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	/**
	 * 根据标标类型详情,获取标的列表
	 * 
	 * @param type
	 * @return
	 */
	public JsonList<Borrow> getBorrowsByType(int type, int pi, int ps) {

		// 0:新专享,1:散标列表,2:聚金U选
		List<Borrow> borrows = null;
		JsonList<Borrow> result = null;
		SqlSession session = null;
		try {
			session = getSession();
			switch (type) {
			case 0:
				borrows = session
						.selectList("com.jujin.mapper.QueryZhuanRangBorrow");
				break;
			case 1:
				borrows = session.selectList("com.jujin.mapper.QueryBorrow");
				break;
			case 2:
				borrows = session.selectList("com.jujin.mapper.QueryUBorrow");
				break;
			}
			result = GetPagedEntity(borrows, pi, ps);
		} finally {
			if (session != null)
				session.close();
		}
		return result;
	}

	public BorrowInfo getBorrowInfo(String borrowId) {

		SqlSession session = null;
		BorrowInfo info = null;
		try {
			session = getSession();
			info = session.selectOne("com.jujin.mapper.QueryBorrowInfo",
					borrowId);

			if(info==null)
			{
				logger.error("getBorrowInfo borrowId:"+borrowId);
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			
			
			
			map.put("borrow_id", borrowId);
			map.put("customize_flg", null);
			map.put("borrow_type", info.getType());

			List<BorrowInfoItem> items = session.selectList(
					"com.jujin.mapper.QueryBorrowInfoItem", map);
			info.setShowItems(items);

			UserIncomeInfo userInfo = session.selectOne(
					"com.jujin.mapper.QueryUserIncomeInfo", borrowId);

			info.setBorrowUser(userInfo);

			List<BorrowAttestationItem> attestationItems = session.selectList("com.jujin.mapper.QueryBorrowAttestation", borrowId);
			
			BorrowAttestion attestation = new BorrowAttestion();
			if (attestationItems != null&&attestationItems.size()>0) {
				
				for (BorrowAttestationItem item : attestationItems) {

					List<BorrowAttestationItem> tmpList = attestation.getAttestType();

					boolean status = false;
					for (BorrowAttestationItem tmpItem : tmpList) {
						if (tmpItem.getAttestationCd().equals(item.getAttestationCd())) {
							status = true;
							break;
						}
					}
					
					if (!status) {
						
						BorrowAttestationItem cloneItem=item.Clone();
						
						if(!StringUtils.isEmpty(cloneItem.getImageUrl()))
						{
							cloneItem.setFileUrl(cloneItem.getImageUrl());
						}
						tmpList.add(cloneItem);
						
						attestation.getAttestFiles().put(
								item.getAttestationCd(),
								new ArrayList<String>());
					}
					
					HashMap<String, List<String>> list = attestation
							.getAttestFiles();
					
					list.get(item.getAttestationCd()).add(item.getFileUrl());
				}
			}
			info.setAttestion(attestation);
			
			String borrowTransferFlg=info.getTransferFlg();
			if("1".equals(borrowTransferFlg))
			{
				BorrowTransfer transfer=session.selectOne("com.jujin.mapper.QueryBorrowTransfer",info.getBorrowId());
				
				if(transfer!=null)
				{
					info.setOriginalBorrowAccount(transfer.getTransferAmount());
					info.setOriginalBorrowId(transfer.getBorrowIdAncestors()); 
					info.setOriginalBorrowPeriod(transfer.getOldOfPeriod());
					info.setDiscountRate(transfer.getDiscountRate());
					info.setDiscountAmount(transfer.getDiscountAmount());
					info.setOriginalBorrowTitle(transfer.getAncestorsBorrowTitle());
					info.setNextRecoverTime(transfer.getNextRecoverTime());
					
					List<BorrowRepay> transfers=session.selectList("com.jujin.mapper.QueryBorrowRepay",transfer.getBorrowIdAncestors());
					if(transfers!=null)
					{
						info.getRepays().addAll(transfers);
					}
				}
			}
			// 还款信用
			/*
			 * com.jujin.entity.borrow.RepayCredit credit =
			 * getSession().selectOne( "com.jujin.mapper.QueryRepayCredit",
			 * info.getBorrowUserId()); info.setCredit(credit);
			 */
		} 
		catch(Exception ex)
		{
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		}
		finally {
			if (session != null)
				session.close();
		}
		return info;
	}

	private void validCoin(String userId, String borrowId, double coin) {
		SqlSession session = null;
		try {
			session = getSession();

		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		} finally {

		}

	}

	/**
	 * 投标人列表
	 * 
	 * @param borrowid
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public JsonList<TenderLog> getTenderLogByBorrowId(String borrowid,
			int pageIndex, int pageSize) {

		SqlSession session = null;
		JsonList<TenderLog> list = null;
		try {
			session = this.getSession();
			List<TenderLog> logs = session.selectList(
					"com.jujin.mapper.QueryTenderLogByBorrowId", borrowid);
			list = GetPagedEntity(logs, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}
		return list;
	}

	public UserAccountInfo GetUserAccountInfo(String userId) {

		SqlSession session = null;
		UserAccountInfo entity = null;
		try {
			session = this.getSession();
			entity = session.selectOne("com.jujin.mapper.QueryUserAccountInfo",userId);
		} finally {
			if (session != null)
				session.close();
		}
		return entity;
	}

	private JsonList<TenderLog> QueryUplanZhaiQuanList(int pageIndex,
			int pageSize) {
		SqlSession session = null;
		JsonList<TenderLog> logs = null;
		try {
			session = this.getSession();
			List<TenderLog> list = session.selectList(
					"com.jujin.mapper.QueryZhaiQuanList", "");
			logs = this.GetPagedEntity(list, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}
		return logs;
	}

	private JsonList<TenderLog> QueryUplanTenderLog(String borrowId,
			int pageIndex, int pageSize) {

		SqlSession session = null;
		JsonList<TenderLog> logs = null;
		try {
			session = this.getSession();
			List<TenderLog> list = session.selectList(
					"com.jujin.mapper.QueryUPlanList", borrowId);
			logs = this.GetPagedEntity(list, pageIndex, pageSize);
		} finally {
			if (session != null)
				session.close();
		}
		return logs;
	}
	
	public boolean QueryBorrowTransfer(String borrowId)
	{
		SqlSession session = null;
		String transferFlg=null;
		try {
			session = this.getSession();
			transferFlg= session.selectOne("com.jujin.mapper.QueryBorrowTransferFlg", borrowId);
		} finally {
			if (session != null)
				session.close();
		}
		if(StringUtils.isEmpty(transferFlg))
		{
			return false;
		}
		return transferFlg.equals("1");
	}

	/**
	 * 投资明细
	 * 
	 * @param borrowId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public JsonList<TenderLog> QueryUplanLog(String borrowId, int pageIndex,
			int pageSize) {

		SqlSession session = null;
		try {
			session = this.getSession();
			int status = session.selectOne(
					"com.jujin.mapper.QueryUplanUserTenderStatus", borrowId);
			if (status < 1) {
				return QueryUplanZhaiQuanList(pageIndex, pageSize);
			} else {
				return QueryUplanTenderLog(borrowId, pageIndex, pageSize);
			}
		} finally {
			if (session != null)
				session.close();
		}
	}

	public JsonList<KeyValuePair> QueryJuJinBaoConfig() {
	 
		SqlSession session = null;
		JsonList<KeyValuePair> result=new JsonList<KeyValuePair>();
		try {
			session = this.getSession();
			List<KeyValuePair> pairs = session.selectList("com.jujin.mapper.QueryJuJinBaoConfig", "JUJINBAO");
			result.addRange(pairs);
		} finally {
			if (session != null)
				session.close();
		}
		return result;

	}

	public boolean AddXglcUserRecord(XglcUserTender record) {
		SqlSession session=null;
		boolean result=false;
		try {
			session=this.getSession();
			session.insert("com.jujin.mapper.InsertXglcUserTender", record);
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

	public String getTenderSeq(String userId, String borrowId) {
		SqlSession session = null;
		String orderId = "";
		try {
			session = this.getSession();
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", userId);
			map.put("borrowId", borrowId);
			orderId = session.selectOne("com.jujin.xglc.mapper.getTenderSeq", map);
		} finally {
			if (session != null)
				session.close();
		}
		return orderId;
	}

}
