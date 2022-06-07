package com.lzy.demo.axon.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@AllArgsConstructor
@Data
public class AddMemberCommand {

    @TargetAggregateIdentifier
    private final String aggregateId;

    private final String memberName;
}
