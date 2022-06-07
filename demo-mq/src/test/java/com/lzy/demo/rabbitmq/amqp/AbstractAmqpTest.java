package com.lzy.demo.rabbitmq.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public abstract class AbstractAmqpTest {

    protected Connection connection;
    protected Channel channel;

    @BeforeEach
    public void before() throws IOException, TimeoutException {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        yamlPropertiesFactoryBean.setResources(resourceLoader.getResource("application.yml"));
        Properties rabbitmq = yamlPropertiesFactoryBean.getObject();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitmq.getProperty("spring.rabbitmq.host"));
        factory.setPort(Integer.valueOf(rabbitmq.getProperty("spring.rabbitmq.port")));
        factory.setUsername(rabbitmq.getProperty("spring.rabbitmq.username"));
        factory.setPassword(rabbitmq.getProperty("spring.rabbitmq.password"));
        factory.setVirtualHost(rabbitmq.getProperty("spring.rabbitmq.virtual-host"));
        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    @AfterEach
    public void after() throws IOException, TimeoutException {
        try {
            channel.close();
            connection.close();
        } catch (Exception e) {

        }
    }
}
