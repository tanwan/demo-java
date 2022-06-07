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
public class SimpleEvent {

    private String body;
}
