package com.lzy.demo.axon.model;

import com.lzy.demo.axon.command.AddMemberCommand;
import com.lzy.demo.axon.command.CompleteCommand;
import com.lzy.demo.axon.command.CreateCommand;
import com.lzy.demo.axon.event.CompletedEvent;
import com.lzy.demo.axon.event.CreatedEvent;
import com.lzy.demo.axon.event.MemberAddedEvent;
import com.lzy.demo.axon.event.MemberRemovedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合
 * 可以使用snapshotTriggerDefinition来指定快照的策略
 *
 * @author lzy
 * @version v1.0
 */
@Aggregate(snapshotTriggerDefinition = "aggregateSnapshotTriggerDefinition")
public class SimpleAggregate {

    /**
     * 聚合的id
     */
    @AggregateIdentifier
    private String id;
    private boolean completed;

    /**
     * 聚合的成员
     */
    @AggregateMember
    private Map<String, SimpleAggregateMember> aggregateMember;

    /**
     * 作为构造函数,表示创建一个聚合
     *
     * @param command the command
     */
    @CommandHandler
    public SimpleAggregate(CreateCommand command) {
        AggregateLifecycle.apply(new CreatedEvent(command.getId()));
    }

    /**
     * 处理AddAggregateMemberCommand
     *
     * @param command the command
     */
    @CommandHandler
    public void handle(AddMemberCommand command) {
        if (completed) {
            throw new IllegalArgumentException(id + " already completed");
        }

        String memberName = command.getMemberName();
        AggregateLifecycle.apply(new MemberAddedEvent(id, memberName));
    }


    /**
     * 处理CompleteCommand
     *
     * @param command the command
     */
    @CommandHandler
    public void handle(CompleteCommand command) {
        // 这边id会自动赋值,也可以从command拿
        AggregateLifecycle.apply(new CompletedEvent(id));
    }

    /**
     * 监听CreatedEvent事件, EventSourcingHandler跟EventHandler也不知道什么差别
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(CreatedEvent event) {
        // 这边的aggregate的id需要唯一,否则会报OUT_OF_RANGE: [AXONIQ-2000] Invalid sequence number的错误
        // Axon Server和其它Event Store的实现都是不允许aggregate的id重复的
        this.id = event.getId();
        this.completed = false;
        this.aggregateMember = new HashMap<>();
    }


    /**
     * 监听CompletedEvent事件
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(CompletedEvent event) {
        this.completed = true;
    }


    /**
     * 监听MemberAddedEvent事件
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(MemberAddedEvent event) {
        String memberName = event.getMemberName();
        this.aggregateMember.put(memberName, new SimpleAggregateMember(memberName));
    }

    /**
     * 监听MemberRemovedEvent事件
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(MemberRemovedEvent event) {
        this.aggregateMember.remove(event.getMemberName());
    }

    protected SimpleAggregate() {
        // 不知道干啥用的
        // Required by Axon to build a default Aggregate prior to Event Sourcing
    }
}
