package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 使用@JsonCreator标注构造函数
 * 适用于没有默认构造函数
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonCreator {

    private String property;


    @JsonCreator
    public SimpleJsonCreator(@JsonProperty("property") String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
