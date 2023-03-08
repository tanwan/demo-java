package com.lzy.demo.plugin.provider

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.lang.properties.PropertiesFileType
import com.intellij.lang.properties.psi.PropertiesFile
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.lzy.demo.plugin.icon.Icons

/**
 * RelatedItemLineMarkerProvider主要用于代码中的关联元素的跳转
 * LineMarkerProvider主要用于单纯的显示出代码相应的信息
 * 可以参考LineMarkerProvider的实现类
 *
 * getLineMarkerInfo: 用于快速创建markerInfo,只能针对psi的叶子节点做处理
 * collectSlowLineMarkers: 用于批量创建markerInfo, 速度比较慢
 * @see com.intellij.codeInsight.daemon.impl.LineMarkersPass.queryProviders
 */
class DemoLineMarkerProvider : RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (element is PsiMethod && element.name == "lineMarker") {
            // 搜索要跳转的目标
            val targets = findProperties(element.project, element.name)
            if (targets.isNotEmpty()) {
                val builder = NavigationGutterIconBuilder.create(Icons.ICON)
                    // 点击跳转的目标
                    .setTargets(targets)
                    .setTooltipText("Navigate to json property")
                result.add(builder.createLineMarkerInfo(element))
            }
        }
    }

    private fun findProperties(project: Project, key: String): List<PsiElement> {
        val result = mutableListOf<PsiElement>()
        val virtualFiles = FileTypeIndex.getFiles(PropertiesFileType.INSTANCE, GlobalSearchScope.projectScope(project))

        for (vf: VirtualFile in virtualFiles) {
            // 查找java可以直接使用JavaPsiFacade,不需要通过FileTypeIndex
            val psiFile = PsiManager.getInstance(project).findFile(vf) as PropertiesFile

            // 也可以使用PsiTreeUtil.getChildrenOfType获取children, 具体的关系可以借助PsiViewer插件进行查看
            psiFile.findPropertyByKey(key)?.let { result.add(it as PsiElement)}
        }
        return result
    }
}