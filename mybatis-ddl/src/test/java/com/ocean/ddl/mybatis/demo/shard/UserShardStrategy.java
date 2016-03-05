package com.ocean.ddl.mybatis.demo.shard;

import java.util.Map;

import javax.sql.DataSource;

import com.ocean.ddl.mybatis.ShardParam;
import com.ocean.ddl.mybatis.strategy.ShardStrategy;

/**
 * 
 * @author Kolor
 */
public class UserShardStrategy extends ShardStrategy {

	@Override
	public DataSource getTargetDataSource() {
		ShardParam shardParam = getShardParam();
		//
		Long param = (Long) shardParam.getShardValue();
		Map<String, DataSource> map = this.getShardDataSources();
		if (param > 100) {
			return map.get("dataSourceSlave");
		}
		return getMainDataSource();
	}

	@Override
	public String getTargetSql() {
		String targetSql = getSql();
		ShardParam shardParam = getShardParam();
		//
		Long param = (Long) shardParam.getShardValue();
		String tableName = "user_" + (param % 2);
		targetSql = targetSql.replaceAll("\\$\\[user\\]\\$", tableName);
		return targetSql;
	}

}
