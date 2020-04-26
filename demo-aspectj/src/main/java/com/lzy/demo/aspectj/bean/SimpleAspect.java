/*
 * Created by lzy on 2019-03-29 00:05.
 */
package com.lzy.demo.aspectj.bean;

import com.lzy.demo.aspectj.annotation.Param;

/**
 * The type SimpleAspect.
 *
 * @author lzy
 * @version v1.0
 */
public interface SimpleAspect {


    /**
     * 方法调用
     */
    void call();

    /**
     * 方法执行
     */
    void execution();

    /**
     * 入参有注解
     *
     * @param param1 the param 1
     * @param param2 the param 2
     */
    void parameterAnnotation(int param1, int param2);

    /**
     * 类型有注解
     *
     * @param param1 the param 1
     * @param param2 the param 2
     * @return the string
     */
    Param typeAnnotation(Param param1, int param2);

    /**
     * Within.
     */
    void within();

    /**
     * At within.
     */
    void atWithin();

    /**
     * Thiz.
     */
    void thiz();


    /**
     * This args.
     */
    void thisArgs();

    /**
     * Target.
     */
    void target();

    /**
     * Target.
     */
    void atTarget();

    /**
     * Target.
     */
    void targetArgs();

    /**
     * Args.
     *
     * @param i the
     */
    void args(int i);


    /**
     * At args.
     *
     * @param param1 the param 1
     * @param param2 the param 2
     * @param param3 the param 3
     */
    void atArgs(Param param1, int param2, int param3);


    /**
     * At annotation.
     */
    void atAnnotation();

    /**
     * Handler.
     */
    void handler();

    /**
     * Order.
     */
    void order();

    /**
     * Clause.
     */
    void clause();
}
