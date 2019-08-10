/*
 * Created by lzy on 11/13/17 10:14 AM.
 */
package com.lzy.demo.mybatis;

import com.lzy.demo.mybatis.entity.MybatisSample;
import com.lzy.demo.mybatis.enums.UseIndexEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.MybatisSampleMapper;
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
            MybatisSample mybatisSample = session.selectOne("com.lzy.demo.mybatis.mapper.MybatisSampleMapper.findOne", 1);
            System.out.println(mybatisSample);

            // 直接使用MybatisSampleMapper
            // 要求MybatisSampleMapper对应mapper.xml的名称空间为MybatisSampleMapper的包名
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            mybatisSampleMapper.findOne(1);
            System.out.println(mybatisSampleMapper.findOne(1));
            System.out.println(mybatisSampleMapper.findAll());
            System.out.println(mybatisSampleMapper.findOneUseAnnotation(1));
        }
    }

    /**
     * 测试添加
     */
    @Test
    public void testInsert() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            MybatisSample mybatisSample = new MybatisSample();
            mybatisSample.setName("insert");
            mybatisSample.setUseStringEnum(UseStringEnum.ONE);
            mybatisSample.setUseIndexEnum(UseIndexEnum.ONE);
            mybatisSampleMapper.insertOne(mybatisSample);
            System.out.println(mybatisSample);
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
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            MybatisSample mybatisSample = new MybatisSample();
            mybatisSample.setUseIndexEnum(UseIndexEnum.TWO);
            mybatisSampleMapper.updateOne(5, mybatisSample);
            System.out.println(mybatisSample);
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
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            MybatisSample mybatisSample = new MybatisSample();
            mybatisSample.setUseIndexEnum(UseIndexEnum.TWO);
            mybatisSampleMapper.deleteOne(5);
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
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            System.out.println(mybatisSampleMapper.selectOrder("id"));
        }
    }


    /**
     * 测试if
     */
    @Test
    public void testIf() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            System.out.println("name:1");
            System.out.println(mybatisSampleMapper.selectIf("1"));
            System.out.println("name:null");
            System.out.println(mybatisSampleMapper.selectIf(null));
        }
    }

    /**
     * 测试choose
     */
    @Test
    public void testChoose() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            System.out.println("name:1");
            System.out.println(mybatisSampleMapper.selectChoose("1"));
            System.out.println("name:null");
            System.out.println(mybatisSampleMapper.selectChoose(null));
            System.out.println("name:''");
            System.out.println(mybatisSampleMapper.selectChoose(""));
        }
    }

    /**
     * 测试foreach
     */
    @Test
    public void testForeach() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            MybatisSampleMapper mybatisSampleMapper = session.getMapper(MybatisSampleMapper.class);
            System.out.println(mybatisSampleMapper.selectForeach(Arrays.asList(1, 2)));
        }
    }
}
