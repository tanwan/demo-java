package com.lzy.demo.spring.ioc.dependson;

import com.lzy.demo.spring.ioc.dependson.direct.DirectBean1;
import com.lzy.demo.spring.ioc.dependson.direct.DirectBean2;
import com.lzy.demo.spring.ioc.dependson.indirect.IndirectBean1;
import com.lzy.demo.spring.ioc.dependson.indirect.IndirectBean2;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试dependOn
 *
 * @author lzy
 * @version v1.0
 */
public class DependOnTest {
    /**
     * 测试直接依赖
     * 使用注入,那么DirectBean2必须在DirectBean1前进行加载
     * 测试bean:DirectBean1,DirectBean2
     * 在解析DirectBean1的时候,去加载DirectBean2,所以DirectBean1比DirectBean2先实例化
     * 但是DirectBean2比DirectBean1先加载完成
     */
    @Test
    public void testDirectDependOn() {
        //DirectBean1()
        //DirectBean2()
        //DirectBean2#afterPropertiesSet()
        //DirectBean1#afterPropertiesSet()
        new AnnotationConfigApplicationContext(DirectBean1.class, DirectBean2.class);
    }

    /**
     * 测试不直接依赖(比如事件发布者和事件监听者,监听者需要在发布者之前加载,才不会错过事件)
     * 测试bean:IndirectBean1,IndirectBean2
     * 在AbstractBeanFactory#doGetBean中,会先判断是否存在dependsOn,如果存在,则先进行dependsOn bean的加载
     * 所以IndirectBean2的实例化也会在IndirectBean1之前
     */
    @Test
    public void testIndirectOn() {
        //IndirectBean2()
        //IndirectBean2#afterPropertiesSet()
        //IndirectBean1()
        //IndirectBean1#afterPropertiesSet()
        new AnnotationConfigApplicationContext(IndirectBean1.class, IndirectBean2.class);
    }
}
