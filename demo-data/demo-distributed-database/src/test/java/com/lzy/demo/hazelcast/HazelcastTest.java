package com.lzy.demo.hazelcast;

import com.hazelcast.config.ClasspathYamlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HazelcastTest {


    /**
     * 测试hazelcast
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testHazelcast() throws InterruptedException {
        // 创建一个hazelcastInstance实例,调用多次就是多个实例
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        // 创建集群Map
        Map<Integer, String> clusterMap = instance.getMap("MyMap");
        clusterMap.put(1, "Hello hazelcast map!");

        // 创建集群Queue
        Queue<String> clusterQueue = instance.getQueue("MyQueue");
        clusterQueue.offer("Hello hazelcast!");
        clusterQueue.offer("Hello hazelcast queue!");
        Thread.sleep(30000L);
    }

    /**
     * 测试使用YAML配置
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testUseCustomYAMLConfig() throws InterruptedException {
        Config config = new ClasspathYamlConfig("custom-hazelcast.yml");
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        Map<Integer, String> demoMap = instance.getMap("demoMap");
        demoMap.put(1, "hello world");
        assertEquals("hello world", demoMap.get(1));
        Thread.sleep(3100);
        assertNull(demoMap.get(1));
    }

    /**
     * 测试使用java配置
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testUseCodeConfig() throws InterruptedException {
        // 创建默认config对象
        Config config = new Config();
        NetworkConfig netConfig = config.getNetworkConfig();
        netConfig.setPort(5701);
        //可以使用通配符
        MapConfig mapConfig = config.getMapConfig("map.*");
        mapConfig.setTimeToLiveSeconds(3);
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
        Map<Integer, String> demoMap = instance.getMap("map.demoMap");
        String value = "hello world";
        demoMap.put(1, value);
        assertEquals(value, demoMap.get(1));
        Thread.sleep(3100);
        assertNull(demoMap.get(1));
    }

    /**
     * 动态添加数据结构配置
     */
    @Test
    public void testDynamicAddDataStructure() throws InterruptedException {
        HazelcastInstance instance = Hazelcast.newHazelcastInstance();
        Map<Integer, String> defaultMap = instance.getMap("defaultMap");
        //动态添加前先获取出map,并且插入过数据
        defaultMap.put(0, "0");
        defaultMap.remove(0);

        //获取config
        Config config = instance.getConfig();
        //动态添加
        MapConfig defaultConfig = new MapConfig("defaultMap");
        defaultConfig.setTimeToLiveSeconds(3);
        config.addMapConfig(defaultConfig);
        MapConfig dynamicConfig = new MapConfig("dynamicMap");
        dynamicConfig.setTimeToLiveSeconds(3);
        config.addMapConfig(dynamicConfig);

        //动态添加后再获取出map
        Map<Integer, String> dynamicMap = instance.getMap("dynamicMap");

        String value = "hello world";
        defaultMap.put(1, value);
        dynamicMap.put(1, value);

        Thread.sleep(3100);
        //动态添加前先获取出map,并且插入过数据,动态添加是不生效的
        assertEquals(value, defaultMap.get(1));
        //动态添加后再获取出map,动态添加生效
        assertNull(dynamicMap.get(1));
    }
}
