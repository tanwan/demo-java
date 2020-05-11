/*
 * Created by lzy on 2020/5/11 12:12 AM.
 */
package com.lzy.demo.module.b;

import com.lzy.demo.module.a.opens.ModuleAService;

/**
 * The type Application.
 *
 * @author lzy
 * @version v1.0
 */
public class Application {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println(new ModuleAService().moduleAService());
    }
}
