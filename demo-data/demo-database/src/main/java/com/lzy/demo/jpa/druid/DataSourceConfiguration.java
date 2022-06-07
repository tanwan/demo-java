package com.lzy.demo.jpa.druid;


import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

/**
 * 数据库连接池配置类
 *
 * DruidDataSourceAutoConfigure有自动配置了,所以这边注释掉
 * @author LZY
 * @version v1.0
 */
//@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource")
//@Configuration
public class DataSourceConfiguration {
    /**
     * Druid配置
     *
     * @param properties the properties
     * @return the druid data source
     * @see org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration
     * @see DruidDataSourceAutoConfigure
     */
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource(DataSourceProperties properties) {
        //DataSourceBuilder
        DruidDataSource dataSource = (DruidDataSource) properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
        DatabaseDriver databaseDriver = DatabaseDriver
                .fromJdbcUrl(properties.determineUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setValidationQuery(validationQuery);
        }
        dataSource.setProxyFilters(logFilter());
        return dataSource;
    }

    private List<Filter> logFilter() {
            Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
            slf4jLogFilter.setResultSetLogEnabled(false);
            slf4jLogFilter.setConnectionLogEnabled(false);
            slf4jLogFilter.setDataSourceLogEnabled(false);
            slf4jLogFilter.setStatementCreateAfterLogEnabled(false);
            slf4jLogFilter.setStatementParameterSetLogEnabled(false);
            slf4jLogFilter.setStatementExecuteQueryAfterLogEnabled(false);
            slf4jLogFilter.setStatementPrepareCallAfterLogEnabled(false);
            slf4jLogFilter.setStatementParameterClearLogEnable(false);
            slf4jLogFilter.setStatementPrepareAfterLogEnabled(false);
            slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
            return Collections.singletonList(slf4jLogFilter);
    }
}
