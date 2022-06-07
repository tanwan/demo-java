package com.lzy.demo.base.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;


public class JsonBaseTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }

    /**
     * 序列化为List
     */
    @Test
    public void testList() throws JsonProcessingException {
        String json = "[{\"str\":\"str1\",\"integer\":1},{\"str\":\"str2\",\"integer\":2}]";
        // 直接读取成List
        List<Map<String, Object>> mapList = objectMapper.readValue(json, List.class);

        // 读取成List<SimpleJsonBean>
        List<SimpleJsonBean> beanList = objectMapper.readValue(json, new TypeReference<>() {
        });

        Assertions.assertTrue(mapList.get(0) instanceof Map);

        Assertions.assertTrue(beanList.get(0) instanceof SimpleJsonBean);
    }
}
