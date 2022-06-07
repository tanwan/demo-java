package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code @JsonAnySetter}适用于统一将未匹配的字段放到map里
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonAny {

    private String name = "simpleJsonAny";

    private Map<String, String> others = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 反序列化时,将未匹配上的值放到这里
     *
     * @param key   the key
     * @param value the value
     */
    @JsonAnySetter
    public void add(String key, String value) {
        others.put(key, value);
    }

    /**
     * 序列化时,将others放到对象的属性中去
     *
     * @return the others
     */
    @JsonAnyGetter
    public Map<String, String> getOthers() {
        return others;
    }

}
