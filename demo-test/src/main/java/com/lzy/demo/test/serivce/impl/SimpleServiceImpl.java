package com.lzy.demo.test.serivce.impl;

import com.lzy.demo.test.bean.SimpleBean;
import com.lzy.demo.test.serivce.DependenceService;
import com.lzy.demo.test.serivce.SimpleService;

public class SimpleServiceImpl implements SimpleService {

    private DependenceService dependenceService = new DependenceServiceImpl();

    @Override
    public SimpleBean simpleMethod(SimpleBean simpleBean) {
        return dependenceService.dependenceMethod(simpleBean);
    }

    @Override
    public void doConsumer() {
        dependenceService.doConsumer(simpleBean -> simpleBean.setBody("real SimpleServiceImpl"));
    }

    public static String staticMethod(SimpleBean simpleBean) {
        return simpleBean.getBody();
    }
}
