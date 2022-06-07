package com.lzy.demo.design.pattern.proxy.cglib;

import com.lzy.demo.design.pattern.proxy.Subject;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

/**
 * cglib 动态代理 基于ASM实现
 * 通过Enhancer动态生成一个原有类的子类去覆盖原有类的非final方法并设置好callback,
 * 则原有类的每个方法的调用就会转变成调用Callback#interceptor
 *
 * @author LZY
 * @version v1.0
 */
public final class CglibProxy implements MethodInterceptor {
    private static Enhancer enhancer = new Enhancer();

    /**
     * 委托类的实例
     */
    private Object realSubject;

    public CglibProxy(Object realSubject) {
        this.realSubject = realSubject;
    }

    /**
     * 获取代理对象(这个方法可以单独抽离出去)
     *
     * @param realSubject the real subject
     * @return the proxy
     */
    public static Subject getProxy(Object realSubject) {
        //设置需要创建类的父类,可以是接口,也可以是类
        enhancer.setSuperclass(Subject.class);
        //设置回调类(返回的实例如果有调用方法,就会进入该入参的intercept方法
        //这里可以添加多个callback,如果添加了多个,就必需使用过滤器
        //也可以使用setCallback只设置一个回调
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, new CglibProxy(realSubject)});
        //添加过滤器
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                //返回值其实就是回调数组的索引
                //返回0,表示使用传进来的callback数据的第1个,这里为NoOp
                //返回1,表示使用传进来的callback数据的第2个,这里为CglibProxy
                return 1;
            }
        });
        //通过字节码技术动态创建实例
        return (Subject) enhancer.create();
    }

    /**
     * {@inheritDoc}
     *
     * @param obj    代理类的实例
     * @param method 拦截的方法
     * @param args   参数
     * @param proxy  用来调用真实类的方法
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("obj:" + obj.getClass().getName());
        // 使用委托类执行方法,也就是调用真正的方法
        return proxy.invoke(this.realSubject, args);
    }
}
