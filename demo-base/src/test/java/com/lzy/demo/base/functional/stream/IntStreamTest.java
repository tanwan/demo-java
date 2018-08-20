/*
 * Created by lzy on 2018/8/20 2:05 PM.
 */
package com.lzy.demo.base.functional.stream;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * IntStream的测试
 *
 * @author lzy
 * @version v1.0
 * @see java.util.stream.IntStream
 */
public class IntStreamTest {

    /**
     * 测试range
     */
    @Test
    public void testRange() {
        //从0到9
        IntStream.range(0, 10).forEach(System.out::print);
    }

    /**
     * 测试builder
     */
    @Test
    public void testBuilder() {
        IntStream.builder().add(0).add(3).add(5).build().forEach(System.out::println);
    }

    /**
     * 测试average
     */
    @Test
    public void testAverage() {
        IntStream.range(1, 11).average().ifPresent(System.out::println);
    }

    /**
     * 测试sum
     */
    @Test
    public void testSum() {
        //sum
        System.out.println(IntStream.range(1, 10).sum());
    }
}
