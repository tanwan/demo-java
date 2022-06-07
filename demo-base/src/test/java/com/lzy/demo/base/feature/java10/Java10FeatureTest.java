package com.lzy.demo.base.feature.java10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Java10FeatureTest {

    /**
     * 测试var,var只能用在局部变量,for循环,不能用在入参,出参,属性
     */
    @Test
    public void testVar() {
        //使用类型推断
        var string = "string";
        var list = new ArrayList<Integer>();
        list.add(1);
        //不指定泛型,默认为Object
        var noType = new ArrayList<>();
        noType.add("1");
        noType.add(2);
        noType.stream().forEach(System.out::print);
    }
}
