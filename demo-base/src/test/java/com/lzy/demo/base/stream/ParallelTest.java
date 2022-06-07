package com.lzy.demo.base.stream;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

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
