/*
 * Created by LZY on 2017/8/5 22:19.
 */
package com.lzy.demo.mongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author LZY
 * @version v1.0
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.lzy.demo.mongo.repository")
public class MongoConfiguration {
}
