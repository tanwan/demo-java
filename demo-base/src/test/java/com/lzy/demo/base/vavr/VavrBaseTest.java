package com.lzy.demo.base.vavr;

import io.vavr.Lazy;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * vavr基础
 *
 * @author lzy
 * @version v1.0
 */
public class VavrBaseTest {

    /**
     * 测试tuple
     */
    @Test
    public void testTuple() {
        Tuple3<String, Integer, Double> java8 = Tuple.of("Java", 8, 1.8);
        // 可以使用_1/_1()取值
        String element1 = java8._1;
        int element2 = java8._2;
        double element3 = java8._3();
        assertEquals("Java", element1);
        assertEquals(8, element2);
        assertEquals(1.8, element3, 0.1);

        // 可以进行map: 支持所有值的map和单个值的map
        Tuple3<String, Integer, Double> maped = java8.map2(i -> i * 2);
        assertEquals(16, maped._2);

        // 可以应用lambda
        String result = java8.apply((i, j, k) -> i + j + k);
        assertEquals("Java81.8", result);
    }



    /**
     * 测试try
     */
    @Test
    public void testTry() {
        Try<Integer> result = Try.of(() -> 1 / 0);
        assertTrue(result.isFailure());
        assertEquals(3, result.getOrElse(3));
    }

    /**
     * 测试lazy
     */
    @Test
    public void testLazy() {
        Lazy<String> lazy = Lazy.of(() -> {
            // 只会调用一次
            System.out.println("call lazy");
            return "lazy";
        });
        System.out.println("begin");
        assertEquals(false, lazy.isEvaluated());
        assertEquals("lazy", lazy.get());
        assertEquals(true, lazy.isEvaluated());
    }
}
