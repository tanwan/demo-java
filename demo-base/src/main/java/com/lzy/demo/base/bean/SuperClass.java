package com.lzy.demo.base.bean;

public class SuperClass {
    public static final int SUPER_STATIC_FINAL_VALUE = 23;
    private static int superStaticValue = 23;

    static {
        System.out.println("SuperClass init");
    }

    public static void superMethod() {
        System.out.println("super class method");
    }

    public static int getSuperStaticValue() {
        return superStaticValue;
    }
}
