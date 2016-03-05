package com.ocean.ddl.mybatis.converter;

import org.apache.ibatis.executor.statement.StatementHandler;

import com.ocean.ddl.mybatis.spring.support.StrategyHolder;
import com.ocean.ddl.mybatis.strategy.NoShardStrategy;
import com.ocean.ddl.mybatis.strategy.ShardStrategy;

public class DefaultSqlConverter implements SqlConverter {

	public String convert(String sql, StatementHandler statementHandler) {
		ShardStrategy strategy = StrategyHolder.getShardStrategy();
		if (strategy == null || strategy instanceof NoShardStrategy) {
			return sql;
		}
		return strategy.getTargetSql();
	}

}
