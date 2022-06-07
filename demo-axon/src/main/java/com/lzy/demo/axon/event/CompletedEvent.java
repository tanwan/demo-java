package com.lzy.demo.axon.event;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CompletedEvent {

    private final String id;

}
