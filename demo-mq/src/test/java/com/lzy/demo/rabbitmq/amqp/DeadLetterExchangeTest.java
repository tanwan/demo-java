package com.lzy.demo.rabbitmq.amqp;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DeadLetterExchangeTest extends AbstractAmqpTest {

    /**
     * 测试死信
     *
     * @throws Exception the exception
     */
    @Test
    public void testDLX() throws Exception {
        // 声明死信Exchange
        String deadLetterExchangeName = "amqp.client.dlx";
        channel.exchangeDeclare(deadLetterExchangeName, BuiltinExchangeType.DIRECT, true);
        // 声明死信队列
        String deadQueueName = "amqp.client.dlx_queue";
        channel.queueDeclare(deadQueueName, true, false, false, null);
        channel.queueBind(deadQueueName, deadLetterExchangeName, "");


        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", deadLetterExchangeName);
        // 如果不指定死信的routingKey的话,默认使用原有的routingKey
        arguments.put("x-dead-letter-routing-key", "");

        String withDLXExchangeName = "amqp.client.with_dlx_exchange";
        channel.exchangeDeclare(withDLXExchangeName, BuiltinExchangeType.DIRECT, true);

        // 1. 消息被拒绝(Basic.Reject/Basic.Nack)井且设置requeue参数为false
        String rejectQueueName = "amqp.client.reject_queue";
        String rejectRoutingKey = "reject";
        channel.queueDeclare(rejectQueueName, true, false, false, arguments);
        channel.queueBind(rejectQueueName, withDLXExchangeName, rejectRoutingKey);
        // 手动确认
        channel.basicConsume(rejectQueueName, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                channel.basicReject(envelope.getDeliveryTag(), false);
            }
        });
        channel.basicPublish(withDLXExchangeName, rejectRoutingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "reject".getBytes());

        String nackQueueName = "amqp.client.nack_queue";
        String nackRoutingKey = "nack";
        channel.queueDeclare(nackQueueName, true, false, false, arguments);
        channel.queueBind(nackQueueName, withDLXExchangeName, nackRoutingKey);
        // 手动确认
        channel.basicConsume(nackQueueName, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                channel.basicNack(envelope.getDeliveryTag(), false, false);
            }
        });
        channel.basicPublish(withDLXExchangeName, nackRoutingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "nack".getBytes());

        // 2. 消息过期,这种场景,可以用来实现延时消息
        String ttlQueueName = "amqp.client.ttl_queue";
        String ttlRoutingKey = "ttl";
        arguments.put("x-message-ttl", 1000);
        channel.queueDeclare(ttlQueueName, true, false, false, arguments);
        channel.queueBind(ttlQueueName, withDLXExchangeName, ttlRoutingKey);
        channel.basicPublish(withDLXExchangeName, ttlRoutingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "ttl".getBytes());

        // 3. 消息队列达到最大长度
        String maxLengthQueueName = "amqp.client.max_length_queue";
        String maxLengthRoutingKey = "maxLength";
        arguments.remove("x-message-ttl");
        arguments.put("x-max-length", 1);
        channel.queueDeclare(maxLengthQueueName, true, false, false, arguments);
        channel.queueBind(maxLengthQueueName, withDLXExchangeName, maxLengthRoutingKey);
        channel.basicPublish(withDLXExchangeName, maxLengthRoutingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "maxLength".getBytes());
        // 由于未消费的队列最多只能有1条,因此下一条会进行死信队列
        channel.basicPublish(withDLXExchangeName, maxLengthRoutingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, "maxLength".getBytes());
        Thread.sleep(2000);
    }

    /**
     * 测试延时消息
     *
     * @throws Exception the exception
     */
    @Test
    public void testDelayMessage() throws Exception {
        // 死信Exchange
        String dlxDelayExchangeName = "amqp.client.dlx_delay";
        channel.exchangeDeclare(dlxDelayExchangeName, BuiltinExchangeType.DIRECT, true);
        // 延时5s的死信队列
        String dlxDelay5SQueue = "amqp.client.dlx_delay_queue_5s";
        channel.queueDeclare(dlxDelay5SQueue, true, false, false, null);
        channel.queueBind(dlxDelay5SQueue, dlxDelayExchangeName, "5S");
        channel.basicConsume(dlxDelay5SQueue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive:" + LocalDateTime.now() + ":" + new String(body));
            }
        });
        // 延时10s有死信队列
        String dlxDelay10SQueue = "amqp.client.dlx_delay_queue_10s";
        channel.queueDeclare(dlxDelay10SQueue, true, false, false, null);
        channel.queueBind(dlxDelay10SQueue, dlxDelayExchangeName, "10S");
        channel.basicConsume(dlxDelay10SQueue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive:" + LocalDateTime.now() + ":" + new String(body));
            }
        });

        // 延时Exchange
        String delayExchangeName = "amqp.client.delay";
        channel.exchangeDeclare(delayExchangeName, BuiltinExchangeType.DIRECT, true);
        Map<String, Object> arguments = new HashMap<>(2);
        arguments.put("x-dead-letter-exchange", dlxDelayExchangeName);
        // 延时5s的队列
        String delay5SQueue = "amqp.client.delay_queue_5s";
        arguments.put("x-message-ttl", 5000);
        channel.queueDeclare(delay5SQueue, true, false, false, arguments);
        // 5S延时
        channel.queueBind(delay5SQueue, delayExchangeName, "5S");
        // 延时10s有队列
        String delay10SQueue = "amqp.client.delay_queue_10s";
        arguments.put("x-message-ttl", 10000);
        channel.queueDeclare(delay10SQueue, true, false, false, arguments);
        // 10S延时
        channel.queueBind(delay10SQueue, delayExchangeName, "10S");
        channel.basicPublish(delayExchangeName, "5S", MessageProperties.PERSISTENT_TEXT_PLAIN, (LocalDateTime.now() + ",5S").getBytes());
        channel.basicPublish(delayExchangeName, "10S", MessageProperties.PERSISTENT_TEXT_PLAIN, (LocalDateTime.now() + ",10S").getBytes());

        Thread.sleep(11000);
    }
}
