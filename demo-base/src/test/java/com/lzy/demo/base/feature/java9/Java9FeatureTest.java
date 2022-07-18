package com.lzy.demo.base.feature.java9;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class Java9FeatureTest {


    /**
     * 测试集合
     */
    @Test
    public void testCollection() {
        //创建不可变的集合
        List<Integer> list = List.of(1, 2, 3, 4);
        //Set.of不能重复
        Set<Integer> set = Set.of(1, 2);
        Map<String, Integer> map = Map.of("key1", 1, "key2", 2);

        assertThat(list).contains(1, 2, 3, 4);
        assertThat(set).hasSize(2);
        assertThat(map).containsKeys("key1", "key2");

        //不可变的集合不能修改
        assertThatCode(() -> list.add(1)).isInstanceOf(UnsupportedOperationException.class);
    }


    /**
     * 测试Optional
     */
    @Test
    public void testOptional() {
        // 类似if else
        Optional.ofNullable(null)
                .ifPresentOrElse(System.out::println, () ->
                        System.out.println("null")
                );
        // 类似if else
        Optional.ofNullable(1)
                .ifPresentOrElse(System.out::println, () ->
                        System.out.println("null")
                );
    }

    /**
     * 测试stream
     */
    @Test
    public void testStream() {
        //ofNullable
        Stream.ofNullable(null).forEach(System.out::println);

        //takeWhile,当断言第一次返回false时,就不再返回,因此结果为1,2
        Stream.of(1, 2, 3, 4, 5, 1, 2).takeWhile(i -> i < 3).forEach(System.out::print);
        System.out.println();

        //dropWhile,跟takeWhile刚好相反,当断言第一次返回false,才开始返回,结尾的1,2也会返回
        Stream.of(1, 2, 3, 4, 5, 1, 2).dropWhile(i -> i < 3).forEach(System.out::print);
        System.out.println();

        //使用iterate,类似for,这边不能使用i++,i++是先返回后加1
        Stream.iterate(1, i -> i < 10, i -> ++i).forEach(System.out::print);
    }

    /**
     * 测试try-with-resources
     *
     * @throws Exception the exception
     */
    @Test
    public void testTryWithResource() throws Exception {
        SimpleAutoClose simpleAutoClose = new SimpleAutoClose();
        //SimpleAutoClose不需要声明在try块里面了
        try (simpleAutoClose) {
            System.out.println(simpleAutoClose);
        }
    }

    /**
     * 测试CompletableFuture
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    public void testCompletableFuture() throws InterruptedException, ExecutionException {
        System.out.println(LocalTime.now());
        CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> System.out.println(LocalTime.now() + " delay"));
        CompletableFuture<Integer> completeOnTimeout = CompletableFuture.supplyAsync(() -> {
            System.out.println(LocalTime.now() + " completeOnTimeout start");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(LocalTime.now() + " completeOnTimeout end");
            return 0;
            //超时就返回1
        }).completeOnTimeout(1, 1, TimeUnit.SECONDS);

        CompletableFuture<Void> orTimeout = CompletableFuture.runAsync(() -> {
            System.out.println(LocalTime.now() + " orTimeout start");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //虽然超时了,但是还是会执行
            System.out.println(LocalTime.now() + " orTimeout end");
            //超时抛出ExecutionException异常
        }).orTimeout(1, TimeUnit.SECONDS);
        Thread.sleep(3000L);
        System.out.println(LocalTime.now() + " call future");
        System.out.println(completeOnTimeout.get());
        //调用orTimeout.get()抛异常
        assertThatCode(orTimeout::get).isInstanceOf(ExecutionException.class);
    }


    private static class SimpleAutoClose implements AutoCloseable {

        @Override
        public void close() throws Exception {
            System.out.println("close");
        }
    }
}
