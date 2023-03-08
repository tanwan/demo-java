package com.lzy.demo.plugin.extension

import com.intellij.openapi.extensions.ExtensionPointName


object Extension {
    private const val EXTENSION_POINT_NAME = "com.lzy.demo-intellij-plugin.demoInterfaceExtensionPoints"
    private val extensionPoints = ExtensionPointName.create<DemoInterface>(EXTENSION_POINT_NAME)

    fun getExtensions(): List<DemoInterface> {
        return extensionPoints.extensionList
    }
}