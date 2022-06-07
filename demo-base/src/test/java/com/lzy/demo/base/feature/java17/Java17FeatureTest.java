package com.lzy.demo.base.feature.java17;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class Java17FeatureTest {

    /**
     * 测试stream转list
     */
    @Test
    public void testStreamToList() {
        // stream可以直接toList,
        List<Integer> list = Stream.of("1", "2", "3").map(Integer::valueOf).toList();
        Assertions.assertThat(list).contains(1, 2, 3);
    }

}
