package com.lzy.demo.jooq;


import com.lzy.demo.jooq.tables.NewTable;
import com.lzy.demo.jooq.tables.SimpleJooq;
import com.lzy.demo.jooq.tables.SimpleMybatis;


/**
 * Convenience access to all tables in demo
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>demo.new_table</code>.
     */
    public static final NewTable NEW_TABLE = NewTable.NEW_TABLE;

    /**
     * The table <code>demo.simple_jooq</code>.
     */
    public static final SimpleJooq SIMPLE_JOOQ = SimpleJooq.SIMPLE_JOOQ;

    /**
     * The table <code>demo.simple_mybatis</code>.
     */
    public static final SimpleMybatis SIMPLE_MYBATIS = SimpleMybatis.SIMPLE_MYBATIS;
}
