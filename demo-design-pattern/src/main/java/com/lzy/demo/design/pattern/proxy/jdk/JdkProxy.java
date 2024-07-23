package com.lzy.demo.design.pattern.proxy.jdk;

import com.lzy.demo.design.pattern.proxy.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * jdk动态代理只能代理接口,依靠java的反射机制和动态生成class的技术,来动态生成被代理的接口的实现对象
 * jdk动态生成出来的类需要调用带有InvocationHandler的构造函数
 *
 * @author LZY
 * @version v1.0
 */
public class JdkProxy implements InvocationHandler {
    private Subject realSubject;

    public JdkProxy() {
    }

    public JdkProxy(Subject realSubject) {
        this.realSubject = realSubject;
    }

    public static Subject getProxy(Subject realSubject) {
        //只能代理接口,不能代理类
        return (Subject) Proxy.newProxyInstance(realSubject.getClass().getClassLoader(), realSubject.getClass().getInterfaces(), new JdkProxy(realSubject));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(realSubject, args);
        after();
        return result;
    }

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }
}
