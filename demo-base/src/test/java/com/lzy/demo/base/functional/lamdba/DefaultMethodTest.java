/*
 * Created by LZY on 12/4/2016 19:07.
 */
package com.lzy.demo.base.functional.lamdba;

import com.lzy.demo.base.functional.lamdba.bean.DefaultMethodInterface;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口默认方法
 *
 * @author LZY
 * @version v1.0
 */
public class DefaultMethodTest implements DefaultMethodInterface {
    private static Logger logger = LoggerFactory.getLogger(DefaultMethodTest.class);

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
        logger.info(message);
    }

    /**
     * 重写接口默认函数
     */
    @Override
    public void overrideDefaultMethod() {
        method("DefaultMethodTest#overrideDefaultMethod()");
    }
}
