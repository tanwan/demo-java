package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.service.SimpleCacheService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest(classes = JpaApplication.class)
@ActiveProfiles("jpa")
public class CacheTest {
    @Resource
    protected SimpleCacheService simpleCacheService;

    /**
     * 一级缓存是Session级别的,不在同一事务下,没有一级缓存
     */
    @Test
    public void testFirstLevelCacheWithoutTransactional() {
        simpleCacheService.firstLevelCacheWithoutTransactional(1);
    }

    /**
     * 一级缓存是Session级别的,同一个事务下,有一级缓存
     */
    @Test
    public void testFirstLevelCacheWithTransactional() {
        simpleCacheService.firstLevelCacheWithTransactional(1);
    }


    @ActiveProfiles({"jpa", "jpa-ehcache2"})
    public static class Ehcache2Test extends CacheTest {
        /**
         * 测试二级缓存
         */
        @Test
        public void testSecondLevelCache() {
            simpleCacheService.secondLevelCache(1, 2);
        }

        /**
         * 测试使用@QueryHints
         */
        @Test
        public void testQueryHint() {
            simpleCacheService.queryHint();
        }

        /**
         * 测试使用缓存
         */
        @Test
        public void testQueryCache() {
            simpleCacheService.queryCache();
        }
    }

    @ActiveProfiles({"jpa", "jpa-ehcache3"})
    public static class Ehcache3Test extends CacheTest {
        /**
         * 测试二级缓存
         */
        @Test
        public void testSecondLevelCache() {
            simpleCacheService.secondLevelCache(1, 2);
        }

        /**
         * 测试使用@QueryHints
         */
        @Test
        public void testQueryHint() {
            simpleCacheService.queryHint();
        }

        /**
         * 测试使用缓存
         */
        @Test
        public void testQueryCache() {
            simpleCacheService.queryCache();
        }
    }
}
