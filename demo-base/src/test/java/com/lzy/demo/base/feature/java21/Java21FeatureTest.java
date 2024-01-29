package com.lzy.demo.base.feature.java21;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Java21FeatureTest {

    /**
     * 有序集合的操作
     */
    @Test
    public void testSequencedOperation() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        // first/last获取第一个/最后一个, 空列表会报错
        assertEquals(1, list.getFirst());
        assertEquals(4, list.getLast());

        // 有序的集合也有first/last
        LinkedHashSet<Integer> set = new LinkedHashSet<>(list);
        assertEquals(1, set.getFirst());
        assertEquals(4, list.getLast());

        // 有序的map也有first/last
        LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        assertEquals(1, map.firstEntry().getKey());
        assertEquals(2, map.lastEntry().getKey());
    }
}
