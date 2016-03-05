package com.jujin.entity.xglc.transaction;

import java.util.List;

/**
 * Title: DebtInfoDTO
 * Description: 账户债权状态容器(西瓜理财)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月26日
 */
public class DebtTemplateDTO {
	
	private int debtCount;
	
	private List<DebtInfoDTO> debtList;
	
	public DebtTemplateDTO(List<DebtInfoDTO> list){
		this.debtList = list;
		this.debtCount = list.size();
	}
	
	public int getDebtCount() {
		return debtCount;
	}
	public void setDebtCount(int debtCount) {
		this.debtCount = debtCount;
	}

	public List<DebtInfoDTO> getDebtList() {
		return debtList;
	}

	public void setDebtList(List<DebtInfoDTO> debtList) {
		this.debtList = debtList;
	}
	
	
	
	
}
