package com.lzy.demo.design.pattern.templatemethod;

public class CallbackTemplate {

    public void templateMethod(Callback callback) {
        callback.primitiveOperation1();
        callback.primitiveOperation2();
    }
}
