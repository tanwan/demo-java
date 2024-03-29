package com.lzy.demo.redis;


import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.annotation.Resource;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = RedisAutoConfiguration.class)
@ActiveProfiles("redis")
public class SpringRedisTest {
    private static final String PREFIX = "spring:";


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试key value
     */
    @Test
    public void testKeyValue() {
        stringRedisTemplate.opsForValue().set(getKey("key"), "hello world");
        assertEquals("hello world", stringRedisTemplate.opsForValue().get(getKey("key")));
        // 设置过期时间
        stringRedisTemplate.opsForValue().set(getKey("key1"), "hello world1", Duration.ofSeconds(30));
        assertEquals("hello world1", stringRedisTemplate.opsForValue().get(getKey("key1")));
        // 不存在才设置
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(getKey("key2"), "hello world2", Duration.ofSeconds(30));
        assertTrue(result);
    }

    private String getKey(String key) {
        return PREFIX + key;
    }

}
