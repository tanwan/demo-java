package com.lzy.demo.service;

import com.lzy.demo.service.thrift.ThriftMessage;
import com.lzy.demo.service.thrift.ThriftService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ThriftClientTest {

    /**
     * 测试thrift客户端
     *
     * @throws Exception the exception
     */
    @Test
    public void testThriftClient() throws Exception {
        TTransport transport;
        // 创建TTransport
        transport = new TSocket("localhost", 28010);

        // 创建TProtocol 协议要与服务端一致
        TProtocol protocol = new TBinaryProtocol(transport);

        // 创建client
        ThriftService.Client client = new ThriftService.Client(protocol);

        transport.open();  // 建立连接

        ThriftMessage innerMessage = new ThriftMessage()
                .setStr("hello world");
        Set<ThriftMessage> set = new HashSet<>();
        Map<String, ThriftMessage> map = new HashMap<>();
        set.add(innerMessage);
        map.put("innerMessage", innerMessage);

        ThriftMessage thriftMessage = new ThriftMessage()
                .setDou(0.23).setInteger(23)
                .setStr("hello world")
                .setStringList(Collections.singletonList("hello world"))
                .setThriftMessageSet(set)
                .setThriftMessageMap(map);
        // client调用server端方法
        System.out.println(client.thriftService(thriftMessage));
        // 请求结束，断开连接
        transport.close();
    }
}
