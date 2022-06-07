package com.lzy.demo.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreatedEvent {

    private final String id;
}
