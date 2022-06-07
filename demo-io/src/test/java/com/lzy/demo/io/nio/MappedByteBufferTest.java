package com.lzy.demo.io.nio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {

    private static final String FILE_PATH = "out/test/resources/tempFile.tmp";

    /**
     * Test input.
     */
    @Test
    public void testInput() {
        try (FileInputStream is = new FileInputStream(FILE_PATH)) {
            FileChannel fileChannel = is.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            while (mappedByteBuffer.hasRemaining()) {
                System.out.print((char) mappedByteBuffer.get());
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
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(FILE_PATH, "rw")) {
            FileChannel fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, "hello world".length());
            mappedByteBuffer.put("hello world".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
