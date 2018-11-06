/*
 * Created by lzy on 2018/10/29 2:50 PM.
 */
package com.lzy.demo.spring.ioc.lazy.declarelazy;

import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 只有声明加@Lazy
 *
 * @author lzy
 * @version v1.0
 */
@Lazy
@Component
public class DeclareLazyBean implements InitializingBean {

    public DeclareLazyBean() {
        System.out.println("DeclareLazyBean()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DeclareLazyBean#afterPropertiesSet()");
    }
}
