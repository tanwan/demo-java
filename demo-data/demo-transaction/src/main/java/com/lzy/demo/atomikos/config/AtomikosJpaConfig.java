package com.lzy.demo.atomikos.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import javax.sql.XADataSource;

/**
 * UserTransactionService和JtaTransactionManager使用springboot自动配置的
 * JtaTransactionManager需要使用UserTransactionService和TransactionManager,这边暂时不知道这两者的区别
 *
 * @author lzy
 * @version v1.0
 * @see AtomikosJtaConfiguration#atomikosTransactionManager(com.atomikos.icatch.config.UserTransactionService)
 * @see AtomikosJtaConfiguration#transactionManager(javax.transaction.UserTransaction, javax.transaction.TransactionManager, org.springframework.beans.factory.ObjectProvider)
 */
@Configuration
@EnableTransactionManagement
public class AtomikosJpaConfig {

    /**
     * 跟SecondConfig使用相同的transactionManager
     */
    @Configuration
    @EnableJpaRepositories(basePackages = "com.lzy.demo.atomikos.first",
            entityManagerFactoryRef = "firstEntityManager", transactionManagerRef = "transactionManager")
    public static class FirstConfig {

        /**
         * @return DataSourceProperties
         */
        @Bean
        @ConfigurationProperties(prefix = "spring.datasource")
        public DataSourceProperties firstXADataSourceProperties() {
            return new DataSourceProperties();
        }

        /**
         * @param dataSourceProperties dataSourceProperties
         * @return XADataSource
         * @see DataSourceConfiguration#createDataSource(org.springframework.boot.autoconfigure.jdbc.DataSourceProperties, java.lang.Class)
         */
        @Bean
        public MysqlXADataSource firstXADataSource(@Qualifier("firstXADataSourceProperties") DataSourceProperties dataSourceProperties) {
            return dataSourceProperties.initializeDataSourceBuilder().type(MysqlXADataSource.class).build();
        }

        /**
         * XADataSourceAutoConfiguration有从XADataSource转为DataSource,不过它只是单数据源的
         *
         * @param xaDataSource xaDataSource
         * @return dataSource
         * @see XADataSourceAutoConfiguration
         */
        @Primary
        @Bean(initMethod = "init", destroyMethod = "close")
        public AtomikosDataSourceBean firstDataSource(@Qualifier("firstXADataSource") XADataSource xaDataSource) {
            AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
            dataSource.setXaDataSource(xaDataSource);
            return dataSource;
        }

        /**
         * factoryBuilder是在HibernateJpaConfiguration配置的,但是它有个@ConditionalOnSingleCandidate(DataSource.class)的条件,所以这边firstDataSource加上了@Primary
         *
         * @param dataSource     dataSource
         * @param factoryBuilder factoryBuilder
         * @return transactionManager
         * @see JpaBaseConfiguration#entityManagerFactory(org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder)
         */
        @Bean
        public LocalContainerEntityManagerFactoryBean firstEntityManager(EntityManagerFactoryBuilder factoryBuilder, @Qualifier("firstDataSource") DataSource dataSource) {
            return factoryBuilder.dataSource(dataSource).packages("com.lzy.demo.atomikos.first")
                    .jta(true).build();
        }
    }


    /**
     * 跟FirstConfig使用相同的transactionManager
     */
    @Configuration
    @EnableJpaRepositories(basePackages = "com.lzy.demo.atomikos.second",
            entityManagerFactoryRef = "secondEntityManager", transactionManagerRef = "transactionManager")
    public static class SecondConfig {
        /**
         * @return DataSourceProperties
         */
        @Bean
        @ConfigurationProperties(prefix = "spring.datasource2")
        public DataSourceProperties secondXADataSourceProperties() {
            return new DataSourceProperties();
        }

        /**
         * @param dataSourceProperties dataSourceProperties
         * @return XADataSource
         * @see FirstConfig#firstXADataSource(org.springframework.boot.autoconfigure.jdbc.DataSourceProperties)
         */
        @Bean
        public MysqlXADataSource secondXADataSource(@Qualifier("secondXADataSourceProperties") DataSourceProperties dataSourceProperties) {
            return dataSourceProperties.initializeDataSourceBuilder().type(MysqlXADataSource.class).build();
        }


        /**
         * @param xaDataSource xaDataSource
         * @return AtomikosDataSourceBean
         */
        @Bean(initMethod = "init", destroyMethod = "close")
        public AtomikosDataSourceBean secondDataSource(@Qualifier("secondXADataSource") XADataSource xaDataSource) {
            AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
            dataSource.setXaDataSource(xaDataSource);
            return dataSource;
        }

        /**
         * @param factoryBuilder factoryBuilder
         * @param dataSource     dataSource
         * @return transactionManager
         * @see FirstConfig#firstEntityManager(org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder, javax.sql.DataSource)
         */
        @Bean
        public LocalContainerEntityManagerFactoryBean secondEntityManager(EntityManagerFactoryBuilder factoryBuilder, @Qualifier("secondDataSource") DataSource dataSource) {
            return factoryBuilder.dataSource(dataSource).packages("com.lzy.demo.atomikos.second")
                    .jta(true).build();
        }
    }


}
