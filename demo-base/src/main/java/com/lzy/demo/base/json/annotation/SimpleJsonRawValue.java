package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * 适用于字段有json字段的
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonRawValue {

    public SimpleJsonRawValue(String property1, String property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    public SimpleJsonRawValue() {
    }


    private String property1;

    /**
     * property2是json的话,就直接使用json,而不是字符串,比如:{"property1":"{\"attr\":false}","property2":{"attr":false}}
     */
    @JsonRawValue
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
