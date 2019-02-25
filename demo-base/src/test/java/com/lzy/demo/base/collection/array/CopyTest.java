/*
 * Created by lzy on 2019-02-19 14:11.
 */
package com.lzy.demo.base.collection.array;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 数组复制
 *
 * @author lzy
 * @version v1.0
 */
public class CopyTest {

    private int[] src = {1, 2, 3};

    private Sample[] srcSample;

    /**
     * Init.
     */
    @BeforeEach
    public void init() {
        srcSample = new Sample[3];
        srcSample[0] = new Sample(1);
        srcSample[1] = new Sample(2);
        srcSample[2] = new Sample(3);
    }

    /**
     * 使用System的arraycopy进行复制
     */
    @Test
    public void testSystemCopy() {
        int[] dest = new int[src.length];
        System.arraycopy(src, 0, dest, 0, 3);
        Assertions.assertNotSame(src, dest);
        Assertions.assertArrayEquals(src, dest);
    }

    /**
     * 使用System的arraycopy进行复制,浅复制
     */
    @Test
    public void testSystemCopyShadowCopy() {
        Sample[] dest = new Sample[srcSample.length];
        System.arraycopy(srcSample, 0, dest, 0, 3);
        Assertions.assertArrayEquals(srcSample, dest);
        Assertions.assertNotSame(srcSample, dest);
        Assertions.assertSame(srcSample[0], dest[0]);
    }


    /**
     * 使用Arrays的copyOf进行复制
     */
    @Test
    public void testArraysCopyOf() {
        int[] dest = Arrays.copyOf(src, 3);
        int[] dest2 = Arrays.copyOfRange(src, 0, 3);
        Assertions.assertArrayEquals(src, dest);
        Assertions.assertArrayEquals(src, dest2);
    }

    /**
     * 使用Arrays的copyOf进行复制,浅复制
     */
    @Test
    public void testArraysCopyOfShadowCopy() {
        Sample[] dest = Arrays.copyOf(srcSample, 3);
        Assertions.assertArrayEquals(srcSample, dest);
        Assertions.assertNotSame(srcSample, dest);
        Assertions.assertSame(srcSample[0], dest[0]);
    }

    /**
     * 使用Stream的toArray进行复制,浅复制
     */
    @Test
    public void testStreamToArray() {
        Sample[] dest = Arrays.stream(srcSample).toArray(Sample[]::new);
        Assertions.assertArrayEquals(srcSample, dest);
    }

    /**
     * 使用SerializationUtils进行复制,深复制
     */
    @Test
    public void testDeepCopy() {
        Sample[] dest = SerializationUtils.clone(srcSample);
        Assertions.assertArrayEquals(srcSample, dest);
        Assertions.assertNotSame(srcSample[0], dest[0]);
    }


    /**
     * sample
     */
    @Data
    @AllArgsConstructor
    private static class Sample implements Serializable {
        private int value;
    }
}
