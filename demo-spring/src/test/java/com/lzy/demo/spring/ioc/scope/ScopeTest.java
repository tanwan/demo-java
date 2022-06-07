package com.lzy.demo.spring.ioc.scope;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({SingletonScope.class, PrototypeScope.class})
public class ScopeTest {
    @Autowired
    private ApplicationContext context;

    /**
     * 测试Singleton
     */
    @Test
    public void testSingletonScope() {
        SingletonScope singletonScope1 = context.getBean("singletonScope", SingletonScope.class);
        SingletonScope singletonScope2 = context.getBean("singletonScope", SingletonScope.class);
        //只会创建一此SingletonScope,因此两次获取到的SingletonScope都是同一个实例
        Assertions.assertSame(singletonScope1, singletonScope2);
        //一共只初始化一次
        Assertions.assertEquals(1, singletonScope1.getInstantiationTimes());
        Assertions.assertEquals(1, singletonScope2.getInstantiationTimes());
    }

    /**
     * 测试prototype
     */
    @Test
    public void testPrototypeScope() {
        PrototypeScope prototypeScope1 = context.getBean("prototypeScope", PrototypeScope.class);
        PrototypeScope prototypeScope2 = context.getBean("prototypeScope", PrototypeScope.class);
        //调用一次getBean,就会创建一次PrototypeScope,所以两个PrototypeScope是不同实例
        Assertions.assertNotSame(prototypeScope1, prototypeScope2);
        //一共初始化2次
        Assertions.assertEquals(2, prototypeScope1.getInstantiationTimes());
        Assertions.assertEquals(2, prototypeScope2.getInstantiationTimes());
    }
}
