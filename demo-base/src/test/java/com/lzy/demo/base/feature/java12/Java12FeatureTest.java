package com.lzy.demo.base.feature.java12;

import org.junit.jupiter.api.Test;

public class Java12FeatureTest {

    /**
     * 测试switch
     */
    @Test
    public void testSwitch() {
        String type = "1";
        switch (type) {
            //使用—>,不需要使用break
            case "1", "2", "3" -> System.out.println("<4");
            case "4" -> System.out.println("4");
            default -> {
            }
        }
    }
}
