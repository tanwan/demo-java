package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.proxy.Subject;
import com.lzy.demo.design.pattern.proxy.cglib.CglibProxy;
import com.lzy.demo.design.pattern.proxy.cglib.LazyLoaderBean;
import com.lzy.demo.design.pattern.proxy.dynamic.JdkProxy;
import com.lzy.demo.design.pattern.proxy.statics.RealSubject;
import com.lzy.demo.design.pattern.proxy.statics.StaticProxy;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import net.sf.cglib.proxy.NoOp;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * 代理模式测试
 *
 * @author LZY
 * @version v1.0
 */
public class ProxyTest {

    /**
     * 测试静态代理
     */
    @Test
    public void testProxy() {
        Subject realSubject = new RealSubject();
        StaticProxy staticProxy = new StaticProxy(realSubject);
        staticProxy.defaultMethod();
        staticProxy.operation();
        staticProxy.operation("hello world");
    }

    /**
     * 测试JDK动态代理
     */
    @Test
    public void testJdkProxy() {
        Subject realSubject = new RealSubject();
        Subject subjectProxy = JdkProxy.getProxy(realSubject);
        // defaultMethod也会被代理
        subjectProxy.defaultMethod();
        subjectProxy.operation();
        subjectProxy.operation("hello world");
    }

    /**
     * 测试需要调用带有InvocationHandler的构造函数
     *
     * @throws Exception the exception
     */
    @Test
    public void testJdkProxyClass() throws Exception {
        Subject realSubject = new RealSubject();
        Class proxyClass = (Class<Subject>) Proxy.getProxyClass(Subject.class.getClassLoader(), Subject.class);
        Constructor<Subject> constructor = proxyClass.getConstructor(InvocationHandler.class);
        // 无法直接使用默认构造函数实例化对象
        assertThatCode(() -> constructor.newInstance())
                .isInstanceOf(IllegalArgumentException.class);
        //需要调用带有InvocationHandler的构造函数
        Subject subjectProxy = constructor.newInstance(new JdkProxy(realSubject));
        subjectProxy.operation();
    }


    /**
     * cglib动态代理
     */
    @Test
    public void testCglibProxy() {
        RealSubject realSubject = new RealSubject();
        Subject subjectProxy = CglibProxy.getProxy(realSubject);
        subjectProxy.defaultMethod();
        subjectProxy.operation();
        subjectProxy.operation("hello world");
    }

    /**
     * 测试cglib过滤器
     */
    @Test
    public void testCglibFilter() {
        Enhancer enhancer = new Enhancer();
        //设置需要创建类的父类
        enhancer.setSuperclass(Subject.class);
        //这边设置了2个回调类,需要使用过滤器来决定使用哪个回调
        enhancer.setCallbacks(new Callback[]{NoOp.INSTANCE, new CglibProxy(new RealSubject())});
        //添加过滤器
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                //返回值其实就是回调数组的索引,这边就是使用NoOp
                return 0;
            }
        });
        //通过字节码技术动态创建实例
        Subject subject = (Subject) enhancer.create();
        // 直接使用父类方法,也就是Subject#defaultMethod
        subject.defaultMethod();
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                //返回值其实就是回调数组的索引,这边就是使用CglibProxy
                return 1;
            }
        });
        subject = (Subject) enhancer.create();
        // 使用CglibProxy进行动态代理
        subject.defaultMethod();
    }

    /**
     * 测试cglib生成的代理类
     *
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    @Test
    public void testCglibClass() throws IllegalAccessException, InstantiationException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Subject.class);
        //一定要指定callback的类型
        enhancer.setCallbackType(CglibProxy.class);
        Class<Subject> subjectClass = enhancer.createClass();
        // 使用反射创建代理类
        Subject subject = subjectClass.newInstance();
        //强制转换成Factory类来设置callback
        ((Factory) subject).setCallback(0, new CglibProxy(new RealSubject()));
        subject.defaultMethod();
    }

    /**
     * 测试懒加载
     */
    @Test
    public void testLazyLoader() {
        LazyLoaderBean lazyLoaderBean = new LazyLoaderBean();
        System.out.println("------------------------------");
        //在这里使用到懒加载的属性,才会去加载,只加载一次
        System.out.println(lazyLoaderBean.getLazyLoaderProperty().getName());
        System.out.println(lazyLoaderBean.getLazyLoaderProperty().getName());
    }

}
