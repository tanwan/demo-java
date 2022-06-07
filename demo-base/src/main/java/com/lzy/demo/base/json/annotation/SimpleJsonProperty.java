package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * {@code @JsonProperty},{@code @JsonGetter},{@code @JsonSetter}适用于key跟bean的属性不一致
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonProperty {

    public SimpleJsonProperty(String property1, String property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public SimpleJsonProperty() {
    }

    /**
     * 序列化将property1改写为renameProperty1,反序列化将renameProperty1设置到property1
     */
    @JsonProperty("renameProperty1")
    private String property1;

    private String property2;


    public String getProperty1() {
        return property1;
    }


    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    /**
     * 序列化将getProperty2改写为renameGetProperty2,适用于序列化跟反序列化key不一样的场景,否则可以直接使用@JsonProperty
     *
     * @return property 2
     */
    @JsonGetter("renameGetProperty2")
    public String getProperty2() {
        return property2;
    }

    /**
     * 反序列化将renameSetProperty2设置到property2,适用于序列化跟反序列化key不一样的场景,否则可以直接使用@JsonProperty
     *
     * @param property2 the property 2
     */
    @JsonSetter("renameSetProperty2")
    public void setProperty2(String property2) {
        this.property2 = property2;
    }
}
