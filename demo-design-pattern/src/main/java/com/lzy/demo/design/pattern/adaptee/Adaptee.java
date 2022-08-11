package com.lzy.demo.design.pattern.adaptee;

/**
 * 不符合目标接口的类
 *
 * @author LZY
 * @version v1.0
 */
public class Adaptee {

    public void method(Integer number) {
        System.out.println(number);
    }
}
