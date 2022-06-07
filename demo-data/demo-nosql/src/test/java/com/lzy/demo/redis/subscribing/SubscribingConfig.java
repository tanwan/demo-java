package com.lzy.demo.redis.subscribing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class SubscribingConfig {

    /**
     * 声明监听容器
     *
     * @param redisConnectionFactory the redis connection factory
     * @return the redis message listener container
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        //添加监听,PatternTopic可以带通配符
        container.addMessageListener(messageListenerAdapter(), new PatternTopic("chat.*"));
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new Receiver(), "receive2");
        messageListenerAdapter.afterPropertiesSet();
        container.addMessageListener(messageListenerAdapter, new PatternTopic("chat2.*"));
        return container;
    }

    /**
     * 声明监听适配器,必须是spring bean,或者手动执行afterPropertiesSet方法
     *
     * @return the message listener adapter
     */
    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new Receiver(), "receive");
    }
}
