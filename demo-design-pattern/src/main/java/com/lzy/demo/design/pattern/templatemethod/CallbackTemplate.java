package com.lzy.demo.design.pattern.templatemethod;

/**
 * 使用回调函数
 *
 * @author LZY
 * @version v1.0
 */
public class CallbackTemplate {

    /**
     * Template method.
     *
     * @param callback the callback
     */
    public void templateMethod(Callback callback) {
        callback.primitiveOperation1();
        callback.primitiveOperation2();
    }
}
