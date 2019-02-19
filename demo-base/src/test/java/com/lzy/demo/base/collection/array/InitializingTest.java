/*
 * Created by lzy on 2019-02-19 16:05.
 */
package com.lzy.demo.base.collection.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 对数组进行初始化
 *
 * @author lzy
 * @version v1.0
 */
public class InitializingTest {
    private int[] array = new int[3];

    /**
     * 使用循环对每个元素赋值
     */
    @Test
    public void testUseLoop() {
        for (int i = 0; i < 3; i++) {
            array[i] = i + 1;
        }
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, array);
    }

    /**
     * 声明的时候就进行赋值
     */
    @Test
    public void testUseInit() {
        int[] array = {1, 2, 3};
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, array);
    }

    /**
     * 使用Arrays的fill进行赋值
     */
    @Test
    public void testArraysFill() {
        Arrays.fill(array, 1);
        Assertions.assertArrayEquals(new int[]{1, 1, 1}, array);
    }

    /**
     * 使用Arrays的fill进行赋值
     */
    @Test
    public void testArraysSetAll() {
        Arrays.setAll(array, i -> i + 1);
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, array);
    }

}
