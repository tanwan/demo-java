package com.lzy.demo.base.feature.java8.stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectTest {

    /**
     * 测试Collectors#toCollection(),转换成自定义集合
     */
    @Test
    public void testToCollection() {
        LinkedList<Integer> linkedList = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(linkedList);
    }

    /**
     * 测试Collectors#toList(),转换成List
     */
    @Test
    public void testToList() {
        List<Integer> list = Stream.of("1", "1", "2", "3").map(Integer::valueOf)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 测试Collectors#toSet(),转换成Set
     */
    @Test
    public void testToSet() {
        Set<Integer> set = Stream.of("1", "2", "3", "3").map(Integer::valueOf)
                .collect(Collectors.toSet());
        System.out.println(set);
    }

    /**
     * 测试Collectors#toMap(),转换成Map
     */
    @Test
    public void testToMap() {
        Map<Integer, String> map = Stream.of("1", "2", "3")
                .collect(Collectors.toMap(Integer::valueOf, value -> value));
        System.out.println(map);
    }

    /**
     * 测试Collectors#toConcurrentMap(),转换成ConcurrentHashMap
     */
    @Test
    public void testToConcurrentMap() {
        Map<Integer, String> concurrentMap = Stream.of("1", "2", "3")
                .collect(Collectors.toConcurrentMap(Integer::valueOf, value -> value));
        System.out.println(concurrentMap);
    }

    /**
     * 测试Collectors#joining(),把流中的元素拼接成字符串
     */
    @Test
    public void testJoining() {
        String str = Stream.of(1, 2, 3).map(Object::toString)
                .collect(Collectors.joining(","));
        System.out.println(str);
        str = Stream.of(1, 2, 3).map(Object::toString)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(str);
    }

    /**
     * 测试Collectors.mapping(),对流中的元素进行映射
     */
    @Test
    public void testMapping() {
        //可以跟groupingBy配合使用
        Map<Integer, List<String>> mappingList = Stream.of(1, 2, 3)
                .collect(Collectors.groupingBy(i -> i % 2,
                        Collectors.mapping(Object::toString, Collectors.toList())));
        System.out.println(mappingList);
    }


    /**
     * 测试Collectors#collectingAndThen(),可以对创建完的集合做处理
     */
    @Test
    public void testCollectingAndThen() {
        List<Integer> collectingAndThenList = Stream.of(1, 2, 3)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        System.out.println(collectingAndThenList);
    }


    /**
     * 测试Collectors#summingInt(),求总和
     *
     * @see Collectors#summingInt(ToIntFunction)
     * @see Collectors#summingLong(ToLongFunction)
     * @see Collectors#summarizingDouble(ToDoubleFunction)
     */
    @Test
    public void testSummingInt() {
        Integer sum = Stream.of("1", "2", "3", "4")
                .collect(Collectors.summingInt(Integer::valueOf));
        System.out.println("sum: " + sum);
    }

    /**
     * 测试Collectors#averagingInt(),求平均值
     *
     * @see Collectors#averagingInt(ToIntFunction)
     * @see Collectors#averagingLong(ToLongFunction)
     * @see Collectors#averagingDouble(ToDoubleFunction)
     */
    @Test
    public void testAveragingInt() {
        Double average = Stream.of("1", "2", "3", "4")
                .collect(Collectors.averagingInt(Integer::valueOf));
        System.out.println("average: " + average);
    }

    /**
     * 测试Collectors#partitioningBy(),把流中的元素分按条件分为true和false两组
     */
    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<Integer>> partitioningMap = Stream.of("1", "2", "3", "4", "5").map(Integer::valueOf)
                .collect(Collectors.partitioningBy(x -> x > 3));
        System.out.println(partitioningMap);
    }

    /**
     * 测试Collectors#groupingBy(),把流中的元素分组
     */
    @Test
    public void testGroupingBy() {
        //groupingBy的入参的计算值当成Key,然后把计算结果相同的元素分到同一组
        Map<String, List<Integer>> groupingMap = Stream.of("1", "1", "1", "2", "2").map(Integer::valueOf)
                .collect(Collectors.groupingBy(Object::toString));
        System.out.println(groupingMap);
    }

    /**
     * 测试Collectors#groupingByConcurrent(),把流中的元素分组,返回值为ConcurrentHashMap
     */
    @Test
    public void testGroupingByConcurrent() {
        Map<String, List<Integer>> groupingConcurrentMap = Stream.of("1", "1", "1", "2", "2").map(Integer::valueOf)
                .collect(Collectors.groupingByConcurrent(Object::toString));
        System.out.println(groupingConcurrentMap);
    }

    /**
     * 测试Collectors.maxBy
     */
    @Test
    public void testMaxBy() {
        Optional<Integer> max = Stream.of(1, 2, 3, 4, 5).collect(Collectors.maxBy(Integer::compare));
        System.out.println(max.get());
    }

    /**
     * 测试Collectors.minBy
     */
    @Test
    public void testMinBy() {
        Optional<Integer> min = Stream.of(1, 2, 3, 4, 5).collect(Collectors.minBy(Integer::compare));
        System.out.println(min.get());
    }
}
