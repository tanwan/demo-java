package com.lzy.demo.plugin.navigate

import com.intellij.ide.IdeBundle
import com.intellij.ide.util.PropertiesComponent
import com.intellij.ide.util.gotoByName.ChooseByNameItemProvider
import com.intellij.ide.util.gotoByName.CustomMatcherModel
import com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider
import com.intellij.ide.util.gotoByName.FilteringGotoByModel
import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.util.ArrayUtilRt

// 搜索的一些配置
class GoToGraphqlModel(project: Project, contributors: Array<ChooseByNameContributor>) :
    FilteringGotoByModel<String>(project, contributors), CustomMatcherModel {
    // 提示文本
    override fun getPromptText(): String = "Enter graphql:"

    override fun getNotInMessage(): String = ""

    // 没有查到的文本
    override fun getNotFoundMessage(): String = IdeBundle.message("label.no.matches.found")

    // 搜索旁边的选择框的名称,返回null可以隐藏
    override fun getCheckBoxName(): String {
        return "All project"
    }

    // 选择框的初始值
    override fun loadInitialCheckBoxState(): Boolean {
        val propertiesComponent = PropertiesComponent.getInstance(myProject)
        return propertiesComponent.getValue("GoToGraphql.allProject")?.toBoolean() ?: false
    }

    // 保存选择框的值
    override fun saveInitialCheckBoxState(state: Boolean) {
        val propertiesComponent = PropertiesComponent.getInstance(myProject)
        propertiesComponent.setValue("GoToGraphql.allProject", java.lang.Boolean.toString(state))
    }

    // 用来替换full name中的分隔符
    override fun getSeparators(): Array<String> = emptyArray()

    override fun getFullName(element: Any): String? = getElementName(element)

    override fun willOpenEditor(): Boolean {
        return true
    }

    override fun filterValueFor(item: NavigationItem?): String? {

        if (item is GraphqlNavigationItem) {
            return item.name
        }
        return null
    }

    /**
     * 自定义匹配方法
     * @see com.intellij.ide.util.gotoByName.DefaultChooseByNameItemProvider.matchName
     */
    override fun matches(popupItem: String, userPattern: String): Boolean {
        return popupItem.startsWith(userPattern)
    }

}