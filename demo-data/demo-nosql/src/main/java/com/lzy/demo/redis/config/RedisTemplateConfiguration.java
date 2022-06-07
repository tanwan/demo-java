package com.lzy.demo.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

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
