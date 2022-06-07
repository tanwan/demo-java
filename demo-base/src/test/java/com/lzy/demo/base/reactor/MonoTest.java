package com.lzy.demo.base.reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * 0或1个元素
 *
 * @author lzy
 * @version v1.0
 */
public class MonoTest {


    /**
     * 创建Mono
     */
    @Test
    public void testCreateMono() {
        Mono<String> emptyMono = Mono.empty();
        emptyMono.subscribe(System.out::println);

        Mono<String> mono = Mono.just("1");
        mono.subscribe(System.out::println);

    }
}
