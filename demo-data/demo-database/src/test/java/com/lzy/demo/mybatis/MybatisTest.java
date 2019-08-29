/*
 * Created by lzy on 11/13/17 10:14 AM.
 */
package com.lzy.demo.mybatis;

import com.lzy.demo.mybatis.entity.SampleMybatis;
import com.lzy.demo.mybatis.enums.UseIndexEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.SampleMybatisMapper;
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

/**
 * mybatis测试
 *
 * @author lzy
 * @version v1.0
 */
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
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, "dev");
    }

    /**
     * 测试查询
     *
     * @throws IOException the io exception
     */
    @Test
    public void testSelect() throws IOException {
        // 使用try-resource自动释放
        // SqlSession不是线程安全的,所以不能共享
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 参数statement对应的就是mapper.xml文件的名称空间加方法名
            SampleMybatis sampleMybatis = session.selectOne("com.lzy.demo.mybatis.mapper.SampleMybatisMapper.findOne", 1);
            System.out.println(sampleMybatis);

            // 直接使用SampleMybatisMapper
            // 要求SampleMybatisMapper对应mapper.xml的名称空间为SampleMybatisMapper的包名
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            System.out.println(sampleMybatisMapper.findOne(1));
            System.out.println(sampleMybatisMapper.findAll());
            System.out.println(sampleMybatisMapper.findOneUseAnnotation(1));
        }
    }

    /**
     * 测试添加
     */
    @Test
    public void testInsert() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            SampleMybatis sampleMybatis = new SampleMybatis();
            sampleMybatis.setName("insert");
            sampleMybatis.setUseStringEnum(UseStringEnum.ONE);
            sampleMybatis.setUseIndexEnum(UseIndexEnum.ONE);
            sampleMybatisMapper.insertOne(sampleMybatis);
            System.out.println(sampleMybatis);
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
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            SampleMybatis sampleMybatis = new SampleMybatis();
            sampleMybatis.setUseIndexEnum(UseIndexEnum.TWO);
            sampleMybatisMapper.updateOne(5, sampleMybatis);
            System.out.println(sampleMybatis);
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
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            SampleMybatis sampleMybatis = new SampleMybatis();
            sampleMybatis.setUseIndexEnum(UseIndexEnum.TWO);
            sampleMybatisMapper.deleteOne(5);
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
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            System.out.println(sampleMybatisMapper.selectOrder("id"));
        }
    }


    /**
     * 测试if
     */
    @Test
    public void testIf() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            System.out.println("name:1");
            System.out.println(sampleMybatisMapper.selectIf("1"));
            System.out.println("name:null");
            System.out.println(sampleMybatisMapper.selectIf(null));
        }
    }

    /**
     * 测试choose
     */
    @Test
    public void testChoose() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            System.out.println("name:1");
            System.out.println(sampleMybatisMapper.selectChoose("1"));
            System.out.println("name:null");
            System.out.println(sampleMybatisMapper.selectChoose(null));
            System.out.println("name:''");
            System.out.println(sampleMybatisMapper.selectChoose(""));
        }
    }

    /**
     * 测试foreach
     */
    @Test
    public void testForeach() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            SampleMybatisMapper sampleMybatisMapper = session.getMapper(SampleMybatisMapper.class);
            System.out.println(sampleMybatisMapper.selectForeach(Arrays.asList(1, 2)));
        }
    }
}
