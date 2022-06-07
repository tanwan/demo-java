package com.lzy.demo.spring.boot.properties;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ToString
public class AtValue {
    /**
     * 整型
     */
    @Value("${configuration.properties.integer}")
    private Integer integer;
    /**
     * double型,无法松散绑定
     */
    @Value("${configuration.properties.a-double}")
    private Double aDouble;
    /**
     * str类型
     */
    @Value("${configuration.properties.str}")
    private String str;

    /**
     * list2: list1,list2,使用spel
     */
    @Value("#{'${configuration.properties.list2}'.split(',')}")
    private List<String> list2;

    /**
     * spel表达式
     */
    @Value("#{3+3}")
    private Integer spel;
}
