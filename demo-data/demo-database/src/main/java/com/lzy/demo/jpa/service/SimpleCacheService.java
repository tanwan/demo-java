package com.lzy.demo.jpa.service;

import com.lzy.demo.jpa.dao.SimpleJpaDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

@Service
public class SimpleCacheService {
    @Resource
    private SimpleJpaDao simpleJpaDao;

    /**
     * 如果不在同一个事务下执行，将不会使用一级缓存(可以使用二级缓存)
     *
     * @param id the id
     */
    public void firstLevelCacheWithoutTransactional(Integer id) {
        simpleJpaDao.findById(id);
        simpleJpaDao.findById(id);
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
        simpleJpaDao.findByName("lzy");
        simpleJpaDao.findById(id);
        simpleJpaDao.findById(id);
    }


    /**
     * 使用@QueryHints做查询缓存
     */
    public void queryHint() {
        simpleJpaDao.queryHint("lzy");
        simpleJpaDao.queryHint("lzy");
        simpleJpaDao.queryHint("1");
        simpleJpaDao.queryHint("lzy");
    }


    /**
     * 查询缓存效果同@QueryHints,同一个条件会进行缓存,不需要在同一事务下
     */
    public void queryCache() {
        simpleJpaDao.queryCache("lzy");
        simpleJpaDao.queryCache("lzy");
        simpleJpaDao.queryCache("1");
        simpleJpaDao.queryCache("lzy");
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
        simpleJpaDao.findById(id1);
        simpleJpaDao.findById(id1);
        simpleJpaDao.findById(id2);
        simpleJpaDao.findById(id1);
    }
}
