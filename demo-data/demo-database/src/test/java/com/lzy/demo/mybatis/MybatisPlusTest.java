package com.lzy.demo.mybatis;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.config.MybatisPlusConfig;
import com.lzy.demo.mybatis.entity.SimpleMybatis;
import com.lzy.demo.enums.UseEnumValueEnum;
import com.lzy.demo.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.SimpleMybatisPlusMapper;
import com.lzy.demo.mybatis.service.SimpleCacheService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@SpringBootApplication
@MapperScan("com.lzy.demo.mybatis.mapper")
@ActiveProfiles("mybatis-plus")
public class MybatisPlusTest {

    @Resource
    private SimpleMybatisPlusMapper simpleMybatisPlusMapper;

    @Resource
    private SimpleCacheService simpleCacheService;

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        SimpleMybatis simpleMybatis = new SimpleMybatis();
        simpleMybatis.setName("mybatis plus");
        simpleMybatis.setUseStringEnum(UseStringEnum.ONE);
        simpleMybatis.setUseEnumValueEnum(UseEnumValueEnum.ONE);
        simpleMybatisPlusMapper.insert(simpleMybatis);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDeleteByMap() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("use_index_enum", 3);
        // key为表字段名
        System.out.println(simpleMybatisPlusMapper.deleteByMap(map));
    }

    /**
     * 测试分页,需要添加PaginationInterceptor插件
     *
     * @see com.lzy.demo.mybatis.config.MybatisPlusConfig
     */
    @Test
    public void testPage() {
        SimpleMybatis simpleMybatis = new SimpleMybatis();
        simpleMybatis.setDelFlg(1);
        Page<SimpleMybatis> page = new Page<>(1, 2);
        simpleMybatisPlusMapper.selectPage(page, Wrappers.query(simpleMybatis));
        System.out.println(page.getRecords());
    }

    /**
     * 测试分页,需要添加PaginationInterceptor插件
     *
     * @see com.lzy.demo.mybatis.config.MybatisPlusConfig
     */
    @Test
    public void testCustomPage() {
        Page<SimpleMybatis> page = new Page<>(1, 2);
        simpleMybatisPlusMapper.customPage(page);
        System.out.println(page.getRecords());
    }

    /**
     * 测试sql注入
     */
    @Test
    public void testSqlInjector() {
        System.out.println(simpleMybatisPlusMapper.customInjectorMethod(1));
    }

    /**
     * 测试乐观锁,需要开启配置
     *
     * @see MybatisPlusConfig#mybatisPlusInterceptor()
     */
    @Test
    public void testVersion() {
        SimpleMybatis simpleMybatis = new SimpleMybatis();
        simpleMybatis.setId(1);
        simpleMybatis.setName("update");
        simpleMybatis.setVersion(0);
        System.out.println(simpleMybatisPlusMapper.updateById(simpleMybatis));
    }

    /**
     * 不使用事务,一级缓存不生效
     */
    @Test
    public void testFirstLevelCacheWithoutTransactional() {
        simpleCacheService.firstLevelCacheWithoutTransactional(1);
    }

    /**
     * 使用事务,一级缓存生效
     */
    @Test
    public void testFirstLevelCacheWithTransactional() {
        simpleCacheService.firstLevelCacheWithTransactional(1);
    }
}
