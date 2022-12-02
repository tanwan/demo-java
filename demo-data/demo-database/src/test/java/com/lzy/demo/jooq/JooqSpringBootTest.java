package com.lzy.demo.jooq;

import com.lzy.demo.jooq.dao.SimpleJooqDao;
import com.lzy.demo.jooq.tables.pojos.SimpleJooq;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.annotation.Resource;
import java.util.List;

@SpringBootTest
@SpringBootApplication
public class JooqSpringBootTest {

    @Resource
    private SimpleJooqDao simpleJooqDao;

    @Resource
    private DSLContext dslContext;


    /**
     * 使用springboot
     */
    @Test
    public void testSelect() {
        List<SimpleJooq> list = simpleJooqDao.fetch(Tables.SIMPLE_JOOQ.NAME, "2");
        System.out.println(list);
        list = dslContext.select().from(Tables.SIMPLE_JOOQ).where(Tables.SIMPLE_JOOQ.NAME.eq("2")).fetchInto(SimpleJooq.class);
        System.out.println(list);
    }
}
