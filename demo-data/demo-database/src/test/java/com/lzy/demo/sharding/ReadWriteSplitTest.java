/*
 * Created by lzy on 2020/5/25 8:32 AM.
 */
package com.lzy.demo.sharding;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzy.demo.mybatis.entity.SimpleMybatis;
import com.lzy.demo.mybatis.enums.UseEnumValueEnum;
import com.lzy.demo.mybatis.enums.UseStringEnum;
import com.lzy.demo.mybatis.mapper.SimpleMybatisPlusMapper;
import com.lzy.demo.sharding.application.Application;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * sharding-jdbc读写分离测试
 *
 * @author lzy
 * @version v1.0
 */
public class ReadWriteSplitTest {
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String MASTER_JDBC_URL = "jdbc:mysql://localhost:3307/demo";
    private static final String SLAVE_JDBC_URL = "jdbc:mysql://localhost:3308/demo";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";


    /**
     * 测试读写分离
     *
     * @throws Exception the exception
     */
    @Test
    public void testReadWriteSplit() throws Exception {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置主库
        HikariDataSource masterDataSource = new HikariDataSource();
        masterDataSource.setDriverClassName(DRIVER_NAME);
        masterDataSource.setJdbcUrl(MASTER_JDBC_URL);
        masterDataSource.setUsername(USERNAME);
        masterDataSource.setPassword(PASSWORD);
        dataSourceMap.put("ds_master", masterDataSource);
        // 配置第一个从库
        HikariDataSource slaveDataSource1 = new HikariDataSource();
        slaveDataSource1.setDriverClassName(DRIVER_NAME);
        slaveDataSource1.setJdbcUrl(SLAVE_JDBC_URL);
        slaveDataSource1.setUsername(USERNAME);
        slaveDataSource1.setPassword(PASSWORD);
        dataSourceMap.put("ds_slave0", slaveDataSource1);
        // 配置读写分离规则
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("ds_master_slave", "ds_master", Collections.singletonList("ds_slave0"));
        Properties properties = new Properties();
        properties.put("sql.show", true);
        // 获取数据源对象
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, properties);
        executeSQL(dataSource);
    }


    /**
     * 测试读写分离使用yaml
     *
     * @throws Exception the exception
     */
    @Test
    public void testReadWriteSplitFromYaml() throws Exception {
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(new File(getClass().getClassLoader().getResource("sharding/read-write-split.yml").getFile()));
        executeSQL(dataSource);
    }

    /**
     * 强制走主库
     */
    @Test
    public void testMaster() throws Exception {
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(new File(getClass().getClassLoader().getResource("sharding/read-write-split.yml").getFile()));
        String querySql = "SELECT * FROM simple_table";
        try (HintManager hintManager = HintManager.getInstance()) {
            //hintManager.setMasterRouteOnly()这个需要在创建出statement之前执行才有效
            hintManager.setMasterRouteOnly();
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(querySql)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        System.out.print(rs.getInt(1) + " ");
                        System.out.println(rs.getString(2));
                    }
                }
            }
        }
    }

    /**
     * 相同线程,相同连接,先执行更新操作,后面的查询就会走主库
     */
    @Test
    public void testSameConnection() throws Exception {
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(new File(getClass().getClassLoader().getResource("sharding/read-write-split.yml").getFile()));
        String querySql = "SELECT * FROM simple_table";
        String insertSql = "INSERT INTO simple_table (name) VALUES ('lzy')";
        try (Connection conn = dataSource.getConnection();
             //先执行更新操作
             PreparedStatement insertStatement = conn.prepareStatement(insertSql);
             PreparedStatement preparedStatement = conn.prepareStatement(querySql)) {
            insertStatement.execute();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.print(rs.getInt(1) + " ");
                    System.out.println(rs.getString(2));
                }
            }
        }
    }


    private void executeSQL(DataSource dataSource) throws Exception {
        String querySql = "SELECT * FROM simple_table";
        String insertSql = "INSERT INTO simple_table (name) VALUES ('lzy')";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(querySql)) {
            conn.prepareStatement(insertSql).execute();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.print(rs.getInt(1) + " ");
                    System.out.println(rs.getString(2));
                }
            }
        }
    }


    /**
     * 配合spring boot使用
     */
    @SpringBootTest(classes = Application.class)
    @MapperScan("com.lzy.demo.mybatis.mapper")
    @TestPropertySource(properties = "spring.config.location=classpath:sharding/springboot-read-write-split.yml")
    public static class SpringBootReadWriteSplitTest {

        @Resource
        private SimpleMybatisPlusMapper simpleMybatisPlusMapper;

        /**
         * 测试插入
         */
        @Test
        public void testInsert() {
            SimpleMybatis simpleMybatis = new SimpleMybatis();
            simpleMybatis.setName("mybatis plus");
            simpleMybatis.setUseStringEnum(UseStringEnum.ONE);
            simpleMybatis.setUseEnumValueEnum(UseEnumValueEnum.ONE);
            simpleMybatis.setDelFlg(1);
            simpleMybatisPlusMapper.insert(simpleMybatis);
        }

        /**
         * 测试查询
         */
        @Test
        public void testSelect() {
            System.out.println(simpleMybatisPlusMapper.selectList(Wrappers.emptyWrapper()));
        }

        /**
         * 强制走主库
         */
        @Test
        public void testMaster() {
            try (HintManager hintManager = HintManager.getInstance()) {
                hintManager.setMasterRouteOnly();
                System.out.println(simpleMybatisPlusMapper.selectList(Wrappers.emptyWrapper()));
            }
        }

        /**
         * 测试相同线程
         * 没有事务的话,在更新后,会关闭当前的SqlSession,所以会导致后面查询使用另一个连接,这边查询也不会使用主库
         * 因此,如果需要更新操作后的查询使用主库,需要使用事务
         *
         * @see SqlSessionTemplate.SqlSessionInterceptor#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
         * @see SqlSessionUtils#closeSqlSession(org.apache.ibatis.session.SqlSession, org.apache.ibatis.session.SqlSessionFactory)
         */
        @Test
        @Transactional
        public void testSameConnection() {
            testInsert();
            testSelect();
        }

    }
}
