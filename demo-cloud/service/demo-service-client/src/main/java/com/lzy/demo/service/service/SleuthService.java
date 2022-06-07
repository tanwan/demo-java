package com.lzy.demo.service.service;

import brave.Tracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceThreadPoolTaskExecutor;
import org.springframework.cloud.sleuth.instrument.async.TraceRunnable;
import org.springframework.cloud.sleuth.instrument.web.mvc.TracingClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@EnableAsync
public class SleuthService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private Tracing tracing;

    @Resource
    private Tracer braveTracer;

    @Resource
    private SpanNamer spanNamer;

    @Resource
    private BeanFactory beanFactory;

    @Resource
    private RestTemplate plainRestTemplate;

    private LazyTraceExecutor lazyTraceExecutor;
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(5);
        lazyTraceExecutor = new LazyTraceExecutor(beanFactory, executorService);
    }

    /**
     * 线程
     *
     * @see LazyTraceExecutor
     * @see LazyTraceThreadPoolTaskExecutor
     */
    public void thread() {
        // 直接使用LazyTraceExecutor,默认spanName为async,可以使用注解@SpanName,或者重写toString
        lazyTraceExecutor.execute(() -> sleep("use lazyTraceExecutor"));

        //直接使用原生的,没有span
        executorService.execute(() -> sleep("use runnable"));
        //使用TraceRunnable,这边可以指定span的名称
        //callable使用 TraceCallable
        Runnable traceRunnable = new TraceRunnable(braveTracer, spanNamer, () -> sleep("use TraceRunnable"),
                "TraceRunnable");
        executorService.execute(traceRunnable);

        //使用tracing进行包装,这样使用当前的trace,不会新创建span
        //callable也可以使用wrap
        Runnable traceRunnableFromTracer = this.tracing.currentTraceContext()
                .wrap(() -> sleep("traceRunnableFromTracer"));
        executorService.execute(traceRunnableFromTracer);
    }

    /**
     * 异步
     *
     * @throws InterruptedException the interrupted exception
     */
    @Async
    public void async() throws InterruptedException {
        logger.info("start async");
        Thread.sleep(1000L);
        logger.info("end async");
    }


    /**
     * restTemplate
     *
     * @see TracingClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)
     */
    public void restTemplate() {
        //使用new集成sleuth
        new RestTemplate().getForObject("http://www.qq.com", String.class);
        //需要声明成spring bean,默认使用HttpRequestParser.DEFAULT解析(不带uri)
        plainRestTemplate.getForObject("http://www.baidu.com", String.class);
    }

    private void sleep(String method) {
        try {
            logger.info("start " + method);
            Thread.sleep(1000L);
            logger.info("end " + method);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
