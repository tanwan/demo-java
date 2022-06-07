package com.lzy.demo.mongo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.lzy.demo.mongo.repository")
public class MongoConfiguration {
}
