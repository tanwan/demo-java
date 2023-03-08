package com.lzy.demo.plugin.navigate

import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiMethod
import org.jetbrains.kotlin.idea.refactoring.memberInfo.qualifiedClassNameForRendering
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtFunction
import javax.swing.Icon

// 搜索的元素
class GraphqlNavigationItem(val psiElement: PsiElement, private val name: String) : NavigationItem {

    // 跳转
    override fun navigate(requestFocus: Boolean) {
        if (psiElement is Navigatable) psiElement.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = (psiElement as Navigatable).canNavigate()

    override fun canNavigateToSource(): Boolean = true

    override fun getName(): String = name

    // 搜索元素的展示
    override fun getPresentation(): ItemPresentation = GraphqlItemPresentation()

    inner class GraphqlItemPresentation : ItemPresentation {
        // 展示出来的名称
        override fun getPresentableText(): String = name

        // 展示出来的路径
        override fun getLocationString(): String {
            val fileName = psiElement.containingFile?.name
            return when (psiElement) {
                is PsiMethod -> (psiElement.containingClass?.qualifiedName ?: fileName ?: "unknownFile") + "#" + psiElement.name
                is PsiClass -> psiElement.qualifiedName ?: fileName ?: "unknownFile"
                is KtFunction -> (psiElement.parent.parent as KtClass).qualifiedClassNameForRendering() + "#" + psiElement.name
                else -> "unknownLocation"
            }
        }

        override fun getIcon(unused: Boolean): Icon? = null
    }
}