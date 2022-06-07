package com.lzy.demo.design.pattern.proxy.statics;


import com.lzy.demo.design.pattern.proxy.Subject;

/**
 * 委托类
 *
 * @author LZY
 * @version v1.0
 */
public class RealSubject implements Subject {
    /**
     * 真实操作
     */
    @Override
    public void operation() {
        System.out.println("operation()");
    }

    /**
     * 真实操作
     *
     * @param str the str
     */
    @Override
    public void operation(String str) {
        System.out.println("operation():" + str);
    }
}
