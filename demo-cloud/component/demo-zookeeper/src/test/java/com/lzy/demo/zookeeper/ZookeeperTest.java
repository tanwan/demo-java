package com.lzy.demo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ZookeeperTest {

    private ZooKeeper zk;

    /**
     * 连接
     *
     * @throws Exception the exception
     */
    @BeforeAll
    public void connect() throws Exception {
        String hostPort = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        CountDownLatch latch = new CountDownLatch(1);
        // ZooKeeper连接是异步的
        zk = new ZooKeeper(hostPort, 2000, event -> latch.countDown());
        latch.await();
    }

    /**
     * 测试连接
     *
     * @throws Exception the exception
     */
    @Test
    public void testConnect() throws Exception {
        System.out.println("connect");
    }


    /**
     * 测试创建节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testCreateNode() throws Exception {
        Stat stat = new Stat();
        // zk不允许递归创建节点,也就是创建/1/2时,一定要确保/1存在
        // 同步创建
        createNode("/createNode", "hello world", null);
        System.out.println(stat);
        // 异步创建
        zk.create("/createNodeCallBack", "hello world".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, String name) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("name:" + name);
            }
        }, null);
    }

    /**
     * 获取节点信息
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetNode() throws Exception {
        createNode("/getNode", "hello world", null);
        Stat stat = new Stat();
        byte[] bytes = zk.getData("/getNode", false, stat);
        System.out.println(new String(bytes));
        // 异步获取
        zk.getData("/getNode", event -> {
            // 这时候如果去修改/getNode下的数据,则会触发此事件
            System.out.println(event);
        }, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println(new String(data));
            }
        }, null);
        Thread.sleep(5000);
    }

    /**
     * 列出所有节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testListNode() throws Exception {
        List<String> nodes = zk.getChildren("/", event -> {
            // 这时候如果去修改/下的节点,则会触发此事件
            System.out.println(event);
        });
        nodes.forEach(System.out::println);
        Thread.sleep(5000);
    }

    /**
     * 更新节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testUpdateNode() throws Exception {
        Stat stat = new Stat();
        createNode("/setNode", "hello world", stat);
        // 这边是同步更新,也可以使用回调异步更新,这边的version如果传入-1的话,则表示使用最新的版本去更新,不需要原子性
        stat = zk.setData("/setNode", "hello world2".getBytes(), stat.getVersion());
        // 异步更新
        zk.setData("/setNode", "hello world3".getBytes(), -1, new AsyncCallback.StatCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, Stat stat) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("stat:" + stat);
            }
        }, null);
    }

    /**
     * 删除节点
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteNode() throws Exception {
        Stat stat = new Stat();
        createNode("/deleteNode", "hello world", stat);
        // 删除父节点,一定要先删除所有子节点
        // 这边是同步删除,也可以使用回调异步删除,这边的version如果传入-1的话,则表示使用最新的版本去更新,不需要原子性
        zk.delete("/deleteNode", stat.getVersion());
        createNode("/deleteNode", "hello world", stat);
        // 异步删除
        zk.delete("/deleteNode", stat.getVersion(), new AsyncCallback.VoidCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx) {
                System.out.println("rc:" + rc);
                System.out.println("path:" + path);
                System.out.println("stat:" + stat);
            }
        }, null);
    }


    private void createNode(String path, String data, Stat stat) throws Exception {
        try {
            zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, stat);
        } catch (Exception e) {
            // 不能重复创建node
            zk.getData(path, false, stat);
        }
    }
}
