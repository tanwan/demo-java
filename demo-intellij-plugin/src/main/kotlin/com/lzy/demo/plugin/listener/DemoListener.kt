package com.lzy.demo.plugin.listener

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener


class DemoListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {
        println("DemoListener projectOpened")
        super.projectOpened(project)
    }
}