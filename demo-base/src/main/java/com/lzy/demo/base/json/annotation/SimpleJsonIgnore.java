package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * 用@JsonIgnoreProperties(allowGetters为true表示允许序列化,allowSetters表示允许反序列化)或者@JsonIgnore(注解在方法上,可以控制序列化和反序列化)来忽略属性
 * 用@JsonIgnoreType直接将某个类型忽略序列化和反序列化
 * 适用于序列化和反序列化忽略属性
 *
 * @author lzy
 * @version v1.0
 */
@JsonIgnoreProperties(value = {"property1"})
public class SimpleJsonIgnore {
    public SimpleJsonIgnore() {
    }

    public SimpleJsonIgnore(String property1, String property2) {
        this.property1 = property1;
        this.property2 = property2;
        this.name = new Name("demo", "demo");
    }

    private String property1;

    //注解在方法上,可以控制序列化和反序列化
    @JsonIgnore
    private String property2;

    private Name name;


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

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    /**
     * 此类型将不会被序列化和反序列化
     */
    @JsonIgnoreType
    public static class Name {
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }
}
