package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.proxy.Subject;
import com.lzy.demo.design.pattern.proxy.cglib.CglibProxy;
import com.lzy.demo.design.pattern.proxy.cglib.LazyLoaderBean;
import com.lzy.demo.design.pattern.proxy.jdk.ExceptionHandle;
import com.lzy.demo.design.pattern.proxy.jdk.JdkProxy;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 代理模式测试
 * cglib启动需要添加--add-opens java.base/java.lang=ALL-UNNAMED
 * jdk代理如果需要保存动态生成的类,需要使用-Djdk.proxy.ProxyGenerator.saveGeneratedFiles=true,文件保存在当前模块的jdk/proxyX/$proxyX
 * 保存的逻辑在java.lang.reflect.ProxyGenerator#generateProxyClass
 *
 * @author LZY
 * @version v1.0
 * @see java.lang.reflect.ProxyGenerator#generateProxyClass(java.lang.ClassLoader, java.lang.String, java.util.List, int)
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
     * 测试jdk动态代理的异常处理
     *
     * @throws Exception Exception
     */
    @Test
    public void testJdkProxyException() throws Exception {
        ExceptionHandle.Subject realSubject = new ExceptionHandle.RealSubject();
        // 不抛出真实的cause
        ExceptionHandle.Subject proxy = ExceptionHandle.ExceptionHandleProxy.getProxy(realSubject, false);

        // 接口声明了Exception,直接抛出InvocationTargetException
        assertThatThrownBy(proxy::declareThrowException).isExactlyInstanceOf(InvocationTargetException.class);

        // 接口声明了UndeclaredThrowableException,抛出UndeclaredThrowableException, 并且SQLException被InvocationTargetException和UndeclaredThrowableException包装了
        assertThatThrownBy(proxy::declareThrowSQLException)
                .isExactlyInstanceOf(UndeclaredThrowableException.class)
                .hasCauseInstanceOf(InvocationTargetException.class)
                .hasRootCauseInstanceOf(SQLException.class);

        // 接口声明了InvocationTargetException,直接抛出InvocationTargetException, 并且被InvocationTargetException包装了一层
        assertThatThrownBy(proxy::declareThrowInvocationTargetException)
                .isExactlyInstanceOf(InvocationTargetException.class)
                .hasCauseInstanceOf(InvocationTargetException.class);

        // 接口声明了IllegalAccessException,但是实际上抛出了SQLException, 所以最终是UndeclaredThrowableException
        assertThatThrownBy(proxy::throwSQLExceptionInInvoke).isExactlyInstanceOf(UndeclaredThrowableException.class);

        // 抛出真实cause
        proxy = ExceptionHandle.ExceptionHandleProxy.getProxy(realSubject, true);

        // 接口声明了Exception, invoke抛出真实的SQLException
        assertThatThrownBy(proxy::declareThrowException).isExactlyInstanceOf(SQLException.class);

        // 接口声明了SQLException, invoke抛出真实的SQLException
        assertThatThrownBy(proxy::declareThrowSQLException).isExactlyInstanceOf(SQLException.class);

        // 接口声明了InvocationTargetException, invoke抛出真实的InvocationTargetException,没有再被InvocationTargetException包装一层
        assertThatThrownBy(proxy::declareThrowInvocationTargetException)
                .isExactlyInstanceOf(InvocationTargetException.class)
                .hasCauseInstanceOf(SQLException.class);

        // 接口声明了IllegalAccessException, invoke抛出SQLException, 最终是UndeclaredThrowableException
        assertThatThrownBy(proxy::throwSQLExceptionInInvoke).isExactlyInstanceOf(UndeclaredThrowableException.class);
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
