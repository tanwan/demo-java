package com.lzy.demo.mybatis.mapper;


import com.lzy.demo.mybatis.entity.SimpleMybatis;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SimpleMybatisMapper {

    /**
     * 函数名需要和userMapper.xml指定的id一致
     *
     * @param id the id
     * @return the user
     */
    SimpleMybatis findOne(Integer id);

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
    List<SimpleMybatis> selectOrder(@Param("orderBy") String orderBy);


    /**
     * 使用注解
     *
     * @param id the id
     * @return the mybatis simple
     */
    @Select("select * from simple_mybatis where id = #{id}")
    SimpleMybatis findOneUseAnnotation(Integer id);


    /**
     * Insert one.
     *
     * @param simpleMybatis the mybatis simple
     */
    void insertOne(SimpleMybatis simpleMybatis);


    /**
     * Update one.
     *
     * @param id            the id
     * @param simpleMybatis the mybatis simple
     */
    void updateOne(@Param("id") Integer id, @Param("simpleMybatis") SimpleMybatis simpleMybatis);


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
    List<SimpleMybatis> selectIf(@Param("name")String name);


    /**
     * Select choose list.
     *
     * @param name the name
     * @return the list
     */
    List<SimpleMybatis> selectChoose(@Param("name")String name);


    /**
     * Select choose list.
     *
     * @param ids the ids
     * @return the list
     */
    List<SimpleMybatis> selectForeach(@Param("ids")List<Integer> ids);
}
