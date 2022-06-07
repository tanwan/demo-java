package com.lzy.demo.axon.value;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 值对象
 *
 * @author lzy
 * @version v1.0
 */
@Data
public class SimpleValueObject {

    private final String id;
    private final Map<String, Integer> members;

    private boolean completed;

    public SimpleValueObject(String id) {
        this.id = id;
        this.members = new HashMap<>();
    }
}
