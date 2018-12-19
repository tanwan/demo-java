/*
 * Created by lzy on 2018/8/7 11:20 AM.
 */
package com.lzy.demo.spring.event.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 自定义事件
 *
 * @author lzy
 * @version v1.0
 */
@AllArgsConstructor
@ToString
@Getter
public class SampleEvent {

    private String body;
}
