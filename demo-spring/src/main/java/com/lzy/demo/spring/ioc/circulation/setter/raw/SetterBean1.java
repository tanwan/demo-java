/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
 * Created by lzy on 2018/10/10 1:07 PM.
 */
package com.lzy.demo.spring.ioc.circulation.setter.raw;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 注入循环依赖
 *
 * @author lzy
 * @version v1.0
 */
@Component
public class SetterBean1 {

    @Resource
    private SetterBean2 setterBean2;
}
