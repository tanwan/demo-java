package com.lzy.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 微服务调用方
 *
 * @author lzy
 * @version v1.0
 */
@EnableDiscoveryClient
@SpringBootApplication
//开启feign的功能
@EnableFeignClients
public class ClientApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }
}
