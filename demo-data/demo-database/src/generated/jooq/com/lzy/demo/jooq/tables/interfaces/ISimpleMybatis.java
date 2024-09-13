/*
 * This file is generated by jOOQ.
 */
package com.lzy.demo.jooq.tables.interfaces;


import com.lzy.demo.jooq.enums.SimpleMybatisUseStringEnum;

import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public interface ISimpleMybatis extends Serializable {

    /**
     * Setter for <code>demo.simple_mybatis.id</code>.
     */
    public ISimpleMybatis setId(Integer value);

    /**
     * Getter for <code>demo.simple_mybatis.id</code>.
     */
    public Integer getId();

    /**
     * Setter for <code>demo.simple_mybatis.name</code>.
     */
    public ISimpleMybatis setName(String value);

    /**
     * Getter for <code>demo.simple_mybatis.name</code>.
     */
    public String getName();

    /**
     * Setter for <code>demo.simple_mybatis.use_string_enum</code>.
     */
    public ISimpleMybatis setUseStringEnum(SimpleMybatisUseStringEnum value);

    /**
     * Getter for <code>demo.simple_mybatis.use_string_enum</code>.
     */
    public SimpleMybatisUseStringEnum getUseStringEnum();

    /**
     * Setter for <code>demo.simple_mybatis.use_index_enum</code>.
     */
    public ISimpleMybatis setUseIndexEnum(Integer value);

    /**
     * Getter for <code>demo.simple_mybatis.use_index_enum</code>.
     */
    public Integer getUseIndexEnum();

    /**
     * Setter for <code>demo.simple_mybatis.use_enum_value_enum</code>.
     */
    public ISimpleMybatis setUseEnumValueEnum(Integer value);

    /**
     * Getter for <code>demo.simple_mybatis.use_enum_value_enum</code>.
     */
    public Integer getUseEnumValueEnum();

    /**
     * Setter for <code>demo.simple_mybatis.version</code>.
     */
    public ISimpleMybatis setVersion(Integer value);

    /**
     * Getter for <code>demo.simple_mybatis.version</code>.
     */
    public Integer getVersion();

    /**
     * Setter for <code>demo.simple_mybatis.del_flg</code>.
     */
    public ISimpleMybatis setDelFlg(Integer value);

    /**
     * Getter for <code>demo.simple_mybatis.del_flg</code>.
     */
    public Integer getDelFlg();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common
     * interface ISimpleMybatis
     */
    public void from(ISimpleMybatis from);

    /**
     * Copy data into another generated Record/POJO implementing the common
     * interface ISimpleMybatis
     */
    public <E extends ISimpleMybatis> E into(E into);
}
