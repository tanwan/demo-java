/*
 * Created by LZY on 12/4/2016 21:37.
 */
package com.lzy.demo.base.functional.lamdba;

import com.lzy.demo.base.functional.lamdba.bean.MethodReference;
import org.junit.Test;

import java.util.Collections;
import java.util.function.Consumer;

/**
 * 方法引用
 *
 * @author LZY
 * @version v1.0
 */
public class MethodReferenceTest {
    private MethodReference methodReference = new MethodReference();

    /**
     * 测试实例方法引用
     */
    @Test
    public void testInstanceMethod() {
        invokeMethod(methodReference::instanceMethod);
    }

    /**
     * 测试构造函数引用
     */
    @Test
    public void testConstructor() {
        invokeMethod(MethodReference::new);
    }

    /**
     * 测试静态方法引用
     */
    @Test
    public void testStaticMethod() {
        invokeMethod(MethodReference::staticMethod);
    }

    /**
     * 测试lambda的第一个参数当成调用这个方法的实例
     */
    @Test
    public void testInstanceInvoke() {
        Collections.singleton(new MethodReference()).forEach(MethodReference::method);
    }

    private void invokeMethod(Consumer<String> action) {
        action.accept("hello");
    }

}
