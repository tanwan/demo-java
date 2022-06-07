package com.lzy.demo.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MemberRemovedEvent {

    private final String id;

    private final String memberName;
}
