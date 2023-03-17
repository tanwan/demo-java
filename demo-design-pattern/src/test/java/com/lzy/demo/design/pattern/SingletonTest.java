package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.singleton.EagerSingleton;
import com.lzy.demo.design.pattern.singleton.EnumSingleton;
import com.lzy.demo.design.pattern.singleton.InnerClassSingleton;
import com.lzy.demo.design.pattern.singleton.LazySingleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

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
        assertSame(EagerSingleton.getInstance(), EagerSingleton.getInstance());
    }

    /**
     * 测试懒汉式
     */
    @Test
    public void testLazySingleton() {
        assertSame(LazySingleton.getInstance(), LazySingleton.getInstance());
    }

    /**
     * 测试内部类
     */
    @Test
    public void innerClassSingleton() {
        assertSame(InnerClassSingleton.getInstance(), InnerClassSingleton.getInstance());
    }

    /**
     * 测试枚举
     */
    @Test
    public void innerEnumSingleton() {
        assertSame(EnumSingleton.INSTANCE, EnumSingleton.INSTANCE);
    }
}
