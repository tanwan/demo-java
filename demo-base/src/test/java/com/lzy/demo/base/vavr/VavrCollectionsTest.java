package com.lzy.demo.base.vavr;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * vavr集合
 *
 * @author lzy
 * @version v1.0
 */
public class VavrCollectionsTest {

    /**
     * 测试listOf
     */
    @Test
    public void testListOf() {
        List<Integer> intList = List.of(1, 2);
        // append后会创建新的list
        intList = intList.append(3);
        assertEquals(3, intList.length());
        assertEquals(1, intList.get(0));
        assertEquals(2, intList.get(1));
        assertEquals(3, intList.get(2));
    }
}
