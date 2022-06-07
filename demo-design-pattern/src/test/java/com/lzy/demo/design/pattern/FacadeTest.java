package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.facade.Facade;
import org.junit.jupiter.api.Test;

/**
 * 测试外观模式
 *
 * @author LZY
 * @version v1.0
 */
public class FacadeTest {
    /**
     * 测试外观模式
     */
    @Test
    public void testFacade() {
        new Facade().method();
    }
}
