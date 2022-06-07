package com.lzy.demo.test.serivce.impl;

import com.lzy.demo.test.bean.SimpleBean;
import com.lzy.demo.test.serivce.DependenceService;

import java.util.function.Consumer;

public class DependenceServiceImpl implements DependenceService {
    @Override
    public SimpleBean dependenceMethod(SimpleBean simpleBean) {
        System.out.println(simpleBean);
        return new SimpleBean("real DependenceServiceImpl object");
    }

    @Override
    public void doConsumer(Consumer<SimpleBean> consumer) {
        consumer.accept(new SimpleBean(null));
    }
}
