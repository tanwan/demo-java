package com.lzy.demo.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory


/**
 * 工具窗口是IDE的子窗口,用于显示信息. 这些窗口通常在主窗口的外边缘有自己的工具栏(称为工具窗口栏)
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/tool-windows.html">ToolWindow</a>
 */
class DemoToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val windowPanel = DemoToolWindow(project)
        // 使用panel创建出content
        val content = ContentFactory.getInstance().createContent(windowPanel, "Demo Tools", true)
        // 将content添加到工具窗口
        toolWindow.contentManager.addContent(content)
        toolWindow.contentManager.setSelectedContent(content)
    }
}