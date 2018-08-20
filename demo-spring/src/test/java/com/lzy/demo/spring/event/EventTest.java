/*
 * Created by lzy on 2018/8/6 9:05 PM.
 */
package com.lzy.demo.spring.event;

import com.lzy.demo.spring.event.custom.SampleApplicationContextAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * The type Event test.
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest
//开启异步
@EnableAsync
public class EventTest {

    @Resource
    private SampleApplicationContextAware sampleApplicationContextAware;

    /**
     * 测试标准事件
     */
    @Test
    public void testStandardEvent() {
    }

    /**
     * 测试自定义事件
     */
    @Test
    public void testCustomEvent() {
        sampleApplicationContextAware.sendEvent("sampleEvent");
        System.out.println("test end");
    }
}
