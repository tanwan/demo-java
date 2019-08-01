/*
 * Created by LZY on 2017/5/17 20:19.
 */
package com.lzy.demo.base.bean;

/**
 * 子类
 *
 * @author LZY
 * @version v1.0
 */
public class SubClass extends SuperClass {
    public static final String SUBCLASS_STATIC_FINAL_VALUE = "静态变量";
    private static int subClassValue = 0;

    static {
        System.out.println("SubClass init");
    }

    /**
     * Method
     */
    public static void subclassMethod() {
        System.out.println("SubClass class method");
    }

    public static int getSubClassValue() {
        return subClassValue;
    }
}
