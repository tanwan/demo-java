package com.lzy.demo.spring.ioc.beans;

import com.lzy.demo.spring.ioc.beans.definition.ParentBean;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * 加载BeanDefinition流程测试
 *
 * @author lzy
 * @version v1.0
 */

@SpringJUnitConfig(ParentBean.class)
public class LoadBeanDefinitionTest {


    /**
     * 测试加载BeanDefinition
     */
    @Test
    public void testLoadBeanDefinition() {
        System.out.println("Debug Load Bean Definition");
    }
}
