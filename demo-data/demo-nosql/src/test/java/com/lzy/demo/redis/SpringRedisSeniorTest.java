package com.lzy.demo.redis;

import com.lzy.demo.redis.subscribing.Receiver;
import com.lzy.demo.redis.subscribing.SubscribingConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * redis高级用法
 *
 * @author lzy
 * @version v1.0
 */
@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class, classes = {RedisAutoConfiguration.class, SubscribingConfig.class})
@ActiveProfiles("redis")
public class SpringRedisSeniorTest {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试pipeline
     */
    @Test
    public void testPipelineUseSessionCallback() {
        //pipeLine操作的返回值
        List<Object> list = stringRedisTemplate.executePipelined(new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations operations) throws DataAccessException {
                //这些操作将一次性发送给redis,减少网络的往返时间
                operations.opsForValue().set("pipeline1", "pipeline1");
                //如果这里出现异常,那么只会执行以上的命令,以下命令将不执行
                operations.opsForValue().set("pipeline2", "pipeline2");
                operations.opsForValue().get("pipeline1");
                operations.opsForValue().get("pipeline2");
                //这里只能返回null
                return null;
            }
        });
        System.out.println(list);
    }

    /**
     * 测试pipeline
     */
    @Test
    public void testPipelineUseRedisCallback() {
        //pipeLine操作的返回值
        List list = stringRedisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.stringCommands().set("pipeline1".getBytes(), "pipeline1".getBytes());
                connection.stringCommands().set("pipeline2".getBytes(), "pipeline2".getBytes());
                connection.stringCommands().get("pipeline1".getBytes());
                connection.stringCommands().get("pipeline1".getBytes());
                return null;
            }
        });
        System.out.println(list);
    }


    /**
     * 测试发布订阅
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSubscribing() throws InterruptedException {
        stringRedisTemplate.convertAndSend("chat.1", "hello");
        stringRedisTemplate.convertAndSend("chat2.1", "hello");
        stringRedisTemplate.convertAndSend("chat.2", "exit");
        Receiver.countDownLatch.await();
    }
}
