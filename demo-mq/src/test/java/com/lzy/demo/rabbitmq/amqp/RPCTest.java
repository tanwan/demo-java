package com.lzy.demo.rabbitmq.amqp;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCTest extends AbstractAmqpTest {

    /**
     * RPC服务端
     *
     * @throws Exception the exception
     */
    @Test
    public void testRPCServer() throws Exception {
        String queueName = "amqp-client.queue";
        channel.queueDeclare(queueName, false, false, true, null);
        //一次只能处理一条
        channel.basicQos(1);
        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
                //发送到reply指定的队列
                channel.basicPublish("", properties.getReplyTo(), new AMQP.BasicProperties().builder()
                        .correlationId(properties.getCorrelationId()).build(), "rpc reply".getBytes());
                //手动发送消息确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
        Thread.sleep(100000);
    }

    /**
     * RPC客户端
     *
     * @throws Exception the exception
     */
    @Test
    public void testRPCClient() throws Exception {
        String queueName = "amqp-client.queue";
        channel.queueDeclare(queueName, false, false, true, null);
        // 创建一个匿名的queue
        String replyQueue = channel.queueDeclare().getQueue();
        String corrId = java.util.UUID.randomUUID().toString();

        channel.basicPublish("", queueName,
                new AMQP.BasicProperties()
                        .builder().replyTo(replyQueue).correlationId(corrId)
                        .build(),
                "rpc request".getBytes());
        // 使用阻塞队列
        BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
        channel.basicConsume(replyQueue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body));
                }
            }
        });
        System.out.println(response.take());
    }
}
