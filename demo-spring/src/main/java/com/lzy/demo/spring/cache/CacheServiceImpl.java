package com.lzy.demo.spring.cache;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 缓存的service
 * 注解@CacheConfig配置的只为当前类生效
 * 对于ehcache,spring-mvc不会动态创建缓存,也就是只会使用配置好的缓存,因此,这里的缓存名,一定需要在ehcache的配置中配置
 * 为了让ehcache2和ehcache3同步测试,这边scope使用prototype
 * @author lzy
 * @version v1.0
 */
@Service
@CacheConfig(cacheNames = "testCache")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CacheServiceImpl implements CacheService {

    private Map<Integer, String> map = new HashMap<>();
    {
        IntStream.range(1, 11).forEach(i -> map.put(i, i + ""));
    }

    /**
     * 有缓存的话,就直接从缓存获取,没有缓存的话,执行此方法,然后再根据条件,判断是否加入到缓存中
     *
     * @param key the key
     * @return the string
     */
    @Override
    @Cacheable(key = "'key:'+#key", condition = "#key<5")
    public String get(Integer key, AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        System.out.println("no use cache:" + key);
        return map.get(key);
    }

    /**
     * 每次都会执行此方法,然后把返回值更新到缓存中,注意这边的condition,如果满足的话,才会加入到缓存中,如果不指定condition,则都会加到缓存
     *
     * @param key the key
     * @return the string
     */
    @Override
    @CachePut(key = "'key:'+#key", condition = "#key<5")
    public String update(Integer key, AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        System.out.println("update:" + key);
        return map.computeIfPresent(key, (k, v) -> (Integer.valueOf(v) + 10) + "");
    }


    /**
     * Remove user.
     *
     * @param key the key
     */
    @Override
    @CacheEvict(key = "'key:'+#key")
    public void remove(Integer key, AtomicInteger atomicInteger) {
        atomicInteger.incrementAndGet();
        System.out.println("remove cache:" + key);
        map.remove(key);
    }


}
