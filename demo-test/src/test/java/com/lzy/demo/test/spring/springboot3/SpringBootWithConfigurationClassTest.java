package com.lzy.demo.test.spring.springboot3;

import com.lzy.demo.test.spring.SpringBean;
import com.lzy.demo.test.spring.springboot2.IgnoreApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNull;


/**
 * 这边的springboot包是为了测试隔离
 * <p>
 * SpringBootTest包含了SpringExtension
 * SpringBootTest会启动springboot应用
 * 满足以下其中一个条件即可
 * 1. 当前测试类所在的包下有SpringBootConfiguration(SpringBootApplication有包含了)注解的类
 * 当前测试类所在包下如果存在多个SpringBootConfiguration注解的类,则SpringBootTest需要使用classes指定使用哪个类
 * 2. 当前测试类下有Configuration注解的子类,会忽略测试类所在包下的其它@SpringBootConfiguration
 * 3. SpringBootTest有可以加载ApplicationContext的组件类 当前测试类属于此条件
 * 注意: AutoConfigureMockMvc/WebMvcTest不能同SpringBootConfiguration和Configuration注解在同一个类上
 * 如果使用1的话, SpringBootConfiguration不能注解在测试类上,所以需要在测试类所在的包下有SpringBootConfiguration注解的类
 *
 * @author lzy
 * @version v1.0
 * @see SpringBootTestContextBootstrapper#getOrFindConfigurationClasses 检测是否有Configuration注解的类或者SpringBootConfiguration的注解的类
 */
public class SpringBootWithConfigurationClassTest {

    /**
     * 使用本类加载ApplicationContext
     */
    @SpringBootTest(classes = UseSelfClassTest.class)
    public static class UseSelfClassTest {
        /**
         * SpringBootTest指定配置类为本类
         *
         * @param application application
         */
        @Test
        public void testUseSelfClass(@Autowired(required = false) IgnoreApplication application) {
            assertNull(application);
            System.out.println("testUseSelfClass");
        }
    }

    /**
     * 使用其它类(Component注解的,Configuration也有Component注解)
     */
    @SpringBootTest(classes = SpringBean.class)
    public static class UseOtherClassTest {

        /**
         * SpringBootTest指定了其它Configuration注解的类
         *
         * @param springBean  the spring bean
         * @param application application
         */
        @Test
        public void testUseOtherClass(@Autowired SpringBean springBean, @Autowired(required = false) IgnoreApplication application) {
            assertNull(application);
            System.out.println(springBean);
        }
    }

    /**
     * 使用SpringJUnitConfig, 相当于@SpringBootTest(classes = SpringBean.class)
     */
    @SpringBootTest
    @SpringJUnitConfig(classes = SpringBean.class)
    public static class UseSpringJUnitConfigTest {

        /**
         * SpringBootTest指定了其它Configuration注解的类
         *
         * @param springBean  the spring bean
         * @param application application
         */
        @Test
        public void testUseSpringJUnitConfig(@Autowired SpringBean springBean, @Autowired(required = false) IgnoreApplication application) {
            assertNull(application);
            System.out.println(springBean);
        }
    }

}
