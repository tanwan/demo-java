/*
 * Created by lzy on 2020/5/28 9:09 AM.
 */
package com.lzy.demo.sharding.application;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 防止扫描到其它的启动类
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class Application {
}
