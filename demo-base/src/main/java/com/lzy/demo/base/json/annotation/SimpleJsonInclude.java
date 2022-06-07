package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Include.ALWAYS:序列化所有字段(默认值)
 * Include.NON_NULL:只序列化非空字段
 *
 * @author lzy
 * @version v1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleJsonInclude {

    private String property1;

    private String property2;


    public SimpleJsonInclude(String property1) {
        this.property1 = property1;
    }

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
