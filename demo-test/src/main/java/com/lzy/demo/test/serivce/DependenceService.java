package com.lzy.demo.test.serivce;

import com.lzy.demo.test.bean.SimpleBean;

import java.util.function.Consumer;

public interface DependenceService {

    /**
     * Simple method simple bean.
     *
     * @param simpleBean the simple bean
     * @return the simple bean
     */
    SimpleBean dependenceMethod(SimpleBean simpleBean);


    /**
     * Default method.
     *
     * @return the string
     */
    default String defaultMethod() {
        return "DependenceService default method";
    }

    /**
     * 没有返回值
     *
     * @param simpleBean the simple bean
     */
    default void defaultNoReturn(SimpleBean simpleBean) {

    }

    /**
     * 使用回调
     * @param consumer consumer
     */
    void doConsumer(Consumer<SimpleBean> consumer);

}
