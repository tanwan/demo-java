/*
 * Created by lzy on 2019-07-28 19:47.
 */
package com.lzy.demo.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.entity.SampleMybatis;

import java.io.Serializable;

/**
 * The interface Mybatis sample plus mapper.
 *
 * @author lzy
 * @version v1.0
 */
public interface SampleMybatisPlusMapper extends BaseMapper<SampleMybatis> {


    /**
     * sql注入
     *
     * @param id the id
     * @return the mybatis sample
     * @see com.lzy.demo.mybatis.injector.CustomInjectorMethod
     */
    SampleMybatis customInjectorMethod(Serializable id);


    /**
     * 分页查询
     * @param page page
     * @return page
     */
    Page<SampleMybatis> customPage(Page<SampleMybatis> page);
}
