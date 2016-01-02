package com.ocean.test.system.service;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ocean.framework.core.utils.Identities;
import com.ocean.test.db1.domain.TsysApp;
import com.ocean.test.db1.service.TsysAppService;
import com.ocean.test.db2.domain.TsysPost;
import com.ocean.test.db2.service.TsysPostService;
import com.ocean.test.util.BaseTestCase;

/**
 * 单元测试案例
 * 
 * @author ocean
 * 
 */
public class AtomikosTransServiceTestCase extends BaseTestCase {
	@Autowired
	private TsysAppService<TsysApp> tsysAppService;

	@Autowired
	private TsysPostService<TsysPost> tsysPostService;

	/**
	 * 如果是分布式数据源，需要重写setDataSource方法
	 */

	@Resource(name = "db1_dataSource")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Test
	public void testInsertDB1Table() {
		TsysApp tsysApp = null;
		for (int j = 0; j < 100; j++) {
			tsysApp = new TsysApp();
			tsysApp.setAppId(Identities.uuid());
			tsysApp.setAppName("子系统、应用" + Identities.uuid());
			tsysApp.setOrderNo(j);
			tsysApp.setAppShortname("app" + j);

			try {
				tsysAppService.insert(tsysApp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 模拟正常数据插入不同数据库中的表
	 */
	@Test
	public void testInsertTwoTable() {
		TsysApp tsysApp = null;
		tsysApp = new TsysApp();
		tsysApp.setAppId(Identities.uuid());
		tsysApp.setAppName("子系统、应用" + Identities.uuid());
		tsysApp.setOrderNo(1000);
		tsysApp.setAppShortname("财政一体化平台");
		TsysPost tsysPost = new TsysPost();
		tsysPost.setPostId(Identities.uuid());
		tsysPost.setPostName("董事长岗位");

		try {
			tsysAppService.insert(tsysApp);
			tsysPostService.insert(tsysPost);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 模拟超过表字段的长度情况下，两个数据库的插入操作是否同步回滚
	 */
	@Test
	public void testInsertTwoTableRollBack() {
		TsysApp tsysApp = null;
		tsysApp = new TsysApp();
		tsysApp.setAppId(Identities.uuid());
		tsysApp.setAppName("子系统、应用" + Identities.uuid());
		tsysApp.setOrderNo(1000);
		//应用名称超长，事务需要回滚
		tsysApp.setAppShortname("财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台财政一体化平台");
		TsysPost tsysPost = new TsysPost();
		tsysPost.setPostId(Identities.uuid());
		tsysPost.setPostName("董事长岗位");

		try {
			tsysAppService.insert(tsysApp);
			tsysPostService.insert(tsysPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
