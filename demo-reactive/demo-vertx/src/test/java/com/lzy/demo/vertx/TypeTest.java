package com.lzy.demo.vertx;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TypeTest {

    /**
     * 测试json
     */
    @Test
    public void testJson() {
        //从string生成Json
        String jsonString = "{\"key\":\"string\"}";
        JsonObject fromString = new JsonObject(jsonString);
        System.out.println(fromString.getString("key"));

        //从map创建JsonObject
        Map<String, Object> map = new HashMap<>();
        map.put("key", "map");
        JsonObject fromMap = new JsonObject(map);
        System.out.println(fromMap.getString("key"));

        //new出来
        JsonObject jsonObject = new JsonObject(map);
        jsonObject.put("key", "new");
        System.out.println(jsonObject.getString("key"));

        //JsonObject映射成类
        System.out.println(jsonObject.mapTo(Map.class));

    }


    /**
     * 测试json数组
     */
    @Test
    public void testJsonArray() {
        String jsonString = "[\"1\",\"2\"]";
        JsonArray fromString = new JsonArray(jsonString);
        System.out.println(fromString.getList());

        JsonArray array = new JsonArray();
        array.add(1).add(2).add(3);
        System.out.println(array.getList());

    }

    /**
     * 测试Buffer
     */
    @Test
    public void testBuffer() {
        Buffer buffer = Buffer.buffer("buffer");
        buffer.appendString(" append");
        System.out.println(buffer.toString());
    }
}
