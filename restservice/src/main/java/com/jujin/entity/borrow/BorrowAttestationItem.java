package com.jujin.entity.borrow;

 

/**
 * 标的风控资料
 * **/
public class BorrowAttestationItem {

	
	private String attestationCd;
	private String attestationName;
	
	private String fileUrl;
	private String imageUrl;
	
	
	public String getAttestationCd() {
		return attestationCd;
	}
	public void setAttestationCd(String attestationCd) {
		this.attestationCd = attestationCd;
	}
	public String getAttestationName() {
		return attestationName;
	}
	public void setAttestationName(String attestationName) {
		this.attestationName = attestationName;
	}
	
	public String getFileUrl() {
		return fileUrl;
	}
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public BorrowAttestationItem Clone()
	{
		BorrowAttestationItem borrowAttestationItem=new  BorrowAttestationItem();
		borrowAttestationItem.setAttestationCd(this.getAttestationCd());
		borrowAttestationItem.setAttestationName(this.getAttestationName()); 
		borrowAttestationItem.setFileUrl(this.getFileUrl()); 
		borrowAttestationItem.setImageUrl(this.getImageUrl()); 
		
		
		return borrowAttestationItem;
		
	}
	
	
}
