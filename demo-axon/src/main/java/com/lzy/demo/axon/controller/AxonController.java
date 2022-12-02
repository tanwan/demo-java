package com.lzy.demo.axon.controller;

import com.lzy.demo.axon.command.AddMemberCommand;
import com.lzy.demo.axon.command.CompleteCommand;
import com.lzy.demo.axon.command.CreateCommand;
import com.lzy.demo.axon.command.DecrementMemberCommand;
import com.lzy.demo.axon.command.IncrementMemberCommand;
import com.lzy.demo.axon.query.FindAllQuery;
import com.lzy.demo.axon.value.SimpleValueObject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class AxonController {

    @Resource
    private CommandGateway commandGateway;
    @Resource
    private QueryGateway queryGateway;

    @GetMapping("/all")
    public CompletableFuture<List<SimpleValueObject>> findAll() {
        return queryGateway.query(new FindAllQuery(), ResponseTypes.multipleInstancesOf(SimpleValueObject.class));
    }

    @PostMapping("/create")
    public CompletableFuture<String> create() {
        // 这边的返回值是聚合的id
        return commandGateway.send(new CreateCommand(UUID.randomUUID().toString()));
    }

    @PostMapping("/create-add-member-complete/{memberName}")
    public CompletableFuture<Void> createAddMemberComplete(@PathVariable String memberName) {
        return commandGateway.send(new CreateCommand(UUID.randomUUID().toString()))
                // CreateCommand的返回值是聚合的id
                .thenCompose(result -> commandGateway.send(new AddMemberCommand(result.toString(), memberName))
                        // AddAggregateMemberCommand的返回值是null, 所以把这个future compose在内部
                        .thenCompose(result2 -> commandGateway.send(new CompleteCommand(result.toString()))));
    }

    @PostMapping("/{id}/complete")
    public CompletableFuture<Void> complete(@PathVariable String id) {
        return commandGateway.send(new CompleteCommand(id));
    }

    @PostMapping("/{id}/{memberName}/add-member")
    public CompletableFuture<Void> addMember(@PathVariable("id") String id, @PathVariable("memberName") String memberName) {
        return commandGateway.send(new AddMemberCommand(id, memberName));
    }

    @PostMapping("/{id}/{memberName}/increment")
    public CompletableFuture<Void> incrementMember(@PathVariable("id") String id, @PathVariable("memberName") String memberName) {
        return commandGateway.send(new IncrementMemberCommand(id, memberName));
    }

    @PostMapping("/{id}/{memberName}/decrement")
    public CompletableFuture<Void> decrementMember(@PathVariable("id") String id, @PathVariable("memberName") String memberName) {
        return commandGateway.send(new DecrementMemberCommand(id, memberName));
    }
}
