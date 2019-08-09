/*
 * Created by LZY on 2017/3/26 22:47.
 */
package com.lzy.demo.design.pattern.decorator;

/**
 * 具体的组件对象
 *
 * @author LZY
 * @version v1.0
 */
public class ConcreteComponent implements Component {
    /**
     * 操作方法
     */
    @Override
    public void operation() {
        System.out.println("ConcreteComponent#operation()");
    }
}
