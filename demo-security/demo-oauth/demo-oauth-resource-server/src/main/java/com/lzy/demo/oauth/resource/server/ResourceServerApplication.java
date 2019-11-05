/*
 * Created by LZY on 2016-09-22.
 */
package com.lzy.demo.oauth.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 默认会扫描这个类所处的包
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootApplication(scanBasePackages = "com.lzy")
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
