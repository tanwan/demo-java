package com.lzy.demo.brave.service;

import brave.ScopedSpan;
import brave.Span;
import brave.SpanCustomizer;
import brave.Tracer;
import brave.Tracing;
import com.lzy.demo.brave.constant.Constants;
import io.micrometer.context.ContextSnapshotFactory;
import io.micrometer.tracing.annotation.NewSpan;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 直接使用Brave
 *
 * @author lzy
 * @version v1.0
 */
@Service
public class BraveService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private Tracing tracing;

    @Resource
    private Tracer tracer;

    @Resource
    private SpanCustomizer spanCustomizer;

    @Resource
    private RestTemplate ribbonRestTemplate;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SimpleFeignService simpleFeignService;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    /**
     * 使用startScopedSpan
     */
    public void startScopedSpan() {
        logger.info("before startScopedSpan");
        // 创建child span或者新的trace(当前不存在trace)
        ScopedSpan span = tracer.startScopedSpan("startScopedSpan");
        try {
            // 在startScopedSpan之后,span.finish之前, current span就是child span
            logger.info("before span finish, span:{}", span.context());
        } catch (RuntimeException | Error e) {
            // 错误
            span.error(e);
            throw e;
        } finally {
            //结束此span
            span.finish();
        }
        // 在span.finish之后, current span就是原来的parent span了
        logger.info("after span finish");
    }


    /**
     * 使用nextSpan和withSpanInScope
     *
     * @throws InterruptedException InterruptedException
     */
    public void withSpanInScope() throws InterruptedException {
        logger.info("before nextSpan");
        //创建child span或者新的trace(当前不存在trace)
        Span span = tracer.nextSpan().name("withSpanInScope").start();
        Thread.sleep(1000);
        //此时,nextSpan没有关联到当前的current span,因此,当前的spanId还是parent的
        //但是nextSpan已经开始计时了, 因为nextSpan已经start了
        logger.info("nextSpan start, current span");
        //使用withSpanInScope关联到当前的span, start也可以在这边调用tracer.withSpanInScope(span.start())
        try (var ws = tracer.withSpanInScope(span)) {
            //关联到当前的span之后, 当前的span就是nextSpan了,因此当前的spanId就是nextSpan的
            logger.info("before nextSpan finish");
        } finally {
            //结束此span
            span.finish();
        }
        logger.info("after nextSpan finish");
    }

    /**
     * 使用注解创建child span或者新的trace(当前不存在trace)
     * 需要配置aop切面
     *
     * @see com.lzy.demo.brave.config.SpanAspectConfiguration
     */
    @NewSpan("useAnnotation")
    public void useAnnotation() {
        logger.info("use annotation");
    }


    /**
     * 定制化span
     */
    @NewSpan("customizing")
    public void customizing() {
        logger.info("customizing");
        //对当前的span进行添加tag
        tracer.currentSpan().tag("customizingTag", "tag");
        //使用spanCustomizer对当前的span添加tag
        spanCustomizer.tag("customizingTag2", "tag2");
    }

    /**
     * 异步需要配置Decorator
     *
     * @throws InterruptedException InterruptedException
     * @see com.lzy.demo.brave.config.AsyncConfig
     */
    @Async
    public void async() throws InterruptedException {
        logger.info("start async");
        Thread.sleep(1000L);
        logger.info("end async");
    }

    public void ribbon() {
        logger.info("use ribbon");
        ribbonRestTemplate.getForObject(Constants.DEMO_MICROMETER_BRAVE_SERVER + "/brave/port", Integer.class);
        ribbonRestTemplate.getForObject(Constants.DEMO_MICROMETER_BRAVE_SERVER + "/brave/simple-request", String.class);
    }

    public void feign() {
        logger.info("use feign");
        // feign支持tracing需要依赖io.github.openfeign:feign-micrometer
        simpleFeignService.serverPort();
        simpleFeignService.simpleRequest();
    }

    public void restTemplate() {
        logger.info("use restTemplate");
        restTemplate.getForObject("http://www.qq.com", String.class);
        restTemplate.getForObject("http://www.baidu.com", String.class);
    }

    public void thread() {
        logger.info("start thread");
        //直接使用原生的线程池,是没有trace的
        executorService.execute(() -> sleep("use runnable"));

        //使用tracing进行包装Runnable,Callable也可以使用wrap
        Runnable wrapRunnable = tracing.currentTraceContext()
                .wrap(() -> sleep("use tracing wrapRunnable"));
        executorService.execute(wrapRunnable);

        //使用tracing包装executorService
        tracing.currentTraceContext().executorService(executorService)
                .execute(() -> sleep("tracing executorService"));

        ContextSnapshotFactory contextSnapshotFactory = ContextSnapshotFactory.builder().build();
        wrapRunnable = contextSnapshotFactory.captureAll(new Object[0])
                .wrap(() -> sleep("use contextSnapshotFactory wrapRunnable"));
        executorService.execute(wrapRunnable);

        logger.info("end thread");
    }

    private void sleep(String method) {
        try {
            logger.info("start " + method);
            Thread.sleep(1000L);
            logger.info("end " + method);
        } catch (InterruptedException e) {
            logger.error("error", e);
        }
    }
}
