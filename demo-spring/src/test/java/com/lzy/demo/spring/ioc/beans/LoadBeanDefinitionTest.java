/*
 * Created by lzy on 2018/11/5 1:03 PM.
 */
package com.lzy.demo.spring.ioc.beans;

import com.lzy.demo.spring.AbstractSpringTest;
import org.junit.jupiter.api.Test;

/**
 * 加载BeanDefinition流程测试
 *
 * @author lzy
 * @version v1.0
 */
public class LoadBeanDefinitionTest extends AbstractSpringTest {


    /**
     * 测试加载BeanDefinition
     */
    @Test
    public void testLoadBeanDefinition() {
        initApplicationContext("definition");
    }
}
