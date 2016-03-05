package com.jujin.biz;

import com.jujin.common.SystemConfig;
import com.jujin.utils.ExceptionHelper;
import com.pro.common.db.Exception.DbException;
import com.wicket.loan.web.common.bean.BorrowVentureBean;
import com.wicket.loan.web.common.bean.UsersAccountBean;
import com.wicket.loan.web.common.mediator.CommonAdminMediator;

/**
 * @author wangning
 * 
 *         由于在操作时需要显示声明事物，而该方法是protected的so,只能做一个简单的包装
 */
public class UserAwardMediator extends CommonAdminMediator {

	//转盘资金抽奖账户类型
	static final String OPERATION_TYPE="100";
	//风险池支出资金类型
	static final String VENTURE_TYPE="10";
	//微信端操作标识
	static final String OP_TYPE="01";
	
	public boolean updateUserAccount(String userId, String ip, double money) {

		boolean result = false;
		try {

			// 贷款标<a
			// href="http://www.jujinziben.com/borrowinfo.page?borrow_id=2015031100000000000000001313"
			// target="_blank">【房产抵押】15.3.11陈先生第三期</a>成功进行贷款，账户打入投资奖励360.18元
			// bean.setRemark(String.format("您参与抽奖，获得{0}元已打入您的账户", money));

			super.transactionBegin();
			UsersAccountBean bean = makeUsersAccountBean(userId, ip, money);
			BorrowVentureBean vbBean = makeBorrowVentureBean(userId, ip, money);

			updUsersAccount(bean);
			updVenture(vbBean);
			result = true;
			
			
			
			super.commit();
			result = true;

		} catch (Exception e) {
			e.printStackTrace();
			result=false;
		} finally {
			super.transactionEnd();
		}
		return result;
	}

	private UsersAccountBean makeUsersAccountBean(String userId, String ip,
			double money) {
		UsersAccountBean bean = new UsersAccountBean();
		bean.setUserId(userId);
		bean.setBpFlg("0");// 用户是收入
		bean.setContinueAmount(0);
		bean.setCredit(0);
		bean.setLoginIp(ip);
		bean.setMoney(money);
		bean.setOperatorUserId(userId);
		bean.setOperatorType(OPERATION_TYPE);
		bean.setOpType(SystemConfig.OP_TYPE);
		bean.setRemark(String.format("恭喜您，参与聚金资本抽奖活动获得%s元红包",money));
		return bean;
	}

	private BorrowVentureBean makeBorrowVentureBean(String userId, String ip,
			double money) {

		BorrowVentureBean vbBean = new BorrowVentureBean();
		vbBean.setAmount(money);
		vbBean.setContent(String.format("聚金资本 抽奖活动支出[%s]元,幸运中奖人[%s]", money,
				userId));
		vbBean.setIeFlg(1);
		vbBean.setLoginIp(ip);
		vbBean.setOperatorUserId("聚金资本");
		vbBean.setVentureType(VENTURE_TYPE);
		vbBean.setUserId(userId);
		//vbBean.set

		return vbBean;
	}

}
