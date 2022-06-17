package com.lzy.demo.jdbi;

import com.lzy.demo.enums.UseEnumValueEnum;
import com.lzy.demo.enums.UseStringEnum;
import com.lzy.demo.jdbi.dao.SimpleJdbiDao;
import com.lzy.demo.jdbi.entity.SimpleJdbi;
import com.lzy.demo.utils.ConfigUtils;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbi Dao测试
 * sql脚本在/flyway/migration/V1_2__jdbi.sql,使用flyway
 *
 * @author lzy
 * @version v1.0
 */
public class JDBIDaoTest {

    private Jdbi jdbi;

    @BeforeEach
    public void init() {
        // 如果有DataSource,也可以直接使用Jdbi.create(database)
        jdbi = Jdbi.create(ConfigUtils.getDBUrl(), ConfigUtils.getDBUsername(), ConfigUtils.getDBPassword());
        // 需要注册SqlObjectPlugin
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        // 使用useExtension
        jdbi.useExtension(SimpleJdbiDao.class, dao -> {
            // 使用顺序来传递参数
            Integer id = dao.insert("insert", "ONE", 2);
            System.out.println(id);
        });
        // 使用onDemand创建出Dao
        SimpleJdbiDao simpleJdbiDao = jdbi.onDemand(SimpleJdbiDao.class);

        // 使用@bind来传递参数
        Integer id = simpleJdbiDao.insertBind("insertBind", "ONE", 2);
        System.out.println(id);

        // 使用map作为参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", "map");
        map.put("stringEnum", "THREE");
        map.put("valueEnum", 3);
        boolean result = simpleJdbiDao.insert(map);
        System.out.println(result);

        // 使用bean作为参数
        SimpleJdbi simpleJdbi = new SimpleJdbi();
        simpleJdbi.setName("bean");
        simpleJdbi.setUseStringEnum(UseStringEnum.ONE);
        simpleJdbi.setUseEnumValueEnum(UseEnumValueEnum.THREE);
        result = simpleJdbiDao.insert(simpleJdbi);
        System.out.println(result);

        // 批量插入
        int[] ids = simpleJdbiDao.bulkInsert(Collections.singletonList(simpleJdbi));
        System.out.println(Arrays.toString(ids));
    }

    /**
     * 测试查询
     */
    @Test
    public void testQuery() {
        SimpleJdbiDao simpleJdbiDao = jdbi.onDemand(SimpleJdbiDao.class);
        Integer id = simpleJdbiDao.insert("insert", "ONE", 2);
        List<Integer> ids = Collections.singletonList(id);
        List<SimpleJdbi> simpleJdbis = simpleJdbiDao.getSimpleJdbis(ids);
        System.out.println(simpleJdbis);

        // 返回Optional
        simpleJdbiDao.findById(id).ifPresent(System.out::println);

        // 使用@UseStringTemplateSqlLocator
        jdbi.useExtension(SimpleJdbiDao.class, dao ->
                // 返回Stream需要在回调中执行
                dao.useStringTemplateSqlLocator(ids)
                        .forEach(System.out::println)
        );

        // 使用@UseClasspathSqlLocator
        simpleJdbiDao.useUseClasspathSqlLocator(ids).forEach(System.out::println);
    }


    @AfterEach
    public void after() {
        jdbi.useHandle(handle -> handle.createUpdate("truncate simple_jdbi")
                .execute());
    }
}
