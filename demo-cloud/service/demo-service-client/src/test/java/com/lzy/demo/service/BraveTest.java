package com.lzy.demo.service;

import com.lzy.demo.service.service.BraveService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.annotation.Resource;

@SpringBootTest
@ActiveProfiles("sleuth")
public class BraveTest {


    @Resource
    private BraveService braveService;


    /**
     * 测试startScopedSpan
     */
    @Test
    public void testStartScopedSpan() {
        braveService.startScopedSpan();
    }

    /**
     * 测试withSpanInScope
     */
    @Test
    public void testWithSpanInScope() {
        braveService.withSpanInScope();
    }

    /**
     * 测试useAnnotation
     */
    @Test
    public void testUseAnnotation() {
        braveService.useAnnotation();
    }
}
