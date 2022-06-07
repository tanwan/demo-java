package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.templatemethod.Callback;
import com.lzy.demo.design.pattern.templatemethod.CallbackTemplate;
import com.lzy.demo.design.pattern.templatemethod.ConcreteTemplate;
import org.junit.jupiter.api.Test;

/**
 * 测试模板方法
 *
 * @author LZY
 * @version v1.0
 */
public class TemplateMethodTest {

    /**
     * 测试模板方法
     */
    @Test
    public void testTemplateMethod() {
        ConcreteTemplate concreteTemplate = new ConcreteTemplate();
        concreteTemplate.templateMethod();
    }

    /**
     * 测试模板方法(使用回调)
     */
    @Test
    public void testCallbackTemplate() {
        CallbackTemplate callbackTemplate = new CallbackTemplate();
        callbackTemplate.templateMethod(new Callback() {
            @Override
            public void primitiveOperation1() {
                System.out.println("operation1");
            }

            @Override
            public void primitiveOperation2() {
                System.out.println("operation2");
            }
        });
    }
}
