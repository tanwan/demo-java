/*
 * Created by lzy on 2018/8/20 10:42 AM.
 */
package com.lzy.demo.base.functional.lamdba.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 方法引用
 *
 * @author lzy
 * @version v1.0
 */
public class MethodReference {

    private static Logger logger = LoggerFactory.getLogger(MethodReference.class);

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
        logger.info("MethodReference(final String str)");
    }

    /**
     * methodReference::instanceMethod => (s) -> instance.instanceMethod(s) (instance为调用的实例对象)
     *
     * @param str the str
     */
    public void instanceMethod(String str) {
        logger.info("instanceMethod()");
    }

    /**
     * MethodReference::staticMethod => (s) -> MethodReference.staticMethod(s)
     *
     * @param str the str
     */
    public static void staticMethod(String str) {
        logger.info("staticMethod()");
    }


    /**
     * MethodReference::method ==> (instance) -> instance.method() (lambda的第一个参数会成为调用实例方法的对象)
     *
     */
    public void method() {
        logger.info("method()");
    }
}
