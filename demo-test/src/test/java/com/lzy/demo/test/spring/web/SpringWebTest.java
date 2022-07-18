package com.lzy.demo.test.spring.web;

import com.lzy.demo.test.spring.AnotherSpringController;
import com.lzy.demo.test.spring.SpringBean;
import com.lzy.demo.test.spring.SpringController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * AutoConfigureMockMvc不能跟@Configuration和SpringBootConfiguration(SpringBootApplication)注解在同一个类上
 *
 * @author lzy
 * @version v1.0
 */
public class SpringWebTest {

    /**
     * 使用Mock环境
     * 如果没有启动整个springboot应用的话,那么需要使用@AutoConfigureWebMvc,用来配置MVC
     * 否则会有一些问题,比如Controller的返回值没有办法进行json序列化
     *
     * @see WebMvcConfigurationSupport#requestMappingHandlerAdapter
     */
    @AutoConfigureMockMvc
    @AutoConfigureWebMvc
    @SpringBootTest(classes = MockWebEnvironmentTest.class)
    public static class MockWebEnvironmentTest {


        /**
         * Test mock mvc.
         *
         * @param mockMvc the mock mvc
         */
        @Test
        public void testMockMvc(@Autowired MockMvc mockMvc) {
            assertNotNull(mockMvc);
        }
    }


    /**
     * MOCK环境,使用明确的端口,这时候,需要ServletWebServerFactoryAutoConfiguration和PropertyPlaceholderAutoConfiguration
     * 如果是配合@SpringBootApplication,就不需要这么麻烦了
     */
    @AutoConfigureMockMvc
    @AutoConfigureWebMvc
    @SpringBootTest(classes = {ServletWebServerFactoryAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public static class DefinedPortEnvironmentTest {


        /**
         * Test mock mvc.
         *
         * @param mockMvc the mock mvc
         */
        @Test
        public void testDefinedPort(@Autowired MockMvc mockMvc) {
            assertNotNull(mockMvc);
        }
    }

    /**
     * 使用WebMvcTest
     * WebMvcTest用于专注于测试spring MVC的组件,它会禁止springboot完整的自动配置,只会应用于spring mvc测试相关的配置
     * 同时它只会装配Controller相关的bean(@Controller/@ControllerAdvice/@JsonComponent/Converter/GenericConverter/Filter/WebMvcConfigurer/HandlerMethodArgumentResolver)
     * 不会装配@Component/@Service/@Repository,所以这些需要使用@MockBean
     * WebMvcTest的controllers是用来同SpringBootConfiguration进行配合的,如果指定了controllers,那么则只会装配此Controller
     * <p>
     * 如果WebMvcTest不适用的话,那么可以使用@SpringBootTest+@AutoConfigureMockMvc
     * <p>
     * 1. 当前测试类所在的包下有SpringBootConfiguration(SpringBootApplication有包含了)注解的类
     * WebMvcTest的controllers是用来同此场景配合的
     * 2. 当前测试类下有Configuration注解的子类
     * 3. SpringBootTest有可以加载ApplicationContext的组件类 本测试属于此条件
     * <p>
     * WebMvcTest不能同SpringBootTest一起使用,否则会有多个BootstrapWith,See BootstrapUtils#resolveExplicitTestContextBootstrapper
     *
     * @see AutoConfigurationImportSelector#isEnabled(AnnotationMetadata) AutoConfigurationImportSelector#isEnabled(AnnotationMetadata)
     */
    @WebMvcTest
    @SpringJUnitConfig(WebMvcTestWithTest.class)
    public static class WebMvcTestWithTest {

        /**
         * 测试mockMvc
         *
         * @param mockMvc the mock mvc
         */
        @Test
        public void testMockMvc(@Autowired MockMvc mockMvc) {
            assertNotNull(mockMvc);
        }
    }

    /**
     * 使用WebMvcTest
     * 当前测试类下有Configuration注解的子类
     */
    @WebMvcTest
    public static class WebMvcTestWithInnerConfigurationTest {

        /**
         * 测试mockMvc
         *
         * @param mockMvc the mock mvc
         */
        @Test
        public void testMockMvc(@Autowired MockMvc mockMvc) {
            assertNotNull(mockMvc);
        }

        /**
         * The type Inner configuration.
         */
        @Configuration
        public static class InnerConfiguration {
        }
    }

    /**
     * 使用WebMvcTest
     * 当前测试类所在的包下有SpringBootConfiguration(SpringBootApplication有包含了)注解的类
     */
    @WebMvcTest
    public static class WebMvcTestWithSpringBootConfigurationTest {

        /**
         * 测试mockMvc
         *
         * @param mockMvc the mock mvc
         */
        @Test
        public void testMockMvc(@Autowired MockMvc mockMvc) {
            assertNotNull(mockMvc);
        }

        /**
         * The type Application.
         */
        @SpringBootApplication
        public static class Application {
        }
    }

    /**
     * 使用WebMvcTest,指定了controllers
     */
    @WebMvcTest(SpringController.class)
    public static class WebMvcTestWithControllerTest {

        @MockBean
        private SpringBean springBean;

        /**
         * Test controller.
         *
         * @param springController        the spring controller
         * @param anotherSpringController the another spring controller
         */
        @Test
        public void testController(@Autowired SpringController springController, @Autowired(required = false) AnotherSpringController anotherSpringController) {
            assertNotNull(springController);
            // 因为WebMvcTest指定了controller为SpringController,所以AnotherSpringController没有被装配
            assertNull(anotherSpringController);
        }

        /**
         * 这边指定了扫包路径
         */
        @SpringBootApplication(scanBasePackageClasses = SpringController.class)
        public static class Application {
        }
    }
}
