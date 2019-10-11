/*
 * Created by LZY on 2016-09-26 22:13.
 */
package com.lzy.demo.redis;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * The type Spring redis test.
 *
 * @author LZY
 * @version v1.0
 */
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(initializers = ConfigFileApplicationContextInitializer.class, classes = RedisAutoConfiguration.class)
@TestPropertySource(properties = "spring.config.location=classpath:redis.yml")
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
        Assertions.assertThat(stringRedisTemplate.opsForValue().get(getKey("key")))
                .isEqualTo("hello world");
        // 设置过期时间
        stringRedisTemplate.opsForValue().set(getKey("key1"), "hello world1", Duration.ofSeconds(30));
        Assertions.assertThat(stringRedisTemplate.opsForValue().get(getKey("key1")))
                .isEqualTo("hello world1");
        // 不存在才设置
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(getKey("key2"), "hello world2", Duration.ofSeconds(30));
        Assertions.assertThat(result).isEqualTo(true);
    }

    private String getKey(String key) {
        return PREFIX + key;
    }

}
