package com.lzy.demo.brave.config;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.annotation.DefaultNewSpanParser;
import io.micrometer.tracing.annotation.ImperativeMethodInvocationProcessor;
import io.micrometer.tracing.annotation.MethodInvocationProcessor;
import io.micrometer.tracing.annotation.NewSpanParser;
import io.micrometer.tracing.annotation.SpanAspect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 要使用@NewSpan需要配置AOP切面
 *
 * @author lzy
 * @version v1.0
 * @see <a href="https://micrometer.io/docs/tracing#_aspect_oriented_programming_starting_from_micrometer_tracing_1_1_0">micrometer</a>
 */
@Configuration
public class SpanAspectConfiguration {

    @Bean
    public SpanAspect spanAspect(Tracer tracer, BeanFactory beanFactory) {
        NewSpanParser newSpanParser = new DefaultNewSpanParser();
        MethodInvocationProcessor methodInvocationProcessor = new ImperativeMethodInvocationProcessor(newSpanParser, tracer,
                beanFactory::getBean, beanFactory::getBean);
        return new SpanAspect(methodInvocationProcessor);
    }
}
