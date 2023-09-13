package com.lzy.demo.brave;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BraveServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BraveServerApplication.class, args);
    }
}
