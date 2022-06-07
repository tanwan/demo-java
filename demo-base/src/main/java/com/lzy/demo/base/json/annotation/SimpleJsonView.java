package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 通过@JsonView控制序列化反序列化
 * 适用于通过级别控制是否序列化和反序列化,可以跟spring mvc配合使用
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleJsonView {

    @JsonView(Low.class)
    private String property1;

    @JsonView(Middle.class)
    private String property2;

    @JsonView(High.class)
    private String property3;

    public SimpleJsonView(String property1, String property2, String property3) {
        this.property1 = property1;
        this.property2 = property2;
        this.property3 = property3;
    }

    public SimpleJsonView() {
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

    public String getProperty3() {
        return property3;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    public static class Low {
    }

    public static class Middle extends Low {
    }

    public static class High extends Middle {
    }

}
