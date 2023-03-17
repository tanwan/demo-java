package com.lzy.demo.redis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 使用jedis
 * See http://doc.redisfans.com/
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JedisTest {

    private static final String PREFIX = "jedis:";

    private Jedis jedis;

    @BeforeAll
    public void init() {
        jedis = new Jedis("127.0.0.1", 6379);
        //设置密码
        //jedis.auth("123456");
        jedis.ping();
    }

    /**
     * 测试set
     */
    @Test
    public void testSet() {
        jedis.set(getKey("key"), "hello world");
        //nx:不存在才插入,xx:存在才插入
        //ex:秒,px:毫秒
        SetParams setParams = SetParams.setParams()
                .nx().ex(30);
        jedis.set(getKey("key1"), "hello world1", setParams);
        assertEquals("hello world", jedis.get(getKey("key")));
        assertEquals("hello world1", jedis.get(getKey("key1")));
    }

    /**
     * 测试bit
     */
    @Test
    public void testBit() {
        //把第8位设为true(1)
        jedis.setbit(getKey("bit"), 8, true);
        assertTrue(jedis.getbit(getKey("bit"), 8));
        //被设置成1的个数
        assertEquals(1, jedis.bitcount(getKey("bit")));

        //把第8位设为false(0)
        jedis.setbit(getKey("bit"), 8, false);
        assertFalse(jedis.getbit(getKey("bit"), 8));
        //被设置成1的个数
        assertEquals(0, jedis.bitcount(getKey("bit")));
    }

    @AfterAll
    public void close() {
        jedis.close();
    }


    private String getKey(String key) {
        return PREFIX + key;
    }
}
