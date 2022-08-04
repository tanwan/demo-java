package com.lzy.demo.base.vavr;

import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * vavr函数
 *
 * @author lzy
 * @version v1.0
 */
public class VavrFunctionTest {

    /**
     * 测试function
     */
    @Test
    public void testFunction() {
        // vavr支持0-8个参数的Function
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

        // andThan: 先调用plusOne,结果作为multiplyByTwo的参数
        Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);
        assertEquals(6, add1AndMultiplyBy2.apply(2));

        // compose: 先调用multiplyByTwo,结果作为plusOne的参数
        Function1<Integer, Integer> multiplyBy2AndAdd1 = plusOne.compose(multiplyByTwo);
        assertEquals(5, multiplyBy2AndAdd1.apply(2));
    }


    /**
     * 测试lift
     */
    @Test
    public void testFunctionLift() {
        Function2<Integer, Integer, Integer> divide = (a, b) -> a / b;
        // 将返回值包装为Option
        Function2<Integer, Integer, Option<Integer>> safeDivide = Function2.lift(divide);

        assertTrue(safeDivide.apply(1, 0).isEmpty());
        assertEquals(2, safeDivide.apply(4, 2).get());
    }

    /**
     * 测试偏函数
     */
    @Test
    public void testFunctionPartialApply() {
        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        // 先给定一个参数,返回少一个参数的函数
        Function1<Integer, Integer> add2 = sum.apply(2);
        assertEquals(6, add2.apply(4));
    }

    /**
     * 测试柯里化
     */
    @Test
    public void testFunctionCurrying() {
        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        // 将原本是多个参数调用的函数,转化为只接收一个参数的高阶函数
        assertEquals(8, sum.curried().apply(2).apply(6));
    }

    /**
     * 测试memoized
     */
    @Test
    public void testMemoized() {
        Function0<Integer> hashCache = Function0.of(() -> {
            // 只会调用一次
            System.out.println("call function");
            return new Random().nextInt();
        }).memoized();
        Integer randomValue1 = hashCache.apply();
        Integer randomValue2 = hashCache.apply();
        assertEquals(randomValue1, randomValue2);
    }
}
