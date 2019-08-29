/*
 * Created by lzy on 2019-07-27 09:41.
 */
package com.lzy.demo.mybatis.mapper;


import com.lzy.demo.mybatis.entity.SampleMybatis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * The interface Mybatis sample mapper.
 *
 * @author lzy
 * @version v1.0
 */
public interface SampleMybatisMapper {

    /**
     * 函数名需要和userMapper.xml指定的id一致
     *
     * @param id the id
     * @return the user
     */
    SampleMybatis findOne(Integer id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<Map<String, Object>> findAll();

    /**
     * Select order list.
     *
     * @param orderBy the order by
     * @return the list
     */
    List<SampleMybatis> selectOrder(@Param("orderBy") String orderBy);


    /**
     * 使用注解
     *
     * @param id the id
     * @return the mybatis sample
     */
    @Select("select * from sample_mybatis where id = #{id}")
    SampleMybatis findOneUseAnnotation(Integer id);


    /**
     * Insert one.
     *
     * @param sampleMybatis the mybatis sample
     */
    void insertOne(SampleMybatis sampleMybatis);


    /**
     * Update one.
     *
     * @param id            the id
     * @param sampleMybatis the mybatis sample
     */
    void updateOne(@Param("id") Integer id, @Param("sampleMybatis") SampleMybatis sampleMybatis);


    /**
     * Delete one.
     *
     * @param id the id
     */
    void deleteOne(Integer id);


    /**
     * Select if list.
     *
     * @param name the name
     * @return the list
     */
    List<SampleMybatis> selectIf(@Param("name")String name);


    /**
     * Select choose list.
     *
     * @param name the name
     * @return the list
     */
    List<SampleMybatis> selectChoose(@Param("name")String name);


    /**
     * Select choose list.
     *
     * @param ids the ids
     * @return the list
     */
    List<SampleMybatis> selectForeach(@Param("ids")List<Integer> ids);
}
