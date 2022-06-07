package com.lzy.demo.spring.boot.autoconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * 用来debug spring自动配置
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class AutoConfigurationDebug {


    /**
     * 用来debug spring自动配置
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationDebug.class, args);
    }
}
