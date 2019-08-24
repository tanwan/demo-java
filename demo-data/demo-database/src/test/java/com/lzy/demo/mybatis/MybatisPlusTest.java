/*
 * Created by lzy on 2019-07-27 17:58.
 */
package com.lzy.demo.mybatis;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzy.demo.mybatis.config.MybatisPlusConfig;
import com.lzy.demo.mybatis.entity.MybatisSample;
import com.lzy.demo.mybatis.enums.UseEnumValueEnum;
import com.lzy.demo.mybatis.enums.UseIndexEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.MybatisSamplePlusMapper;
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
    private MybatisSamplePlusMapper mybatisSamplePlusMapper;

    @Resource
    private SampleCacheService sampleCacheService;

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        MybatisSample mybatisSample = new MybatisSample();
        mybatisSample.setName("mybatis plus");
        mybatisSample.setUseStringEnum(UseStringEnum.ONE);
        mybatisSample.setUseIndexEnum(UseIndexEnum.ONE);
        mybatisSample.setUseEnumValueEnum(UseEnumValueEnum.ONE);
        mybatisSamplePlusMapper.insert(mybatisSample);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDeleteByMap() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("use_index_enum", 3);
        // key为表字段名
        System.out.println(mybatisSamplePlusMapper.deleteByMap(map));
    }

    /**
     * 测试分页,需要添加PaginationInterceptor插件
     *
     * @see com.lzy.demo.mybatis.config.MybatisPlusConfig
     */
    @Test
    public void testPage() {
        MybatisSample mybatisSample = new MybatisSample();
        mybatisSample.setDelFlg(1);
        Page<MybatisSample> page = new Page<>(1, 2);
        mybatisSamplePlusMapper.selectPage(page, Wrappers.query(mybatisSample));
        System.out.println(page.getRecords());
    }

    /**
     * 测试sql注入
     */
    @Test
    public void testSqlInjector() {
        System.out.println(mybatisSamplePlusMapper.customInjectorMethod(1));
    }

    /**
     * 测试乐观锁,需要开启配置
     *
     * @see MybatisPlusConfig#optimisticLockerInterceptor()
     */
    @Test
    public void testVersion() {
        MybatisSample mybatisSample = new MybatisSample();
        mybatisSample.setId(1);
        mybatisSample.setName("update");
        mybatisSample.setVersion(0);
        System.out.println(mybatisSamplePlusMapper.updateById(mybatisSample));
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
