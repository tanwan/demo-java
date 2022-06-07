package com.lzy.demo.axon;

import com.lzy.demo.axon.command.AddMemberCommand;
import com.lzy.demo.axon.command.CompleteCommand;
import com.lzy.demo.axon.command.CreateCommand;
import com.lzy.demo.axon.command.DecrementMemberCommand;
import com.lzy.demo.axon.command.IncrementMemberCommand;
import com.lzy.demo.axon.event.CompletedEvent;
import com.lzy.demo.axon.event.CreatedEvent;
import com.lzy.demo.axon.event.MemberAddedEvent;
import com.lzy.demo.axon.event.MemberDecrementedEvent;
import com.lzy.demo.axon.event.MemberIncrementedEvent;
import com.lzy.demo.axon.model.SimpleAggregate;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AggregateTest {

    private static final String ID = UUID.randomUUID().toString();
    private static final String MEMBER_NAME = "simpleMemberName";

    private FixtureConfiguration<SimpleAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(SimpleAggregate.class);
    }

    /**
     * 测试Create
     */
    @Test
    void testCreate() {
        fixture.givenNoPriorActivity()
                .when(new CreateCommand(ID))
                // 期待返回CreatedEvent
                .expectEvents(new CreatedEvent(ID));
    }

    /**
     * 测试AddMember
     */
    @Test
    void testAddMember() {
        fixture.given(new CreatedEvent(ID))
                .when(new AddMemberCommand(ID, MEMBER_NAME))
                .expectEvents(new MemberAddedEvent(ID, MEMBER_NAME));
    }


    /**
     * 测试IncrementMember
     */
    @Test
    void testIncrementMember() {
        fixture.given(new CreatedEvent(ID), new MemberAddedEvent(ID, MEMBER_NAME))
                .when(new IncrementMemberCommand(ID, MEMBER_NAME))
                .expectEvents(new MemberIncrementedEvent(ID, MEMBER_NAME));
    }

    /**
     * 测试DecrementMember
     */
    @Test
    void testDecrementMember() {
        fixture.given(new CreatedEvent(ID),
                        new MemberAddedEvent(ID, MEMBER_NAME),
                        new MemberIncrementedEvent(ID, MEMBER_NAME))
                .when(new DecrementMemberCommand(ID, MEMBER_NAME))
                .expectEvents(new MemberDecrementedEvent(ID, MEMBER_NAME));
    }


    /**
     * 测试Complete
     */
    @Test
    void testComplete() {
        fixture.given(new CreatedEvent(ID))
                .when(new CompleteCommand(ID))
                .expectEvents(new CompletedEvent(ID));
    }

}
