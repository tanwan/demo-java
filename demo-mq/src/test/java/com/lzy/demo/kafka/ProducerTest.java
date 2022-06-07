package com.lzy.demo.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class ProducerTest {

    private static final String KAFKA_SERVERS = "127.0.0.1:9092";

    /**
     * 测试生产者
     */
    @Test
    public void testProducer() {
        Properties properties = new Properties();
        // 多个使用逗号分隔
        properties.put("bootstrap.servers", KAFKA_SERVERS);
        properties.put("acks", "all");
        properties.put("retries", 3);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        // 序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(properties);

        ProducerRecord<String, String> record = new ProducerRecord<>("demo_topic", "key", "Hello World");

        producer.send(record);

        producer.close();
    }
}
