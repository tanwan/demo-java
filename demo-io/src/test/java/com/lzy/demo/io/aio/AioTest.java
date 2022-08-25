package com.lzy.demo.io.aio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AioTest {

    @Test
    public void testServer() throws Exception {
        AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(9999));
        //注册事件和事件完成后的处理器
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {
                server.accept(null, this);
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                socketChannel.read(byteBuffer, byteBuffer, new ReadHandler(socketChannel));
                // 这个线程就用来模拟服务端保存了客户端的channel,然后主动向客户端发送信息
                new Thread(() -> {
                    for (int i = 0; i < 5; i++) {
                        socketChannel.write(ByteBuffer.wrap(("server send " + i).getBytes()));
                        sleep(1000);
                    }
                }).start();
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        Thread.sleep(1000000);
    }

    /**
     * 测试客户端
     *
     * @throws Exception the exception
     */
    @Test
    public void testClient() throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 连接事件
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                socketChannel.read(buffer, buffer, new ReadHandler(socketChannel));
                // 这个线程就用来模拟客户端保存了服务端的channel,然后主动向服务端发送信息
                new Thread(() -> {
                    for (int i = 0; i < 5; i++) {
                        socketChannel.write(ByteBuffer.wrap(("client send " + i).getBytes()));
                        sleep(1000);
                    }
                }).start();
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });
        Thread.sleep(100000);
    }


    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {

        private AsynchronousSocketChannel socketChannel;

        ReadHandler(AsynchronousSocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer attachment) {
            attachment.flip();
            System.out.println(new String(attachment.array(), 0, attachment.remaining()));
            attachment.clear();
            // 读取到-1时,需要将channel关闭
            if (result == -1) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // 每读完一次,都需要继续读
                socketChannel.read(attachment, attachment, this);
            }
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
            exc.printStackTrace();
        }
    }
}
