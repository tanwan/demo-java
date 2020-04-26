/*
 * Created by LZY on 2017/8/5 22:35.
 */
package com.lzy.demo.mongo;

import com.lzy.demo.mongo.config.MongoConfiguration;
import com.lzy.demo.mongo.model.SimpleMongo;
import com.lzy.demo.mongo.repository.SimpleMongoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

/**
 * The type Mongo test.
 *
 * @author LZY
 * @version v1.0
 */
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(initializers = ConfigFileApplicationContextInitializer.class, classes = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoConfiguration.class})
@TestPropertySource(properties = "spring.config.location=classpath:mongo.yml")
public class SpringMongoTest {

    @Resource
    private SimpleMongoRepository simpleMongoRepository;

    /**
     * 测试保存
     */
    @Test
    public void testSave() {
        SimpleMongo simpleMongo = new SimpleMongo();
        simpleMongo.setAge(24);
        simpleMongo.setName("lzy");
        simpleMongoRepository.save(simpleMongo);
    }
}
