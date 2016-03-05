package com.jujin.entity.award;
/**
 * Title: UtilEnum
 * Description: 枚举工具类
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年4月22日
 */
public enum UtilEnum {

	PECENTS(1000),    //比率
	VIPPERCENT(1);

	private int index;

	UtilEnum(int idx) {
		this.index = idx;
	}

	public int getIndex() {
		return index;
	}
}
