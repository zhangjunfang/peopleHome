package com.ocean.session.sessionCluster.httpssion;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession //springSessionRepositoryFilter 
public class Config {
	//springSessionRepositoryFilter
        @Bean
        public JedisConnectionFactory connectionFactory() {
                return new JedisConnectionFactory(); 
        }
}