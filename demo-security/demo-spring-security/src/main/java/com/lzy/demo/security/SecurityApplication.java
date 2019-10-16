/*
 * Created by LZY on 2016-09-22.
 */
package com.lzy.demo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author LZY
 * @version v1.0
 */
@SpringBootApplication(scanBasePackages = "com.lzy")
public class SecurityApplication {
    /**
     * main函数
     *
     * @param args the input arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
