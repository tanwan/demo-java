package com.lzy.demo.io.nio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    private static final String FILE_PATH = "out/test/resources/tempFile.tmp";


    /**
     * Test input.
     */
    @Test
    public void testInput() {
        try (FileInputStream is = new FileInputStream(FILE_PATH)) {
            FileChannel fileChannel = is.getChannel();
            //分配buffer的大小
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            //从channel读到buffer
            while ((fileChannel.read(byteBuffer) != -1)) {
                //从buffer读出数据
                byteBuffer.flip();
                //这里并没有修改position的值,position=0,因此不用调用clear或compact
                System.out.println(new String(byteBuffer.array(), 0, byteBuffer.remaining()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test output.
     */
    @Test
    public void testOutput() {
        try (FileOutputStream out = new FileOutputStream(FILE_PATH)) {
            FileChannel fileChannel = out.getChannel();
            //分配buffer的大小,position为0
            ByteBuffer byteBuffer = ByteBuffer.wrap("hello world".getBytes());
            fileChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
