package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * 使用@JsonTypeInfo,@JsonSubTypes,@JsonTypeName进行子类序列化,反序列化
 * 适用于多子类
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonType {
    private Parent subType;

    public SimpleJsonType() {

    }

    public SimpleJsonType(final Parent subType) {
        this.subType = subType;
    }

    public Parent getSubType() {
        return subType;
    }

    public void setSubType(Parent subType) {
        this.subType = subType;
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
    @JsonSubTypes({@JsonSubTypes.Type(value = Sub1.class, name = "sub1"), @JsonSubTypes.Type(value = Sub2.class, name = "sub2")})
    public static class Parent {
        private String name;

        public Parent() {
        }

        public Parent(final String name) {
            this.name = name;
        }
    }

    @JsonTypeName("sub1")
    public static class Sub1 extends Parent {
        public Sub1(String name) {
            super(name);
        }

        public Sub1() {
        }
    }

    @JsonTypeName("sub2")
    public static class Sub2 extends Parent {
        public Sub2() {
        }

        public Sub2(String name) {
            super(name);
        }
    }
}
