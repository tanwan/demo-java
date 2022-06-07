package com.lzy.demo.oauth.authorization.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;

/**
 * H2数据库配置,访问<a href="http://127.0.0.1:18080/h2-console">h2-console</a>可以进入console
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class H2DBConfig {

    /**
     * 这边直接使用H2内存数据库,如果使用真实数据库,也需要执行下面的ddl
     *
     * @return the embedded database
     * @see JdbcRegisteredClientRepository
     * @see JdbcOAuth2AuthorizationService
     * @see <a href="https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources">spring oauth2 ddl</a>
     */
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase embeddedDatabase() {
        return new EmbeddedDatabaseBuilder()
                .setName("oauth2-db")
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                // 在JdbcOAuth2AuthorizationService同级目录下, 直接点击可以跳转过去
                .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql")
                .addScript("org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql")
                // 在JdbcRegisteredClientRepository的同级目录下
                .addScript("org/springframework/security/oauth2/server/authorization/client/oauth2-registered-client-schema.sql")
                .build();
    }
}
