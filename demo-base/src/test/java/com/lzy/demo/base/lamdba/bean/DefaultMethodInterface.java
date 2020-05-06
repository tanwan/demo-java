/*
 * Created by LZY on 12/4/2016 19:13.
 */
package com.lzy.demo.base.lamdba.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 其它接口如果继承这个接口,可以重写默认方法
 *
 * @author LZY
 * @version v1.0
 */
public interface DefaultMethodInterface {
    Logger logger = LoggerFactory.getLogger(DefaultMethodInterface.class);

    /**
     * Method
     *
     * @param message the message
     */
    void method(String message);

    /**
     * 接口默认函数
     */
    default void overrideDefaultMethod() {
        method("DefaultMethodInterface#overrideDefaultMethod()");
    }

    /**
     * 接口默认函数
     */
    default void defaultMethod() {
        logger.info("DefaultMethodInterface#defaultMethod()");
    }

    /**
     * 静态方法
     */
    static void staticMethod() {
        logger.info("DefaultMethodInterface#staticMethod()");
    }
}
