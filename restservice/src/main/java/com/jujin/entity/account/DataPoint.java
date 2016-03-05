/**   
* @author wangning
* @date 2015年2月10日 上午9:18:03 
* @version V1.0   
* @Description: 收益排名折线中的点信息
*/
package com.jujin.entity.account;

 
/**收益排名折线中的点信息*/
public class DataPoint {
	
	 
	/**横坐标信息*/
	private String labelCn;
	
	 
	/**备注信息,如鼠标放上去的显示信息*/
	private String memo;
	
	/**值信息*/
	private double value;

	/**
	 * @return the labelCn
	 */
	public String getLabelCn() {
		return labelCn;
	}

	/**
	 * @param labelCn the labelCn to set
	 */
	public void setLabelCn(String labelCn) {
		this.labelCn = labelCn;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	

}
