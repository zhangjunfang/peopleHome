package com.ocean.session.sessionCluster.users;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Rob Winch
 */

@Configuration
@EnableRedisHttpSession
public class Config {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		return  new JedisConnectionFactory();
	}
}