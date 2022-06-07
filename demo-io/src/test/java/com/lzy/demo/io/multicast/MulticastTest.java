package com.lzy.demo.io.multicast;

import com.lzy.demo.io.utils.LocalAddressUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastTest {
    /**
     * 组播ip
     */
    private static final String MULTICAST_IP = "224.2.2.3";

    /**
     * 端口
     */
    private static final int MULTICAST_PORT = 4446;

    /**
     * 测试接收
     *
     * @throws IOException the io exception
     */
    @Test
    public void testMulticastReceive() throws IOException {
        MulticastSocket socket = new MulticastSocket(MULTICAST_PORT);
        socket.setInterface(InetAddress.getByName(LocalAddressUtils.getLocalAddress(false)));
        InetAddress group = InetAddress.getByName(MULTICAST_IP);
        socket.joinGroup(group);
        while (true) {
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            String received = new String(
                    packet.getData(), 0, packet.getLength());
            System.out.println("receive:" + received);
            if ("end".equals(received)) {
                break;
            }
        }
        socket.leaveGroup(group);
        socket.close();
    }

    /**
     * 测试发送
     *
     * @throws Exception the exception
     */
    @Test
    public void testMulticastSend() throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = InetAddress.getByName(MULTICAST_IP);

        byte[] message = "hello world".getBytes();
        byte[] end = "end".getBytes();
        socket.send(new DatagramPacket(message, message.length, group, MULTICAST_PORT));
        socket.send(new DatagramPacket(end, end.length, group, MULTICAST_PORT));
        socket.close();
    }
}
