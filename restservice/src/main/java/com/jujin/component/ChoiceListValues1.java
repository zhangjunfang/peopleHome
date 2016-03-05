/**
 * 
 */
package com.jujin.component;

import java.io.Serializable;

/**
 * @author 宁
 *
 */
public class ChoiceListValues1 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String prefix = "";
	private String suffix = "";
	private String onClickJs;
	private String display = null;
	private String value = null;

	public ChoiceListValues1() {
	}

	public ChoiceListValues1(String paramString1, String paramString2) {
		this.display = paramString1;
		this.value = paramString2;
	}

	public ChoiceListValues1(String paramString1, String paramString2,
			String paramString3, String paramString4) {
		this.display = paramString1;
		this.value = paramString2;
		this.prefix = paramString3;
		this.suffix = paramString4;
	}

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String paramString) {
		this.display = paramString;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String paramString) {
		this.value = paramString;
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String paramString) {
		this.prefix = paramString;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String paramString) {
		this.suffix = paramString;
	}

	public String getOnClickJs() {
		return this.onClickJs;
	}

	public void setOnClickJs(String paramString) {
		this.onClickJs = paramString;
	}
}