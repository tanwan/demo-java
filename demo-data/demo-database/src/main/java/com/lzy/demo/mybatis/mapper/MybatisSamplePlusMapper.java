/*
 * Created by lzy on 2019-07-28 19:47.
 */
package com.lzy.demo.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzy.demo.mybatis.entity.MybatisSample;

import java.io.Serializable;

/**
 * The interface Mybatis sample plus mapper.
 *
 * @author lzy
 * @version v1.0
 */
public interface MybatisSamplePlusMapper extends BaseMapper<MybatisSample> {


    /**
     * sql注入
     *
     * @param id the id
     * @return the mybatis sample
     * @see com.lzy.demo.mybatis.injector.CustomInjectorMethod
     */
    MybatisSample customInjectorMethod(Serializable id);
}
