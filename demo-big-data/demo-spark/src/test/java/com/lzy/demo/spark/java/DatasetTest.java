package com.lzy.demo.spark.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;
import java.util.Arrays;

public class DatasetTest {
    /**
     * Test map.
     */
    @Test
    public void testLocal() {
        SparkConf conf = new SparkConf().setAppName("Test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input = sc.parallelize(Arrays.asList("1", "2", "3", "4"));
        JavaRDD<Integer> inputMap = input.map(Integer::valueOf);
        System.out.println(inputMap.count());
    }

    /**
     * Test remote.
     *
     * @throws UnknownHostException the unknown host exception
     */
    @Test
    public void testRemote() throws UnknownHostException {
        SparkConf conf = new SparkConf().setAppName("Test").setMaster("spark://hadoop-pseudo:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> input = sc.parallelize(Arrays.asList("1", "2", "3", "4"));
        JavaRDD<Integer> inputMap = input.map(Integer::valueOf);
        System.out.println(inputMap.count());
    }
}
