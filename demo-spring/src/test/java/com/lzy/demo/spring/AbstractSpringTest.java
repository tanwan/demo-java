/*
 * Created by lzy on 2018/8/6 9:10 PM.
 */
package com.lzy.demo.spring;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

/**
 * 测试抽象类
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractSpringTest {

    /**
     * The Context.
     */
    protected AnnotationConfigApplicationContext context;

    /**
     * 创建容器
     *
     * @param subPackage the sub package
     */
    protected void initApplicationContext(String subPackage) {
        if (StringUtils.hasLength(subPackage)) {
            //subPackage有值,扫描特定的子包
            context = new AnnotationConfigApplicationContext(getClass().getPackage().getName() + "." + subPackage);
        } else if (AnnotationUtils.findAnnotation(getClass(), Configuration.class) == null) {
            //没有@Configuration扫描当前包
            context = new AnnotationConfigApplicationContext(getClass().getPackage().getName());
        } else {
            //有@Configuration只扫当前类
            context = new AnnotationConfigApplicationContext(getClass());
        }

    }
}
