package com.lzy.demo.jdbi;


import com.lzy.demo.enums.UseEnumValueEnum;
import com.lzy.demo.enums.UseStringEnum;
import com.lzy.demo.jdbi.entity.SimpleJdbi;
import com.lzy.demo.jdbi.mapper.SimpleJdbiRowMapper;
import com.lzy.demo.jdbi.mapper.UseEnumValueEnumArgumentFactory;
import com.lzy.demo.jdbi.mapper.UseEnumValueEnumMapper;
import com.lzy.demo.utils.ConfigUtils;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * jdbi Dao基础测试
 * sql脚本在/flyway/migration/V1_2__jdbi.sql,使用flyway
 *
 * @author lzy
 * @version v1.0
 */
public class JDBIBaseTest {
    private Jdbi jdbi;

    @BeforeEach
    public void init() {
        // 如果有DataSource,也可以直接使用Jdbi.create(database)
        jdbi = Jdbi.create(ConfigUtils.getDBUrl(), ConfigUtils.getDBUsername(), ConfigUtils.getDBPassword());
    }

    /**
     * 测试传递参数
     */
    @Test
    public void testArguments() {
        // 可以直接使用open打开一个handle,使用这种方式,都跟try-with-resources配合
        try (Handle handle = jdbi.open()) {
            handle.execute("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(?,?,?)",
                    "use open", "TWO", 1);
        }
        // withHandle有返回值,不需要返回值的使用useHandle
        List<SimpleJdbi> simpleJdbis = jdbi.withHandle(handle -> {
            // 使用execute直接执行sql, 参数直接传入即可
            handle.execute("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(?,?,?)",
                    "execute insert", "TWO", 2);

            // 使用createUpdate创建sql,然后执行
            handle.createUpdate("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(?,?,?)")
                    // 使用bind index来传递参数
                    .bind(0, "createUpdate bind index")
                    .bind(1, "TWO")
                    .bind(2, 2)
                    .execute();

            // 使用createUpdate创建sql,然后执行
            handle.createUpdate("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(:name, :stringEnum, :valueEnum)")
                    // 使用bind name来传递参数
                    .bind("name", "createUpdate bind name")
                    .bind("stringEnum", "TWO")
                    .bind("valueEnum", 2)
                    .execute();

            SimpleJdbi simpleJdbi = new SimpleJdbi();
            simpleJdbi.setName("createUpdate bind bean");
            simpleJdbi.setUseStringEnum(UseStringEnum.THREE);
            simpleJdbi.setUseEnumValueEnum(UseEnumValueEnum.THREE);
            handle.createUpdate("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(:name, :useStringEnum, :useEnumValueEnum)")
                    //绑定java bean, 还有bindMap(跟bindBean类似),bindMethods(sql里的参数跟bean的方法对应), bindFields(跟bindBean类似)
                    //可以同时使用多个bind
                    //这些方法还都有重载函数,添加一个prefix, 这样sql中的参数可以使用:bean.property,  bindBean("bean", bean)
                    .bindBean(simpleJdbi)
                    // 如果有没有办法自动映射的字段,需要添加ArgumentFactory
                    // 可以使用AbstractArgumentFactory
                    // 也可以直接bind('name', CustomArgument)
                    .registerArgument(new UseEnumValueEnumArgumentFactory())
                    .execute();

            // 直接执行sql,然后映射成bean
            return handle.createQuery("SELECT * FROM simple_jdbi")
                    // 如果有没有办法自动映射的字段,需要添加ColumnMapper
                    .registerColumnMapper(new UseEnumValueEnumMapper())
                    // 使用BeanMapper进行映射, See http://jdbi.org/#_beanmapper
                    .mapToBean(SimpleJdbi.class)
                    .list();
        });

        System.out.println(simpleJdbis);

        // 不需要返回值的使用useHandle
        jdbi.useHandle(handle -> handle.createUpdate("DELETE FROM simple_jdbi WHERE id in (<ids>)")
                // 如果需要绑定list的话, 参数需要使用<arguments>
                .bindList("ids", simpleJdbis.stream().map(SimpleJdbi::getId).collect(Collectors.toList()))
                .execute());
    }

    /**
     * 测试mapper
     */
    @Test
    public void testMaper() {
        jdbi.useHandle(handle ->
                handle.execute("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(?,?,?)",
                        "test Mapper", "TWO", 2)
        );
        List<SimpleJdbi> simpleJdbis = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM simple_jdbi")
                // 使用指定的maper进行映射, 也可以直接使用lambda表达式
                .map(new SimpleJdbiRowMapper())
                .list());
        System.out.println(simpleJdbis);


        // 可以直接给jdbi注册mapper, 也可以注册RowMapperFactory
        jdbi.registerRowMapper(new SimpleJdbiRowMapper());
        simpleJdbis = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM simple_jdbi")
                // 使用注册的mapper进行映射
                .mapTo(SimpleJdbi.class)
                .list());
        System.out.println(simpleJdbis);

        // 重置jbdi
        init();

        // 可以直接给jdbi注册ColumnMapper, 也可以注册ColumnMapperFactory
        jdbi.registerColumnMapper(new UseEnumValueEnumMapper());
        simpleJdbis = jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM simple_jdbi")
                .mapToBean(SimpleJdbi.class)
                .list());
        System.out.println(simpleJdbis);
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        jdbi.useHandle(handle -> {
                    Integer id = handle.createUpdate("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(:name, :stringEnum, :valueEnum)")
                            .bind("name", "test insert")
                            .bind("stringEnum", "TWO")
                            .bind("valueEnum", 2)
                            // 获取自动生成的id
                            .executeAndReturnGeneratedKeys()
                            .mapTo(Integer.class)
                            .one();
                    System.out.println(id);
                }
        );


        // 批量插入
        jdbi.useHandle(handle -> {
                    PreparedBatch batch = handle.prepareBatch("INSERT INTO simple_jdbi(name, use_string_enum, use_enum_value_enum) VALUES(:name, :stringEnum, :valueEnum)");
                    for (int i = 0; i < 3; i++) {
                        batch.bind("name", "name" + i)
                                .bind("stringEnum", "ONE")
                                .bind("valueEnum", 2)
                                .add();
                    }
                    int[] counts = batch.execute();
                    System.out.println(Arrays.toString(counts));
                }
        );
    }

    @AfterEach
    public void after() {
        jdbi.useHandle(handle -> handle.createUpdate("truncate simple_jdbi")
                .execute());
    }
}
