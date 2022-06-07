package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 序列化进行排序,可以自定义指定顺序,也可以alphabetic属性使用字母顺序
 *
 * @author lzy
 * @version v1.0
 */
@JsonPropertyOrder(value = {"property2", "property1"})
public class SimpleJsonPropertyOrder {

    public SimpleJsonPropertyOrder(String property1, String property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public SimpleJsonPropertyOrder() {
    }

    private String property1;

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
