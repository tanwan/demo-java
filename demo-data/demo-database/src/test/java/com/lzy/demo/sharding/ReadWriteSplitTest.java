package com.lzy.demo.sharding;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzy.demo.mybatis.entity.SimpleMybatis;
import com.lzy.demo.mybatis.mapper.SimpleMybatisPlusMapper;
import com.lzy.demo.sharding.application.Application;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.hint.HintManager;
import org.apache.shardingsphere.core.yaml.config.masterslave.YamlRootMasterSlaveConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.apache.shardingsphere.underlying.common.yaml.engine.YamlEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * sharding-jdbc读写分离测试
 * 如果数据库表没有创建,先执行shardsphere/sql/shardingsphere-dml.sql
 *
 * @author lzy
 * @version v1.0
 */
public class ReadWriteSplitTest {
    private static String driverName;
    private static String masterJDBCUrl;
    private static String slaveJDBSUrl;
    private static String username;
    private static String password;

    private static final String QUERY_SQL = "SELECT * FROM `simple_mybatis`";
    private static final String INSERT_SQL = "INSERT INTO `simple_mybatis` (name) VALUES ('lzy')";

    private static final File CONFIG_FILE = new File(Objects.requireNonNull(ReadWriteSplitTest.class.getResource("/shardingsphere/read-write-split.yml")).getFile());

    /**
     * init config
     * @throws Exception e
     */
    @BeforeAll
    public static void initConfig() throws Exception {
        YamlRootMasterSlaveConfiguration config = YamlEngine.unmarshal(CONFIG_FILE, YamlRootMasterSlaveConfiguration.class);
        HikariDataSource master = (HikariDataSource) config.getDataSources().get("ds_master");
        HikariDataSource slave = (HikariDataSource) config.getDataSources().get("ds_slave0");
        driverName = master.getDriverClassName();
        masterJDBCUrl = master.getJdbcUrl();
        username = master.getUsername();
        password = master.getPassword();
        slaveJDBSUrl = slave.getJdbcUrl();
    }

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
        masterDataSource.setDriverClassName(driverName);
        masterDataSource.setJdbcUrl(masterJDBCUrl);
        masterDataSource.setUsername(username);
        masterDataSource.setPassword(password);
        dataSourceMap.put("ds_master", masterDataSource);
        // 配置第一个从库
        HikariDataSource slaveDataSource1 = new HikariDataSource();
        slaveDataSource1.setDriverClassName(driverName);
        slaveDataSource1.setJdbcUrl(slaveJDBSUrl);
        slaveDataSource1.setUsername(username);
        slaveDataSource1.setPassword(password);
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
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(CONFIG_FILE);
        executeSQL(dataSource);
    }

    /**
     * 强制走主库
     */
    @Test
    public void testMaster() throws Exception {
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(CONFIG_FILE);
        try (HintManager hintManager = HintManager.getInstance()) {
            //hintManager.setMasterRouteOnly()这个需要在创建出statement之前执行才有效
            hintManager.setMasterRouteOnly();
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(QUERY_SQL)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        printResult(rs);
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
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(CONFIG_FILE);
        try (Connection conn = dataSource.getConnection();
             //先执行更新操作
             PreparedStatement insertStatement = conn.prepareStatement(INSERT_SQL);
             PreparedStatement preparedStatement = conn.prepareStatement(QUERY_SQL)) {
            insertStatement.execute();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    printResult(rs);
                }
            }
        }
    }


    /**
     * 先执行查询,再执行更新,查询会走从库
     *
     * @param dataSource dataSource
     * @throws Exception exception
     */
    private void executeSQL(DataSource dataSource) throws Exception {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(QUERY_SQL)) {
            conn.prepareStatement(INSERT_SQL).execute();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    printResult(rs);
                }
            }
        }
    }

    private void printResult(ResultSet rs) throws SQLException {
        System.out.print(rs.getString(1) + " ");
        System.out.println(rs.getString(2));
    }


    /**
     * 配合spring boot使用
     */
    @SpringBootTest(classes = Application.class)
    @MapperScan("com.lzy.demo.mybatis.mapper")
    @ActiveProfiles({"shardingsphere-read-write-split", "mybatis-plus"})
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
         *
         * @see SqlSessionTemplate.SqlSessionInterceptor#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
         * @see SqlSessionUtils#closeSqlSession(org.apache.ibatis.session.SqlSession, org.apache.ibatis.session.SqlSessionFactory)
         */
        @Test
        public void testSameConnectionWithoutTransactional() {
            testInsert();
            testSelect();
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
        public void testSameConnectionWithTransactional() {
            testSameConnectionWithoutTransactional();
        }

    }
}
