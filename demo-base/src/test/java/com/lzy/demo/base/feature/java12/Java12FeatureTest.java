/*
 * Created by lzy on 2020/5/13 9:10 AM.
 */
package com.lzy.demo.base.feature.java12;

import org.junit.jupiter.api.Test;

/**
 * The type Java 12 feature test.
 *
 * @author lzy
 * @version v1.0
 */
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
        }
    }
}
