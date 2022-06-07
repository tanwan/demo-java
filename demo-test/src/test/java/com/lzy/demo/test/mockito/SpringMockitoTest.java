package com.lzy.demo.test.mockito;

import com.lzy.demo.test.bean.SimpleBean;
import com.lzy.demo.test.serivce.DependenceService;
import com.lzy.demo.test.serivce.impl.SimpleServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SpringMockitoTest {


    /**
     * 使用MockBean可以直接将对象Mock出来,然后添加到spring容器中,不能与@Mock共用
     * 还有一种办法是手动将这些Mock对象声明成Spring bean,然后添加@Primary
     */
    @MockBean(name = "dependenceService")
    private DependenceService dependenceService;

    /**
     * 无法注入@MockBean,所以还是需要添加@Mock
     */
    @InjectMocks
    private SimpleServiceImpl simpleService;


    @Mock
    private DependenceService mockDependenceService;


    /**
     * Test mock bean.
     *
     * @param applicationContext the application context
     */
    @Test
    public void testMockBean(ApplicationContext applicationContext) {
        DependenceService dependenceServiceFromSpring = applicationContext.getBean("dependenceService", DependenceService.class);

        SimpleBean simpleBean = new SimpleBean("mock");
        Mockito.when(dependenceServiceFromSpring.dependenceMethod(Mockito.any()))
                .thenReturn(simpleBean);
        Mockito.when(mockDependenceService.dependenceMethod(Mockito.any()))
                .thenReturn(simpleBean);

        Assertions.assertThat(dependenceServiceFromSpring.dependenceMethod(simpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("mock");


        Assertions.assertThat(dependenceService.dependenceMethod(simpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("mock");

        Assertions.assertThat(simpleService.simpleMethod(simpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("mock");


    }
}
