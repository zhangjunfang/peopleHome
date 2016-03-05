package com.jujin.entity.xglc.borrow;
/**
 * Title: GuaranteeDTO
 * Description: 担保公司(西瓜理财)
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年10月21日
 */
public class GuaranteeDTO {
	/**
	 * 担保方ID
	 */
	private String id;
	/**
	 * 担保方简称
	 */
	private String guaranteeName;
	/**
	 * 担保方全名
	 */
	private String guaranteeCorp;
	/**
	 * 担保方法人
	 */
	private String legalRepresentative;
	/**
	 * 担保类型(担保类型：无担保、风险准备金、第三方担保)
	 */
	private String guaranteeType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuaranteeName() {
		return guaranteeName;
	}
	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}
	public String getGuaranteeCorp() {
		return guaranteeCorp;
	}
	public void setGuaranteeCorp(String guaranteeCorp) {
		this.guaranteeCorp = guaranteeCorp;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public String getGuaranteeType() {
		return guaranteeType;
	}
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	
	
	
}
