/*
 * This file is generated by jOOQ.
 */
package com.lzy.demo.jooq;


import com.lzy.demo.jooq.tables.SimpleJdbi;
import com.lzy.demo.jooq.tables.SimpleJooq;
import com.lzy.demo.jooq.tables.SimpleMybatis;
import com.lzy.demo.jooq.tables.records.SimpleJdbiRecord;
import com.lzy.demo.jooq.tables.records.SimpleJooqRecord;
import com.lzy.demo.jooq.tables.records.SimpleMybatisRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * demo.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<SimpleJdbiRecord> KEY_SIMPLE_JDBI_PRIMARY = Internal.createUniqueKey(SimpleJdbi.SIMPLE_JDBI, DSL.name("KEY_simple_jdbi_PRIMARY"), new TableField[] { SimpleJdbi.SIMPLE_JDBI.ID }, true);
    public static final UniqueKey<SimpleJooqRecord> KEY_SIMPLE_JOOQ_PRIMARY = Internal.createUniqueKey(SimpleJooq.SIMPLE_JOOQ, DSL.name("KEY_simple_jooq_PRIMARY"), new TableField[] { SimpleJooq.SIMPLE_JOOQ.ID }, true);
    public static final UniqueKey<SimpleMybatisRecord> KEY_SIMPLE_MYBATIS_PRIMARY = Internal.createUniqueKey(SimpleMybatis.SIMPLE_MYBATIS, DSL.name("KEY_simple_mybatis_PRIMARY"), new TableField[] { SimpleMybatis.SIMPLE_MYBATIS.ID }, true);
}
