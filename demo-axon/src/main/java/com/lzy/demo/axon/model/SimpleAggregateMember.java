package com.lzy.demo.axon.model;

import com.lzy.demo.axon.command.DecrementMemberCommand;
import com.lzy.demo.axon.command.IncrementMemberCommand;
import com.lzy.demo.axon.event.CompletedEvent;
import com.lzy.demo.axon.event.MemberDecrementedEvent;
import com.lzy.demo.axon.event.MemberIncrementedEvent;
import com.lzy.demo.axon.event.MemberRemovedEvent;
import lombok.EqualsAndHashCode;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.EntityId;

/**
 * 聚合的成员
 *
 * @author lzy
 * @version v1.0
 */
@EqualsAndHashCode
public class SimpleAggregateMember {

    @EntityId
    private final String memberName;
    private Integer count;

    private boolean completed;

    public SimpleAggregateMember(String memberName) {
        this.memberName = memberName;
        this.count = 1;
    }

    /**
     * 处理IncrementMemberCommand
     *
     * @param command the command
     */
    @CommandHandler
    public void handle(IncrementMemberCommand command) {
        AggregateLifecycle.apply(new MemberIncrementedEvent(command.getId(), memberName));
    }

    /**
     * 处理DecrementMemberCommand
     *
     * @param command the command
     */
    @CommandHandler
    public void handle(DecrementMemberCommand command) {
        if (count <= 1) {
            AggregateLifecycle.apply(new MemberRemovedEvent(command.getId(), memberName));
        } else {
            AggregateLifecycle.apply(new MemberDecrementedEvent(command.getId(), memberName));
        }
    }

    /**
     * 监听MemberIncrementedEvent事件
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(MemberIncrementedEvent event) {
        this.count++;
    }

    /**
     * 监听MemberDecrementedEvent事件
     *
     * @param event the event
     */
    @EventSourcingHandler
    public void on(MemberDecrementedEvent event) {
        this.count--;
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
}
