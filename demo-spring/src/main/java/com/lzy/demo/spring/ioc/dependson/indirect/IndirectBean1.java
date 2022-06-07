package com.lzy.demo.spring.ioc.dependson.indirect;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * IndirectBean1不直接依赖IndirectBean2,使用@DependsOn可以确保IndirectBean2在IndirectBean1之前加载
 *
 * @author lzy
 * @version v1.0
 */
@Component
@DependsOn("indirectBean2")
public class IndirectBean1 implements InitializingBean {
    public IndirectBean1() {
        System.out.println("IndirectBean1()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("IndirectBean1#afterPropertiesSet()");
    }
}
