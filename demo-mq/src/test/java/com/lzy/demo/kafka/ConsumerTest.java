package com.lzy.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsumerTest {

    private static final String KAFKA_SERVERS = "127.0.0.1:9092";


    /**
     * 配置
     *
     * @param autoCommit the auto commit
     * @return the properties
     */
    public Properties initProperties(Boolean autoCommit) {
        Properties properties = new Properties();
        // 多个使用逗号分隔
        properties.put("bootstrap.servers", KAFKA_SERVERS);
        // 自动commit
        properties.put("enable.auto.commit", autoCommit);
        // 自动commit间隔
        properties.put("auto.commit.interval.ms", "1000");
        // 一次poll最大条数
        properties.put("max.poll.records", 10);
        // 序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // group
        properties.put("group.id", "demoGroup");
        return properties;
    }


    /**
     * 测试消费者,自动提交
     */
    @Test
    public void testConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(initProperties(true));

        // 一个消费者可以消费多个topic
        consumer.subscribe(Collections.singletonList("demo_topic"));
        // 添加监听,当订阅的topic列表改变,topic被创建或删除,consumer线程挂掉,添加新的consumer线程,会唤醒ConsumerRebalanceListener线程
        // consumer.subscribe(Collection<String> topics, ConsumerRebalanceListener listener);
        // 订阅那些满足一定规则的topic
        // consumer.subscribe(Pattern pattern, ConsumerRebalanceListener listener);
        while (true) {
            // 只有poll方法调用时,consumer才会真正去连接kafka集群
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }

    /**
     * 测试消费者,手动提交
     */
    @Test
    public void testConsumerManualCommit() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(initProperties(false));

        // 一个消费者可以消费多个topic
        consumer.subscribe(Collections.singletonList("demo_topic"));
        while (true) {
            // 只有poll方法调用时,consumer才会真正去连接kafka集群
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
            // kafka没有失败重试机制
            // 异步提交
            consumer.commitAsync();
            // 同步提交
            // consumer.commitSync();
        }
    }

}
