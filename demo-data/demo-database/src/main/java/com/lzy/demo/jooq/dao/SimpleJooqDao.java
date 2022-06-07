package com.lzy.demo.jooq.dao;

import com.lzy.demo.jooq.BaseDAO;
import com.lzy.demo.jooq.Tables;
import com.lzy.demo.jooq.tables.pojos.SimpleJooq;
import com.lzy.demo.jooq.tables.records.SimpleJooqRecord;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleJooqDao extends BaseDAO<SimpleJooqRecord, SimpleJooq, Integer> {
    public SimpleJooqDao() {
        super(Tables.SIMPLE_JOOQ, SimpleJooq.class);
    }
}
