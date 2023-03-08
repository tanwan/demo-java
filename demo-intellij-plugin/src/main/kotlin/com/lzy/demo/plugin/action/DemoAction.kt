package com.lzy.demo.plugin.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.lzy.demo.plugin.ui.DemoDialog

/**
 * 一个action表示IDEA菜单里的一个menu item或工具栏上的一个按钮
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/working-with-custom-actions.html">action</a>
 */
class DemoAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        // 弹出对话框
        DemoDialog(e.project).show()
    }
}