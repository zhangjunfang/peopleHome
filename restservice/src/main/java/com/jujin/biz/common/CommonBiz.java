package com.jujin.biz.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.util.StringUtils;

import com.jujin.biz.BaseBiz;
import com.jujin.common.ExceptionHelper;
import com.jujin.entity.common.AppVersion;
import com.jujin.entity.common.DeviceLocInfo;

public class CommonBiz extends BaseBiz {

	public AppVersion getVersion(String type, String version) {
		SqlSession session = null;
		AppVersion app = new AppVersion();
		
		List<AppVersion> list = null;
		try {
			session = getSession();

			list = session.selectList("com.jujin.mapper.QueryVersion", type);
			if(null==list||list.size()<1)
			{
				return null;
			}

			Collections.sort(list, new Comparator<AppVersion>() {
				public int compare(AppVersion ver1, AppVersion ver2) {

					if (null == ver1 && null == ver2)
						return 0;
					if (null == ver1)
						return -1;
					if (null == ver2)
						return 1;
					String arg0 = ver1.getVersion();
					String arg1 = ver2.getVersion();
					return VersionCompare(arg0, arg1);
				}
			});

			int index = list.size() - 1;
			String lastVersion = list.get(index).getVersion();
			int versionResult = VersionCompare(lastVersion, version);
			// 检测是否为最新版本
			if (versionResult == 0) {
				app.setHasNew(false);
				app.setMemo("当前已是最新版本");
				app.setVersion(lastVersion);
				app.setPath(list.get(index).getPath());
			} else {
				//不存在和不是最新的都要更新
				app.setHasNew(true);
				app.setMemo(list.get(index).getMemo());
				app.setPath(list.get(index).getPath());
				app.setVersion(lastVersion);
			}

			 

			/*
			 * for (int i = 0; i < list.size(); i++) { AppVersion item =
			 * list.get(i); ver = item.getVersion(); String[] temp =
			 * ver.split("\\.");
			 * 
			 * for (int s = 0; s < versionArray.length; s++) { if (s >=
			 * ver.length()) { vers = version; break; } if
			 * (temp[s].compareTo(versionArray[s]) > 0) { vers = ver; } } memo =
			 * item.getMemo(); path = item.getPath(); } app.setVersion(vers);
			 * app.setMemo(memo); app.setPath(path);
			 */
		} catch (Exception ex) {
			logger.error(ExceptionHelper.getExceptionDetail(ex));
		} finally {
			if (session != null)
				session.close();
		}
		return app;
	}

	private int VersionCompare(String arg0, String arg1) {
		if (StringUtils.isEmpty(arg0) && StringUtils.isEmpty(arg1))
			return 0;
		if (StringUtils.isEmpty(arg0))
			return -1;
		if (StringUtils.isEmpty(arg1))
			return 1;

		String[] temp0 = arg0.split("\\.");
		String[] temp1 = arg1.split("\\.");

		if (arg0.equals(arg1))
			return 0;

		int loopCount = Math.min(temp0.length, temp1.length);

		for (int i = 0; i < loopCount; i++) {
			if (Integer.valueOf(temp0[i]) > Integer.valueOf(temp1[i])) {
				return 1;
			}
		}
		return -1;

	}

	public void InsertDeviceLocInf(DeviceLocInfo dli) {
		SqlSession session = null;

		try {
			session = this.getSession(true);
			session.insert("com.jujin.mapper.InsertDeviceLocInfo", dli);
		} catch (Exception e) {
			logger.error(ExceptionHelper.getExceptionDetail(e));
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}
	}
}
