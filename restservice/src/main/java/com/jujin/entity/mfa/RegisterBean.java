package com.jujin.entity.mfa;

import java.io.Serializable;

/**
 * Title: RegBean
 * Description: 
 * Company: jujinziben
 * @author zhengshaoxu
 * @date 2015年12月10日
 */
public class RegisterBean implements Serializable{

	/** serialVersionUID*/
	private static final long serialVersionUID = -5751717061128838433L;
	/**
	 * 账号
	 */
	private String username;
	/**
	 * 手机
	 */
	private String tel;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 注册时是否带有密码
	 */
	private boolean hasPwd;
	/**
	 * 密码
	 */
	private String pwd;
	/**
	 * 接入第三方平台标识（参考PlatformConstants）
	 */
	private String platCode;
	/**
	 * 接入第三方平台名称（参考PlatformConstants）
	 */
	private String platUsername;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isHasPwd() {
		return hasPwd;
	}

	public void setHasPwd(boolean hasPwd) {
		this.hasPwd = hasPwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public String getPlatUsername() {
		return platUsername;
	}

	public void setPlatUsername(String platUsername) {
		this.platUsername = platUsername;
	}



	
}
