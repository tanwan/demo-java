/*
 * Created by LZY on 2017/5/17 20:21.
 */
package com.lzy.demo.base.bean;

/**
 * 父类
 *
 * @author LZY
 * @version v1.0
 */
public class SuperClass {
    public static final int SUPER_STATIC_FINAL_VALUE = 23;
    private static int superStaticValue = 23;

    static {
        System.out.println("SuperClass init");
    }

    /**
     * Method.
     */
    public static void superMethod() {
        System.out.println("super class method");
    }

    public static int getSuperStaticValue() {
        return superStaticValue;
    }
}
