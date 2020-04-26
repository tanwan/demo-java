/*
 * Created by lzy on 2019/8/24 3:48 PM.
 */
package com.lzy.demo.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.demo.mybatis.entity.SimpleMybatis;
import com.lzy.demo.mybatis.mapper.SimpleMybatisPlusMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 缓存
 *
 * @author lzy
 * @version v1.0
 */
@Service
public class SimpleCacheService {


    @Resource
    private SimpleMybatisPlusMapper simpleMybatisPlusMapper;

    /**
     * 如果不在同一个事务下执行,将不会使用一级缓存
     *
     * @param id the id
     */
    public void firstLevelCacheWithoutTransactional(Integer id) {
        simpleMybatisPlusMapper.selectById(id);
        simpleMybatisPlusMapper.selectById(id);
    }

    /**
     * 在同一个事务下执行,会有一级缓存
     *
     * @param id the id
     */
    @Transactional
    public void firstLevelCacheWithTransactional(Integer id) {
        // 这边跟hibernate不一样的是,使用其它方法查询的不会加入到一级缓存
        simpleMybatisPlusMapper.selectOne(new QueryWrapper<SimpleMybatis>().eq("id", id));
        simpleMybatisPlusMapper.selectById(id);
        simpleMybatisPlusMapper.selectById(id);
    }
}
