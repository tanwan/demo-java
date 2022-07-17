package com.lzy.demo.io.bio;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharacterStreamTest {
    private static final String FILE_PATH = "out/test/resources/tempFile.tmp";

    /**
     * 测试Read
     */
    @Test
    public void testRead() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            char[] chars = new char[8];
            int len;
            int count = 0;
            while ((len = reader.read(chars)) != -1) {
                //一个中文字符也算一个char
                System.out.println("char:" + new String(chars, 0, len));
                count += len;
            }
            System.out.println("count: " + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试Write.
     */
    @Test
    public void testWrite() {
        File file = new File(FILE_PATH);
        try (FileWriter writer = new FileWriter(file)) {
            String content = "我";
            char[] chars = content.toCharArray();
            writer.write(chars, 0, chars.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
