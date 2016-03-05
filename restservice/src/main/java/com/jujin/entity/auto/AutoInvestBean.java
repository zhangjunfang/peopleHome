package com.jujin.entity.auto;

import java.util.ArrayList;
import java.util.List;

import com.jujin.common.JsonList;

public class AutoInvestBean {

	private JsonList<AutoInvestSetting> settings;

	private JsonList<AutoInvestRecord> records;

	private AutoInvestSetting defaultSetting;
	
	
	
	

	public AutoInvestBean() {

	}

	public JsonList<AutoInvestSetting> getSettings() {
		return settings;
	}

	public void setSettings(JsonList<AutoInvestSetting> settings) {
		this.settings = settings;
	}

	public JsonList<AutoInvestRecord> getRecords() {
		return records;
	}

	public void setRecords(JsonList<AutoInvestRecord> records) {
		this.records = records;
	}

	public AutoInvestSetting getDefaultSetting() {
		return defaultSetting;
	}

	public void setDefaultSetting(AutoInvestSetting defaultSetting) {
		this.defaultSetting = defaultSetting;
	}

}
