/**   
 * @author wangning
 * @date 2015年2月12日 上午9:26:01 
 * @version V1.0   
 * @Description: 
 */
package com.jujin.biz;

import java.util.List;

import com.jujin.common.JsonList;
import com.jujin.common.OpEntityResult;
import com.jujin.entity.borrow.Borrow;
import com.jujin.entity.borrow.BorrowInfo;
import com.jujin.entity.borrow.RepayCredit;
import com.jujin.entity.borrow.TenderLog;

/**
 * 产品逻辑(新专享、聚金U选，员工宝，散标理财列表）
 */
public class ProductBiz extends BaseBiz {

	/**
	 * @return 产品详情首页,返回每个产品中可用(最新)的产品列表
	 */
	public JsonList<Borrow> getQuickProducts() {
		return null;
	}

	/**
	 * @param type
	 *            标类型
	 * @param isnew
	 *            是否为新手标
	 * @param pageIndex
	 *            第几页
	 * @param pageSize
	 *            每页返回的数量(前台呈现的时候可以和这个不完全一致,如后台一次返回50条,前台每次仅呈现10条即可）
	 * @return 标的列表分页显示
	 */
	public JsonList<Borrow> getProducts(int type, boolean isnew, int pageIndex,
			int pageSize) {
		return null;
	}

	public OpEntityResult<BorrowInfo> getInfo(String borrowId) {
		BorrowInfo info = new BorrowInfo();
		info.setAccountLabel("100");
		info.setAccountScale(50);
		info.setBeginTime("2015-02-12");
		info.setCreditLevel("1");

		OpEntityResult<BorrowInfo> entity = new OpEntityResult<BorrowInfo>(info);

		return entity;
	}

	/**借款人还款信用展示
	 * @param borrowId
	 * @return
	 */
	public OpEntityResult<RepayCredit> getRepayCredit(String userId) {
		return null;
	}
	
	
	/**投标人列表
	 * @param borrowid
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public JsonList<TenderLog> getTenderLogs(String borrowid,int pageIndex,int pageSize)
	{
		return null;
	}
	
	
}
