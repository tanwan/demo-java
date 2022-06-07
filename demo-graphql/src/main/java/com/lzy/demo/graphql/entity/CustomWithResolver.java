package com.lzy.demo.graphql.entity;

import com.lzy.demo.graphql.resolver.CustomResolver;

/**
 * 这个类不需要有方法,因为会通过CustomResolver去执行
 *
 * @author lzy
 * @version v1.0
 * @see CustomResolver
 */
public class CustomWithResolver {

    private String value;

    public CustomWithResolver(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
