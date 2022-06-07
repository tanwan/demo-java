package com.lzy.demo.spring.ioc.bean.definition;

import com.lzy.demo.spring.ioc.beans.definition.ParentBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * BeanDefinition测试
 *
 * @author lzy
 * @version v1.0
 */
public class BeanDefinitionTest {

    private AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();


    /**
     * 测试RootDefinition
     */
    @Test
    public void testRootBeanDefinition() {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(ParentBean.class);
        rootBeanDefinition.getPropertyValues().add("name", "root");
        applicationContext.registerBeanDefinition("rootBean", rootBeanDefinition);
        applicationContext.refresh();
        Assertions.assertThat(applicationContext.getBean("rootBean"))
                .hasFieldOrPropertyWithValue("name", "root");
    }


    /**
     * 测试ChildBeanDefinition
     */
    @Test
    public void testChildBeanDefinition() {

        // 创建RootBeanDefinition
        RootBeanDefinition parentBeanDefinition = new RootBeanDefinition();
        parentBeanDefinition.setBeanClass(ParentBean.class);
        parentBeanDefinition.getPropertyValues().add("name", "parent");
        applicationContext.registerBeanDefinition("parentBean", parentBeanDefinition);

        // 创建ChildBeanDefinition,root和child的创建顺序没有影响
        ChildBeanDefinition childBeanDefinition = new ChildBeanDefinition("parentBean");
        applicationContext.registerBeanDefinition("childBean", childBeanDefinition);


        applicationContext.refresh();
        Assertions.assertThat(applicationContext.getBean("parentBean"))
                .hasFieldOrPropertyWithValue("name", "parent");

        // childBeanDefinition会使用RootBeanDefinition的属性
        Assertions.assertThat(applicationContext.getBean("childBean"))
                .hasFieldOrPropertyWithValue("name", "parent");
    }


    /**
     * 测试GenericBeanDefinition
     */
    @Test
    public void testGenericBeanDefinition() {
        // 创建GenericBeanDefinition,父
        GenericBeanDefinition parentBeanDefinition = new GenericBeanDefinition();
        parentBeanDefinition.setBeanClass(ParentBean.class);
        parentBeanDefinition.getPropertyValues().add("name", "parent");
        applicationContext.registerBeanDefinition("parentBean", parentBeanDefinition);

        // 创建GenericBeanDefinition,子,parent和child的创建顺序没有影响
        GenericBeanDefinition childBeanDefinition = new GenericBeanDefinition();
        childBeanDefinition.setParentName("parentBean");
        applicationContext.registerBeanDefinition("childBean", childBeanDefinition);

        applicationContext.refresh();
        Assertions.assertThat(applicationContext.getBean("parentBean"))
                .hasFieldOrPropertyWithValue("name", "parent");

        // childBeanDefinition会使用RootBeanDefinition的属性
        Assertions.assertThat(applicationContext.getBean("childBean"))
                .hasFieldOrPropertyWithValue("name", "parent");
    }
}
