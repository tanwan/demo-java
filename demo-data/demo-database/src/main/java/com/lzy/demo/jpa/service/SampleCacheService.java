/*
 * Created by LZY on 2017/3/11 21:50.
 */


package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.dao.SampleJpaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 缓存service
 *
 * @author LZY
 * @version v1.0
 */
@Service
public class SampleCacheService {
    @Resource
    private SampleJpaDao sampleJpaDao;

    /**
     * 如果不在同一个事务下执行，将不会使用一级缓存(可以使用二级缓存)
     *
     * @param id the id
     */
    public void firstLevelCacheWithoutTransactional(Integer id) {
        sampleJpaDao.findById(id);
        sampleJpaDao.findById(id);
    }

    /**
     * 在同一个事务下执行，会有一级缓存
     * 一级缓存是Session级别的缓存,需要在同一个事务中,实际上是使用Map的方式来实现缓存的,key为实体的主键,value为实体,
     * 因此只有使用主键查询时(只能是对象级别的,查询属性级别的不行),才会命中缓存,save(),update()和各种对象级别的查询,
     * 都会把这些对象加入到缓存中
     *
     * @param id the id
     */
    @Transactional
    public void firstLevelCacheWithTransactional(Integer id) {
        // findByName查询后会加入到缓存中
        sampleJpaDao.findByName("lzy");
        sampleJpaDao.findById(id);
        sampleJpaDao.findById(id);
    }


    /**
     * 使用@QueryHints做查询缓存
     */
    public void queryHint() {
        sampleJpaDao.queryHint("lzy");
        sampleJpaDao.queryHint("lzy");
        sampleJpaDao.queryHint("1");
        sampleJpaDao.queryHint("lzy");
    }


    /**
     * 查询缓存效果同@QueryHints,同一个条件会进行缓存,不需要在同一事务下
     */
    public void queryCache() {
        sampleJpaDao.queryCache("lzy");
        sampleJpaDao.queryCache("lzy");
        sampleJpaDao.queryCache("1");
        sampleJpaDao.queryCache("lzy");
    }

    /**
     * 二级缓存:不在同一个事务下也可以使用
     *
     * @param id1 the id 1
     * @param id2 the id 2
     */
    @Transactional(propagation = Propagation.NEVER)
    public void secondLevelCache(Integer id1, Integer id2) {
        // key值同样是主键,value是实体
        sampleJpaDao.findById(id1);
        sampleJpaDao.findById(id1);
        sampleJpaDao.findById(id2);
        sampleJpaDao.findById(id1);
    }
}
