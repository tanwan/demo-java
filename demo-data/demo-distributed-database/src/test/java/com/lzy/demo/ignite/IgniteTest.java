package com.lzy.demo.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class IgniteTest {
    private static final String CACHE_NAME = "demoCache";

    /**
     * 测试服务端
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testServer() throws InterruptedException {
        // 开启ignite节点
        Ignite ignite = Ignition.start(getIgniteConfiguration(false));

        IgniteCache<String, String> cache = ignite.getOrCreateCache(CACHE_NAME);
        cache.put("key", "value");

        Thread.sleep(1000000L);
        // Disconnect from the cluster.
        ignite.close();
    }

    /**
     * 测试客户端,要先等服务端启动
     */
    @Test
    public void testClient() {
        // 开启ignite节点
        Ignite ignite = Ignition.start(getIgniteConfiguration(true));

        IgniteCache<String, String> cache = ignite.cache(CACHE_NAME);
        Assertions.assertThat(cache.get("key")).isEqualTo("value");

        // Disconnect from the cluster.
        ignite.close();
    }


    /**
     * 测试瘦客户端,连接docker安装的ignite
     */
    @Test
    public void testThinClients() {
        //可以配置多个地址
        ClientConfiguration cfg = new ClientConfiguration().setAddresses("127.0.0.1:10800");
        IgniteClient client = Ignition.startClient(cfg);
        ClientCache<String, String> cache = client.getOrCreateCache(CACHE_NAME);
        if (!cache.containsKey("key")) {
            cache.put("key", "value");
        }
        Assertions.assertThat(cache.get("key")).isEqualTo("value");
    }


    private IgniteConfiguration getIgniteConfiguration(boolean client) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        // 服务端节点才能存储数据,客户端节点只能获取数据
        cfg.setClientMode(client);

        // PeerClassLoading是一种分布式的类加载,可以在集群中交换字节码
        cfg.setPeerClassLoadingEnabled(true);

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        //用来发现其它节点,这边使用静态ip,也可以使用组播
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        cfg.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));
        return cfg;
    }
}
