/*
 * Created by lzy on 2020/5/9 2:30 PM.
 */
package com.lzy.demo.module.a.opens;

/**
 * @author lzy
 * @version v1.0
 */
class OpenClass {

    private OpenClass() {
        System.out.println("OpenClass");
    }

    /**
     * 私有方法
     */
    private String privateMethod() {
        return "privateMethod";
    }
}
