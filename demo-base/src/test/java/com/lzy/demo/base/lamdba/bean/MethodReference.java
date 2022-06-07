package com.lzy.demo.base.lamdba.bean;

public class MethodReference {
    /**
     * MethodReference::new ==》 () -> new MethodReference();
     */
    public MethodReference() {
    }

    /**
     * MethodReference::new => (s) -> new MethodReference(s)
     *
     * @param str the str
     */
    public MethodReference(final String str) {
        System.out.println("MethodReference(final String str)");
    }

    /**
     * methodReference::instanceMethod => (s) -> instance.instanceMethod(s) (instance为调用的实例对象)
     *
     * @param str the str
     */
    public void instanceMethod(String str) {
        System.out.println("instanceMethod()");
    }

    /**
     * MethodReference::staticMethod => (s) -> MethodReference.staticMethod(s)
     *
     * @param str the str
     */
    public static void staticMethod(String str) {
        System.out.println("staticMethod()");
    }


    /**
     * MethodReference::method ==> (instance) -> instance.method() (lambda的第一个参数会成为调用实例方法的对象)
     */
    public void method() {
        System.out.println("method()");
    }
}
