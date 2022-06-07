package com.lzy.demo.base.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TerminalOperationTest {

    /**
     * 测试find
     */
    @Test
    public void testFind() {
        //返回流中的第一个元素
        Stream.of(4, 2, 1, 3, 4).parallel().findFirst().ifPresent(System.out::println);
        //返回流中的一个元素
        Stream.of(4, 2, 1, 3, 4).parallel().findAny().ifPresent(System.out::println);
    }

    /**
     * 测试max
     */
    @Test
    public void testMax() {
        Stream.of(1, 2, 3, 4).max(Integer::compare).ifPresent(System.out::println);
    }

    /**
     * 测试min
     */
    @Test
    public void testMin() {
        Stream.of(1, 2, 3, 4).min(Integer::compare).ifPresent(System.out::println);
    }

    /**
     * 测试match
     */
    @Test
    public void testMatch() {
        //所有值都大于0
        System.out.println(Stream.of(1, 2, 3, 4, 5).allMatch(x -> x > 0));
        //有任意一个值大于3
        System.out.println(Stream.of(1, 2, 3, 4, 5).anyMatch(x -> x > 3));
        //没有值都大于0
        System.out.println(Stream.of(1, 2, 3, 4, 5).noneMatch(x -> x > 5));
    }

    /**
     * 测试reduce,把一组值合成一个值
     */
    @Test
    public void testReduce() {
        //无初始值
        Stream.of(5, 10, 15, 20)
                .reduce((sum, x) -> sum + x).ifPresent(System.out::println);

        //有初始值
        System.out.println(Stream.of(5, 10, 15, 20)
                .reduce(5, (sum, x) -> sum + x));

        //第三个参数在并发流才有效果
        //并发流使用fork join
        //每个子任务在计算的时候使用第二个参数
        //子任务在合成结果的时候,使用第三个参数
        System.out.println(IntStream.range(1, 100).parallel().boxed()
                .reduce(0, (sum, x) -> sum + x,
                        (left, right) -> left + right
                ));
    }
}
