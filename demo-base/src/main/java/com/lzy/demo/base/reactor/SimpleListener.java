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

    public SimpleListener(FluxSink<String> sink) {
        this.sink = sink;
    }

    public void onDataChunk(List<String> chunk) {
        for (String s : chunk) {
            //产生一个元素
            sink.next(s);
        }
    }

    public void processComplete() {
        //完成
        sink.complete();
    }
}
