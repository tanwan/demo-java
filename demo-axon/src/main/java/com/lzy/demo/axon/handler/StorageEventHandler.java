package com.lzy.demo.axon.handler;

import com.lzy.demo.axon.event.CompletedEvent;
import com.lzy.demo.axon.event.CreatedEvent;
import com.lzy.demo.axon.event.MemberAddedEvent;
import com.lzy.demo.axon.event.MemberDecrementedEvent;
import com.lzy.demo.axon.event.MemberIncrementedEvent;
import com.lzy.demo.axon.event.MemberRemovedEvent;
import com.lzy.demo.axon.query.FindAllQuery;
import com.lzy.demo.axon.storage.SimpleStorage;
import com.lzy.demo.axon.value.SimpleValueObject;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 事件处理
 *
 * @author lzy
 * @version v1.0
 */
@Service
@ProcessingGroup("demo-axon-group")
public class StorageEventHandler {

    /**
     * 监听CreatedEvent
     *
     * @param event the event
     */
    @EventHandler
    public void on(CreatedEvent event) {
        String id = event.getId();
        SimpleStorage.addValue(new SimpleValueObject(id));
    }

    /**
     * 监听MemberAddedEvent
     *
     * @param event the event
     */
    @EventHandler
    public void on(MemberAddedEvent event) {
        SimpleStorage.getValue(event.getId()).getMembers().put(event.getMemberName(), 1);
    }

    /**
     * 监听MemberIncrementedEvent
     *
     * @param event the event
     */
    @EventHandler
    public void on(MemberIncrementedEvent event) {
        SimpleStorage.getValue(event.getId()).getMembers()
                .computeIfPresent(event.getMemberName(), (k, v) -> v + 1);
    }

    /**
     * 监听MemberDecrementedEvent
     *
     * @param event the event
     */
    @EventHandler
    public void on(MemberDecrementedEvent event) {
        SimpleStorage.getValue(event.getId()).getMembers()
                .computeIfPresent(event.getMemberName(), (k, v) -> v - 1);
    }

    /**
     * 监听MemberRemovedEvent
     *
     * @param event the event
     */
    @EventHandler
    public void on(MemberRemovedEvent event) {
        SimpleStorage.getValue(event.getId()).getMembers().remove(event.getMemberName());
    }

    /**
     * 监听CompletedEvent
     *
     * @param event the event
     */
    @EventHandler
    public void on(CompletedEvent event) {
        SimpleStorage.getValue(event.getId()).setCompleted(true);
    }

    @QueryHandler
    public List<SimpleValueObject> handle(FindAllQuery query) {
        return SimpleStorage.getValues();
    }
}
