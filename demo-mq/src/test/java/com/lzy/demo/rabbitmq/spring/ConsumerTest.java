/*
 * Created by lzy on 2019-06-27 15:55.
 */
package com.lzy.demo.rabbitmq.spring;

import com.lzy.demo.config.DeclarableConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * The type Consumer test.
 *
 * @author lzy
 * @version v1.0
 */
@ExtendWith(SpringExtension.class)
@SpringJUnitConfig(classes = {DeclarableConfig.class})
@SpringBootApplication
@SpringBootTest
public class ConsumerTest {

    /**
     * Test consumer.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testConsumer() throws InterruptedException {
        Thread.sleep(1000000);
    }


    /**
     * The type Declare listener.
     */
    @Component
    public static class DeclareListener {
        /**
         * 在@RabbitListener声明,可以使用spel表达式
         *
         * @param message the message
         * @throws InterruptedException the interrupted exception
         */
        @RabbitListener(queues = DeclarableConfig.DIRECT_ROUTING_KEY1)
        public void consumer(String message) throws InterruptedException {
            System.out.println(message);
        }
    }
}
