package com.lzy.demo.groovy.base

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * 基本用法
 *
 * @author lzy
 * @version v1.0
 */
class BaseTest {


    /**
     * equals和判断同实例
     */
    @Test
    void testEqualsAndSame() {
        def a = new NoConstructorClass()
        def b = new NoConstructorClass()
        // ==相当于java的equals
        Assertions.assertTrue(a == b)
        Assertions.assertFalse(a != b)
        Assertions.assertEquals(a, b)
        // ===相当于java的==
        Assertions.assertTrue(a !== b)
        Assertions.assertFalse(a === b)
        Assertions.assertNotSame(a, b)
    }

    /**
     * 安全占位符
     */
    @Test
    void testSafeNavigation() {
        List list = null
        // ?用来避免空指针异常,当？前面的对象为null,则直接返回null
        println("list:${list ?[3]},size:${list?.size()}")
    }

    /**
     * 测试CompareTo
     */
    @Test
    void testCompareTo() {
        def a = 1
        def b = 2
        def c = 1
        // <==> 相当于compareTo
        assert a <=> c == 0
        assert a <=> b == -1
        assert b <=> c == 1
    }

}
