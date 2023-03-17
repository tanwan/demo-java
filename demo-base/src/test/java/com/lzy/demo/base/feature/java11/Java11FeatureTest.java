package com.lzy.demo.base.feature.java11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Java11FeatureTest {

    /**
     * 测试string
     */
    @Test
    public void testString() {
        String str = " \u2000a b ";
        System.out.println(str);
        //判断空
        assertTrue("".isEmpty());
        //判断空字符串
        assertTrue(" ".isBlank());
        //去头尾不可见空格
        assertEquals("a b", str.strip());
        //去头尾空格
        assertEquals("\u2000a b", str.trim());
        //去头不可见空格
        assertEquals("a b ", str.stripLeading());
        //去尾不可见空格
        assertEquals(" \u2000a b", str.stripTrailing());
        //可以获取字符串的行
        assertEquals(3, "a\nb\nc".lines().count());
        //重复
        assertEquals("aaa", "a".repeat(3));
    }
}
