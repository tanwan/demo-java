package com.lzy.demo.oauth.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 资源管理器
 * @author LZY
 * @version v1.0
 * @see <a href="https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html">resource-server</a>
 */
@SpringBootApplication
public class ResourceServerApplication {
    /**
     * main函数
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }
}
