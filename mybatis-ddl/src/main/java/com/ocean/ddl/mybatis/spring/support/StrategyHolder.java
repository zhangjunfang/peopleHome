package com.ocean.ddl.mybatis.spring.support;

import com.ocean.ddl.mybatis.strategy.ShardStrategy;

public class StrategyHolder {

	private static ThreadLocal<ShardStrategy> holder = new ThreadLocal<ShardStrategy>();

	public static ShardStrategy getShardStrategy() {
		return holder.get();
	}

	static void removeShardStrategy() {
		holder.remove();
	}

	static void setShardStrategy(ShardStrategy shardStrategy) {
		holder.set(shardStrategy);
	}

}
