package com.lzy.demo.test.mockito;

import com.lzy.demo.test.bean.SimpleBean;
import com.lzy.demo.test.serivce.DependenceService;
import com.lzy.demo.test.serivce.impl.DependenceServiceImpl;
import com.lzy.demo.test.serivce.impl.FinalClassServiceImpl;
import com.lzy.demo.test.serivce.impl.SimpleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.configuration.InjectingAnnotationEngine;
import org.mockito.internal.configuration.injection.PropertyAndSetterInjection;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class SimpleMockitoTest {

    /**
     * 会将@Mock注解的类注入到SimpleServiceImpl
     *
     * @see PropertyAndSetterInjection#processInjection(java.lang.reflect.Field, java.lang.Object, java.util.Set)
     */
    @InjectMocks
    private SimpleServiceImpl simpleService;

    /**
     * 对DependenceService进行mock
     *
     * @see InjectingAnnotationEngine#processInjectMocks(java.lang.Object)
     */
    @Mock
    private DependenceService dependenceService;

    /**
     * 对DependenceServiceImpl进行spy
     */
    @Spy
    private DependenceService spyDependenceService = new DependenceServiceImpl();

    /**
     * 进行参数捕获
     */
    @Captor
    private ArgumentCaptor<SimpleBean> useCaptorAnnotation;

    /**
     * 测试 when then
     */
    @Test
    public void testWhenThen() {
        DependenceService anyMockService = Mockito.mock(DependenceService.class);
        //不管参数是什么,都返回new SimpleBean("any mock")
        Mockito.when(anyMockService.dependenceMethod(Mockito.any())).thenReturn(new SimpleBean("any mock"));
        assertThat(anyMockService.dependenceMethod(new SimpleBean("param1")))
                .extracting(SimpleBean::getBody).isEqualTo("any mock");
        assertThat(anyMockService.dependenceMethod(new SimpleBean("param2")))
                .extracting(SimpleBean::getBody).isEqualTo("any mock");

        //使用doReturn
        DependenceService doReturnAnyMockService = Mockito.mock(DependenceService.class);
        Mockito.doReturn(new SimpleBean("any mock")).when(doReturnAnyMockService)
                .dependenceMethod(Mockito.any());
        assertThat(doReturnAnyMockService.dependenceMethod(new SimpleBean("param2")))
                .extracting(SimpleBean::getBody).isEqualTo("any mock");

        //精确的返回
        SimpleBean preciseSimpleBean = new SimpleBean("precise mock");
        DependenceService preciseMockService = Mockito.mock(DependenceService.class);
        Mockito.when(preciseMockService.dependenceMethod(preciseSimpleBean)).thenReturn(preciseSimpleBean);
        assertThat(preciseMockService.dependenceMethod(preciseSimpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("precise mock");
        //入参跟when不一样,则只能返回null
        assertNull(preciseMockService.dependenceMethod(new SimpleBean("param")));

        //多次调用
        DependenceService multiCallMockService = Mockito.mock(DependenceService.class);
        Mockito.when(multiCallMockService.dependenceMethod(Mockito.any()))
                //第一次调用返回1,第二次调用返回2,后面都是返回2
                .thenReturn(new SimpleBean("1"))
                .thenReturn(new SimpleBean("2"));
        assertThat(multiCallMockService.dependenceMethod(preciseSimpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("1");
        assertThat(multiCallMockService.dependenceMethod(preciseSimpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("2");


        //使用answer
        DependenceService answerMockService = Mockito.mock(DependenceService.class);
        Mockito.when(answerMockService.dependenceMethod(Mockito.any()))
                //invocation可以获取到入参
                .thenAnswer(invocation -> invocation.getArgument(0));
        assertThat(answerMockService.dependenceMethod(new SimpleBean("answer")))
                .extracting(SimpleBean::getBody).isEqualTo("answer");
    }

    /**
     * 测试verify,verify用来判断方法有没有被调用,被调用了几次
     *
     * @param dependenceService the dependence service
     */
    @Test
    public void testVerify(@Mock DependenceService dependenceService) {
        SimpleBean param = new SimpleBean("testVerify");
        //如果参数不一样,会判断为不一样的调用
        dependenceService.dependenceMethod(param);
        dependenceService.defaultMethod();
        //判断执行一次
        Mockito.verify(dependenceService, Mockito.times(1)).dependenceMethod(param);

        //表示不判断参数
        Mockito.verify(dependenceService).dependenceMethod(Mockito.any());

        //判断调用顺序
        InOrder inOrder = Mockito.inOrder(dependenceService);
        inOrder.verify(dependenceService).dependenceMethod(param);
        inOrder.verify(dependenceService).defaultMethod();
    }


    /**
     * 测试spy,spy就是部分方法使用mock的真实对象
     */
    @Test
    public void testSpy() {
        // 使用@Spy
        Mockito.when(spyDependenceService.defaultMethod()).thenReturn("defaultMethod");
        //调用的是mock的方法
        assertEquals("defaultMethod", spyDependenceService.defaultMethod());
        //调用的是真实的方法
        assertThat(spyDependenceService.dependenceMethod(new SimpleBean("")))
                .extracting(SimpleBean::getBody).isEqualTo("real DependenceServiceImpl object");

        //使用Mockito.spy
        //使用真实类创建spy,这边也可以使用真实对象
        DependenceService dependenceService = Mockito.spy(DependenceServiceImpl.class);
        Mockito.when(dependenceService.defaultMethod()).thenReturn("defaultMethod");
        //调用的是mock的方法
        assertEquals("defaultMethod", dependenceService.defaultMethod());
        //调用的是真实的方法
        assertThat(dependenceService.dependenceMethod(new SimpleBean("")))
                .extracting(SimpleBean::getBody).isEqualTo("real DependenceServiceImpl object");
    }


    /**
     * 测试 @InjectMocks
     */
    @Test
    public void testInjectMocks() {
        Mockito.when(dependenceService.dependenceMethod(Mockito.any())).thenReturn(new SimpleBean("mock"));
        //SimpleServiceImpl依赖的dependenceService已经是Mock的了,所以这边返回的是mock而不是
        assertThat(simpleService.simpleMethod(new SimpleBean("simpleService")))
                .extracting(SimpleBean::getBody).isEqualTo("mock");
    }

    /**
     * 测试captor,用来捕获入参,可以用来验证无返回的方法
     */
    @Test
    public void testArgumentCaptor() {
        SimpleBean simpleBean1 = new SimpleBean("captor");
        SimpleBean simpleBean2 = new SimpleBean("captor");

        dependenceService.defaultNoReturn(simpleBean1);
        dependenceService.defaultNoReturn(simpleBean2);
        //使用@Captor
        Mockito.verify(dependenceService, Mockito.times(2)).defaultNoReturn(useCaptorAnnotation.capture());
        //使用ArgumentCaptor
        ArgumentCaptor<SimpleBean> argumentCaptor = ArgumentCaptor.forClass(SimpleBean.class);
        Mockito.verify(dependenceService, Mockito.times(2)).defaultNoReturn(argumentCaptor.capture());

        //校验所有的参数
        assertThat(useCaptorAnnotation.getAllValues()).contains(simpleBean1, simpleBean2);
        //校验最后一次的参数
        assertEquals(simpleBean2, useCaptorAnnotation.getValue());

        //校验所有的参数
        assertThat(argumentCaptor.getAllValues()).contains(simpleBean1, simpleBean2);
        //校验最后一次的参数
        assertEquals(simpleBean2, argumentCaptor.getValue());
    }


    /**
     * mock final类和方法
     * 需要在添加resource/mockito-extensions/org.mockito.plugins.MockMaker这个文件
     * 文件的内容为mock-maker-inline
     */
    @Test
    public void testFinal() {
        FinalClassServiceImpl finalClassServiceImpl = Mockito.mock(FinalClassServiceImpl.class);
        Mockito.when(finalClassServiceImpl.finalMethod()).thenReturn("mock");
        assertEquals("mock", finalClassServiceImpl.finalMethod());
    }


    /**
     * 测试回调,这边使用Consumer模拟callback
     */
    @Test
    public void testCallback() {
        ArgumentCaptor<Consumer<SimpleBean>> argumentCaptor = ArgumentCaptor.forClass(Consumer.class);
        //这边会去调用DependenceService#doConsumer(Consumer)
        simpleService.doConsumer();
        //捕获SimpleServiceImpl传递给DependenceService的Consumer
        Mockito.verify(dependenceService).doConsumer(argumentCaptor.capture());

        SimpleBean simpleBean = new SimpleBean(null);
        //这边相当于手动执行了一次callback
        argumentCaptor.getValue().accept(simpleBean);

        assertEquals("real SimpleServiceImpl", simpleBean.getBody());
    }

}
