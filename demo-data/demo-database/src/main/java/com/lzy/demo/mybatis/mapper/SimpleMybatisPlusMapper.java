/*
 * Created by lzy on 2019-07-28 19:47.
 */
package com.lzy.demo.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.entity.SimpleMybatis;

import java.io.Serializable;

/**
 * The interface Simple Mybatis plus mapper.
 *
 * @author lzy
 * @version v1.0
 */
public interface SimpleMybatisPlusMapper extends BaseMapper<SimpleMybatis> {


    /**
     * sql注入
     *
     * @param id the id
     * @return the simple mybatis
     * @see com.lzy.demo.mybatis.injector.CustomInjectorMethod
     */
    SimpleMybatis customInjectorMethod(Serializable id);


    /**
     * 分页查询
     * @param page page
     * @return page
     */
    Page<SimpleMybatis> customPage(Page<SimpleMybatis> page);
}
