package com.lzy.demo.base.collection.array;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CopyTest {

    private int[] src = {1, 2, 3};

    private Simple[] srcSimple;

    @BeforeEach
    public void init() {
        srcSimple = new Simple[3];
        srcSimple[0] = new Simple(1);
        srcSimple[1] = new Simple(2);
        srcSimple[2] = new Simple(3);
    }

    /**
     * 使用System的arraycopy进行复制
     */
    @Test
    public void testSystemCopy() {
        int[] dest = new int[src.length];
        System.arraycopy(src, 0, dest, 0, 3);
        assertNotSame(src, dest);
        assertArrayEquals(src, dest);
    }

    /**
     * 使用System的arraycopy进行复制,浅复制
     */
    @Test
    public void testSystemCopyShadowCopy() {
        Simple[] dest = new Simple[srcSimple.length];
        System.arraycopy(srcSimple, 0, dest, 0, 3);
        assertArrayEquals(srcSimple, dest);
        assertNotSame(srcSimple, dest);
        assertSame(srcSimple[0], dest[0]);
    }


    /**
     * 使用Arrays的copyOf进行复制
     */
    @Test
    public void testArraysCopyOf() {
        int[] dest = Arrays.copyOf(src, 3);
        int[] dest2 = Arrays.copyOfRange(src, 0, 3);
        assertArrayEquals(src, dest);
        assertArrayEquals(src, dest2);
    }

    /**
     * 使用Arrays的copyOf进行复制,浅复制
     */
    @Test
    public void testArraysCopyOfShadowCopy() {
        Simple[] dest = Arrays.copyOf(srcSimple, 3);
        assertArrayEquals(srcSimple, dest);
        assertNotSame(srcSimple, dest);
        assertSame(srcSimple[0], dest[0]);
    }

    /**
     * 使用Stream的toArray进行复制,浅复制
     */
    @Test
    public void testStreamToArray() {
        Simple[] dest = Arrays.stream(srcSimple).toArray(Simple[]::new);
        assertArrayEquals(srcSimple, dest);
    }

    /**
     * 使用SerializationUtils进行复制,深复制
     */
    @Test
    public void testDeepCopy() {
        Simple[] dest = SerializationUtils.clone(srcSimple);
        assertArrayEquals(srcSimple, dest);
        assertNotSame(srcSimple[0], dest[0]);
    }

    @Data
    @AllArgsConstructor
    private static class Simple implements Serializable {
        private int value;
    }
}
