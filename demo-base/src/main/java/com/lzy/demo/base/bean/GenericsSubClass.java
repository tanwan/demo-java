package com.lzy.demo.base.bean;

import java.lang.reflect.ParameterizedType;

public class GenericsSubClass extends GenericsClass<String> {

    public GenericsSubClass() {
        //使用反射获取泛型的实际类型(通过父类)
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        System.out.println("generic class: " + pt.getActualTypeArguments()[0]);
    }
}
