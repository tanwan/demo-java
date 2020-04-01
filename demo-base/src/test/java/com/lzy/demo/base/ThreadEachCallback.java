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
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 判断是否有其它线程
 *
 * @author lzy
 * @version v1.0
 */
public class ThreadEachCallback implements AfterEachCallback, ParameterResolver {

    private static final int DEFAULT_THREAD = 10;

    private ExecutorService executorService;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Optional.ofNullable(executorService).ifPresent(ExecutorService::shutdown);
        boolean wait;
        do {
            int allCount = Thread.currentThread().getThreadGroup().activeCount();
            Thread[] threads = new Thread[allCount];
            Thread.currentThread().getThreadGroup().enumerate(threads);
            //非守护线程
            long threadCount = Arrays.stream(threads).filter(t -> !t.isDaemon()).count();
            wait = threadCount > 1;
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
