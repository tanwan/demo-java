package com.lzy.demo.test.spring.springboot1;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 这边的springboot包是为了测试隔离
 * <p>
 * SpringBootTest包含了SpringExtension
 * SpringBootTest会启动springboot应用
 * 满足以下其中一个条件即可
 * 1. 当前测试类所在的包下有SpringBootConfiguration(SpringBootApplication有包含了)注解的类 当前测试类属于此条件(测试类使用SpringBootConfiguration注解)
 *    当前测试类所在包下如果存在多个SpringBootConfiguration注解的类,则SpringBootTest需要使用classes指定使用哪个类
 * 2. 当前测试类下有Configuration注解的子类,会忽略测试类所在包下的其它@SpringBootConfiguration
 * 3. SpringBootTest有可以加载ApplicationContext的组件类
 * 注意: AutoConfigureMockMvc/WebMvcTest不能同SpringBootConfiguration和Configuration注解在同一个类上
 * 如果使用1的话, SpringBootConfiguration不能注解在测试类上,所以需要在测试类所在的包下有SpringBootConfiguration注解的类
 *
 *
 *
 * @author lzy
 * @version v1.0
 * @see SpringBootTestContextBootstrapper#getOrFindConfigurationClasses 检测是否有Configuration注解的类或者SpringBootConfiguration的注解的类
 */
@SpringBootTest
@SpringBootConfiguration
public class SpringBootWithSpringBootConfigurationTest {

    /**
     * 当前测试类所在的包下有SpringBootConfiguration(SpringBootApplication有包含了)注解的类
     */
    @Test
    public void testWithSpringBootConfiguration() {
        System.out.println("testWithSpringBootConfiguration");
    }

    /**
     * 这个类使用外层类的SpringBootConfiguration,所以可以运行
     * 设置spring.config.location的值,指定springboot的配置
     * 更好的方法应该是使用ActiveProfiles
     */
    @SpringBootTest(properties = "spring.config.location=classpath:properties.yml")
    public static class SpringConfigTest {
        /**
         * 设置spring.config.location,指定springboot的配置
         *
         * @param applicationName the application name
         * @param key             the key
         * @param innerKey        the inner key
         */
        @Test
        public void testProperties(@Value("${spring.application.name:#{null}}") String applicationName,
                                   @Value("${key}") String key, @Value("${outer.inner.key}") String innerKey) {
            assertEquals("value", key);
            assertEquals("outer.inner.value", innerKey);
            // spring.config.location指定springboot的配置,所以这边为null
            assertNull(applicationName);
        }
    }

    /**
     * ActiveProfiles测试
     */
    @SpringBootTest
    @ActiveProfiles("active")
    public static class ActiveProfilesTest {
        /**
         * 使用ActiveProfiles指定profile
         *
         * @param applicationName the application name
         */
        @Test
        public void testActiveProfiles(@Value("${spring.application.name}") String applicationName) {
            assertEquals("demo-test-active", applicationName);
        }
    }
}
