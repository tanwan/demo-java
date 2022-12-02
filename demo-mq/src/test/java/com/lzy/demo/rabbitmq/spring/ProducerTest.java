package com.lzy.demo.rabbitmq.spring;


import com.lzy.demo.config.DeclarableConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@SpringBootTest(classes = {DeclarableConfig.class})
public class ProducerTest {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 使用RabbitTemplate#send()发布消息
     */
    @Test
    public void testSend() {
        rabbitTemplate.send(DeclarableConfig.DIRECT_EXCHANGE, DeclarableConfig.DIRECT_ROUTING_KEY1,
                MessageBuilder.withBody("hello world".getBytes())
                        .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                        .build());
    }

    /**
     * 使用RabbitTemplate#convertAndSend()发布消息
     */
    @Test
    public void testConvertAndSend() {
        Map<String, Object> params = new HashMap<>();
        params.put("hello", "world");
        rabbitTemplate.convertAndSend(DeclarableConfig.DIRECT_EXCHANGE, DeclarableConfig.DIRECT_ROUTING_KEY2, params);
    }

    /**
     * 测试重试
     *
     * @throws Exception the exception
     */
    @Test
    public void testRetry() throws Exception {
        RetryTemplate retryTemplate = new RetryTemplate();
        String message = "hello world";
        retryTemplate.execute(
                new RetryCallback<Object, Exception>() {
                    @Override
                    public Object doWithRetry(RetryContext context) throws Exception {
                        rabbitTemplate.convertAndSend(DeclarableConfig.DIRECT_EXCHANGE, DeclarableConfig.DIRECT_ROUTING_KEY1, message);
                        return null;
                    }
                }, new RecoveryCallback<Object>() {
                    @Override
                    public Object recover(RetryContext context) throws Exception {
                        Throwable t = context.getLastThrowable();
                        t.printStackTrace();
                        return null;
                    }
                });
    }


    /**
     * 确定消息发布到broker
     * CachingConnectionFactory#setPublisherConfirmType要设置为CORRELATED(spring.rabbitmq.publisher-confirm-type=CORRELATED)
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testConfirm() throws InterruptedException {
        //correlationData 发送的时候指定的,用于确定是哪一条消息
        //ack 消息是否已经到达broker
        //cause 消息的未到达broker的原因
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println(correlationData);
            System.out.println(ack);
            System.out.println(cause);
        });
        // 这条ack
        rabbitTemplate.convertAndSend(DeclarableConfig.DIRECT_EXCHANGE, DeclarableConfig.DIRECT_ROUTING_KEY1, "hello world", new CorrelationData(UUID.randomUUID().toString()));
        // 这条nack
        rabbitTemplate.convertAndSend("noExchange", DeclarableConfig.DIRECT_ROUTING_KEY1, "hello world", new CorrelationData(UUID.randomUUID().toString()));
        Thread.sleep(1000);
    }

    /**
     * 确定消息路由到queue
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testMandatory() throws InterruptedException {
        // 可以强制开启Mandatory
        // rabbitTemplate.setMandatory(true);
        // rabbitTemplate.setMandatoryExpression()
        // exchange存在,但是无法被路由到队列的才会执行此回调方法,正常路由和exchange不存在的都不执行此方法
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("message: " + returned.getMessage());
            System.out.println("replyCode: " + returned.getReplyCode() + " replyText: " + returned.getReplyText() +
                    " exchange: " + returned.getExchange() + " routingKey: " + returned.getRoutingKey());
        });
        rabbitTemplate.convertAndSend(DeclarableConfig.DIRECT_EXCHANGE, "", "no-queue exchange");
        Thread.sleep(1000);
    }

    /**
     * The type Transaction test.
     */
    @SpringBootApplication
    @SpringBootTest(classes = {DeclarableConfig.class, ProducerTest.SimpleTransaction.class}, properties = "spring.rabbitmq.publisher-confirms=false")
    public static class TransactionTest extends ProducerTest {
        /**
         * Test transaction.
         *
         * @param simpleTransaction the simple transaction
         */
        @Test
        public void testTransaction(@Autowired SimpleTransaction simpleTransaction) {
            simpleTransaction.transaction();
        }
    }

    /**
     * The type SimpleTransaction.
     */
    @Service
    public static class SimpleTransaction {

        @Resource
        private RabbitTemplate rabbitTemplate;

        /**
         * Rabbit transaction manager rabbit transaction manager.
         *
         * @param connectionFactory the connection factory
         * @return the rabbit transaction manager
         */
        @Bean
        public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
            return new RabbitTransactionManager(connectionFactory);
        }

        @Transactional
        public void transaction() {
            //开启务事rabbitTemplate.setChannelTransacted(true);
            rabbitTemplate.setChannelTransacted(true);
            rabbitTemplate.convertAndSend(DeclarableConfig.DIRECT_EXCHANGE, DeclarableConfig.DIRECT_ROUTING_KEY1, "hello world");
            throw new RuntimeException("expect exception");
        }
    }

}
