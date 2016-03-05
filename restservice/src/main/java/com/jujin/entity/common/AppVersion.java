package com.jujin.entity.common;


/**移动端版本**/
public class AppVersion {
 
	private String type;
	
	private boolean hasNew;
	
	private String memo;
	
	private String path;
	
	private String version;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo; 
	}

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
 
	public boolean isHasNew() {
		return hasNew;
	}

	public void setHasNew(boolean hasNew) {
		this.hasNew = hasNew;
	}
}
