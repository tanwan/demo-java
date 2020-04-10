/*
 * Created by lzy on 2020/3/31 4:22 PM.
 */
package com.lzy.demo.base;

import org.junit.jupiter.api.extension.AfterEachCallback;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 判断是否有其它线程
 *
 * @author lzy
 * @version v1.0
 */
public class ThreadEachCallback implements AfterEachCallback, ParameterResolver {

    private static final int DEFAULT_THREAD = 10;
    private static final int WAIT_MAX_TIMES = 2;

    private ExecutorService executorService;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Optional.ofNullable(executorService).ifPresent(ExecutorService::shutdown);
        Map<String, Integer> threadMap = new HashMap<>();
        boolean wait;
        do {
            int allCount = Thread.currentThread().getThreadGroup().activeCount();
            Thread[] threads = new Thread[allCount];
            Thread.currentThread().getThreadGroup().enumerate(threads);
            //非守护线程,并且非当前线程
            Map<String, Integer> map = threadMap;
            threadMap = Arrays.stream(threads).filter(Objects::nonNull).filter(t -> !t.isDaemon() && t != Thread.currentThread())
                    .collect(Collectors.toMap(Thread::getName,
                            t -> t.getState() == Thread.State.RUNNABLE ? 0 :
                                    Optional.ofNullable(map.get(t.getName())).map(i -> i + 1).orElse(1)));
            wait = !threadMap.values().stream().allMatch(t -> t > WAIT_MAX_TIMES);
            if (wait) {
                Thread.sleep(1000);
            }
        } while (wait);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == ExecutorService.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        int nThread = parameterContext.findAnnotation(ThreadParam.class)
                .map(ThreadParam::nThread).orElse(DEFAULT_THREAD);
        executorService = Executors.newFixedThreadPool(nThread);
        return executorService;
    }

    /**
     * The interface Thread param.
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ThreadParam {

        /**
         * 线程数
         *
         * @return the int
         */
        int nThread() default DEFAULT_THREAD;
    }
}
