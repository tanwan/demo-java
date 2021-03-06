/*
 * Created by lzy on 2019/9/25 3:11 PM.
 */
package com.lzy.demo.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.ExecutionException;

/**
 * lettuce测试
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LettuceTest {


    private static final String PREFIX = "lettuce:";

    private RedisClient redisClient;

    private StatefulRedisConnection<String, String> connection;

    private RedisCommands<String, String> syncCommands;

    private RedisAsyncCommands<String, String> asyncCommands;

    /**
     * Init.
     */
    @BeforeAll
    public void init() {
        // url的格式为 redis://password@hostname:port/database
        redisClient = RedisClient.create("redis://localhost:6379/0");
        connection = redisClient.connect();
        syncCommands = connection.sync();
        asyncCommands = connection.async();
    }

    /**
     * 测试set
     *
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSet() throws ExecutionException, InterruptedException {
        syncCommands.set(getKey("key"), "hello world");
        Assertions.assertThat(syncCommands.get(getKey("key")))
                .isEqualTo("hello world");
        // 异步调用,返回RedisFuture
        asyncCommands.set(getKey("key1"), "hello world1").get();
        Assertions.assertThat(syncCommands.get(getKey("key1")))
                .isEqualTo("hello world1");
    }

    /**
     * 测试bit
     */
    @Test
    public void testBit() {
        //把第8位设为1
        syncCommands.setbit(getKey("bit"), 8, 1);
        Assertions.assertThat(syncCommands.getbit(getKey("bit"), 8))
                .isEqualTo(1);
        //被设置成1的个数
        Assertions.assertThat(syncCommands.bitcount(getKey("bit"))).isEqualTo(1);

        //把第8位设为0
        syncCommands.setbit(getKey("bit"), 8, 0);
        Assertions.assertThat(syncCommands.getbit(getKey("bit"), 8))
                .isEqualTo(0);
        //被设置成1的个数
        Assertions.assertThat(syncCommands.bitcount(getKey("bit"))).isEqualTo(0);

    }


    /**
     * Close.
     */
    @AfterAll
    public void close() {
        connection.close();
        redisClient.shutdown();
    }

    private String getKey(String key) {
        return PREFIX + key;
    }
}
