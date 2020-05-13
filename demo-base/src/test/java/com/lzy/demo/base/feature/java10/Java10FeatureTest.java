/*
 * Created by lzy on 2020/5/13 8:19 AM.
 */
package com.lzy.demo.base.feature.java10;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * java10测试
 * @author lzy
 * @version v1.0
 */
public class Java10FeatureTest {

    /**
     * 测试var,var只能用在局部变量,for循环,不能用在入参,出参,属性
     */
    @Test
    public void testVar(){
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
