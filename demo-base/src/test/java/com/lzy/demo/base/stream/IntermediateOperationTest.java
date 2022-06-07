package com.lzy.demo.base.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

public class IntermediateOperationTest {


    /**
     * 测试skip,忽略指定数量的元素
     */
    @Test
    public void testSkip() {
        Stream.of(1, 1, 2, 3, 3, 4).skip(2).forEach(System.out::println);
    }

    /**
     * 测试filter,过滤流中的元素
     */
    @Test
    public void testFilter() {
        Stream.of(1, 2, 3, 4, 5).filter(x -> x > 3).forEach(System.out::println);
    }

    /**
     * 测试limit,限制返回流的个数
     */
    @Test
    public void testLimit() {
        Stream.of(1, 1, 2, 3, 3, 4).limit(2).forEach(System.out::println);
    }

    /**
     * 测试map,对流中的元素进行映射
     */
    @Test
    public void testMap() {
        //将小写转换成大写
        Stream.of("a", "b", "c").map(String::toUpperCase).forEach(System.out::print);
        System.out.println();
        //特殊的map
        //mapToInt,mapToLong,mapToDouble
        Stream.of("1", "2", "3")
                .mapToInt(Integer::valueOf)
                .mapToObj(x -> "hello world " + x)
                .forEach(System.out::println);
    }

    /**
     * 测试flatMap,先对给定的元素进行map处理,然后把结果进行合并
     */
    @Test
    public void testFlatMap() {
        //先对Arrays.asList(1, 2, 3), Arrays.asList(4, 5)进行 .stream()操作,然后把得到的结果合成一个结果
        Stream.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5))
                .flatMap(numList -> numList.stream())
                .forEach(System.out::print);

        //特殊的flatMap
        //flatMapToInt,flatMapToLong,flatMapToDouble
        Stream.of(Arrays.asList("1", "2"), Arrays.asList("3", "4"))
                .flatMapToInt(list -> list.stream().mapToInt(Integer::valueOf))
                .forEach(System.out::print);
    }

    /**
     * 测试sort,排序
     */
    @Test
    public void testSort() {
        //使用顺序流
        Stream.of(5, 4, 3, 1, 2).sorted().forEach(System.out::print);
        System.out.println();

        //使用并行流
        //forEachOrdered在并行流中顺序输出
        Stream.of(5, 4, 3, 1, 2).parallel().sorted().forEachOrdered(System.out::print);
        System.out.println();

        //自定义Comparator方法
        Stream.of(5, 4, 3, 1, 2).sorted(Integer::compareTo).forEach(System.out::print);
        System.out.println();
    }

    /**
     * 测试peek,对流中的元素进行处理
     */
    @Test
    public void testPeek() {
        System.out.println("count: " + Stream.of(1, 1, 2, 3, 3, 4).peek(System.out::println).count());
    }

    /**
     * 测试distinct,对元素进行去重
     */
    @Test
    public void testDistinct() {
        Stream.of(1, 1, 2, 3, 3, 4).distinct().forEach(System.out::println);
    }
}
