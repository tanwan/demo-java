/*
 * Created by LZY on 12/4/2016 19:19.
 */
package com.lzy.demo.base.lamdba.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 函数式接口:只能有一个抽象方法,不是只能有一个方法,可以有多个默认方法
 *
 * @param <T> the type parameter
 * @author LZY
 * @version v1.0
 */
@FunctionalInterface
public interface LambdaInterface<T> {
    /**
     * The constant logger.
     */
    Logger logger = LoggerFactory.getLogger(LambdaInterface.class);

    /**
     * 函数式接口
     *
     * @param x the x
     * @return the string
     */
    T functionalInterface(T x);

    /**
     * 默认方法
     */
    default void defaultMethod() {
        logger.info("defaultMethod");
    }
}
