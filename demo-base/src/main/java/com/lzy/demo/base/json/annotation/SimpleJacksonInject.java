package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JacksonInject;

/**
 * {@code @JacksonInject}可以在反序列化的时候添加值
 * 适用于需要额外添加值
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJacksonInject {
    private String property1;
    @JacksonInject(value = "property2")
    private String property2;


    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }
}
