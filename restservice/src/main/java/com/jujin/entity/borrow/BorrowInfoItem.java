/**   
* @author wangning
* @date 2015年2月10日 下午3:36:51 
* @version V1.0   
* @Description: TODO
*/
package com.jujin.entity.borrow;

/**
 * 借款资料展示,一般为有价值的图片认证
 */
public class BorrowInfoItem {

	/*资料类型*/
	private String type;
	
	/*上传时间*/
	private String uploadTime;
	
	/*审核说明*/
	private String verifyTime;
	
	/*审核说明*/
	private String verifyMemo;
	
	/*关联的标*/
	private String borrowId;
	
	/*关联的图片*/
	private String image;
	
	/*认证名称*/
	private String attestationName;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the uploadTime
	 */
	public String getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the verifyTime
	 */
	public String getVerifyTime() {
		return verifyTime;
	}

	/**
	 * @param verifyTime the verifyTime to set
	 */
	public void setVerifyTime(String verifyTime) {
		this.verifyTime = verifyTime;
	}

	/**
	 * @return the verifyMemo
	 */
	public String getVerifyMemo() {
		return verifyMemo;
	}

	/**
	 * @param verifyMemo the verifyMemo to set
	 */
	public void setVerifyMemo(String verifyMemo) {
		this.verifyMemo = verifyMemo;
	}

	/**
	 * @return the borrowId
	 */
	public String getBorrowId() {
		return borrowId;
	}

	/**
	 * @param borrowId the borrowId to set
	 */
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the attestationName
	 */
	public String getAttestationName() {
		return attestationName;
	}

	/**
	 * @param attestationName the attestationName to set
	 */
	public void setAttestationName(String attestationName) {
		this.attestationName = attestationName;
	}
	
	
	
	
	
		
	
	
}
