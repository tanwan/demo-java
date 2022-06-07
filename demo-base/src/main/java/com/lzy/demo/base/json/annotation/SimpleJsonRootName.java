package com.lzy.demo.base.json.annotation;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * 为json包裹一层,需要SerializationFeature.WRAP_ROOT_VALUE
 * {code @JsonRootName}用来指定外层key的名称,默认为类名
 *
 * @author lzy
 * @version v1.0
 */
@JsonRootName(value = "simpleJsonRootName")
public class SimpleJsonRootName {
    public SimpleJsonRootName(String property) {
        this.property = property;
    }

    public SimpleJsonRootName() {
    }

    private String property;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
}
