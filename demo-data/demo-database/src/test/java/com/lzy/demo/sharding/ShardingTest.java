package com.lzy.demo.sharding;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.core.yaml.config.sharding.YamlRootShardingConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;
import org.apache.shardingsphere.underlying.common.yaml.engine.YamlEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * 测试分片
 * 如果数据库表没有创建,先执行shardsphere/sql/shardingsphere-dml.sql
 *
 * @author lzy
 * @version v1.0
 */
public class ShardingTest {

    private static String driverName;
    private static String jdbcUrl0;
    private static String jdbdUrl1;
    private static String username;
    private static String password;

    private static final File CONFIG_FILE = new File(Objects.requireNonNull(ShardingTest.class.getResource("/shardingsphere/sharding.yml")).getFile());


    /**
     * init config
     *
     * @throws Exception e
     */
    @BeforeAll
    public static void initConfig() throws Exception {
        YamlRootShardingConfiguration config = YamlEngine.unmarshal(CONFIG_FILE, YamlRootShardingConfiguration.class);
        HikariDataSource master = (HikariDataSource) config.getDataSources().get("ds_0");
        HikariDataSource slave = (HikariDataSource) config.getDataSources().get("ds_1");
        driverName = master.getDriverClassName();
        jdbcUrl0 = master.getJdbcUrl();
        username = master.getUsername();
        password = master.getPassword();
        jdbdUrl1 = slave.getJdbcUrl();
    }


    /**
     * 测试多库多表插入
     */
    @Test
    public void testInsert() throws Exception {
        insert(dataSource());
    }

    /**
     * 测试多库多表查询
     */
    @Test
    public void testSelect() throws Exception {
        select(dataSource());
    }

    /**
     * 测试分片使用yaml
     *
     * @throws Exception the exception
     */
    @Test
    public void testShardingFromYaml() throws Exception {
        DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(new File(getClass().getClassLoader().getResource("sharding/sharding.yml").getFile()));
        select(dataSource);
    }

    /**
     * 测试单库分表
     */
    @Test
    public void testSingleDatabase() throws SQLException {
        Map<String, DataSource> dataSourceMap = dataSourceMap();
        //删除另一个库
        dataSourceMap.remove("ds_1");
        // 配置order表规则
        TableRuleConfiguration orderTableRuleConfig =
                //ds_0.order_0,ds_0.order_1,ds_1.order_0,ds_1.order_1
                new TableRuleConfiguration("order", "ds_0.order_${0..1}");
        // 分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "order_${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        Properties properties = new Properties();
        properties.put("sql.show", true);
        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap(), shardingRuleConfig, properties);
        String insertOrderSql = "INSERT INTO `order`(`order_id`,`user_id`) VALUES (?,?)";
        for (int i = 0; i < 10; i++) {
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement orderStatement = conn.prepareStatement(insertOrderSql)) {
                orderStatement.setLong(1, i);
                orderStatement.setLong(2, i);
                orderStatement.execute();
            }
        }
    }

    private Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 配置第一个数据源
        HikariDataSource dataSource0 = new HikariDataSource();
        dataSource0.setDriverClassName(driverName);
        dataSource0.setJdbcUrl(jdbcUrl0);
        dataSource0.setUsername(username);
        dataSource0.setPassword(password);
        dataSourceMap.put("ds_0", dataSource0);
        // 配置第二个数据源
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName(driverName);
        dataSource1.setJdbcUrl(jdbdUrl1);
        dataSource1.setUsername(username);
        dataSource1.setPassword(password);
        dataSourceMap.put("ds_1", dataSource1);
        return dataSourceMap;
    }


    private DataSource dataSource() throws Exception {
        // 配置order表规则
        TableRuleConfiguration orderTableRuleConfig =
                //ds_0.order_0,ds_0.order_1,ds_1.order_0,ds_1.order_1
                new TableRuleConfiguration("order", "ds_${0..1}.order_${0..1}");

        // 分库策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
        // 分表策略
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "order_${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);


        // 配置order_other表规则
        TableRuleConfiguration orderOtherTableRuleConfig =
                //ds0.order_other_0,ds0.order_other_1,ds_1.order_other_0,ds_1.order_other_1
                new TableRuleConfiguration("order_other", "ds_${0..1}.order_other_${0..1}");

        // 分库策略,如果只使用一个库进行分表,则不需要配置分库策略
        orderOtherTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));
        // 分表策略
        orderOtherTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "order_other_${order_id % 2}"));

        // 配置分片规则
        shardingRuleConfig.getTableRuleConfigs().add(orderOtherTableRuleConfig);

        // 配置绑定表关系,绑定表关系使用逗号分隔
        shardingRuleConfig.getBindingTableGroups().add("order,order_other");

        // 默认数据库,没有分表使用的数据库
        shardingRuleConfig.setDefaultDataSourceName("ds_0");

        // 如果要和读写分离一起使用,则这边需要配置读写分离的配置
        //shardingRuleConfig.setMasterSlaveRuleConfigs();


        Properties properties = new Properties();
        properties.put("sql.show", true);
        // 获取数据源对象
        return ShardingDataSourceFactory.createDataSource(dataSourceMap(), shardingRuleConfig, properties);
    }


    private void insert(DataSource dataSource) throws Exception {
        String insertOrderSql = "INSERT INTO `order`(`order_id`,`user_id`) VALUES (?,?)";
        String insertOrderOtherSql = "INSERT INTO `order_other`(`order_id`,`name`) VALUES (?,?)";

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                try (Connection conn = dataSource.getConnection();
                     PreparedStatement orderStatement = conn.prepareStatement(insertOrderSql);
                     PreparedStatement orderOtherStatement = conn.prepareStatement(insertOrderOtherSql)
                ) {
                    orderStatement.setLong(1, j);
                    orderStatement.setLong(2, i);
                    orderStatement.execute();
                    orderOtherStatement.setLong(1, j);
                    orderOtherStatement.setString(2, i + "" + j);
                    orderOtherStatement.execute();
                }
            }
        }
    }

    private void select(DataSource dataSource) throws Exception {
        String querySql = "SELECT * FROM `order` o inner join `order_other` oo on o.order_id = oo.order_id where o.user_id =? and o.order_id =?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(querySql)) {
            preparedStatement.setLong(1, 1);
            preparedStatement.setLong(2, 3);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.print(rs.getInt(1) + " ");
                    System.out.print(rs.getLong(2) + " ");
                    System.out.print(rs.getLong(3) + " ");
                    System.out.print(rs.getInt(4) + " ");
                    System.out.print(rs.getLong(5) + " ");
                    System.out.println(rs.getString(6));
                }
            }
        }
    }
}
