package com.lzy.demo.base.feature.java8.lamdba;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.*;

/**
 * 常用的function interface
 *
 * @author lzy
 * @version v1.0
 */
public class CommonFunctionalInterfaceTest {


    /**
     * 测试Predicate
     *
     * @see Predicate
     * @see BiPredicate
     */
    @Test
    public void testPredicate() {
        Predicate<String> predicate = x -> x.length() > 0;

        System.out.println(predicate.test("hello world"));
        Predicate<String> nonNull = Objects::nonNull;
        Predicate<String> isNull = nonNull.negate();

        System.out.println(nonNull.and(predicate).test(null));
    }

    /**
     * 测试Function
     *
     * @see Function
     * @see BiFunction
     */
    @Test
    public void testFunction() {
        Function<String, String> function = x -> "hello world " + x;
        Function<String, String> beforeFunction = x -> "before " + x;
        Function<String, String> afterFunction = x -> x + " after";
        //先执行compose的参数,再执行function
        System.out.println(function.compose(beforeFunction).apply("current"));
        //先执行function,再执行andThen的参数
        System.out.println(function.andThen(afterFunction).apply("current"));
    }

    /**
     * 测试Supplier
     *
     * @see Supplier
     */
    @Test
    public void testSupplier() {
        Supplier<CommonFunctionalInterfaceTest> supplier = CommonFunctionalInterfaceTest::new;
        Supplier<String> stringSupplier = supplier.get()::supplier;
        System.out.println(stringSupplier.get());
    }

    /**
     * 测试Consumer
     *
     * @see Consumer
     * @see BiConsumer
     */
    @Test
    public void testConsumer() {
        Consumer<String> consumer = System.out::println;
        Consumer<String> afterConsumer = x -> System.out.println("after:" + x);
        consumer.andThen(afterConsumer).accept("hello world");
    }

    /**
     * 测试BinaryOperator
     *
     * @see BinaryOperator
     */
    @Test
    public void testBinaryOperator() {
        BinaryOperator<String> binaryOperator = (x, y) -> x + y;
        System.out.println(binaryOperator.apply("hello", " world"));
    }


    /**
     * 测试Comparator
     *
     * @see Comparator
     */
    @Test
    public void testComparator() {
        Comparator<Integer> comparator = Integer::compareTo;
        System.out.println(comparator.compare(1, 2));
        System.out.println(comparator.reversed().compare(1, 2));
    }

    /**
     * 测试Callable
     *
     * @throws Exception the exception
     * @see Callable
     */
    @Test
    public void testCallable() throws Exception {
        Callable<String> callable = () -> "hello world";
        System.out.println(callable.call());
    }

    /**
     * 测试Runnable
     *
     * @see Runnable
     */
    @Test
    public void testRunnable() {
        Runnable runnable = () -> System.out.println("thread name: " + Thread.currentThread().getName());
        new Thread(runnable).start();
    }

    /**
     * Instantiates a new Common com.lzy.demo.base.functional interface demo.
     */
    public CommonFunctionalInterfaceTest() {
        System.out.println("new CommonFunctionalInterfaceTest()");
    }

    private String supplier() {
        return "hello world";
    }

}
