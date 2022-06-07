package com.lzy.demo.axon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AxonApplication {
    /**
     * 需要启动axon server(使用docker)
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AxonApplication.class, args);
    }
}
