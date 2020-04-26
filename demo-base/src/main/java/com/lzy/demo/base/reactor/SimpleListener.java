/*
 * Created by lzy on 2020/3/31 9:11 AM.
 */
package com.lzy.demo.base.reactor;

import reactor.core.publisher.FluxSink;

import java.util.List;

/**
 * 模拟监听器
 *
 * @author lzy
 * @version v1.0
 */
public class SimpleListener {


    private FluxSink<String> sink;

    /**
     * Instantiates a new Simple listener.
     *
     * @param sink the sink
     */
    public SimpleListener(FluxSink<String> sink) {
        this.sink = sink;
    }

    /**
     * On data chunk.
     *
     * @param chunk the chunk
     */
    public void onDataChunk(List<String> chunk) {
        for (String s : chunk) {
            //产生一个元素
            sink.next(s);
        }
    }

    /**
     * Process complete.
     */
    public void processComplete() {
        //完成
        sink.complete();
    }
}
