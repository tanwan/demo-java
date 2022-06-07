package com.lzy.demo.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

/**
 * 测试Curator
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuratorTest {

    private CuratorFramework client;
    private int i = 0;


    /**
     * 连接
     */
    @BeforeAll
    public void connect() {
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183", 5000, 3000, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    /**
     * 测试连接
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
    }

    /**
     * 测试创建节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testCreateNode() throws Exception {
        createNode("/createCuratorNode", "hello world", null);
    }

    /**
     * 获取节点信息
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetNode() throws Exception {
        createNode("/getCuratorNode", "hello world", null);
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/getCuratorNode");
        System.out.println(new String(bytes));
    }

    /**
     * 列出所有节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testListNode() throws Exception {
        client.getChildren().forPath("/").forEach(System.out::println);
    }

    /**
     * 更新节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateNode() throws Exception {
        Stat stat = new Stat();
        createNode("/updateCuratorNode", "hello world", stat);
        client.setData().withVersion(stat.getVersion()).forPath("/updateCuratorNode", "hello world2".getBytes());
    }

    /**
     * 测试删除节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteNode() throws Exception {
        Stat stat = new Stat();
        createNode("/deleteCuratorNode", "hello world", stat);
        client.delete().withVersion(stat.getVersion()).forPath("/deleteCuratorNode");
    }


    /**
     * 测试分布式锁
     *
     * @throws Exception the exception
     */
    @Test
    public void testDistributedLock() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, "/distributedLock");
        for (int j = 0; j < 100; j++) {
            new Thread(() -> {
                boolean acquireLock = false;
                try {
                    // 这边等待时间一定要够,不然会获取不到锁
                    acquireLock = lock.acquire(10, TimeUnit.SECONDS);
                    System.out.println(acquireLock);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (acquireLock) {
                    try {
                        i++;
                    } finally {
                        try {
                            lock.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(i);
    }

    private void createNode(String path, String data, Stat stat) throws Exception {
        try {
            //使用 client.create().creatingParentsIfNeeded()可以递归创建父节点
            client.create().storingStatIn(stat).withMode(CreateMode.PERSISTENT).forPath(path, data.getBytes());
        } catch (Exception e) {
            // 不能重复创建node
            client.getData().storingStatIn(stat).forPath(path);
        }
    }
}
