package com.ocean.ddl.mybatis.demo.dao.impl;

import com.ocean.ddl.mybatis.ShardParam;
import com.ocean.ddl.mybatis.demo.dao.UserDao;
import com.ocean.ddl.mybatis.demo.dao.entity.UserEntity;
import com.ocean.ddl.mybatis.spring.support.SqlSessionDaoSupport;

/**
 *  
 * @author Kolor
 */
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

	public boolean insertUser(UserEntity user) {
		ShardParam shardParam = new ShardParam("Shard-User", user.getId(), user);
		
		return getSqlSession().insert("NS-User.insertUser", shardParam) > 0;
	}

}
