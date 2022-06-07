package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.adaptee.Adaptee;
import com.lzy.demo.design.pattern.adaptee.Adapter;
import org.junit.jupiter.api.Test;

/**
 * 适配器模式测试
 *
 * @author LZY
 * @version v1.0
 */
public class AdapterTest {

    /**
     * 测试适配器模式
     */
    @Test
    public void testAdapter() {
        Adapter adapter = new Adapter(new Adaptee());
        adapter.method("23");
    }
}
