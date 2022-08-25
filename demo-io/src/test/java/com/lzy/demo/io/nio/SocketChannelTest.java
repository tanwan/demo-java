package com.lzy.demo.io.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SocketChannelTest {

    @Test
    public void testSever() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        // 注册accept事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("server start");
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        while (true) {
            //阻塞到至少有一个channel在Selector上注册的事件就绪了
            selector.select();
            //返回就绪的SelectionKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    //相当于SocketChannel client = ((ServerSocketChannel) selectionKey.channel()).accept();
                    SocketChannel client = serverSocketChannel.accept();
                    System.out.println("Accept connection from: " + client);
                    client.configureBlocking(false);
                    //连接进来的client注册到selector,注册读事件
                    client.register(selector, SelectionKey.OP_READ);
                    // 这个线程就用来模拟服务端保存了客户端的channel,然后主动向客户端发送信息
                    new Thread(() -> {
                        sleep(3000);
                        for (int i = 0; i < 5; i++) {
                            try {
                                client.write(ByteBuffer.wrap(("server send " + i).getBytes()));
                                //这边如果发送得太快的话,可以出现粘包效果
                                sleep(1000);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else if (selectionKey.isReadable()) {
                    //client端调用Channel#close()也会进入这个方法,只不过SocketChannel#read()的返回值是-1
                    //因此在这里,如果返回值为-1时,就需要关闭SocketChannel
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    byteBuffer.clear();
                    int read = socketChannel.read(byteBuffer);
                    if (read == -1) {
                        //或者调用selectionKey.cancel()
                        socketChannel.close();
                        System.out.println("close channel");
                    } else {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, byteBuffer.remaining()));
                        byteBuffer.clear();
                        byteBuffer.put("server reply".getBytes());
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                    }
                }
                iterator.remove();
            }
        }
    }

    /**
     * 阻塞模式客户端
     *
     * @throws Exception the exception
     */
    @Test
    public void testBlockingClient() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        //阻塞模式下返回true表示连接已建立
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        for (int i = 0; i < 5; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            byteBuffer.put("hello world".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, byteBuffer.remaining()));
            byteBuffer.clear();
            Thread.sleep(1000);
        }
        socketChannel.close();
    }

    /**
     * 非阻塞模式客户端
     *
     * @throws Exception the exception
     */
    @Test
    public void testNonBlockingClient() throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        // 非阻塞
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        //非阻塞模式下返回false,不论连接是否成功建立
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isConnectable()) {
                    //这里需要使用finishConnect来判断连接是否成功建立
                    if (socketChannel.finishConnect()) {
                        System.out.println("connect");
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        byteBuffer.put("hello world".getBytes());
                        byteBuffer.flip();
                        socketChannel.write(byteBuffer);
                        byteBuffer.clear();
                        // 这个线程就用来模拟客户端保存了服务端的channel,然后主动向服务端发送信息
                        new Thread(() -> {
                            sleep(1000);
                            for (int i = 0; i < 5; i++) {
                                try {
                                    socketChannel.write(ByteBuffer.wrap(("client send " + i).getBytes()));
                                    //这边如果发送得太快的话,可以出现粘包效果
                                    sleep(500);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                } else if (selectionKey.isReadable()) {
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new String(byteBuffer.array(), 0, byteBuffer.remaining()));
                    byteBuffer.clear();
                }
                iterator.remove();
            }
        }
    }


    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
