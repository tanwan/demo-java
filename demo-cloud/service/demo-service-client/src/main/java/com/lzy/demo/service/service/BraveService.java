package com.lzy.demo.service.service;

import brave.ScopedSpan;
import brave.Span;
import brave.SpanCustomizer;
import brave.Tracer;
import io.micrometer.tracing.annotation.NewSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

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
    private Tracer tracer;

    @Resource
    private SpanCustomizer spanCustomizer;

    /**
     * 使用startScopedSpan
     */
    public void startScopedSpan() {
        logger.info("before span");
        // 如果不存在trace,则创建一个新的trace,如果存在,则创建一个child span,finish没有调用之前current span一直都是startScopedSpan的返回值
        ScopedSpan span = tracer.startScopedSpan("startScopedSpan");
        try {
            logger.info("before span finish");
        } catch (RuntimeException | Error e) {
            // 错误
            span.error(e);
            throw e;
        } finally {
            //结束此span
            span.finish();
        }
        logger.info("after span finish");
    }


    /**
     * 使用nextSpan
     */
    public void withSpanInScope() {
        logger.info("before span");
        //如果不存在trace,则创建一个新的trace,如果存在,则创建一个child span
        Span span = tracer.nextSpan().name("withSpanInScope").start();
        logger.info("after nextSpan");
        //关联到current span
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
            logger.info("before span finish");
        } finally {
            //结束此span
            span.finish();
        }
        logger.info("after span finish");
    }

    /**
     * 使用注解
     */
    @NewSpan("useAnnotation")
    public void useAnnotation() {
        logger.info("useAnnotation");
    }


    /**
     * 定制
     */
    @NewSpan("customizing")
    public void customizing() {
        //添加tag
        tracer.currentSpan().tag("customizingTag", "tag");
        //使用当前的span,不需要判空
        spanCustomizer.tag("customizingTag2", "tag2");
    }
}
