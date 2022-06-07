package com.lzy.demo.base.bean;

public class SubClass extends SuperClass {
    public static final String SUBCLASS_STATIC_FINAL_VALUE = "静态变量";
    private static int subClassValue = 0;

    static {
        System.out.println("SubClass init");
    }

    public static void subclassMethod() {
        System.out.println("SubClass class method");
    }

    public static int getSubClassValue() {
        return subClassValue;
    }
}
