package com.lzy.demo.test.serivce;

import com.lzy.demo.test.bean.SimpleBean;

public interface SimpleService {

    /**
     * Simple method simple bean.
     *
     * @param simpleBean the simple bean
     * @return the simple bean
     */
    SimpleBean simpleMethod(SimpleBean simpleBean);


    /**
     * 使用回调
     */
    void doConsumer();
}
