package com.lzy.demo.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
public class CompleteCommand {

    @TargetAggregateIdentifier
    private final String id;
}
