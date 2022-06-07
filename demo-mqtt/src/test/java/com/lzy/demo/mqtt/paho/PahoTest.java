package com.lzy.demo.mqtt.paho;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.jupiter.api.Test;

public class PahoTest {
    private static final String BROKER = "tcp://localhost:1883";

    /**
     * mqtt 发送
     */
    @Test
    public void testPublish() {
        String baseTopic = "top/topic";
        try {
            // 创建客户端, clientId需要唯一, 并且连接到broker后需要保持不变
            MqttClient mqttClient = new MqttClient(BROKER, "simpleMQTTPublishClient", new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(false);
            // 这边使用eclipse-mosquitto作用broker,配置了可以匿名登录
            // connOpts.setUserName();
            // connOpts.setPassword();

            mqttClient.connect(connOpts);

            for (int i = 0; i < 3; i++) {
                MqttMessage message = new MqttMessage(("hello world" + i).getBytes());
                // 设置消息的服务质量,0:最多一次, 1:至少一次, 2: 只有一次
                message.setQos(1);
                mqttClient.publish(baseTopic + i, message);
            }

            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }


    /**
     * mqtt订阅
     *
     * @throws InterruptedException interrupted exception
     */
    @Test
    public void testSubscribeOneTopic() throws InterruptedException {

        try {
            MqttClient client = new MqttClient(BROKER, "simpleMQTTSubscribeOneTopicClient", new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置超时时间(秒)
            options.setConnectionTimeout(10);
            // 设置监听
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("topic:" + topic);
                    System.out.println("Qos:" + message.getQos());
                    System.out.println("message content:" + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete:" + token.isComplete());
                }
            });
            client.connect(options);
            // 订阅消息
            client.subscribe("top/topic1", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(30000L);
    }

    /**
     * mqtt订阅
     *
     * @throws InterruptedException interrupted exception
     */
    @Test
    public void testSubscribeMultipleTopic() throws InterruptedException {
        try {
            MqttClient client = new MqttClient(BROKER, "simpleMQTTSubscribeMultipleTopicClient", new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置超时时间(秒)
            options.setConnectionTimeout(10);
            client.connect(options);
            // 订阅top下的所有topic, 可以直接使用subscribe添加监听
            client.subscribe("top/#", (topic, message) -> {
                System.out.println("topic:" + topic);
                System.out.println("Qos:" + message.getQos());
                System.out.println("message content:" + new String(message.getPayload()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread.sleep(30000L);
    }
}

