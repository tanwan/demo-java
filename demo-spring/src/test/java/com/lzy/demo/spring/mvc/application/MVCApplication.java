/*
 * Created by lzy on 2019-08-02 08:52.
 */
package com.lzy.demo.spring.mvc.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * spring mvc 启动类
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class MVCApplication {
}
