package com.lzy.demo.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.ui.treeStructure.SimpleNode


class MyNode : SimpleNode {
    // 计算出子节点
    override fun getChildren(): Array<SimpleNode> {
        if (parent?.parent != null) {
            return NO_CHILDREN
        }

        return arrayOf(MyNode(this).apply {
            index = 1
        }, MyNode(this).apply {
            index = 2
        })
    }

    constructor(project: Project?) : super(project)

    constructor(parent: MyNode) : super(parent)

    // 节点的展示名称
    override fun getName(): String {
        return if (parent == null) "top" else if (parent.parent == null) "one$index" else "two$index"
    }
}