/*
 * Created by lzy on 2020/4/18 10:54 PM.
 */
package com.lzy.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * The type Consumer test.
 *
 * @author lzy
 * @version v1.0
 */
public class ConsumerTest {
    private static final String NAME_SERVERS = "127.0.0.1:9876";

    /**
     * 测试消费
     *
     * @throws Exception the exception
     */
    @Test
    public void testConsumer() throws Exception {

        //消费组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo_consumer_group");
        consumer.setNamesrvAddr(NAME_SERVERS);

        consumer.subscribe("demo_topic", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                msgs.forEach(msg -> System.out.println("receive:" + new String(msg.getBody())));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //启动消费
        consumer.start();
        Thread.sleep(10000);
    }
}

