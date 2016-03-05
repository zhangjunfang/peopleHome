/**   
 * @author wangning
 * @date 2015年2月10日 上午9:52:39 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.account;

import java.util.List;

import com.jujin.common.JsonList;
import com.jujin.common.OpResult;

/**
 * 邀请奖励
 */
public class InviteReward extends OpResult {

	/** 奖励规则说明 */
	private String memo;

	/* 邀请人 */
	private JsonList<FriendInvestment> invests;
	
	public double getInviteReward()
	{
		double result=0;
		if(invests!=null&&invests.getList()!=null&&invests.getTotal()>0)
		{
			for(FriendInvestment entity  :  invests.getList())
			{
				result+=entity.getIncome();
			}
		}
		return result;
	}

	public JsonList<FriendInvestment> getInvets() {
		return invests;
	}

	public void setInvets(JsonList<FriendInvestment> invests) {
		this.invests = invests;
	}
	
	public String  getMemo()
	{
		return  this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
