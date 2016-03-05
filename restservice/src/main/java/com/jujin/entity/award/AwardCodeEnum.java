package com.jujin.entity.award;

/**
 * Title: AwardCodeEnum 
 * Description: 奖品编码枚举类 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年4月22日
 */
public enum AwardCodeEnum {

	VIPCODE(1),    //12个月VIP
	TENCODE(2),    //红包10块
	FIVECODE(3),   //红包5块
	REPEATCODE(4), //再抽一次
	ONECODE(5),    //红包1块
	THANKCODE(6);  //谢谢参与

	private int index;

	AwardCodeEnum(int idx) {
		this.index = idx;
	}

	public int getIndex() {
		return index;
	}
}
