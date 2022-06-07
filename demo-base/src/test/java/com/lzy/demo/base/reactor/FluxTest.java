package com.lzy.demo.base.reactor;

import com.lzy.demo.base.ThreadEachCallback;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

/**
 * 0到N个元素
 *
 * @author lzy
 * @version v1.0
 */
@ExtendWith(ThreadEachCallback.class)
public class FluxTest {

    /**
     * 创建Flux
     */
    @Test
    public void testCreateFlux() {
        //使用just创建
        Flux<String> flux = Flux.just("1", "2", "3");
        flux.subscribe(System.out::println);

        List<String> list = Arrays.asList("1", "2", "3");
        //使用迭代器创建
        Flux<String> fromIterable = Flux.fromIterable(list);
        fromIterable.subscribe(System.out::println);

        //空的Flux
        Flux<String> emptyFlux = Flux.empty();
        emptyFlux.subscribe(System.out::println);

        //使用range创建
        Flux<Integer> fromRange = Flux.range(5, 3);
        fromRange.subscribe(System.out::println);
    }


    /**
     * 使用generate创建Flux,适用于同步一个一个创建
     */
    @Test
    public void testGenerate() {
        Flux<String> flux = Flux.generate(
                //state的初始值
                () -> 0,
                (state, sink) -> {
                    //开始生成元素
                    sink.next("3 x " + state + " = " + 3 * state);
                    //当state=10的时候,则完成生成
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                });
        //这边的subscribe是同步的
        flux.subscribe(System.out::println);

        //使用相同的state
        Flux<String> useSameStateFlux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    //开始生成元素
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) {
                        sink.complete();
                    }
                    return state;
                },
                //可选参数,state的消费者,当generate完成时调用
                (state) -> System.out.println("state: " + state));
        //这边的subscribe是同步的
        useSameStateFlux.subscribe(System.out::println);
    }

    /**
     * 使用create创建Flux,适用于异步和多线程
     *
     * @param executorService the executor service
     */
    @Test
    public void testCreate(ExecutorService executorService) {
        Flux<String> flux = Flux.create(sink -> {
            SimpleListener simpleListener = new SimpleListener(sink);
            //模拟多线程和异步
            for (int j = 0; j < 10; j++) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    simpleListener.onDataChunk(Arrays.asList("1", "2"));
                });
            }
            //等待线程执行完成
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //完成
            simpleListener.processComplete();
        });
        //这边的subscribe是异步的
        flux.subscribe(System.out::println);
    }

    /**
     * 适用于只有一个线程的情况
     */
    @Test
    public void testPush() {
        Flux<String> flux = Flux.push(sink -> {
            SimpleListener simpleListener = new SimpleListener(sink);
            //模拟一个线程异步
            new Thread(() -> {
                for (int i = 0; i < 3; i++) {
                    simpleListener.onDataChunk(Arrays.asList("1", "2"));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                simpleListener.processComplete();
            }).start();
        });
        //这边的subscribe是异步的
        flux.subscribe(System.out::println);
    }

    /**
     * 消费者
     */
    @Test
    public void testSubscribe() {
        Flux<String> flux = Flux.just("1", "2", "3");
        //有异常的数据
        Flux<Integer> withErrorFlux = Flux.range(1, 4)
                .map(i -> {
                    //如果没有subscribe,这边就不会运行
                    if (i <= 3) {
                        return i;
                    }
                    throw new RuntimeException("Got to 4");
                });
        //触发
        flux.subscribe();
        //第一个参数消费每一个正常的值
        flux.subscribe(System.out::println);
        //第二个参数处理异常
        withErrorFlux.subscribe(System.out::println,
                error -> System.err.println("Error: " + error));
        //第三个参数处理全部正常完成,跟处理异常两个只会有一个执行
        flux.subscribe(System.out::println,
                error -> System.err.println("Error: " + error), () -> System.out.println("complete"));
        //第四个参数控制消费
        withErrorFlux.subscribe(System.out::println,
                error -> System.err.println("Error: " + error), () -> System.out.println("complete"),
                sub -> sub.request(3));
    }


    /**
     * 进行清理
     */
    @Test
    public void testClean() {
        Flux<String> flux = Flux.create(sink -> {
            SimpleListener simpleListener = new SimpleListener(sink);
            //模拟一个线程异步
            new Thread(() -> {
                for (int i = 0; i < 3; i++) {
                    simpleListener.onDataChunk(Arrays.asList("1", "2"));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                simpleListener.processComplete();
            }).start();
            //cancel在接收到cancel的信号后先进行回调
            sink.onCancel(() -> System.out.println("cancel"));
            //dispose在完成,错误或者接收到cancel的信息后进行回调,在这里可以进行清理动作
            sink.onDispose(() -> System.out.println("dispose"));
        });
        //异步
        flux.subscribe(System.out::println);
    }

    /**
     * 测试handle,类似map和filter的操作
     */
    @Test
    public void testHandle() {
        Flux<String> flux = Flux.just(-1, 30, 13, 9, 20)
                .handle((i, sink) -> {
                    if (i > 10) {
                        sink.next("i:" + i);
                    }
                });

        flux.subscribe(System.out::println);
    }


    /**
     * 测试publishOn
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testPublishOn() throws InterruptedException {
        Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                //subscribe触发此map的运行,所以这个map在主线程执行
                .map(i -> {
                    System.out.println("map1,thread:" + Thread.currentThread().getName());
                    return 10 + i;
                })
                //这边指定了线程池
                .publishOn(scheduler)
                //这边的map就在scheduler执行
                .map(i -> {
                    System.out.println("map2,thread:" + Thread.currentThread().getName());
                    return "value " + i;
                });
        flux.subscribe(i -> {
            //这边是在第2个map之后才运行,也就是使用scheduler线程
            System.out.println("subscribe,thread:" + Thread.currentThread().getName());
            System.out.println(i);
        });
        Thread.sleep(300);
        scheduler.dispose();
    }


    /**
     * 测试subscribeOn
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSubscribeOn() throws InterruptedException {
        Scheduler scheduler = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                //subscribe触发此map的运行,而subscribe指定了线程,因为这边在scheduler执行
                .map(i -> {
                    System.out.println("map1,thread:" + Thread.currentThread().getName());
                    return 10 + i;
                })
                //这边指定了线程池
                .subscribeOn(scheduler)
                //这边的map就在scheduler执行
                .map(i -> {
                    System.out.println("map2,thread:" + Thread.currentThread().getName());
                    return "value " + i;
                });
        flux.subscribe(i -> {
            //subscribe虽然在主线程调用,但是会马上切换成scheduler执行
            System.out.println("subscribe,thread:" + Thread.currentThread().getName());
            System.out.println(i);
        });
        Thread.sleep(300);
        scheduler.dispose();
    }

    /**
     * 测试error
     */
    @Test
    public void testError() {
        Flux<String> flux = Flux.just(1, 0, 2)
                //这边触发异常
                .map(i -> "100 / " + i + " = " + (100 / i))
                //这边到0后就不执行了
                .onErrorReturn("errorReturn");
        flux.subscribe(System.out::println);
    }

    /**
     * 测试transform
     */
    @Test
    public void testTransform() {
        Function<Flux<String>, Flux<String>> filterAndMap =
                f -> {
                    System.out.println("filterAndMap");
                    return f.filter(color -> !color.equals("orange"))
                            .map(String::toUpperCase);
                };
        Flux<String> transform = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                //对每个元素进行操作
                .doOnNext(System.out::println)
                //将Flux转换成另一个Flux,多次subscribe,filterAndMap也只会调用一次
                .transform(filterAndMap);
        transform.subscribe(d -> System.out.println("Subscriber1 to Transformed MapAndFilter: " + d));
        transform.subscribe(d -> System.out.println("Subscriber2 to Transformed MapAndFilter: " + d));

        Flux<String> transformDeferred = Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"))
                //对每个元素进行操作
                .doOnNext(System.out::println)
                //将Flux转换成另一个Flux,多次subscribe,filterAndMap也会调用多次
                .transformDeferred(filterAndMap);
        transformDeferred.subscribe(d -> System.out.println("Subscriber1 to transformDeferred MapAndFilter: " + d));
        transformDeferred.subscribe(d -> System.out.println("Subscriber2 to transformDeferred MapAndFilter: " + d));
    }

    /**
     * 测试DirectProcessor,先订阅,后发布
     */
    @Test
    public void testDirectProcessor() {
        DirectProcessor<String> hotFlux = DirectProcessor.create();
        //先订阅
        hotFlux.subscribe(d -> System.out.println("Subscriber 1 to Hot Source: " + d));
        //后发布
        hotFlux.onNext("blue");
        hotFlux.onNext("green");
        hotFlux.subscribe(d -> System.out.println("Subscriber 2 to Hot Source: " + d));
        hotFlux.onNext("orange");
        hotFlux.onNext("purple");
        hotFlux.onComplete();
    }


    /**
     * 测试map和flatMap
     */
    @Test
    public void testMap() {
        //单纯地进行转换
        Flux.just(2, 3).log().map(t -> 2 * t).subscribe(System.out::println);

        //将元素转换成Flux或者Mono
        Flux.just(2, 3).log().flatMap(t -> Flux.range(2, 3)).subscribe(System.out::println);
    }


    /**
     * 测试then
     */
    @Test
    public void testThen() {
        Flux.just(1, 2)
                .map(t -> {
                    System.out.println("map1");
                    return t;
                })
                .thenMany(Flux.just(3, 4)
                        .map(t -> {
                            System.out.println("map2");
                            return t;
                        })
                )
                //map1,map2都会执行
                .subscribe(System.out::println);
    }

}
