package com.lzy.demo.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        // 见ReactorNetty,限制线程数
        System.setProperty("reactor.netty.ioWorkerCount", "1");
        SpringApplication.run(WebfluxApplication.class, args);
    }
}
