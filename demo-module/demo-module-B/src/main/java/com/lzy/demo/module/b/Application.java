package com.lzy.demo.module.b;

import com.lzy.demo.module.a.opens.ModuleAService;

public class Application {

    public static void main(String[] args) {
        System.out.println(new ModuleAService().moduleAService());
    }
}
