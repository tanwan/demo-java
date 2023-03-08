package com.lzy.demo.plugin.action

import com.intellij.ide.actions.SearchEverywhereBaseAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.lzy.demo.plugin.navigate.GoToGraphqlSearchEveryContributor

// 直接打开搜索框, 类似⌘+⇧+O
class GoToGraphqlSearchEveryWhereAction : SearchEverywhereBaseAction() {
    override fun actionPerformed(e: AnActionEvent) {
        showInSearchEverywherePopup(GoToGraphqlSearchEveryContributor::class.simpleName ?: "", e, true, true)
    }
}