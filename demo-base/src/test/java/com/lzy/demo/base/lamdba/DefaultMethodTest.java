package com.lzy.demo.base.lamdba;

import com.lzy.demo.base.lamdba.bean.DefaultMethodInterface;
import org.junit.jupiter.api.Test;

public class DefaultMethodTest implements DefaultMethodInterface {

    /**
     * 测试实现接口函数方法
     */
    @Test
    public void testImplementMethod() {
        method("method");
    }

    /**
     * 测试默认方法
     */
    @Test
    public void testDefaultMethod() {
        defaultMethod();
    }

    /**
     * 测试重载默认方法
     */
    @Test
    public void testOverrideDefaultMethod() {
        overrideDefaultMethod();
    }

    /**
     * 测试静态方法
     */
    @Test
    public void testStaticMethod() {
        DefaultMethodInterface.staticMethod();
    }

    /**
     * 实现接口方法
     *
     * @param message the message
     */
    @Override
    public void method(String message) {
        System.out.println(message);
    }

    /**
     * 重写接口默认函数
     */
    @Override
    public void overrideDefaultMethod() {
        method("DefaultMethodTest#overrideDefaultMethod()");
    }
}
