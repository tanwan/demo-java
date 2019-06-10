/*
 * Created by lzy on 2019-06-03 17:54.
 */
package com.lzy.demo.spring.cache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The interface Cache service.
 *
 * @author lzy
 * @version v1.0
 */
public interface CacheService {


    /**
     * Get string.
     *
     * @param key           the key
     * @param atomicInteger the atomic integer
     * @return the string
     */
    String get(Integer key, AtomicInteger atomicInteger);

    /**
     * Update string.
     *
     * @param key           the key
     * @param atomicInteger the atomic integer
     * @return the string
     */
    String update(Integer key, AtomicInteger atomicInteger);

    /**
     * Remove user.
     *
     * @param key           the key
     * @param atomicInteger the atomic integer
     */
    void remove(Integer key, AtomicInteger atomicInteger);
}
