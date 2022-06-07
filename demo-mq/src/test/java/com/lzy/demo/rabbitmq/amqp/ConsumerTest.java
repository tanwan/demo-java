package com.lzy.demo.rabbitmq.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ConsumerTest extends AbstractAmqpTest {

    /**
     * 测试消费者
     *
     * @throws Exception the exception
     */
    @Test
    public void testReceiver() throws Exception {
        String queueName = "amqp.client.direct_queue";
        channel.queueDeclare(queueName, true, false, false, null);

        //broker最多发送prefetchCount条消息给消费者处理,也相当于unacked消息的最大值
        channel.basicQos(1);

        //autoAck:是否自动确认接收.只有确认接收的消息,队列才会把这条消息删除
        //自动接收:consumer只是接收到消息,而不保证已经正确处理消息
        channel.basicConsume(queueName, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
                //手动确认接收
                System.out.println(envelope.getDeliveryTag());
                channel.basicAck(envelope.getDeliveryTag(), false);
                //手动拒绝消息,basicNack可以设置一次拒绝多条,而basicReject一次只能拒绝一条
                //channel.basicNack(envelope.getDeliveryTag(),false,false);
                //channel.basicReject(envelope.getDeliveryTag(),false);
            }
        });
        Thread.sleep(1000);
    }
}
