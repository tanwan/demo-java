package com.lzy.demo.spring.mvc.controller;

import com.lzy.demo.spring.mvc.bean.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * 异步执行
 *
 * @author LZY
 * @version v1.0
 */
@RestController
@RequestMapping("/async")
public class AsyncController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${waitRate:1}")
    private Integer rate;

    /**
     * 使用同步
     * 两个请求的的返回值相差5秒
     *
     * @return the int
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping("/sync")
    public String sync() throws InterruptedException {
        logger.info("Sync");
        LocalTime start = LocalTime.now();
        Thread.sleep(5000 / rate);
        return start.toString();
    }

    /**
     * 使用异步
     * 两个请求的的返回时间几乎相同,同一个浏览器的不同标签异步不生效
     *
     * @return the callable
     */
    @GetMapping("/callable")
    public Callable<String> callable() {
        logger.info("Callable");
        // 使用spring mvc管理的线程池
        return () -> {
            logger.info("Callable work");
            LocalTime start = LocalTime.now();
            Thread.sleep(5000 / rate);
            return start.toString();
        };
    }

    /**
     * 异步,需要在另一个线程设置结果
     *
     * @return the deferred result
     */
    @GetMapping("/deferred-result")
    public DeferredResult<String> deferredResult() {
        logger.info("DeferredResult");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        // 使用自己的线程池
        new Thread(() -> {
            logger.info("DeferredResult work");
            LocalTime start = LocalTime.now();
            try {
                Thread.sleep(5000 / rate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //在另一个线程给此DeferredResult设置结果
            deferredResult.setResult(start.toString());
        }).start();
        return deferredResult;
    }

    /**
     * 返回WebAsyncTask可以定义超时时间
     *
     * @param sleep the sleep
     * @return the web async task
     */
    @GetMapping("/web-async-task")
    public WebAsyncTask<String> webAsyncTask(@RequestParam(defaultValue = "4000") Integer sleep) {
        logger.info("WebAsyncTask");
        // 使用spring mvc管理的线程
        return new WebAsyncTask<>(5000 / rate, () -> {
            logger.info("WebAsyncTask work");
            LocalTime start = LocalTime.now();
            Thread.sleep(sleep / rate);
            return start.toString();
        });
    }


    /**
     * 同DeferredResult一样
     *
     * @return the completable future
     */
    @GetMapping("/completable-future")
    public CompletableFuture completableFuture() {
        logger.info("CompletableFuture");
        // 使用java的fork join线程
        return CompletableFuture.supplyAsync(() -> {
            logger.info("CompletableFuture worker");
            LocalTime start = LocalTime.now();
            try {
                Thread.sleep(5000 / rate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return start.toString();
        });
    }

    /**
     * 同DeferredResult一样是异步,只是DeferredResult只能返回一个对象,而ResponseBodyEmitter同时返回多个对象,
     * 对象使用HttpMessageConverter进行转换
     *
     * @return the response body emitter
     */
    @RequestMapping("/response-body-emitter")
    public ResponseBodyEmitter responseBodyEmitter() {
        logger.info("ResponseBodyEmitter");
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        // 使用自定义的线程池
        new Thread(() -> {
            logger.info("ResponseBodyEmitter work");
            try {
                Message message = new Message();
                emitter.send(message);
                Thread.sleep(1000 / rate);
                emitter.send(LocalTime.now().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //表示该异步请求的处理可以完成了,可以返回了
            emitter.complete();
        }).start();
        return emitter;
    }

    /**
     * ResponseBodyEmitter的子类,返回值有格式
     *
     * @return the sse emitter
     */
    @RequestMapping("/sse-emitter")
    public SseEmitter sseEmitter() {
        logger.info("SseEmitter");
        SseEmitter sseEmitter = new SseEmitter();
        new Thread(() -> {
            logger.info("SseEmitter work");
            try {
                sseEmitter.send(LocalTime.now().toString());
                Thread.sleep(1000 / rate);
                sseEmitter.send(LocalTime.now().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //表示该异步请求的处理可以完成了,可以返回了
            sseEmitter.complete();
        }).start();
        return sseEmitter;
    }

    /**
     * 直接把流写入到Response中,不占用Servlet容器的线程,可以当ResponseEntity的body,用来返回图片,文件的下载
     *
     * @return the streaming response body
     */
    @RequestMapping("/streaming-response-body")
    public StreamingResponseBody streamingResponseBody() {
        logger.info("StreamingResponseBody");
        // 使用spring mvc管理的线程池
        return outputStream -> {
            logger.info("StreamingResponseBody work");
            LocalTime start = LocalTime.now();
            try {
                Thread.sleep(5000 / rate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.write(start.toString().getBytes());
        };
    }
}
