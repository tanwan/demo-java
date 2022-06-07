package com.lzy.demo.atomikos;

import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * 如果表不存在,则先执行atomikos/ddl.sql
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AtomikosTest {

    private DataSource firstDB;
    private DataSource secondDB;

    private static final String INSERT_SQL = "INSERT INTO simple_atomikos (name) VALUES ('%s')";
    private static final String QUERY_SQL = "SELECT * FROM simple_atomikos WHERE name = '%s'";

    /**
     * 测试插入成功
     */
    @Test
    public void testInsertSuccess() throws Exception {
        String value = UUID.randomUUID().toString();
        insertValue(value, false);

        Connection firstDBConnection = firstDB.getConnection();
        Statement s1 = firstDBConnection.createStatement();
        ResultSet rs1 = s1.executeQuery(String.format(QUERY_SQL, value));

        Connection secondDBConnection = firstDB.getConnection();
        Statement s2 = secondDBConnection.createStatement();
        ResultSet rs2 = s2.executeQuery(String.format(QUERY_SQL, value));

        Assertions.assertThat(rs1.next()).isTrue();
        Assertions.assertThat(rs2.next()).isTrue();
    }

    /**
     * 测试回滚
     */
    @Test
    public void testRollback() throws Exception {
        String value = UUID.randomUUID().toString();
        insertValue(value, true);

        Connection firstDBConnection = firstDB.getConnection();
        Statement s1 = firstDBConnection.createStatement();
        ResultSet rs1 = s1.executeQuery(String.format(QUERY_SQL, value));

        Connection secondDBConnection = firstDB.getConnection();
        Statement s2 = secondDBConnection.createStatement();
        ResultSet rs2 = s2.executeQuery(String.format(QUERY_SQL, value));

        Assertions.assertThat(rs1.next()).isFalse();
        Assertions.assertThat(rs2.next()).isFalse();
    }

    private void insertValue(String value, boolean throwException) throws Exception {
        String sql = String.format(INSERT_SQL, value);
        UserTransactionImp utx = new UserTransactionImp();
        boolean rollback = false;
        try {
            utx.begin();
            Connection firstConnection = firstDB.getConnection();
            Connection secondConnection = secondDB.getConnection();
            Statement s1 = firstConnection.createStatement();
            s1.executeUpdate(sql);
            s1.close();
            Statement s2 = secondConnection.createStatement();
            s2.executeUpdate(sql);
            s2.close();
            firstConnection.close();
            secondConnection.close();
            if (throwException) {
                throw new RuntimeException("expect exception");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollback = true;
        } finally {
            if (rollback) {
                utx.rollback();
            } else {
                utx.commit();
            }
        }
    }

    @BeforeAll
    public void init() throws SQLException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        //YamlMapFactoryBean 读取成map, YamlPropertiesFactoryBean读取成properties, YamlPropertySourceLoader可以读取多文档
        YamlMapFactoryBean yamlMapFactoryBean = new YamlMapFactoryBean();

        yamlMapFactoryBean.setResources(resourceLoader.getResource("application.yml"));
        Map<String, Object> map = yamlMapFactoryBean.getObject();
        Map<String, Object> firstDBMap = (Map<String, Object>) ((Map<String, Object>) map.get("spring")).get("datasource");
        Map<String, Object> secondDBMap = (Map<String, Object>) ((Map<String, Object>) map.get("spring")).get("datasource2");

        firstDB = getDataSource("firstDB", firstDBMap);
        secondDB = getDataSource("secondDB", secondDBMap);
        firstDB.getConnection();


        //设置atomikos的日志路径
        Properties properties = new Properties();
        properties.setProperty("com.atomikos.icatch.log_base_dir", "log");
        new UserTransactionServiceImp(properties).init();
    }


    private DataSource getDataSource(String db, Map<String, Object> datasourceMap) {
        AtomikosDataSourceBean ads = new AtomikosDataSourceBean();
        //这边也可以手动创建MysqlXADataSource,然后设置到AtomikosDataSourceBean中
        ads.setXaDataSourceClassName(MysqlXADataSource.class.getName());
        Properties properties = new Properties();
        properties.put("databaseName", db);
        properties.put("url", datasourceMap.get("url").toString());
        properties.put("user", datasourceMap.get("username").toString());
        properties.put("password", datasourceMap.get("password").toString());
        ads.setXaProperties(properties);
        ads.setUniqueResourceName(db);
        ads.setPoolSize(10);
        ads.setBorrowConnectionTimeout(10);
        return ads;
    }
}
