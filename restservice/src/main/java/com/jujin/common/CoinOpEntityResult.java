/**   
* @author gaojunfeng
* @date 2015年6月17日 上午9:46:26 
* @version V1.0   
* @Description: 
*/
package com.jujin.common;

/**
 * 返回操作的对象
 */
public class CoinOpEntityResult<T> extends OpResult {

	private T entity;
	//红包状态
	//	001     红包可抢
	//	002     红包失效
	//	003     红包对应的活动过期
	//	004     抢红包时间过期
	//	005     当前红包已抢过
	//	006     红包已抢完
	private String coinStates;
	
	private String totalAmount;
	
	public String getCoinStates() {
		return coinStates;
	}

	public void setCoinStates(String coinStates) {
		this.coinStates = coinStates;
	}

	public  CoinOpEntityResult(T entity)
	{
		this.entity=entity;
	}

	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	
	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
	
	
}
