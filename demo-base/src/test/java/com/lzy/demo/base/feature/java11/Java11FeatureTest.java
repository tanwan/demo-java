package com.lzy.demo.base.feature.java11;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Java11FeatureTest {

    /**
     * 测试string
     */
    @Test
    public void testString() {
        String str = " \u2000a b ";
        System.out.println(str);
        //判断空
        Assertions.assertThat("".isEmpty()).isEqualTo(true);
        //判断空字符串
        Assertions.assertThat(" ".isBlank()).isEqualTo(true);
        //去头尾不可见空格
        Assertions.assertThat(str.strip()).isEqualTo("a b");
        //去头尾空格
        Assertions.assertThat(str.trim()).isEqualTo("\u2000a b");
        //去头不可见空格
        Assertions.assertThat(str.stripLeading()).isEqualTo("a b ");
        //去尾不可见空格
        Assertions.assertThat(str.stripTrailing()).isEqualTo(" \u2000a b");
        //可以获取字符串的行
        Assertions.assertThat("a\nb\nc".lines().count()).isEqualTo(3);
        //重复
        Assertions.assertThat("a".repeat(3)).isEqualTo("aaa");
    }
}
