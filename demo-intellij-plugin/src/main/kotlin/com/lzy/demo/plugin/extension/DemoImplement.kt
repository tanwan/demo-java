package com.lzy.demo.plugin.extension


class DemoImplement : DemoInterface {
    override fun provideData(): String {
        return "DemoImplement provideData"
    }
}