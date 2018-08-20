/*
 * Created by lzy on 17-6-1 下午6:20.
 */
package com.lzy.demo.base.generics.bean;

import java.lang.reflect.ParameterizedType;

/**
 * 获取接口类泛型的实际类型
 *
 * @author lzy
 * @version v1.0
 */
public class GenericsSubClass extends GenericsClass<String> {

    public GenericsSubClass() {
        //使用反射获取泛型的实际类型(通过父类)
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        System.out.println("generic class: " + pt.getActualTypeArguments()[0]);
    }
}
