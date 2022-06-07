package com.lzy.demo.spring.async;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 判断是否有其它线程
 *
 * @author lzy
 * @version v1.0
 */
public class ThreadEachCallback implements AfterEachCallback, ParameterResolver, BeforeEachCallback {

    private static final int DEFAULT_THREAD = 10;
    private static final int WAIT_MAX_TIMES = 20;
    private ExecutorService executorService;

    private final Map<String, Set<Thread>> existThreadMap = new ConcurrentHashMap<>();


    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Optional.ofNullable(executorService).ifPresent(ExecutorService::shutdown);
        Map<String, Integer> threadMap = new HashMap<>();
        boolean wait;
        do {
            Thread[] threads = getAllActiveThread();
            //非守护线程,并且非当前线程
            Map<String, Integer> map = threadMap;
            threadMap = Arrays.stream(threads).filter(Objects::nonNull).filter(t -> !t.isDaemon() && t != Thread.currentThread())
                    .filter(t -> !existThreadMap.get(context.getUniqueId()).contains(t))
                    .collect(Collectors.toMap(Thread::getName,
                            t -> t.getState() == Thread.State.RUNNABLE ? 0 :
                                    Optional.ofNullable(map.get(t.getName())).map(i -> i + 1).orElse(1)));
            wait = !threadMap.values().stream().allMatch(t -> t > WAIT_MAX_TIMES);
            if (wait) {
                Thread.sleep(100);
            }
        } while (wait);
        existThreadMap.remove(context.getUniqueId());
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ExecutorService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        int nThread = parameterContext.findAnnotation(ThreadParam.class)
                .map(ThreadParam::value).orElse(DEFAULT_THREAD);
        executorService = Executors.newFixedThreadPool(nThread);
        return executorService;
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        Thread[] threads = getAllActiveThread();
        existThreadMap.put(context.getUniqueId(), Arrays.stream(threads).collect(Collectors.toSet()));
    }

    private Thread[] getAllActiveThread() {
        int allCount = Thread.currentThread().getThreadGroup().activeCount();
        Thread[] threads = new Thread[allCount];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        return threads;
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface ThreadParam {

        /**
         * 线程数
         *
         * @return the int
         */
        int value() default DEFAULT_THREAD;
    }
}
