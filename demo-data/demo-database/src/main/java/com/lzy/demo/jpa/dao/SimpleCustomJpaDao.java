package com.lzy.demo.jpa.dao;

import com.lzy.demo.jpa.entity.SimpleJpa;

import javax.persistence.Tuple;
import java.util.List;

public interface SimpleCustomJpaDao {

    /**
     * 使用自定义查询
     *
     * @param name the name
     * @return the list
     */
    List<SimpleJpa> customQuery(String name);

    /**
     * 使用缓存,效果同@QueryHints
     *
     * @param name the name
     * @return the list
     */
    List<SimpleJpa> queryCache(String name);

    /**
     * Criteria query list.
     *
     * @param name the name
     * @param age  the age
     * @return the list
     */
    List<SimpleJpa> criteriaQuery(String name, Integer age);

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
