package com.lzy.demo.jooq;

import com.lzy.demo.jooq.dao.SimpleJooqDao;
import com.lzy.demo.jooq.entity.JoinRecord;
import com.lzy.demo.jooq.enums.SimpleJooqUseStringEnum;
import com.lzy.demo.jooq.enums.SimpleMybatisUseStringEnum;
import com.lzy.demo.jooq.tables.pojos.SimpleJooq;
import com.lzy.demo.jooq.tables.records.SimpleJooqRecord;
import com.lzy.demo.utils.ConfigUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitStep;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * sql脚本在/flyway/migration/V1_1__init.sql,使用flyway
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JooqTest {

    private DSLContext dslContext;

    /**
     * 创建jooq执行器
     *
     * @throws SQLException the sql exception
     */
    @BeforeAll
    public void init() throws SQLException {
        Connection connection = DriverManager.getConnection(ConfigUtils.getDBUrl(), ConfigUtils.getDBUsername(), ConfigUtils.getDBPassword());
        //jooq执行器
        dslContext = DSL.using(connection, SQLDialect.MYSQL);
    }

    /**
     * 测试查询全部
     */
    @Test
    public void testSelectAll() {
        //fetch返回结果集
        Result<Record> recordResult = dslContext.select().from(Tables.SIMPLE_JOOQ).fetch();
        recordResult.forEach(record -> {
            Integer id = record.getValue(Tables.SIMPLE_JOOQ.ID);
            //枚举
            SimpleJooqUseStringEnum simpleJooqUseStringEnum = record.getValue(Tables.SIMPLE_JOOQ.USE_STRING_ENUM);
            System.out.printf("id:%s,simpleJooqUseStringEnum:%s%n", id, simpleJooqUseStringEnum);
        });

        //Record.into和Result.into都可以将结果集转换为实体类
        Result<SimpleJooqRecord> userRecordResult = recordResult.into(Tables.SIMPLE_JOOQ);
        //SimpleJooqRecord是Record的子类
        userRecordResult.forEach(record -> System.out.printf("id:%s,name:%s%n", record.getId(), record.getName()));

        //fetchInto直接返回实体类,也可以使用fetchInto(Tables.SIMPLE_JOOQ)
        List<SimpleJooqRecord> fetchIntoClassResultList = dslContext.select().from(Tables.SIMPLE_JOOQ).fetchInto(SimpleJooqRecord.class);

        System.out.println("fetchIntoClassResultList:" + fetchIntoClassResultList.toString());
    }

    /**
     * 测试查询
     */
    @Test
    public void testSelect() {
        List<SimpleJooqRecord> selectWhere = dslContext.select(Tables.SIMPLE_JOOQ.ID, Tables.SIMPLE_JOOQ.NAME, Tables.SIMPLE_JOOQ.USE_STRING_ENUM).from(Tables.SIMPLE_JOOQ)
                //where条件
                .where(Tables.SIMPLE_JOOQ.ID.in(1, 2))
                //多条用fetchInto,一条用fetchOneInto
                .fetchInto(SimpleJooqRecord.class);
        System.out.println("selectWhere:" + selectWhere);
    }


    /**
     * 测试分页
     */
    @Test
    public void testPage() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "id"));
        SelectLimitStep<Record> selectLimitStep = dslContext.select().from(Tables.SIMPLE_JOOQ).where(Tables.SIMPLE_JOOQ.ID.gt(3))
                //排序,不指定asc或者desc时,默认为asc
                .orderBy(Tables.SIMPLE_JOOQ.field(pageable.getSort().stream().findFirst().get().getProperty()).desc());

        //使用mysql的SQL_CALC_FOUND_ROWS获取条数和结果
        String pageSql = selectLimitStep.getSQL(ParamType.INLINED);
        pageSql = pageSql.replaceFirst("select", "select SQL_CALC_FOUND_ROWS ") + " limit ?,?";
        List<SimpleJooqRecord> records = dslContext.fetch(pageSql, pageable.getOffset(), pageable.getPageSize()).into(Tables.SIMPLE_JOOQ);
        long totalCount = dslContext.fetchOne("SELECT FOUND_ROWS()").into(Long.class);
        Page<SimpleJooqRecord> page = new PageImpl<>(records, pageable, totalCount);
        System.out.println(page);


        //使用limit获取结果,使用count获取条数
        records = selectLimitStep.limit(pageable.getOffset(), pageable.getPageSize()).fetchInto(Tables.SIMPLE_JOOQ);
        totalCount = dslContext.fetchCount(dslContext.select().from(Tables.SIMPLE_JOOQ).where(Tables.SIMPLE_JOOQ.ID.gt(3)));
        page = new PageImpl<>(records, pageable, totalCount);
        System.out.println(page);

        SimpleJooqDao simpleJooqDao = new SimpleJooqDao();
        simpleJooqDao.setConfiguration(dslContext.configuration());
        Page<SimpleJooq> pojoPage = simpleJooqDao.fetchPage(pageable, simpleJooqDao.ctx().select().from(Tables.SIMPLE_JOOQ).where(Tables.SIMPLE_JOOQ.ID.gt(3)));
        System.out.println(pojoPage);

    }

    /**
     * 测试join
     */
    @Test
    public void testSelectJoin() {
        SelectConditionStep<Record2<String, String>> selectConditionStep = dslContext.select(Tables.SIMPLE_JOOQ.NAME,
                        Tables.SIMPLE_MYBATIS.NAME.as("name2"))
                .from(Tables.SIMPLE_JOOQ)
                .leftJoin(Tables.SIMPLE_MYBATIS).on(Tables.SIMPLE_JOOQ.ID.eq(Tables.SIMPLE_MYBATIS.ID))
                .where(Tables.SIMPLE_JOOQ.ID.in(1, 2, 3));
        //使用fetch,获取到record
        Result<Record2<String, String>> result = selectConditionStep.fetch();
        System.out.println(result);
        //使用fetchInto,直接获取到实体
        List<JoinRecord> joinRecords = selectConditionStep.fetchInto(JoinRecord.class);
        System.out.println(joinRecords);
    }

    /**
     * 测试原生sql
     */
    @Test
    public void testNativeSQL() {
        List<SimpleJooqRecord> recordResult = dslContext.fetch("SELECT * FROM simple_jooq").into(SimpleJooqRecord.class);
        System.out.println(recordResult);
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        //需要使用newRecord创建实体
        SimpleJooqRecord simpleJooqRecord = dslContext.newRecord(Tables.SIMPLE_JOOQ);
        simpleJooqRecord.setName("3").setUseIndexEnum(1).insert();
        //使用这种方法insert,可以直接获取到主键
        System.out.println(simpleJooqRecord.getId());
    }

    /**
     * 批量插入
     */
    @Test
    public void testBatchInsert() {
        //execute批量插入
        dslContext.insertInto(Tables.SIMPLE_JOOQ, Tables.SIMPLE_JOOQ.NAME, Tables.SIMPLE_MYBATIS.USE_STRING_ENUM, Tables.SIMPLE_JOOQ.USE_INDEX_ENUM)
                .values("4", SimpleMybatisUseStringEnum.TWO, 2)
                .values("5", SimpleMybatisUseStringEnum.THREE, 3)
                .execute();
        //returning批量插入,可以获取主键
        Result<SimpleJooqRecord> result = dslContext.insertInto(Tables.SIMPLE_JOOQ, Tables.SIMPLE_JOOQ.NAME, Tables.SIMPLE_MYBATIS.USE_STRING_ENUM, Tables.SIMPLE_JOOQ.USE_INDEX_ENUM)
                .values("6", SimpleMybatisUseStringEnum.TWO, 2)
                .returning().fetch();
        System.out.println(result.get(0).getId());

        //使用此方法批量插入,无法获取主键id
        SimpleJooqRecord simpleJooqRecord = new SimpleJooqRecord().setName("7");
        dslContext.batchInsert(Collections.singletonList(simpleJooqRecord));
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        //where条件可以自行选择
        dslContext.update(Tables.SIMPLE_JOOQ)
                .set(Tables.SIMPLE_JOOQ.NAME, "3")
                .where(Tables.SIMPLE_JOOQ.ID.eq(1))
                .execute();

        //使用Record的话,where条件只能使用id,不设置id,则更新全部
        SimpleJooqRecord record = dslContext.newRecord(Tables.SIMPLE_JOOQ);
        record.setId(2).setName("1").update();
    }

    /**
     * 批量更新
     */
    @Test
    public void testBatchUpdate() {
        SimpleJooqRecord record1 = new SimpleJooqRecord().setId(1).setName("1");
        SimpleJooqRecord record2 = new SimpleJooqRecord().setId(2).setName("2");
        dslContext.batchUpdate(Arrays.asList(record1, record2)).execute();
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        dslContext.delete(Tables.SIMPLE_JOOQ).where(Tables.SIMPLE_JOOQ.ID.eq(5)).execute();

        //使用Record删除,所设置的值都是where条件
        SimpleJooqRecord record = dslContext.newRecord(Tables.SIMPLE_JOOQ);
        record.setName("5").delete();
    }

    /**
     * 测试批量删除
     */
    @Test
    public void testBatchDelete() {
        SimpleJooqRecord record1 = new SimpleJooqRecord().setId(5);
        SimpleJooqRecord record2 = new SimpleJooqRecord().setId(6);
        dslContext.batchDelete(record1, record2).execute();
    }
}
