package com.lzy.demo.base.collection.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 从数组中查找某个元素
 *
 * @author lzy
 * @version v1.0
 */
public class SearchTest {
    private Integer[] arrays = {1, 2, 3};

    /**
     * 转成List,然后进行查找
     */
    @Test
    public void testSearchList() {
        Assertions.assertTrue(Arrays.asList(arrays).contains(1));
    }


    /**
     * 转成Set,然后进行查找
     */
    @Test
    public void testSearchSet() {
        Assertions.assertTrue(new HashSet<>(Arrays.asList(arrays)).contains(1));
    }

    /**
     * 使用循环
     */
    @Test
    public void testSearchLoop() {
        Assertions.assertTrue(searchLoop(3));
    }

    /**
     * 二分查找
     */
    @Test
    public void testSearchBinarySearch() {
        //需要进行排序
        Arrays.sort(arrays);
        //返回值为目标元素在数组的下标
        Assertions.assertEquals(Arrays.binarySearch(arrays, 2), 1);
    }

    private boolean searchLoop(Integer except) {
        for (Integer i : arrays) {
            if (i.equals(except)) {
                return true;
            }
        }
        return false;
    }
}
