package com.lzy.demo.jooq;


import com.lzy.demo.jooq.tables.NewTable;
import com.lzy.demo.jooq.tables.SimpleJooq;
import com.lzy.demo.jooq.tables.SimpleMybatis;
import com.lzy.demo.jooq.tables.records.NewTableRecord;
import com.lzy.demo.jooq.tables.records.SimpleJooqRecord;
import com.lzy.demo.jooq.tables.records.SimpleMybatisRecord;

import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>demo</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<SimpleJooqRecord, Integer> IDENTITY_SIMPLE_JOOQ = Identities0.IDENTITY_SIMPLE_JOOQ;
    public static final Identity<SimpleMybatisRecord, Integer> IDENTITY_SIMPLE_MYBATIS = Identities0.IDENTITY_SIMPLE_MYBATIS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<NewTableRecord> KEY_NEW_TABLE_PRIMARY = UniqueKeys0.KEY_NEW_TABLE_PRIMARY;
    public static final UniqueKey<SimpleJooqRecord> KEY_SIMPLE_JOOQ_PRIMARY = UniqueKeys0.KEY_SIMPLE_JOOQ_PRIMARY;
    public static final UniqueKey<SimpleMybatisRecord> KEY_SIMPLE_MYBATIS_PRIMARY = UniqueKeys0.KEY_SIMPLE_MYBATIS_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<SimpleJooqRecord, Integer> IDENTITY_SIMPLE_JOOQ = Internal.createIdentity(SimpleJooq.SIMPLE_JOOQ, SimpleJooq.SIMPLE_JOOQ.ID);
        public static Identity<SimpleMybatisRecord, Integer> IDENTITY_SIMPLE_MYBATIS = Internal.createIdentity(SimpleMybatis.SIMPLE_MYBATIS, SimpleMybatis.SIMPLE_MYBATIS.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<NewTableRecord> KEY_NEW_TABLE_PRIMARY = Internal.createUniqueKey(NewTable.NEW_TABLE, "KEY_new_table_PRIMARY", new TableField[] { NewTable.NEW_TABLE.ID }, true);
        public static final UniqueKey<SimpleJooqRecord> KEY_SIMPLE_JOOQ_PRIMARY = Internal.createUniqueKey(SimpleJooq.SIMPLE_JOOQ, "KEY_simple_jooq_PRIMARY", new TableField[] { SimpleJooq.SIMPLE_JOOQ.ID }, true);
        public static final UniqueKey<SimpleMybatisRecord> KEY_SIMPLE_MYBATIS_PRIMARY = Internal.createUniqueKey(SimpleMybatis.SIMPLE_MYBATIS, "KEY_simple_mybatis_PRIMARY", new TableField[] { SimpleMybatis.SIMPLE_MYBATIS.ID }, true);
    }
}
