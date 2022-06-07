package com.lzy.demo.spring.ioc.lazy;

import com.lzy.demo.spring.ioc.lazy.alllazy.AllLazyBean1;
import com.lzy.demo.spring.ioc.lazy.alllazy.AllLazyBean2;
import com.lzy.demo.spring.ioc.lazy.declarelazy.DeclareLazyBean;
import com.lzy.demo.spring.ioc.lazy.dependlazy.DependLazyBean1;
import com.lzy.demo.spring.ioc.lazy.dependlazy.DependLazyBean2;
import com.lzy.demo.spring.ioc.lazy.nolazy.NoLazyBean1;
import com.lzy.demo.spring.ioc.lazy.nolazy.NoLazyBean2;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试注入和@Lazy
 *
 * @author lzy
 * @version v1.0
 */
public class LazyAutowiredTest {

    /**
     * 测试依赖和声明都没有使用@Lazy
     * 测试bean:NoLazyBean1,NoLazyBean2
     * 在解析NoLazyBean1的时候,去加载NoLazyBean2(无论NoLazyBean2的声明是否有@Lazy),所以NoLazyBean1,NoLazyBean2都会立即被加载
     */
    @Test
    public void testNoLazy() {
        ApplicationContext context = new AnnotationConfigApplicationContext(NoLazyBean1.class, NoLazyBean2.class);
        //NoLazyBean1()
        //NoLazyBean2()
        //NoLazyBean2#afterPropertiesSet()
        //NoLazyBean1#afterPropertiesSet()
        //---------noLazyBean1.getNoLazyBean2()--------------
        NoLazyBean1 noLazyBean1 = context.getBean(NoLazyBean1.class);
        System.out.println("---------noLazyBean1.getNoLazyBean2()--------------");
        System.out.println(noLazyBean1.getNoLazyBean2());
    }


    /**
     * 测试依赖和声明都使用@Lazy
     * 测试bean:AllLazyBean1,AllLazyBean2
     * 在InjectionMetadata.InjectedElement#inject会把AllLazyBean2注入到AllLazyBean1
     * 在这边会判断依赖是否有@Lazy,如果有的话,则返回一个代理类(代理类需要获取代理对象的时候,就会对AllLazyBean2进行加载)
     */
    @Test
    public void testAllLazy() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AllLazyBean1.class, AllLazyBean2.class);
        //AllLazyBean1()
        //AllLazyBean1#afterPropertiesSet()
        //---------allLazyBean1.getAllLazyBean2()--------------
        //AllLazyBean2()
        //AllLazyBean2#afterPropertiesSet()
        AllLazyBean1 allLazyBean1 = context.getBean(AllLazyBean1.class);
        System.out.println("---------allLazyBean1.getAllLazyBean2()--------------");
        //这里是调用代理类的toString()方法,然后代理类调用getTarget()的时候,就会触发AllLazyBean2的加载
        System.out.println(allLazyBean1.getAllLazyBean2());
    }


    /**
     * 测试只有依赖使用@Lazy
     * 测试bean:DependLazyBean1,DependLazyBean2
     * 在解析DependLazyBean1的时候,会判断依赖有@Lazy,所以就返回一个代理类(没有去加载DependLazyBean2)
     * 因为DependLazyBean2声明没有@Lazy,所以当容器扫到这个类的时候,就会对它进行加载
     */
    @Test
    public void testDependLazy() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DependLazyBean1.class, DependLazyBean2.class);
        //DependLazyBean1()
        //DependLazyBean1#afterPropertiesSet()
        //DependLazyBean2()
        //DependLazyBean2#afterPropertiesSet()
        //---------dependLazyBean1.getDependLazyBean2()--------------
        DependLazyBean1 dependLazyBean1 = context.getBean(DependLazyBean1.class);
        System.out.println("---------dependLazyBean1.getDependLazyBean2()--------------");
        System.out.println(dependLazyBean1.getDependLazyBean2());
    }


    /**
     * 测试只有声明使用@Lazy
     * 测试bean:DeclareLazyBean
     * 在DefaultListableBeanFactory#preInstantiateSingletons()不会加载@Lazy的bean
     * 所以当容器启动起来DeclareLazyBean也不会去加载(如果这个类有被非Lazy依赖,那么这个类也会被加载,也就是相当于没有使用@Lazy)
     */
    @Test
    public void testAutowiredLazy() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DeclareLazyBean.class);
        //---------context.getBean(DeclareLazyBean.class)--------------
        //DeclareLazyBean()
        //DeclareLazyBean#afterPropertiesSet()
        System.out.println("---------context.getBean(DeclareLazyBean.class)--------------");
        context.getBean(DeclareLazyBean.class);
    }
}
