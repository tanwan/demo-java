/*
 * This file is generated by jOOQ.
 */
package com.lzy.demo.jooq.tables;


import com.lzy.demo.jooq.Demo;
import com.lzy.demo.jooq.Keys;
import com.lzy.demo.jooq.enums.SimpleMybatisUseStringEnum;
import com.lzy.demo.jooq.tables.records.SimpleMybatisRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SimpleMybatis extends TableImpl<SimpleMybatisRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>demo.simple_mybatis</code>
     */
    public static final SimpleMybatis SIMPLE_MYBATIS = new SimpleMybatis();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SimpleMybatisRecord> getRecordType() {
        return SimpleMybatisRecord.class;
    }

    /**
     * The column <code>demo.simple_mybatis.id</code>.
     */
    public final TableField<SimpleMybatisRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>demo.simple_mybatis.name</code>.
     */
    public final TableField<SimpleMybatisRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(45), this, "");

    /**
     * The column <code>demo.simple_mybatis.use_string_enum</code>.
     */
    public final TableField<SimpleMybatisRecord, SimpleMybatisUseStringEnum> USE_STRING_ENUM = createField(DSL.name("use_string_enum"), SQLDataType.VARCHAR(5).asEnumDataType(SimpleMybatisUseStringEnum.class), this, "");

    /**
     * The column <code>demo.simple_mybatis.use_index_enum</code>.
     */
    public final TableField<SimpleMybatisRecord, Integer> USE_INDEX_ENUM = createField(DSL.name("use_index_enum"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>demo.simple_mybatis.use_enum_value_enum</code>.
     */
    public final TableField<SimpleMybatisRecord, Integer> USE_ENUM_VALUE_ENUM = createField(DSL.name("use_enum_value_enum"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>demo.simple_mybatis.version</code>.
     */
    public final TableField<SimpleMybatisRecord, Integer> VERSION = createField(DSL.name("version"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>demo.simple_mybatis.del_flg</code>.
     */
    public final TableField<SimpleMybatisRecord, Integer> DEL_FLG = createField(DSL.name("del_flg"), SQLDataType.INTEGER, this, "");

    private SimpleMybatis(Name alias, Table<SimpleMybatisRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private SimpleMybatis(Name alias, Table<SimpleMybatisRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>demo.simple_mybatis</code> table reference
     */
    public SimpleMybatis(String alias) {
        this(DSL.name(alias), SIMPLE_MYBATIS);
    }

    /**
     * Create an aliased <code>demo.simple_mybatis</code> table reference
     */
    public SimpleMybatis(Name alias) {
        this(alias, SIMPLE_MYBATIS);
    }

    /**
     * Create a <code>demo.simple_mybatis</code> table reference
     */
    public SimpleMybatis() {
        this(DSL.name("simple_mybatis"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Demo.DEMO;
    }

    @Override
    public Identity<SimpleMybatisRecord, Integer> getIdentity() {
        return (Identity<SimpleMybatisRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<SimpleMybatisRecord> getPrimaryKey() {
        return Keys.KEY_SIMPLE_MYBATIS_PRIMARY;
    }

    @Override
    public SimpleMybatis as(String alias) {
        return new SimpleMybatis(DSL.name(alias), this);
    }

    @Override
    public SimpleMybatis as(Name alias) {
        return new SimpleMybatis(alias, this);
    }

    @Override
    public SimpleMybatis as(Table<?> alias) {
        return new SimpleMybatis(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public SimpleMybatis rename(String name) {
        return new SimpleMybatis(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SimpleMybatis rename(Name name) {
        return new SimpleMybatis(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public SimpleMybatis rename(Table<?> name) {
        return new SimpleMybatis(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SimpleMybatis where(Condition condition) {
        return new SimpleMybatis(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SimpleMybatis where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SimpleMybatis where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SimpleMybatis where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SimpleMybatis where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SimpleMybatis where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SimpleMybatis where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SimpleMybatis where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SimpleMybatis whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SimpleMybatis whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
