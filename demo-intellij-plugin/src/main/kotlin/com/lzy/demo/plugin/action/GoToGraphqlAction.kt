package com.lzy.demo.plugin.action

import com.intellij.ide.actions.GotoActionBase
import com.intellij.ide.util.gotoByName.ChooseByNamePopup
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.lzy.demo.plugin.navigate.GoToGraphqlModel
import com.lzy.demo.plugin.navigate.GoToGraphqlContributor
import com.lzy.demo.plugin.navigate.GraphqlNavigationItem


// DumbAware表示在dumb模式(idea在进行一些重量级操作会进入dumb模式,比如重建索引)下还可以使用
class GoToGraphqlAction : GotoActionBase(), DumbAware {
    override fun gotoActionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val chooseByNameContributors = arrayOf<ChooseByNameContributor>(GoToGraphqlContributor())

        // 创建model
        val model = GoToGraphqlModel(project, chooseByNameContributors)

        // 回调
        val callback: GotoActionCallback<String> = object : GotoActionCallback<String>() {
            override fun elementChosen(chooseByNamePopup: ChooseByNamePopup, element: Any) {
                if (element is GraphqlNavigationItem && element.canNavigate()) {
                    // 设置跳转到对应的位置
                    element.navigate(true)
                }
            }
        }

        // 展示搜索框
        showNavigationPopup(e, model, callback)
    }
}