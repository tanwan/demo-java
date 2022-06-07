package com.lzy.demo.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MongoTest {
    private MongoDatabase database;

    private MongoCollection<Document> collection;

    @BeforeAll
    private void init() {
        // 多个使用逗号分隔,这边的url可以包含数据库名,也可以不包含,如果不包含数据库名,需要保证用户名是admin库的,否则无法连接
        // 比如demo这个用户是属于demo数据库的,如果url不包含数据库名,则无法进行连接
        ConnectionString connString = new ConnectionString("mongodb://demo:123456@127.0.0.1:27017/demo");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("demo");
        collection = database.getCollection("restaurants");
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        Document document = new Document("name", "名称")
                .append("contact", new Document("phone", "228-555-0149")
                        .append("email", "cafeconleche@example.com")
                        .append("location", Arrays.asList(-73.92502, 40.8279556)))
                .append("stars", 3)
                .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));
        collection.insertOne(document);
    }
}
