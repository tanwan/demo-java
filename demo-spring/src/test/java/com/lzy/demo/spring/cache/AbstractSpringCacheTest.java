package com.lzy.demo.spring.cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The type Abstract spring cache test.
 * 这边没有完整的启动springboot,所以使用ConfigDataApplicationContextInitializer加载application.yml的配置
 *
 * @author lzy
 * @version v1.0
 */
@EnableCaching
@Import(CacheAutoConfiguration.class)
@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {CacheServiceImpl.class})
public abstract class AbstractSpringCacheTest {
    @Autowired
    private CacheService cacheService;
    @Autowired
    private CacheManager cacheManager;

    private AtomicInteger atomicInteger;
    private Cache cache;

    /**
     * 初始化
     */
    @BeforeEach
    public void init() {
        atomicInteger = new AtomicInteger();
        cache = cacheManager.getCache("testCache");
    }

    /**
     * 清除缓存
     */
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
        assertThat(cache.get("key:" + key)).isNotNull();

        cacheService.get(key, atomicInteger);

        // 获取多次,CacheService.get只调用1次
        assertThat(atomicInteger.intValue()).isEqualTo(1);
    }

    /**
     * 测试不使用缓存获取
     */
    @Test
    public void testGetNoUseCache() {
        Integer key = 6;
        // 6不缓存
        cacheService.get(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNull();
        cacheService.get(key, atomicInteger);
        // 由于没有使用缓存,因此获取多次,CacheService.get调用多次
        assertThat(atomicInteger.intValue()).isEqualTo(2);
    }


    /**
     * 测试使用缓存更新
     */
    @Test
    public void testUpdateUseCache() {
        Integer key = 1;
        // 1有缓存
        assertThat(cacheService.get(key, atomicInteger)).isEqualTo("1");
        assertThat(cache.get("key:" + key)).isNotNull();

        // 对1进行更新
        cacheService.update(key, atomicInteger);

        // 再次获取1
        assertThat(cacheService.get(key, atomicInteger)).isEqualTo("11");
        assertThat(cache.get("key:" + key).get()).isEqualTo("11");
        // 最后一次获取使用的是缓存,因此atomicInteger的值为2
        assertThat(atomicInteger.intValue()).isEqualTo(2);
    }

    /**
     * 测试使用缓存更新
     */
    @Test
    public void testUpdateNoUseCache() {
        Integer key = 6;
        // 6不缓存
        assertThat(cacheService.get(key, atomicInteger));
        assertThat(cache.get("key:" + key)).isNull();

        // 对6进行更新
        cacheService.update(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNull();

        // 再次获取6
        cacheService.get(key, atomicInteger);

        // 因为不缓存,因此2次获取和一次更新,总次数为3
        assertThat(atomicInteger.intValue()).isEqualTo(3);
    }


    /**
     * 测试使用缓存删除
     */
    @Test
    public void testRemoveUseCache() {
        Integer key = 1;
        // 1有缓存
        cacheService.get(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNotNull();

        // 对1进行删除
        cacheService.remove(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNull();

        // 再次获取1
        cacheService.get(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNotNull();
        // 因为缓存被删除了,所以最后一次获取是没有缓存的,因此次数为3
        assertThat(atomicInteger.intValue()).isEqualTo(3);
    }

    /**
     * 测试使用缓存删除
     */
    @Test
    public void testRemoveNoUseCache() {
        Integer key = 6;
        // 6没有缓存
        cacheService.get(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNull();

        // 对6进行删除
        cacheService.remove(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNull();

        // 再次获取6
        cacheService.get(key, atomicInteger);
        assertThat(cache.get("key:" + key)).isNull();
        // 都没有使用缓存,因此,次数为3
        assertThat(atomicInteger.intValue()).isEqualTo(3);
    }
}
