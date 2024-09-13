package com.lzy.demo.mybatis;

import com.lzy.demo.mybatis.entity.SimpleMybatis;
import com.lzy.demo.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.SimpleMybatisMapper;
import com.lzy.demo.utils.ConfigUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MybatisTest {

    private SqlSessionFactory sqlSessionFactory;

    /**
     * 从XML获取SqlSessionFactory
     *
     * @throws IOException the io exception
     */
    @BeforeAll
    public void createSqlSessionFactoryFromXML() throws IOException {
        InputStream inputStream = new ClassPathResource("mybatis/mybatis-config.xml").getInputStream();
        // SqlSessionFactoryBuilder用来创建SqlSessionFactory,一旦SqlSessionFactory创建完成,就不需要再用到SqlSessionFactoryBuilder了
        // SqlSessionFactory应该保持单例
        Properties properties = new Properties();
        properties.put("url", ConfigUtils.getDBUrl());
        properties.put("username", ConfigUtils.getDBUsername());
        properties.put("password", ConfigUtils.getDBPassword());
        // 这边传入properties相当于mybatis/mybatis-config.xml的  <properties resource="my.properties">
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "dev", properties);
    }

    /**
     * 测试查询
     *
     */
    @Test
    public void testSelect() {
        // 使用try-resource自动释放
        // SqlSession不是线程安全的,所以不能共享
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 参数statement对应的就是mapper.xml文件的名称空间加方法名
            SimpleMybatis simpleMybatis = session.selectOne("com.lzy.demo.mybatis.mapper.SimpleMybatisMapper.findOne", 1);
            System.out.println(simpleMybatis);

            // 直接使用SimpleMybatisMapper
            // 要求SimpleMybatisMapper对应mapper.xml的名称空间为SimpleMybatisMapper的包名
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            System.out.println(simpleMybatisMapper.findOne(1));
            System.out.println(simpleMybatisMapper.findAll());
            System.out.println(simpleMybatisMapper.findOneUseAnnotation(1));
        }
    }

    /**
     * 测试添加
     */
    @Test
    public void testInsert() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            SimpleMybatis simpleMybatis = new SimpleMybatis();
            simpleMybatis.setName("insert");
            simpleMybatis.setUseStringEnum(UseStringEnum.ONE);
            simpleMybatisMapper.insertOne(simpleMybatis);
            System.out.println(simpleMybatis);
            // 需要手动提交
            session.commit();
        }
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            SimpleMybatis simpleMybatis = new SimpleMybatis();
            simpleMybatisMapper.updateOne(5, simpleMybatis);
            System.out.println(simpleMybatis);
            // 需要手动提交
            session.commit();
        }
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            SimpleMybatis simpleMybatis = new SimpleMybatis();
            simpleMybatisMapper.deleteOne(5);
            // 需要手动提交
            session.commit();
        }
    }


    /**
     * 测试指定orderBy
     */
    @Test
    public void testOrderBy() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            System.out.println(simpleMybatisMapper.selectOrder("id"));
        }
    }


    /**
     * 测试if
     */
    @Test
    public void testIf() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            System.out.println("name:1");
            System.out.println(simpleMybatisMapper.selectIf("1"));
            System.out.println("name:null");
            System.out.println(simpleMybatisMapper.selectIf(null));
        }
    }

    /**
     * 测试choose
     */
    @Test
    public void testChoose() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            System.out.println("name:1");
            System.out.println(simpleMybatisMapper.selectChoose("1"));
            System.out.println("name:null");
            System.out.println(simpleMybatisMapper.selectChoose(null));
            System.out.println("name:''");
            System.out.println(simpleMybatisMapper.selectChoose(""));
        }
    }

    /**
     * 测试foreach
     */
    @Test
    public void testForeach() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SimpleMybatisMapper simpleMybatisMapper = session.getMapper(SimpleMybatisMapper.class);
            System.out.println(simpleMybatisMapper.selectForeach(Arrays.asList(1, 2)));
        }
    }
}
