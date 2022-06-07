package com.lzy.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeclarableConfig {
    public static final String DIRECT_EXCHANGE = "amqp.spring.direct";
    public static final String DIRECT_ROUTING_KEY1 = "amqp.spring.direct.queue1";
    public static final String DIRECT_ROUTING_KEY2 = "amqp.spring.direct.queue2";
    /**
     * Direct exchange direct exchange.
     *
     * @return the exchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    /**
     * 一次声明多个队列和Binding
     *
     * @param directExchange the direct exchange
     * @return the list
     */
    @Bean
    public Declarables directBindings(DirectExchange directExchange) {
        return new Declarables(
                new Queue("amqp.spring.direct.queue1"),
                new Queue("amqp.spring.direct.queue2"),
                new Binding("amqp.spring.direct.queue1",
                        Binding.DestinationType.QUEUE, directExchange.getName(),
                        DIRECT_ROUTING_KEY1, null),
                new Binding("amqp.spring.direct.queue2",
                        Binding.DestinationType.QUEUE, directExchange.getName(),
                        DIRECT_ROUTING_KEY2, null)
        );
    }
}
