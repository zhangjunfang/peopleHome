package com.ocean.session.sessionCluster.rest;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration   
@EnableRedisHttpSession // <1>
public class HttpSessionConfig {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();  // <2>
	}

	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy(); // <3>
	}
}
