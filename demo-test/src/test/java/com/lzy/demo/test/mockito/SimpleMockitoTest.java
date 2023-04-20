package com.lzy.demo.test.mockito;

import com.lzy.demo.test.bean.SimpleBean;
import com.lzy.demo.test.serivce.DependenceService;
import com.lzy.demo.test.serivce.impl.DependenceServiceImpl;
import com.lzy.demo.test.serivce.impl.FinalClassServiceImpl;
import com.lzy.demo.test.serivce.impl.SimpleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
     * mock, 相当于Mockito.mock, 对于mock的对象, 所有的方法默认执行的都是mock的逻辑
     *
     * @see InjectingAnnotationEngine#processInjectMocks(java.lang.Object)
     */
    @Mock
    private DependenceService dependenceService;

    /**
     * spy, 相当于Mockito.spy, 对于spy的对象, 所有的方法默认执行的都是真实的逻辑(调用原来的代码)
     * 也就是就是部分方法使用mock的真实对象
     */
    @Spy
    private DependenceService spyDependenceService = new DependenceServiceImpl();

    /**
     * 进行参数捕获, 相当于ArgumentCaptor.forClass
     */
    @Captor
    private ArgumentCaptor<SimpleBean> useCaptorAnnotation;

    /**
     * 测试 when then
     */
    @Test
    public void testWhenThen() {
        DependenceService anyMockService = mock(DependenceService.class);
        //不管参数是什么,都返回new SimpleBean("any mock")
        when(anyMockService.dependenceMethod(any())).thenReturn(new SimpleBean("any mock"));

        assertThat(anyMockService.dependenceMethod(new SimpleBean("param1")))
                .extracting(SimpleBean::getBody).isEqualTo("any mock");
        assertThat(anyMockService.dependenceMethod(new SimpleBean("param2")))
                .extracting(SimpleBean::getBody).isEqualTo("any mock");

        //使用doReturn
        DependenceService doReturnAnyMockService = mock(DependenceService.class);
        doReturn(new SimpleBean("any mock")).when(doReturnAnyMockService)
                .dependenceMethod(any());

        assertThat(doReturnAnyMockService.dependenceMethod(new SimpleBean("param2")))
                .extracting(SimpleBean::getBody).isEqualTo("any mock");

        //多次调用
        DependenceService multiCallMockService = mock(DependenceService.class);
        when(multiCallMockService.dependenceMethod(any()))
                //第一次调用返回1,第二次调用返回2,后面都是返回2
                .thenReturn(new SimpleBean("1"))
                .thenReturn(new SimpleBean("2"));

        assertThat(multiCallMockService.dependenceMethod(new SimpleBean("param1")))
                .extracting(SimpleBean::getBody).isEqualTo("1");
        assertThat(multiCallMockService.dependenceMethod(new SimpleBean("param2")))
                .extracting(SimpleBean::getBody).isEqualTo("2");
    }

    /**
     * 使用answer
     * doReturn是简单地给方法设置返回值, void的方法不适用
     * doAnswer不止可以设置返回值,还可以对方法的参数进行操作, void的方法也适用
     */
    @Test
    public void testAnswer() {
        //使用answer
        DependenceService answerMockService = mock(DependenceService.class);

        // 非void的方法可以使用when.thenAnswer
        when(answerMockService.dependenceMethod(any()))
                .thenAnswer(invocation -> {
                    //invocation可以获取到入参
                    SimpleBean simpleBean = invocation.getArgument(0);
                    //可以对入参进行操作
                    simpleBean.setBody(simpleBean.getBody() + " override");
                    //这一步相当于doReturn
                    return simpleBean;
                });

        assertThat(answerMockService.dependenceMethod(new SimpleBean("answer")))
                .extracting(SimpleBean::getBody).isEqualTo("answer override");

        // void的方法只能使用doAnswer.when
        doAnswer(invocation -> {
            Consumer<SimpleBean> consumer = invocation.getArgument(0);
            // 可以调用传进来的回调函数
            consumer.accept(new SimpleBean("invocation"));
            return null;
        }).when(answerMockService).doConsumer(any());

        List<String> list = new ArrayList<>();
        answerMockService.doConsumer(t -> list.add(t.getBody()));
        assertEquals("invocation", list.get(0));
    }

    /**
     * 测试spy,spy,相当于Mockito.spy, 对于spy的对象, 所有的方法默认执行的都是真实的逻辑(调用原来的代码)
     * 也就是就是部分方法使用mock的真实对象
     */
    @Test
    public void testSpy() {
        // 使用@Spy
        when(spyDependenceService.defaultMethod()).thenReturn("defaultMethod");
        //调用的是mock的方法
        assertEquals("defaultMethod", spyDependenceService.defaultMethod());
        //调用的是真实的方法
        assertThat(spyDependenceService.dependenceMethod(new SimpleBean("")))
                .extracting(SimpleBean::getBody).isEqualTo("real DependenceServiceImpl object");

        //使用spy
        //使用真实类创建spy,这边也可以使用真实对象
        DependenceService dependenceService = spy(DependenceServiceImpl.class);
        when(dependenceService.defaultMethod()).thenReturn("defaultMethod");
        //调用的是mock的方法
        assertEquals("defaultMethod", dependenceService.defaultMethod());
        //调用的是真实的方法
        assertThat(dependenceService.dependenceMethod(new SimpleBean("")))
                .extracting(SimpleBean::getBody).isEqualTo("real DependenceServiceImpl object");
    }

    /**
     * mock静态方法
     */
    @Test
    public void testMockStatic() {
        MockedStatic<SimpleServiceImpl> mockedStatic = mockStatic(SimpleServiceImpl.class);
        mockedStatic.when(() -> SimpleServiceImpl.staticMethod(any())).thenReturn("mock static");

        assertEquals("mock static", SimpleServiceImpl.staticMethod(new SimpleBean("real")));
    }

    /**
     * 根据入参来进行匹配return
     *
     * @see ArgumentMatchers 一些常用的matcher
     * any(): 任何值
     * any(Class<T> type): 任何此类型
     * isA(Class<T> type): 此类型的实例
     * any<Type>: anyString,anyBoolean等
     * any<Iterable>: anyList,anyMap等
     * eq(type): 相等
     * refEq: 使用反射来判断相等
     * same: 相同对象
     * null相关: isNull,notNull,isNotNull,nullable(空或者是指定的类型)
     * string相关: contains,matches(正则),endsWith,startsWith
     * argThat: 自定义判断
     * @see AdditionalMatchers 一些比较少用的matcher
     * 大小相关: gt,geq,lt,leq, cmpEq(compare相等)
     * find: 正则find
     * aryEq: 数组
     * and,or,eq: 逻辑判断
     */
    @Test
    public void testMatchReturn() {
        SimpleBean matchSimpleBean = new SimpleBean("match mock");
        DependenceService preciseMockService = mock(DependenceService.class);
        // 当真正的入参跟这里的入参一样时(equals为true), 才会返回这边的return
        when(preciseMockService.dependenceMethod(matchSimpleBean)).thenReturn(matchSimpleBean);

        assertThat(preciseMockService.dependenceMethod(matchSimpleBean))
                .extracting(SimpleBean::getBody).isEqualTo("match mock");
        //入参跟when不一样,则只能返回null
        assertNull(preciseMockService.dependenceMethod(new SimpleBean("param")));
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
        verify(dependenceService, times(1)).dependenceMethod(param);

        //表示不判断参数
        verify(dependenceService).dependenceMethod(any());

        //判断调用顺序
        InOrder inOrder = Mockito.inOrder(dependenceService);
        inOrder.verify(dependenceService).dependenceMethod(param);
        inOrder.verify(dependenceService).defaultMethod();
    }

    /**
     * 验证无返回的方法, 使用captor,用来捕获入参
     */
    @Test
    public void testArgumentCaptor() {
        SimpleBean simpleBean1 = new SimpleBean("captor");
        SimpleBean simpleBean2 = new SimpleBean("captor");

        dependenceService.defaultNoReturn(simpleBean1);
        dependenceService.defaultNoReturn(simpleBean2);
        //使用@Captor
        verify(dependenceService, times(2)).defaultNoReturn(useCaptorAnnotation.capture());
        //使用ArgumentCaptor
        ArgumentCaptor<SimpleBean> argumentCaptor = ArgumentCaptor.forClass(SimpleBean.class);
        verify(dependenceService, times(2)).defaultNoReturn(argumentCaptor.capture());

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
     * 需要在添加resource/mockito-extensions/org.plugins.MockMaker这个文件
     * 文件的内容为mock-maker-inline
     */
    @Test
    public void testFinal() {
        FinalClassServiceImpl finalClassServiceImpl = mock(FinalClassServiceImpl.class);
        when(finalClassServiceImpl.finalMethod()).thenReturn("mock");
        assertEquals("mock", finalClassServiceImpl.finalMethod());
    }

    /**
     * 测试回调
     */
    @Test
    public void testCallback() {
        ArgumentCaptor<Consumer<SimpleBean>> argumentCaptor = ArgumentCaptor.forClass(Consumer.class);
        //这边会去调用DependenceService#doConsumer(Consumer)
        simpleService.doConsumer();

        //捕获SimpleServiceImpl传递给DependenceService的Consumer
        verify(dependenceService).doConsumer(argumentCaptor.capture());

        SimpleBean simpleBean = new SimpleBean(null);

        // 获取到传递给dependenceService.doConsumer的参数
        Consumer<SimpleBean> consumer = argumentCaptor.getValue();
        // 手动执行Consumer
        consumer.accept(simpleBean);

        assertEquals("real SimpleServiceImpl", simpleBean.getBody());
    }

    /**
     * 测试 @InjectMocks
     */
    @Test
    public void testInjectMocks() {
        when(dependenceService.dependenceMethod(any())).thenReturn(new SimpleBean("mock"));
        //SimpleServiceImpl依赖的dependenceService已经是Mock的了,所以这边返回的是mock而不是
        assertThat(simpleService.simpleMethod(new SimpleBean("simpleService")))
                .extracting(SimpleBean::getBody).isEqualTo("mock");
    }
}
