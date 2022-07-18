package com.lzy.demo.base.feature.java17;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Java17FeatureTest {

    /**
     * 测试stream转list
     */
    @Test
    public void testStreamToList() {
        // stream可以直接toList,
        List<Integer> list = Stream.of("1", "2", "3").map(Integer::valueOf).toList();
        assertThat(list).contains(1, 2, 3);
    }

}
