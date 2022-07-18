package com.lzy.demo.base.feature.java11;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Java11FeatureTest {

    /**
     * 测试string
     */
    @Test
    public void testString() {
        String str = " \u2000a b ";
        System.out.println(str);
        //判断空
        assertThat("".isEmpty()).isEqualTo(true);
        //判断空字符串
        assertThat(" ".isBlank()).isEqualTo(true);
        //去头尾不可见空格
        assertThat(str.strip()).isEqualTo("a b");
        //去头尾空格
        assertThat(str.trim()).isEqualTo("\u2000a b");
        //去头不可见空格
        assertThat(str.stripLeading()).isEqualTo("a b ");
        //去尾不可见空格
        assertThat(str.stripTrailing()).isEqualTo(" \u2000a b");
        //可以获取字符串的行
        assertThat("a\nb\nc".lines().count()).isEqualTo(3);
        //重复
        assertThat("a".repeat(3)).isEqualTo("aaa");
    }
}
