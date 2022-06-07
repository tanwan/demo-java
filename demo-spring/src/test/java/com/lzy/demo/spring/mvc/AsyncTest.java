package com.lzy.demo.spring.mvc;

import com.lzy.demo.spring.mvc.application.MVCApplication;
import com.lzy.demo.spring.mvc.controller.AsyncController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试异步
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = {MVCApplication.class, AsyncController.class}, properties = {"server.tomcat.max-threads=1", "waitRate=5"})
@AutoConfigureMockMvc
public class AsyncTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试同步,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#sync()
     */
    @Test
    public void testSync() throws Exception {
        for (int i = 0; i < 2; i++) {
            mockMvc.perform(MockMvcRequestBuilders.get("/async/sync"))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        }
    }

    /**
     * 测试callable,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#callable()
     */
    @Test
    public void testCallable() throws Exception {
        performAsync("callable");
    }


    /**
     * 测试DeferredResult,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#deferredResult()
     */
    @Test
    public void testDeferredResult() throws Exception {
        performAsync("deferred-result");
    }


    /**
     * 测试WebAsyncTask,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#webAsyncTask(Integer)
     */
    @Test
    public void testWebAsyncTask() throws Exception {
        performAsync("web-async-task");
    }

    /**
     * 测试WebAsyncTask超时,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#webAsyncTask(Integer)
     */
    @Test
    public void testWebAsyncTaskTimeOut() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/async/web-async-task")
                        .param("sleep", "6000"))
                .andReturn();
        // 超时抛出异常
        Assertions.assertThatCode(() -> mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                        .andDo(MockMvcResultHandlers.print()))
                .isInstanceOf(IllegalStateException.class);
    }


    /**
     * 测试CompletableFuture,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#completableFuture()
     */
    @Test
    public void testCompletableFuture() throws Exception {
        performAsync("completable-future");
    }


    /**
     * 测试StreamingResponseBody,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#streamingResponseBody()
     */
    @Test
    public void testStreamingResponseBody() throws Exception {
        performAsync("streaming-response-body");
    }

    /**
     * 测试ResponseBodyEmitter,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#responseBodyEmitter()
     */
    @Test
    public void testResponseBodyEmitter() throws Exception {
        performAsync("response-body-emitter");
    }

    /**
     * 测试SseEmitter,先把server.tomcat.max-threads把并发线程设置成1
     *
     * @throws Exception the exception
     * @see AsyncController#sseEmitter()
     */
    @Test
    public void testSseEmitter() throws Exception {
        performAsync("sse-emitter");
    }


    private void performAsync(String url) throws Exception {
        List<MvcResult> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/async/" + url))
                    .andReturn();
            results.add(result);
        }
        for (MvcResult result : results) {
            mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(result))
                    .andDo(MockMvcResultHandlers.print());
        }
    }

}
