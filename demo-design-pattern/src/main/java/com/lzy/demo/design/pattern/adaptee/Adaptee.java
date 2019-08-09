/*
 * Created by LZY on 2017/7/15 21:51.
 */
package com.lzy.demo.design.pattern.adaptee;

/**
 * 不符合目录接口的类
 *
 * @author LZY
 * @version v1.0
 */
public class Adaptee {

    /**
     * Method.
     *
     * @param number the number
     */
    public void method(Integer number) {
        System.out.println(number);
    }
}
