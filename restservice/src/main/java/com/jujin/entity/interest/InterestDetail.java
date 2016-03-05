package com.jujin.entity.interest;
/**
 * Title: InterestDetail
 * Description: 
 * Company: jujinziben
 * @author gaojunfeng
 * @date 2015年7月8日
 */
public class InterestDetail {
	
	private String compressDate;
	
	private double amount;
	
	/**0:收入;1：支出**/
	private int bpFlg;
	
	/**操作类型**/
	private String bpMemo;
	
	
	public String getCompressDate() {
		return compressDate;
	}

	public void setCompressDate(String compressDate) {
		this.compressDate = compressDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	
	
	public int getBpFlg() {
		return bpFlg;
	}

	public void setBpFlg(int bpFlg) {
		this.bpFlg = bpFlg;
	}

	public String getBpMemo() {
		return bpMemo;
	}

	public void setBpMemo(String bpMemo) {
		this.bpMemo = bpMemo;
	}
	
	
	
	
	

}
