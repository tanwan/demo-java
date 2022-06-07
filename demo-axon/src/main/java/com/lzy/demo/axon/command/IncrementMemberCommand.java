package com.lzy.demo.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Data
public class IncrementMemberCommand {

    @TargetAggregateIdentifier
    private final String id;

    private final String memberName;
}
