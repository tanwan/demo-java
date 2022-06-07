package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * 使用@JsonUnwrapped将该字段的属性添加到外层的属性
 * 适用于json字段是平级的,bean是分级的
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonUnwrapped {

    private String property;

    @JsonUnwrapped
    private UnwrappedType unwrappedType;

    public SimpleJsonUnwrapped() {

    }

    public SimpleJsonUnwrapped(String property) {
        this.property = property;
        this.unwrappedType = new UnwrappedType("subProperty");
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public UnwrappedType getUnwrappedType() {
        return unwrappedType;
    }

    public void setUnwrappedType(UnwrappedType unwrappedType) {
        this.unwrappedType = unwrappedType;
    }

    public static class UnwrappedType {
        private String subProperty;

        public UnwrappedType() {
        }

        public UnwrappedType(String subProperty) {
            this.subProperty = subProperty;
        }

        public String getSubProperty() {
            return subProperty;
        }

        public void setSubProperty(String subProperty) {
            this.subProperty = subProperty;
        }
    }
}
