/*
 * This file is generated by jOOQ.
 */
package com.lzy.demo.jooq.tables.interfaces;


import com.lzy.demo.jooq.enums.SimpleJdbiUseStringEnum;

import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public interface ISimpleJdbi extends Serializable {

    /**
     * Setter for <code>demo.simple_jdbi.id</code>.
     */
    public ISimpleJdbi setId(Integer value);

    /**
     * Getter for <code>demo.simple_jdbi.id</code>.
     */
    public Integer getId();

    /**
     * Setter for <code>demo.simple_jdbi.name</code>.
     */
    public ISimpleJdbi setName(String value);

    /**
     * Getter for <code>demo.simple_jdbi.name</code>.
     */
    public String getName();

    /**
     * Setter for <code>demo.simple_jdbi.use_string_enum</code>.
     */
    public ISimpleJdbi setUseStringEnum(SimpleJdbiUseStringEnum value);

    /**
     * Getter for <code>demo.simple_jdbi.use_string_enum</code>.
     */
    public SimpleJdbiUseStringEnum getUseStringEnum();

    /**
     * Setter for <code>demo.simple_jdbi.use_enum_value_enum</code>.
     */
    public ISimpleJdbi setUseEnumValueEnum(Integer value);

    /**
     * Getter for <code>demo.simple_jdbi.use_enum_value_enum</code>.
     */
    public Integer getUseEnumValueEnum();

    // -------------------------------------------------------------------------
    // FROM and INTO
    // -------------------------------------------------------------------------

    /**
     * Load data from another generated Record/POJO implementing the common
     * interface ISimpleJdbi
     */
    public void from(ISimpleJdbi from);

    /**
     * Copy data into another generated Record/POJO implementing the common
     * interface ISimpleJdbi
     */
    public <E extends ISimpleJdbi> E into(E into);
}
