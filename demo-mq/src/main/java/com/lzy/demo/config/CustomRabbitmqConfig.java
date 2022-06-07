package com.lzy.demo.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@ConditionalOnProperty(name = "custom.config.rabbitmq", havingValue = "true", matchIfMissing = true)
public class CustomRabbitmqConfig {

    /**
     * rabbitmq连接工厂
     *
     * @return the caching connection factory
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.RabbitConnectionFactoryCreator#rabbitConnectionFactory(RabbitProperties)
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public CachingConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        //如果使用事务,就不能使用confirms模式
        //cachingConnectionFactory.setPublisherConfirms(true);
        return cachingConnectionFactory;
    }

    /**
     * RabbitTemplate
     *
     * @param rabbitConnectionFactory the rabbit connection factory
     * @return the amqp template
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.RabbitTemplateConfiguration#rabbitTemplate(ConnectionFactory)
     */
    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(@Qualifier("rabbitConnectionFactory") ConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        template.setRetryTemplate(retryTemplate);
        return template;
    }

    /**
     * AmqpAdmin
     *
     * @param rabbitConnectionFactory the rabbit connection factory
     * @return the rabbit admin
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration.RabbitTemplateConfiguration#amqpAdmin(ConnectionFactory)
     */
    @Bean
    public AmqpAdmin amqpAdmin(@Qualifier("rabbitConnectionFactory") ConnectionFactory rabbitConnectionFactory) {
        return new RabbitAdmin(rabbitConnectionFactory);
    }

    /**
     * 监听的Container
     *
     * @param rabbitConnectionFactory the rabbit connection factory
     * @return the simple rabbit listener container factory
     * @see org.springframework.boot.autoconfigure.amqp.RabbitAnnotationDrivenConfiguration
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("rabbitConnectionFactory") ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        // 设置消息转换器
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //最少消费者数量
        factory.setConcurrentConsumers(2);
        //最大消费者数量
        factory.setMaxConcurrentConsumers(10);
        //一个消费者每处理consecutiveActiveTrigger个消息,触发考虑增加消费者
        factory.setConsecutiveActiveTrigger(10);
        //增加消费者最小间隔
        factory.setStartConsumerMinInterval(10000L);
        //true:如果监听抛出异常,那么消息将会重新入队,false:消息将会被reject(如果有死信,会发到死信队列)
        factory.setDefaultRequeueRejected(false);
        //也可以设置事务,如果设置了,那么setDefaultRequeueRejected就无效了,需要使用AbstractMessageListenerContainer#setAlwaysRequeueWithTxManagerRollback
        //factory.setTransactionManager();
        return factory;
    }

}
