package com.lzy.demo.module.b;

import com.lzy.demo.module.a.opens.ModuleAService;

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
