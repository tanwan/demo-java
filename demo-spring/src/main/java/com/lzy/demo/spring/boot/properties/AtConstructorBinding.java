package com.lzy.demo.spring.boot.properties;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

/**
 * 使用@ConstructorBinding, 无需setter方法，直接在构造器注入
 *
 * @author lzy
 * @version v1.0
 */
@ConfigurationProperties("configuration.properties")
@Validated
@Getter
@ToString
public class AtConstructorBinding {

    private Integer integer;

    private Double aDouble;

    private String str;

    @Value("${configuration.properties.actual-value}")
    private String value;

    @ConstructorBinding
    public AtConstructorBinding(Integer integer, Double aDouble, String str) {
        this.integer = integer;
        this.aDouble = aDouble;
        this.str = str;
    }
}
