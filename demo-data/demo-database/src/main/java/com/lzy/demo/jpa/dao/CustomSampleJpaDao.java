/*
 * Created by LZY on 2017/3/12 8:09.
 */
package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SampleJpa;

import javax.persistence.Tuple;
import java.util.List;

/**
 * 自定义Dao
 *
 * @author LZY
 * @version v1.0
 */
public interface CustomSampleJpaDao {

    /**
     * 使用自定义查询
     *
     * @param name the name
     * @return the list
     */
    List<SampleJpa> customQuery(String name);

    /**
     * 使用缓存,效果同@QueryHints
     *
     * @param name the name
     * @return the list
     */
    List<SampleJpa> queryCache(String name);

    /**
     * Criteria query list.
     *
     * @param name the name
     * @param age  the age
     * @return the list
     */
    List<SampleJpa> criteriaQuery(String name, Integer age);

    /**
     * 返回自定义的类
     *
     * @param name the name
     * @return the object
     */
    Object criteriaQueryReturnCustom(String name);


    /**
     * 返回Object
     *
     * @param name the name
     * @return the object
     */
    List<Object[]> criteriaQueryReturnObject(String name);

    /**
     * 返回Tuple
     *
     * @param name the name
     * @return the object
     */
    List<Tuple> criteriaQueryReturnTuple(String name);

    /**
     * 多表查询
     *
     * @param userId    the user id
     * @param imageName the image name
     * @return the object
     */
    List<Tuple> criteriaQueryJoin(Integer userId, String imageName);
}
