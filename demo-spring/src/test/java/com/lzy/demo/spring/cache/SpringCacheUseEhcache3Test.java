package com.lzy.demo.spring.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Disabled("ehcache3底层还是依赖还是java ee,而这边引入的则是jakarta")
@ActiveProfiles("spring-cache-jcache")
@EnableCaching
@Import(CacheAutoConfiguration.class)
@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {CacheServiceImpl.class})
public class SpringCacheUseEhcache3Test {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private CacheManager cacheManager;

    private AtomicInteger atomicInteger;
    private Cache cache;

    @BeforeEach
    public void init() {
        atomicInteger = new AtomicInteger();
        cache = cacheManager.getCache("testCache");
    }

    @AfterEach
    public void reset() {
        cacheManager.getCache("testCache").clear();
    }


    /**
     * 测试使用缓存获取
     */
    @Test
    public void testGetUseCache() {
        Integer key = 1;
        // 1会缓存
        cacheService.get(key, atomicInteger);
        assertNotNull(cache.get("key:" + key));

        cacheService.get(key, atomicInteger);

        // 获取多次,CacheService.get只调用1次
        assertEquals(1, atomicInteger.intValue());
    }

    /**
     * 测试不使用缓存获取
     */
    @Test
    public void testGetNoUseCache() {
        Integer key = 6;
        // 6不缓存
        cacheService.get(key, atomicInteger);
        assertNull(cache.get("key:" + key));
        cacheService.get(key, atomicInteger);
        // 由于没有使用缓存,因此获取多次,CacheService.get调用多次
        assertEquals(2, atomicInteger.intValue());
    }


    /**
     * 测试使用缓存更新
     */
    @Test
    public void testUpdateUseCache() {
        Integer key = 1;
        // 1有缓存
        assertEquals("1", cacheService.get(key, atomicInteger));
        assertNotNull(cache.get("key:" + key));

        // 对1进行更新
        cacheService.update(key, atomicInteger);

        // 再次获取1
        assertEquals("11", cacheService.get(key, atomicInteger));
        assertEquals("11", cache.get("key:" + key).get());
        // 最后一次获取使用的是缓存,因此atomicInteger的值为2
        assertEquals(2, atomicInteger.intValue());
    }

    /**
     * 测试使用缓存更新
     */
    @Test
    public void testUpdateNoUseCache() {
        Integer key = 6;
        // 6不缓存
        assertNotNull(cacheService.get(key, atomicInteger));
        assertNull(cache.get("key:" + key));

        // 对6进行更新
        cacheService.update(key, atomicInteger);
        assertNull(cache.get("key:" + key));

        // 再次获取6
        cacheService.get(key, atomicInteger);

        // 因为不缓存,因此2次获取和一次更新,总次数为3
        assertEquals(3, atomicInteger.intValue());
    }


    /**
     * 测试使用缓存删除
     */
    @Test
    public void testRemoveUseCache() {
        Integer key = 1;
        // 1有缓存
        cacheService.get(key, atomicInteger);
        assertNotNull(cache.get("key:" + key));

        // 对1进行删除
        cacheService.remove(key, atomicInteger);
        assertNull(cache.get("key:" + key));

        // 再次获取1
        cacheService.get(key, atomicInteger);
        assertNotNull(cache.get("key:" + key));
        // 因为缓存被删除了,所以最后一次获取是没有缓存的,因此次数为3
        assertEquals(3, atomicInteger.intValue());
    }

    /**
     * 测试使用缓存删除
     */
    @Test
    public void testRemoveNoUseCache() {
        Integer key = 6;
        // 6没有缓存
        cacheService.get(key, atomicInteger);
        assertNull(cache.get("key:" + key));

        // 对6进行删除
        cacheService.remove(key, atomicInteger);
        assertNull(cache.get("key:" + key));

        // 再次获取6
        cacheService.get(key, atomicInteger);
        assertNull(cache.get("key:" + key));
        // 都没有使用缓存,因此,次数为3
        assertEquals(3, atomicInteger.intValue());
    }
}
