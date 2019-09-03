/*
 * Created by LZY on 2017/3/5 20:09.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.service.SampleCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

/**
 * 缓存测试
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
public class CacheTest {
    @Resource
    protected SampleCacheService sampleCacheService;

    /**
     * 一级缓存是Session级别的,不在同一事务下,没有一级缓存
     */
    @Test
    public void testFirstLevelCacheWithoutTransactional() {
        sampleCacheService.firstLevelCacheWithoutTransactional(1);
    }

    /**
     * 一级缓存是Session级别的,同一个事务下,有一级缓存
     */
    @Test
    public void testFirstLevelCacheWithTransactional() {
        sampleCacheService.firstLevelCacheWithTransactional(1);
    }


    @TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-ehcache2.yml")
    public static class Ehcache2Test extends CacheTest {
        /**
         * 测试二级缓存
         */
        @Test
        public void testSecondLevelCache() {
            sampleCacheService.secondLevelCache(1, 2);
        }

        /**
         * 测试使用@QueryHints
         */
        @Test
        public void testQueryHint() {
            sampleCacheService.queryHint();
        }

        /**
         * 测试使用缓存
         */
        @Test
        public void testQueryCache() {
            sampleCacheService.queryCache();
        }
    }

    @TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-ehcache3.yml")
    public static class Ehcache3Test extends CacheTest {
        /**
         * 测试二级缓存
         */
        @Test
        public void testSecondLevelCache() {
            sampleCacheService.secondLevelCache(1, 2);
        }

        /**
         * 测试使用@QueryHints
         */
        @Test
        public void testQueryHint() {
            sampleCacheService.queryHint();
        }

        /**
         * 测试使用缓存
         */
        @Test
        public void testQueryCache() {
            sampleCacheService.queryCache();
        }
    }
}
