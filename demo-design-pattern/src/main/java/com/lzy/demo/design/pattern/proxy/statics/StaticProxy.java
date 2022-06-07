package com.lzy.demo.design.pattern.proxy.statics;

import com.lzy.demo.design.pattern.proxy.Subject;

/**
 * 静态代理
 * 代理对象的一个接口只服务于一种类型的对象,如果要代理的方法很多,势必要为每一种方法都进行代理,静态代理在程序规模稍大时就无法胜任了
 * 如果接口增加一个方法,除了所有实现类需要实现这个方法外,所有代理类也需要实现此方法.增加了代码维护的复杂度
 *
 * @author LZY
 * @version v1.0
 */
public class StaticProxy implements Subject {
    private Subject subject;

    public StaticProxy(Subject subject) {
        this.subject = subject;
    }

    /**
     * 代理操作
     * 一个方法对应一个代理方法
     */
    @Override
    public void operation() {
        before();
        subject.operation();
        after();
    }

    /**
     * 代理操作
     *
     * @param str the str
     */
    @Override
    public void operation(String str) {
        before();
        subject.operation(str);
        after();
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
