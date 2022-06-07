package com.lzy.demo.io.copy;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Instant;

/**
 * 测试文件复制的效率
 *
 * @author LZY
 * @version v1.0
 */
public class CopyTest {
    private static final String BASE_URL = System.getProperty("user.home") + "/Desktop/";
    private static final String SOURCE_FILE_NAME = "1.avi";
    private static final String SOURCE_FILE_URL = BASE_URL + SOURCE_FILE_NAME;
    private static final String DEST_FILE_URL = BASE_URL + "%s-" + SOURCE_FILE_NAME;
    private static final int BUFFER_SIZE = 1024;

    private long startTime;

    @BeforeEach
    public void init() {
        startTime = Instant.now().toEpochMilli();
    }

    @AfterEach
    public void after() {
        System.out.println("spend time:" + (Instant.now().toEpochMilli() - startTime));
    }

    /**
     * 使用BufferedStream复制文件
     */
    @Test
    public void testBufferedStream() {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(SOURCE_FILE_URL));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(String.format(DEST_FILE_URL, "BufferedStream")))) {
            byte[] bytes = new byte[BUFFER_SIZE];
            int size;
            while ((size = bufferedInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用FileStream复制文件
     */
    @Test
    public void testFileStream() {
        try (InputStream inputStream = new FileInputStream(SOURCE_FILE_URL);
             OutputStream outputStream = new FileOutputStream(String.format(DEST_FILE_URL, "FileStream"))) {
            byte[] bytes = new byte[BUFFER_SIZE];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用Nio的ByteBuffer复制文件
     */
    @Test
    public void testNioByteBuffer() {
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE_FILE_URL);
             FileOutputStream fileOutputStream = new FileOutputStream(String.format(DEST_FILE_URL, "NioByteBuffer"));
             FileChannel inputFileChannel = fileInputStream.getChannel();
             FileChannel outputFileChannel = fileOutputStream.getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (inputFileChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                outputFileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用nio的transfer复制文件
     */
    @Test
    public void testNioTransfer() {
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE_FILE_URL);
             FileOutputStream fileOutputStream = new FileOutputStream(String.format(DEST_FILE_URL, "NioTransfer"));
             FileChannel inputFileChannel = fileInputStream.getChannel();
             FileChannel outputFileChannel = fileOutputStream.getChannel()) {
            inputFileChannel.transferTo(0, inputFileChannel.size(), outputFileChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用nio的MappedByteBuffer复制文件
     */
    @Test
    public void testNioMappedByteBuffer() {
        try (FileInputStream fileInputStream = new FileInputStream(SOURCE_FILE_URL);
             FileOutputStream fileOutputStream = new FileOutputStream(String.format(DEST_FILE_URL, "NioMappedByteBuffer"));
             FileChannel inputFileChannel = fileInputStream.getChannel();
             FileChannel outputFileChannel = fileOutputStream.getChannel()) {
            MappedByteBuffer mappedByteBuffer = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputFileChannel.size());
            outputFileChannel.write(mappedByteBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
