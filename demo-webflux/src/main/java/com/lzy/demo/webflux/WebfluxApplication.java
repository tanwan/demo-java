/*
 * Created by lzy on 2019/12/2 2:01 PM.
 */
package com.lzy.demo.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Webflux application.
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication
public class WebfluxApplication {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        // 见ReactorNetty,限制线程数
        System.setProperty("reactor.netty.ioWorkerCount", "1");
        SpringApplication.run(WebfluxApplication.class, args);
    }
}
