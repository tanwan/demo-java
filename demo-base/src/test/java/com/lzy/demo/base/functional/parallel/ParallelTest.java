/*
 * Created by LZY on 12/8/2016 21:50.
 */
package com.lzy.demo.base.functional.parallel;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * 并行化流
 *
 * @author LZY
 * @version v1.0
 */
public class ParallelTest {


    /**
     * 测试并行流
     */
    @Test
    public void testParallel() {
        long begin = System.currentTimeMillis();
        long count = IntStream.range(1, 1000000).filter(ParallelTest::isPrime).count();
        System.out.println("count:" + count + " time:" + (System.currentTimeMillis() - begin));
        begin = System.currentTimeMillis();
        count = IntStream.range(1, 1000000).parallel().filter(ParallelTest::isPrime).count();
        System.out.println("parallel count:" + count + " time:" + (System.currentTimeMillis() - begin));
    }

    /**
     * 判断一个数是否为质数，是返回true，不是返回false
     *
     * @param number the number
     * @return boolean
     */
    private static boolean isPrime(int number) {
        int x = number;
        if (x < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
