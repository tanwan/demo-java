package com.lzy.demo.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.jupiter.api.Test;

public class ProducerTest {

    private static final String NAME_SERVERS = "127.0.0.1:9876";


    /**
     * 测试生产者
     *
     * @throws Exception the exception
     */
    @Test
    public void testProducer() throws Exception {
        //生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_group");
        producer.setNamesrvAddr(NAME_SERVERS);
        producer.start();
        Message msg = new Message("demo_topic", "TagA", "hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
        //发送
        SendResult sendResult = producer.send(msg);
        System.out.println(sendResult);
        //关闭生产者
        producer.shutdown();
    }


}
