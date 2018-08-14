/*
 * Created by lzy on 2018/8/9 9:57 AM.
 */
package com.lzy.demo.spring.event.standard;

import org.springframework.context.event.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * spring标准事件
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class StandardEventListener {


    /**
     * ApplicationContext调用start()方法时触发该事件,spring boot的SpringApplication#run()不会调用start方法
     * 手动调用SpringApplication.run().start()
     *
     * @param event the event to respond to
     */
    @EventListener(ContextStartedEvent.class)
    public void contextStartedEvent(ContextStartedEvent event) {
        System.out.println("contextStartedEvent:" + event);
    }

    /**
     * 在容器初始化或者ApplicationContext调用refresh()方法时触发该事件
     *
     * @param event the event to respond to
     */
    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent(ContextRefreshedEvent event) {
        System.out.println("contextRefreshedEvent:" + event);
    }

    /**
     * ApplicationContext调用stop()方法时触发该事件,调用stop后还可以调用start
     *
     * @param event the event to respond to
     */
    @EventListener(ContextStoppedEvent.class)
    public void contextStoppedEvent(ContextStoppedEvent event) {
        System.out.println("contextStoppedEvent:" + event);
    }


    /**
     * ApplicationContext调用close()方法时触发该事件,表示容器生命周期结束,无法再调用start
     *
     * @param event the event to respond to
     */
    @EventListener(ContextClosedEvent.class)
    public void contextClosedEvent(ContextClosedEvent event) {
        System.out.println("contextClosedEvent:" + event);
    }


    /**
     * spring mvc请求经过DispatcherServlet就会触发该事件
     *
     * @param event the event to respond to
     */
    @EventListener(RequestHandledEvent.class)
    public void requestHandledEvent(RequestHandledEvent event) {
        System.out.println("requestHandledEvent:" + event);
    }
}
