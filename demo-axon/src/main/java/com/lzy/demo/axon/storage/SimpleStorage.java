package com.lzy.demo.axon.storage;

import com.lzy.demo.axon.value.SimpleValueObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleStorage {

    private static final Map<String, SimpleValueObject> SIMPLE_VALUE_OBJECT_MAP = new HashMap<>();

    public static List<SimpleValueObject> getValues() {
        return new ArrayList<>(SIMPLE_VALUE_OBJECT_MAP.values());
    }

    public static SimpleValueObject getValue(String id) {
        return SIMPLE_VALUE_OBJECT_MAP.get(id);
    }

    public static void addValue(SimpleValueObject simpleValueObject) {
        SIMPLE_VALUE_OBJECT_MAP.put(simpleValueObject.getId(), simpleValueObject);
    }
}
