package com.lzy.demo.jpa.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
@EnableJpaRepositories(basePackages = "com.lzy.demo.jpa")
@EntityScan("com.lzy.demo.jpa")
@EnableSpringDataWebSupport
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.lzy.demo.jpa")
public class JpaApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }
}
