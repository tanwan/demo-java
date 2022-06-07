package com.lzy.demo.axon.config;

import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    /**
     * 触发快照的策略
     * Event Source不保存对象的最新状态, 而是保存对象产生的所有事件
     * 通过事件回溯(Event Sourcing, ES)得到对象最新的状态
     * 如果事件很多的话,那么回溯对象的最新状态就比较困难了
     * 所以这边根据策略,把一部分的事件产生的状态作为一个快照,后续从这个快照开始回溯
     *
     * @param snapshotter snapshotter
     * @param threshold   threshold
     * @return SnapshotTriggerDefinition
     */
    @Bean
    public SnapshotTriggerDefinition aggregateSnapshotTriggerDefinition(Snapshotter snapshotter,
                                                                        @Value("${axon.aggregate.order.snapshot-threshold:250}") int threshold) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, threshold);
    }
}
