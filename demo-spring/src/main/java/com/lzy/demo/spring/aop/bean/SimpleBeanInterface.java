package com.lzy.demo.spring.aop.bean;


import com.lzy.demo.spring.aop.annotation.Param;

public interface SimpleBeanInterface {

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


    void within();

    void atWithin();

    void thiz();

    void thisArgs();

    void target();

    void atTarget();

    void targetArgs();

    void args(int i);

    void atArgs(Param param1, int param2, int param3);

    void atAnnotation();

    void order();

    void clause();
}
