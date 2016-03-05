package com.jujin.biz.auto;

import java.util.HashMap;
import java.util.List;

import javax.mail.Session;

import org.apache.ibatis.session.SqlSession;

import com.jujin.biz.BaseBiz;
import com.jujin.common.Constants;
import com.jujin.common.JsonList;
import com.jujin.common.SystemConfig;
import com.jujin.entity.auto.AutoInvestBean;
import com.jujin.entity.auto.AutoInvestRecord;
import com.jujin.entity.auto.AutoInvestSetting;
import com.jujin.entity.borrow.Borrow;

public class AutoInvestBiz extends BaseBiz {
 
	public AutoInvestBean getAutoInvestBean(String userId) {

		AutoInvestBean result = new AutoInvestBean();
		SqlSession session = super.getSession();
		
		
		try {
			List<AutoInvestSetting> settings = session
					.selectList("com.jujin.mapper.QueryAutoSetting",userId);

			List<AutoInvestRecord> records = session
					.selectList("com.jujin.mapper.QueryAutoInvestRecord",userId);
			AutoInvestSetting defaultSetting = session
					.selectOne("com.jujin.mapper.QueryDefaultSetting",userId);

			result.setDefaultSetting(defaultSetting);
			result.setRecords(this.GetPagedEntity(records, 1,
					Constants.PAGE_SIZE));
			result.setSettings(this.GetPagedEntity(settings, 1,
					Constants.PAGE_SIZE));
		} finally {
			session.close();
		}
		return result;
	}

	public boolean updateAutoSetting(int id, int status) {

		SqlSession session = super.getSession(true);
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("ID", String.valueOf(id));
			params.put("STATUS", String.valueOf(status));
			if (session.update("com.jujin.mapper.UpdateAutoSettingStatus", params) > 0) {
				return true;
			}
		} finally {
			session.close();
		}
		return false;
	}

	public JsonList<AutoInvestSetting> getAutoSetting(String userId, int pi,
			int ps) {
		SqlSession session = this.getSession();
		JsonList<AutoInvestSetting> result = new JsonList<AutoInvestSetting>();
		try {

			List<AutoInvestSetting> settings = session
					.selectList("com.jujin.mapper.QueryAutoSetting",userId);
			result = this.GetPagedEntity(settings, pi, ps);
		} finally {
			session.close();
		}

		return result;
	}

	public JsonList<AutoInvestRecord> getAutoRecord(String userId, int pi,
			int ps) {
		SqlSession session = this.getSession();
		JsonList<AutoInvestRecord> result = new JsonList<AutoInvestRecord>();
		try {

			List<AutoInvestRecord> settings = session
					.selectList("com.jujin.mapper.QueryAutoInvestRecord",userId);
			result = this.GetPagedEntity(settings, pi, ps);
			
		} finally {
			session.close();
		}

		return result;
	}
	
	public boolean addOrModifyAutoSetting(AutoInvestSetting setting)
	{
		SqlSession session=this.getSession(true);
		
		try {
			
			HashMap<String,String> params=new  HashMap<String,String>();
			params.put("userId",setting.getUserId());
			params.put("id",setting.getId());
			
			
			Object obj=session.selectOne("com.jujin.mapper.QueryAutoCount",params);
			
			if(obj!=null&&(int)obj>0)
			{
				if(session.update("com.jujin.mapper.UpdateBorrowAuto",setting)>0)
				{
					return true;
				}
			}
			else {
				if(session.insert("com.jujin.mapper.InsertBorrowAuto",setting)>0)
				{
					return true;
				}
			}
		} 
		finally
		{
			session.close();
		}
		return false;
	}

	public void DeleteAutoSetting(int id) {
		
		SqlSession session=null;
		try {
			session=this.getSession(true);
			session.delete("com.jujin.mapper.DeleteAutoSetting",id);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
	}
	
}
