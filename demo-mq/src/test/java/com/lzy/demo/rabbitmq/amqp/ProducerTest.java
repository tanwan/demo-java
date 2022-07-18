package com.lzy.demo.rabbitmq.amqp;

import com.rabbitmq.client.AlreadyClosedException;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.MessageProperties;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

public class ProducerTest extends AbstractAmqpTest {

    /**
     * 测试发送到fanout
     *
     * @throws Exception the exception
     */
    @Test
    public void testFanout() throws Exception {
        String exchangeName = "amqp.client.fanout";
        String message = "hello world";
        // 这边是为了测试,所以在这边创建了exchange和queue
        String queueName1 = "amqp.client.fanout.queue1";
        String queueName2 = "amqp.client.fanout.queue2";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueBind(queueName1, exchangeName, "");
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, exchangeName, "");
        // fanout会发送到queue1和queue2
        channel.basicPublish(exchangeName, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    /**
     * 测试Direct
     *
     * @throws Exception the exception
     */
    @Test
    public void testDirect() throws Exception {
        String exchangeName = "amqp.client.direct";
        String message = "hello word";
        String queueName1 = "amqp.client.direct.queue1";
        String routingKey1 = "routingKey1";
        String queueName2 = "amqp.client.direct.queue2";
        String routingKey2 = "routingKey2";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueBind(queueName1, exchangeName, routingKey1);
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, exchangeName, routingKey2);
        // 使用routingKey1发送,只会发送到queue1
        channel.basicPublish(exchangeName, routingKey1, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    /**
     * 测试topic
     *
     * @throws Exception the exception
     */
    @Test
    public void testTopic() throws Exception {
        String exchangeName = "amqp.client.topic";
        String message = "hello word";
        String queueName1 = "amqp.client.topic.queue1";
        String routingKey1 = "routingKey.#";
        String queueName2 = "amqp.client.topic.queue2";
        String routingKey2 = "routingKey2";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true);
        channel.queueDeclare(queueName1, true, false, false, null);
        channel.queueBind(queueName1, exchangeName, routingKey1);
        channel.queueDeclare(queueName2, true, false, false, null);
        channel.queueBind(queueName2, exchangeName, routingKey2);
        // 使用routingKey.1发送,只会queue1的routingKey匹配,因此只发送到queue1
        channel.basicPublish(exchangeName, "routingKey.1", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    /**
     * 测试直接发送到queue
     *
     * @throws Exception the exception
     */
    @Test
    public void testSendQueue() throws Exception {
        String queueName = "amqp.client.direct_queue";
        String message = "hello world";
        //rabbitmq有一个默认的exchange,是direct类型的,使用空字符串表示,这个queue就是绑定到这个默认的exchange上的
        channel.queueDeclare(queueName, true, false, false, null);
        //routing使用queue名称
        channel.basicPublish("", queueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
    }

    /**
     * 测试发送到不存在的exchange
     *
     * @throws Exception the exception
     */
    @Test
    public void testNoExistExchange() throws Exception {
        String message = "hello world";
        //如果发送到不存在的exchange,那么此channel将会被关闭,因此需要确保发送的exchange是已经存在的
        channel.basicPublish("noExistExchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        assertThatCode(() -> channel.queueDeclare("queueName", true, false, false, null))
                .isInstanceOf(AlreadyClosedException.class);
    }


}
