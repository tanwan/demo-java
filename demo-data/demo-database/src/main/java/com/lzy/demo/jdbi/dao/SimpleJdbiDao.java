package com.lzy.demo.jdbi.dao;

import com.lzy.demo.jdbi.entity.SimpleJdbi;
import com.lzy.demo.jdbi.mapper.SimpleJdbiRowMapper;
import com.lzy.demo.jdbi.mapper.UseEnumValueEnumArgumentFactory;
import org.jdbi.v3.sqlobject.SqlObject;
import org.jdbi.v3.sqlobject.config.RegisterArgumentFactory;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.customizer.BindMap;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.stringtemplate4.UseStringTemplateSqlLocator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 可以选择是否继承SqlObject,继承的话,可以调用SqlObject的方法
 *
 * @author lzy
 * @version v1.0
 */
public interface SimpleJdbiDao extends SqlObject {

    /**
     * sql的占位符和参数顺序要一一对应
     * 使用@GetGeneratedKeys可以返回自动生成的字段
     *
     * @param name       name
     * @param stringEnum stringEnum
     * @param enumValue  enumValue
     * @return id
     */
    @SqlUpdate("insert into simple_jdbi(name, use_string_enum, use_enum_value_enum) values(?,?,?)")
    @GetGeneratedKeys("id")
    int insert(String name, String stringEnum, int enumValue);


    /**
     * 插入后,返回插入后的值,如果有不能自动映射的字段时,需要使用@RegisterBeanMapper BeanMapper.factory(UserBean.class)
     * 因为方法参数编译后名称会被修改,所以需要使用@Bind来指定名称,或者编译使用-parameters参数,则可以省略@Bind
     *
     * @param name       name
     * @param stringEnum stringEnum
     * @param valueEnum  valueEnum
     * @return SimpleJdbi
     */
    @SqlUpdate("insert into simple_jdbi(name, use_string_enum, use_enum_value_enum) values(:name, :stringEnum, :valueEnum)")
    @GetGeneratedKeys("id")
    int insertBind(@Bind("name") String name, @Bind("stringEnum") String stringEnum, @Bind("valueEnum") Integer valueEnum);


    /**
     * 使用@BindMap,参数使用map
     *
     * @param map map
     * @return result
     */
    @SqlUpdate("insert into simple_jdbi(name, use_string_enum, use_enum_value_enum) values(:name, :stringEnum, :valueEnum)")
    boolean insert(@BindMap Map<String, ?> map);


    /**
     * 使用@BindBean,参数使用bean, @RegisterArgumentFactory可以注解在当前方法上,也可以直接注解上当前接口上
     *
     * @param simpleJdbi simpleJdbi
     * @return result
     */
    @RegisterArgumentFactory(UseEnumValueEnumArgumentFactory.class)
    @SqlUpdate("insert into simple_jdbi(name, use_string_enum, use_enum_value_enum) values(:name, :useStringEnum, :useEnumValueEnum)")
    boolean insert(@BindBean SimpleJdbi simpleJdbi);


    /**
     * 指定插入
     *
     * @param simpleJdbis simpleJdbis
     * @return ids
     */
    @SqlBatch("insert into simple_jdbi(name, use_string_enum, use_enum_value_enum) values(:name, :useStringEnum, :useEnumValueEnum)")
    @GetGeneratedKeys("id")
    @RegisterArgumentFactory(UseEnumValueEnumArgumentFactory.class)
    int[] bulkInsert(@BindBean List<SimpleJdbi> simpleJdbis);


    /**
     * 这边可以使用@RegisterBeanMapper/@RegisterRowMapper/@RegisterRowMapperFactory/@RegisterColumnMapper/@RegisterColumnMapperFactory
     * 可以注解在当前方法,也可以注解到当前接口上
     *
     * @param ids ids
     * @return SimpleJdbis
     */
    @RegisterRowMapper(SimpleJdbiRowMapper.class)
    @SqlQuery("SELECT * from simple_jdbi where id in (<ids>)")
    List<SimpleJdbi> getSimpleJdbis(@BindList("ids") List<Integer> ids);


    /**
     * 可以返回Optional/Stream/ResultIterable 返回Stream/ResultIterable不能在on-demand获取的Dao执行,需要在回调中执行
     *
     * @param id id
     * @return SimpleJdbi
     */
    @RegisterRowMapper(SimpleJdbiRowMapper.class)
    @SqlQuery("SELECT * from simple_jdbi where id = ?")
    Optional<SimpleJdbi> findById(Integer id);


    /**
     * 从同包下以此类命名的sql.stg文件中的相同方法获取sql
     * SimpleJdiDao.sql.stg#useStringTemplateSqlLocator(ids)
     *
     * @param ids ids
     * @return SimpleJdbis
     */
    @SqlQuery
    @UseStringTemplateSqlLocator
    @RegisterRowMapper(SimpleJdbiRowMapper.class)
    Stream<SimpleJdbi> useStringTemplateSqlLocator(@BindList("ids") List<Integer> ids);


    /**
     * 从同包下以此接口命名的文件夹中获取以此方法为名称的sql文件
     * useUseClasspathSqlLocator.sql
     * 此注解可以直接注解在当前接口上,则表示当前接口的的所有类都使用了此注解
     *
     * @param ids ids
     * @return SimpleJdbis
     */
    @SqlQuery
    @UseClasspathSqlLocator
    @RegisterRowMapper(SimpleJdbiRowMapper.class)
    List<SimpleJdbi> useUseClasspathSqlLocator(@BindList("ids") List<Integer> ids);
}
