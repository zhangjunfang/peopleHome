/**   
 * @author wangning
 * @date 2015年2月10日 下午3:26:49 
 * @version V1.0   
 * @Description: TODO
 */
package com.jujin.entity.borrow;

/**
 * 还款信用
 */
public class RepayCredit {

	/* 借款成功次数 */
	private int successBorrowCount;

	/* 流标次数 */
	private int liuBiaoCount;

	/* 待还次数 */
	private int awaitCount;

	/* 提前还款 */
	private int aheadRepayCount;

	/* 准时还款 */
	private int intimeRepayCount;

	/* 迟还款 */
	private int laterRepayCount;

	/* 30天之内逾期还款 */
	private int inThreeDayCount;

	/* 超过30天逾期还款 */
	private int outThreeDayCount;

	/* 逾期未还款 */
	private int dueRepayCount;

	/**
	 * @return the successBorrowCount
	 */
	public int getSuccessBorrowCount() {
		return successBorrowCount;
	}

	/**
	 * @param successBorrowCount
	 *            the successBorrowCount to set
	 */
	public void setSuccessBorrowCount(int successBorrowCount) {
		this.successBorrowCount = successBorrowCount;
	}

	/**
	 * @return the liuBiaoCount
	 */
	public int getLiuBiaoCount() {
		return liuBiaoCount;
	}

	/**
	 * @param liuBiaoCount
	 *            the liuBiaoCount to set
	 */
	public void setLiuBiaoCount(int liuBiaoCount) {
		this.liuBiaoCount = liuBiaoCount;
	}

	/**
	 * @return the awaitCount
	 */
	public int getAwaitCount() {
		return awaitCount;
	}

	/**
	 * @param awaitCount
	 *            the awaitCount to set
	 */
	public void setAwaitCount(int awaitCount) {
		this.awaitCount = awaitCount;
	}

	/**
	 * @return the aheadRepayCount
	 */
	public int getAheadRepayCount() {
		return aheadRepayCount;
	}

	/**
	 * @param aheadRepayCount
	 *            the aheadRepayCount to set
	 */
	public void setAheadRepayCount(int aheadRepayCount) {
		this.aheadRepayCount = aheadRepayCount;
	}

	/**
	 * @return the intimeRepayCount
	 */
	public int getIntimeRepayCount() {
		return intimeRepayCount;
	}

	/**
	 * @param intimeRepayCount
	 *            the intimeRepayCount to set
	 */
	public void setIntimeRepayCount(int intimeRepayCount) {
		this.intimeRepayCount = intimeRepayCount;
	}

	/**
	 * @return the laterRepayCount
	 */
	public int getLaterRepayCount() {
		return laterRepayCount;
	}

	/**
	 * @param laterRepayCount
	 *            the laterRepayCount to set
	 */
	public void setLaterRepayCount(int laterRepayCount) {
		this.laterRepayCount = laterRepayCount;
	}

	/**
	 * @return the inThreeDayCount
	 */
	public int getInThreeDayCount() {
		return inThreeDayCount;
	}

	/**
	 * @param inThreeDayCount
	 *            the inThreeDayCount to set
	 */
	public void setInThreeDayCount(int inThreeDayCount) {
		this.inThreeDayCount = inThreeDayCount;
	}

	/**
	 * @return the outThreeDayCount
	 */
	public int getOutThreeDayCount() {
		return outThreeDayCount;
	}

	/**
	 * @param outThreeDayCount
	 *            the outThreeDayCount to set
	 */
	public void setOutThreeDayCount(int outThreeDayCount) {
		this.outThreeDayCount = outThreeDayCount;
	}

	/**
	 * @return the dueRepayCount
	 */
	public int getDueRepayCount() {
		return dueRepayCount;
	}

	/**
	 * @param dueRepayCount
	 *            the dueRepayCount to set
	 */
	public void setDueRepayCount(int dueRepayCount) {
		this.dueRepayCount = dueRepayCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RepayCredit [successBorrowCount=" + successBorrowCount
				+ ", liuBiaoCount=" + liuBiaoCount + ", awaitCount="
				+ awaitCount + ", aheadRepayCount=" + aheadRepayCount
				+ ", intimeRepayCount=" + intimeRepayCount
				+ ", laterRepayCount=" + laterRepayCount + ", inThreeDayCount="
				+ inThreeDayCount + ", outThreeDayCount=" + outThreeDayCount
				+ ", dueRepayCount=" + dueRepayCount + "]";
	}

}
