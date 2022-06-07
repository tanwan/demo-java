package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 当序列化此类时,则使用@JsonValue注解的方法返回序列化的值
 * 适用于枚举序列化
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonValue {

    //序列化时,SimpleJsonValueEnum的序列化结果为@JsonValue注解方法的返回值
    SimpleJsonValueEnum simpleJsonValueEnum;

    public SimpleJsonValueEnum getSimpleJsonValueEnum() {
        return simpleJsonValueEnum;
    }

    public void setSimpleJsonValueEnum(SimpleJsonValueEnum simpleJsonValueEnum) {
        this.simpleJsonValueEnum = simpleJsonValueEnum;
    }

    public enum SimpleJsonValueEnum {
        SIMPLE_JSON_VALUE("key", "value"),
        SIMPLE_JSON_VALUE2("key2", "value");

        private String key;
        private String value;

        SimpleJsonValueEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        /**
         * 一个类只能有一个@JsonValue
         *
         * @return value
         */
        @JsonValue
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
