/*
 * Created by lzy on 2019-07-27 17:58.
 */
package com.lzy.demo.mybatis;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.config.MybatisPlusConfig;
import com.lzy.demo.mybatis.entity.SampleMybatis;
import com.lzy.demo.mybatis.enums.UseEnumValueEnum;
import com.lzy.demo.mybatis.enums.UseIndexEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.SampleMybatisPlusMapper;
import com.lzy.demo.mybatis.service.SampleCacheService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Mybatis plus test.
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest
@SpringBootApplication
@MapperScan("com.lzy.demo.mybatis.mapper")
@TestPropertySource(properties = "spring.config.location=classpath:mybatis/mybatis-plus.yml")
public class MybatisPlusTest {

    @Resource
    private SampleMybatisPlusMapper sampleMybatisPlusMapper;

    @Resource
    private SampleCacheService sampleCacheService;

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        SampleMybatis sampleMybatis = new SampleMybatis();
        sampleMybatis.setName("mybatis plus");
        sampleMybatis.setUseStringEnum(UseStringEnum.ONE);
        sampleMybatis.setUseIndexEnum(UseIndexEnum.ONE);
        sampleMybatis.setUseEnumValueEnum(UseEnumValueEnum.ONE);
        sampleMybatisPlusMapper.insert(sampleMybatis);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDeleteByMap() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("use_index_enum", 3);
        // key为表字段名
        System.out.println(sampleMybatisPlusMapper.deleteByMap(map));
    }

    /**
     * 测试分页,需要添加PaginationInterceptor插件
     *
     * @see com.lzy.demo.mybatis.config.MybatisPlusConfig
     */
    @Test
    public void testPage() {
        SampleMybatis sampleMybatis = new SampleMybatis();
        sampleMybatis.setDelFlg(1);
        Page<SampleMybatis> page = new Page<>(1, 2);
        sampleMybatisPlusMapper.selectPage(page, Wrappers.query(sampleMybatis));
        System.out.println(page.getRecords());
    }

    /**
     * 测试sql注入
     */
    @Test
    public void testSqlInjector() {
        System.out.println(sampleMybatisPlusMapper.customInjectorMethod(1));
    }

    /**
     * 测试乐观锁,需要开启配置
     *
     * @see MybatisPlusConfig#optimisticLockerInterceptor()
     */
    @Test
    public void testVersion() {
        SampleMybatis sampleMybatis = new SampleMybatis();
        sampleMybatis.setId(1);
        sampleMybatis.setName("update");
        sampleMybatis.setVersion(0);
        System.out.println(sampleMybatisPlusMapper.updateById(sampleMybatis));
    }

    /**
     * 不使用事务,一级缓存不生效
     */
    @Test
    public void testFirstLevelCacheWithoutTransactional() {
        sampleCacheService.firstLevelCacheWithoutTransactional(1);
    }

    /**
     * 使用事务,一级缓存生效
     */
    @Test
    public void testFirstLevelCacheWithTransactional() {
        sampleCacheService.firstLevelCacheWithTransactional(1);
    }
}
