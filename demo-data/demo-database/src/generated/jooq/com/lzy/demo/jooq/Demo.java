/*
 * This file is generated by jOOQ.
 */
package com.lzy.demo.jooq;


import com.lzy.demo.jooq.tables.SimpleJdbi;
import com.lzy.demo.jooq.tables.SimpleJooq;
import com.lzy.demo.jooq.tables.SimpleMybatis;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Demo extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>demo</code>
     */
    public static final Demo DEMO = new Demo();

    /**
     * The table <code>demo.simple_jdbi</code>.
     */
    public final SimpleJdbi SIMPLE_JDBI = SimpleJdbi.SIMPLE_JDBI;

    /**
     * The table <code>demo.simple_jooq</code>.
     */
    public final SimpleJooq SIMPLE_JOOQ = SimpleJooq.SIMPLE_JOOQ;

    /**
     * The table <code>demo.simple_mybatis</code>.
     */
    public final SimpleMybatis SIMPLE_MYBATIS = SimpleMybatis.SIMPLE_MYBATIS;

    /**
     * No further instances allowed
     */
    private Demo() {
        super("demo", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            SimpleJdbi.SIMPLE_JDBI,
            SimpleJooq.SIMPLE_JOOQ,
            SimpleMybatis.SIMPLE_MYBATIS
        );
    }
}
