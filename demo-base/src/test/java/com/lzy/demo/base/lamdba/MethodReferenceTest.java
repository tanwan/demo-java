package com.lzy.demo.base.lamdba;

import com.lzy.demo.base.lamdba.bean.MethodReference;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.function.Consumer;

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
