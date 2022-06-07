package com.lzy.demo.io.bio;

import org.junit.jupiter.api.Test;

import java.io.*;

public class ByteStreamTest {
    private static final String FILE_PATH = "out/test/resources/tempFile.tmp";

    /**
     * 输入流
     */
    @Test
    public void testInput() {
        try (InputStream is = new FileInputStream(FILE_PATH)) {
            byte[] bytes = new byte[8];
            int len;
            int count = 0;
            while ((len = is.read(bytes)) != -1) {
                //如果最后一次循环不能读满整个bytes,那么只会覆盖读取的内容
                //比如bytes长度为8,最后一次读取了4个字节,那么bytes的后4个字节还是上一次读取的内容
                System.out.println("byte: " + new String(bytes, 0, len));
                count += len;
            }
            System.out.println("count:" + count);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出流
     *
     * @throws IOException the io exception
     */
    @Test
    public void testOutput() throws IOException {
        File file = new File(FILE_PATH);
        try (OutputStream os = new FileOutputStream(file, Boolean.TRUE)) {
            String content = "我";
            // bytes 中为 -26 -120 -111
            byte[] bytes = content.getBytes();
            //将数组中偏移量为off,长度为len的写入OutputStream
            os.write(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
