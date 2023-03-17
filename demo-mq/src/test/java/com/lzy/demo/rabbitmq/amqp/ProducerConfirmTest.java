package com.lzy.demo.rabbitmq.amqp;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.MessageProperties;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProducerConfirmTest extends AbstractAmqpTest {

    /**
     * 测试mandatory
     *
     * @throws Exception the exception
     */
    @Test
    public void testMandatory() throws Exception {
        String mandatoryExchange = "amqp.client.mandatory";
        String message = "hello world";
        channel.exchangeDeclare(mandatoryExchange, BuiltinExchangeType.DIRECT, true);
        //exchange存在,但是无法被路由到队列的才会执行此回调方法,消息正常路由和exchange不存在的都不执行此回调方法,需要在basicPublish之前添加
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) ->
                System.out.println(new String(body)));
        channel.basicPublish(mandatoryExchange, "", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        Thread.sleep(1000);
    }


    /**
     * 使用事务发送,对性能有损耗
     *
     * @throws Exception the exception
     */
    @Test
    public void testTransactional() throws Exception {
        String message = "hello world";
        String transactionalQueue = "amqp.client.transactional_queue";
        channel.queueDeclare(transactionalQueue, true, false, false, null);
        //开启事务
        channel.txSelect();
        try {
            // 这里可以发送多条消息
            channel.basicPublish("", transactionalQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            //提交成功后,表示消息一定到达了broker
            channel.txCommit();
        } catch (Exception e) {
            e.printStackTrace();
            //出现异常后,回滚事务
            channel.txRollback();
        }
    }


    /**
     * 测试confirm模式(发送一条,确认一条)
     *
     * @throws Exception the exception
     */
    @Test
    public void testConfirmOneByOne() throws Exception {
        String message = "hello world";
        String confirmQueue = "amqp.client.confirm_queue";
        channel.queueDeclare(confirmQueue, true, false, false, null);
        channel.confirmSelect();
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        //阻塞直到broker给出ack,true表示ack,false表示nack
        assertTrue(channel.waitForConfirms());
    }

    /**
     * 测试confirm模式(批量确认)
     *
     * @throws Exception the exception
     */
    @Test
    public void testConfirmBatch() throws Exception {
        String confirmQueue = "amqp.client.confirm_queue";
        channel.confirmSelect();
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "1".getBytes());
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "1".getBytes());
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "1".getBytes());
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "1".getBytes());
        // 如果使用的是Channel#waitForConfirmsOrDie(),那么如果有nack的话,则会抛出异常
        // 如果这边返回是的nack,那么以上所有的消息都需要重发
        assertTrue(channel.waitForConfirms());
    }

    /**
     * 测试confirm listener
     *
     * @throws Exception the exception
     */
    @Test
    public void testConfirmListen() throws Exception {
        String confirmQueue = "amqp.client.confirm_queue";
        //需要手动维护未确认的delivery tag
        SortedSet<Long> sortedSet = new ConcurrentSkipListSet<>();
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack: " + deliveryTag + ",multiple:" + multiple);
                if (multiple) {
                    // 表示deliveryTag和它之前的消息已经ack
                    sortedSet.headSet(deliveryTag + 1).clear();
                } else {
                    // 表示deliveryTag已经ack
                    sortedSet.remove(deliveryTag);
                }
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("nack: " + deliveryTag + ",multiple:" + multiple);
                if (multiple) {
                    // 表示deliveryTag和它之前的消息nack
                    sortedSet.headSet(deliveryTag + 1).clear();
                } else {
                    // 表示deliveryTag nack
                    sortedSet.remove(deliveryTag);
                }
                //todo 需要进行重新发送ack失败的消息
            }
        });
        channel.confirmSelect();
        long seqNo = channel.getNextPublishSeqNo();
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "1".getBytes());
        sortedSet.add(seqNo);
        seqNo = channel.getNextPublishSeqNo();
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "2".getBytes());
        sortedSet.add(seqNo);
        seqNo = channel.getNextPublishSeqNo();
        channel.basicPublish("", confirmQueue, MessageProperties.PERSISTENT_TEXT_PLAIN, "3".getBytes());
        sortedSet.add(seqNo);
        //确保addConfirmListener能够执行
        Thread.sleep(1000);
    }
}
