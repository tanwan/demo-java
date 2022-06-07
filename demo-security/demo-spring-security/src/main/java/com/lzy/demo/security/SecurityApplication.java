package com.lzy.demo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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
