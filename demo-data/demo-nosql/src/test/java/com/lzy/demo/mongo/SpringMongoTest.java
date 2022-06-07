package com.lzy.demo.mongo;

import com.lzy.demo.mongo.config.MongoConfiguration;
import com.lzy.demo.mongo.model.SimpleMongo;
import com.lzy.demo.mongo.repository.SimpleMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoConfiguration.class})
@ActiveProfiles("mongo")
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
