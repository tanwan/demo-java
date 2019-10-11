/*
 * Created by LZY on 2016-09-22.
 */
package com.lzy.demo.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * redisTemplate的配置类
 *
 * @author LZY
 * @version v1.0
 */
@Configuration
public class RedisTemplateConfiguration {
    /**
     * Spring boot 自动配置了StringRedisTemplate,和RedisTemplate(JdkSerializationRedisSerializer)
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis template
     * @see RedisAutoConfiguration#redisTemplate(org.springframework.data.redis.connection.RedisConnectionFactory)
     * @see org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
