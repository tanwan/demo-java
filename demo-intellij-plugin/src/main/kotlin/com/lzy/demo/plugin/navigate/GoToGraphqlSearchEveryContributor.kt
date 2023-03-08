package com.lzy.demo.plugin.navigate

import com.intellij.ide.actions.searcheverywhere.AbstractGotoSEContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributor
import com.intellij.ide.actions.searcheverywhere.SearchEverywhereContributorFactory
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project

// 用来为Search Every Where提供搜索结果
class GoToGraphqlSearchEveryContributor(event: AnActionEvent) : AbstractGotoSEContributor(event) {

    override fun createModel(project: Project): FilteringGotoByModel<*> {
        val chooseByNameContributors = arrayOf<ChooseByNameContributor>(GoToGraphqlContributor())
        return GoToGraphqlModel(project, chooseByNameContributors)
    }

    // 用来为这个group在Search Every Where的排序
    override fun getSortWeight(): Int {
        return 1000
    }

    // Search Every Where的Tab的名字
    override fun getGroupName(): String {
        return "Graphql"
    }

    class Factory : SearchEverywhereContributorFactory<Any> {
        override fun createContributor(initEvent: AnActionEvent): SearchEverywhereContributor<Any> {
            return GoToGraphqlSearchEveryContributor(initEvent)
        }
    }
}