package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.singleton.EagerSingleton;
import com.lzy.demo.design.pattern.singleton.EnumSingleton;
import com.lzy.demo.design.pattern.singleton.InnerClassSingleton;
import com.lzy.demo.design.pattern.singleton.LazySingleton;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 测试单例
 *
 * @author LZY
 * @version v1.0
 */
public class SingletonTest {

    /**
     * 测试饿汉式
     */
    @Test
    public void testEagerSingleton() {
        assertThat(EagerSingleton.getInstance())
                .isEqualTo(EagerSingleton.getInstance());
    }

    /**
     * 测试懒汉式
     */
    @Test
    public void testLazySingleton() {
        assertThat(LazySingleton.getInstance())
                .isEqualTo(LazySingleton.getInstance());
    }

    /**
     * 测试内部类
     */
    @Test
    public void innerClassSingleton() {
        assertThat(InnerClassSingleton.getInstance())
                .isEqualTo(InnerClassSingleton.getInstance());
    }

    /**
     * 测试枚举
     */
    @Test
    public void innerEnumSingleton() {
        assertThat(EnumSingleton.INSTANCE)
                .isEqualTo(EnumSingleton.INSTANCE);
    }
}
