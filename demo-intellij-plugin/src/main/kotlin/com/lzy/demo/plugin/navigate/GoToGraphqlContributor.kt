package com.lzy.demo.plugin.navigate

import com.intellij.navigation.ChooseByNameContributor
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiReferenceList
import com.intellij.psi.impl.java.stubs.index.JavaSuperClassNameOccurenceIndex
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.idea.stubindex.KotlinSuperClassIndex
import org.jetbrains.kotlin.psi.KtClassBody
import org.jetbrains.kotlin.psi.KtClassOrObject
import org.jetbrains.kotlin.psi.KtFunction

// 用来提供搜索的元素
class GoToGraphqlContributor(private var items: List<GraphqlNavigationItem> = emptyList()) :
    ChooseByNameContributor {

    // 返回可以被搜索的元素
    override fun getNames(project: Project, includeNonProjectItems: Boolean): Array<String> {
        // 在project搜索
        val globalSearchScope = GlobalSearchScope.projectScope(project)

        val javaList = mutableListOf<PsiReferenceList>()
        javaList.addAll(JavaSuperClassNameOccurenceIndex.getInstance()["GraphQLQueryResolver", project, globalSearchScope])
        javaList.addAll(JavaSuperClassNameOccurenceIndex.getInstance()["GraphQLResolver", project, globalSearchScope])
        javaList.addAll(JavaSuperClassNameOccurenceIndex.getInstance()["GraphQLMutationResolver", project, globalSearchScope])

        val javaItems = javaList.flatMap { r ->
            r.parent.children.filterIsInstance<PsiMethod>()
                .map { GraphqlNavigationItem(it, it.name) }
        }
        val kotlinList = mutableListOf<KtClassOrObject>()
        kotlinList.addAll(KotlinSuperClassIndex["GraphQLQueryResolver", project, globalSearchScope])
        kotlinList.addAll(KotlinSuperClassIndex["GraphQLResolver", project, globalSearchScope])
        kotlinList.addAll(KotlinSuperClassIndex["GraphQLMutationResolver", project, globalSearchScope])
        val kotlinItems = kotlinList.flatMap { k ->
            k.children.filterIsInstance<KtClassBody>()
                .flatMap { b ->
                    b.children.filterIsInstance<KtFunction>()
                        .map {
                            GraphqlNavigationItem(it, it.name ?: "")
                        }
                }
        }
        items = javaItems + kotlinItems
        return items.map { it.name }.distinct().toTypedArray()
    }

    // 返回过滤之后的元素
    override fun getItemsByName(
        name: String?,
        pattern: String?,
        project: Project?,
        includeNonProjectItems: Boolean
    ): Array<NavigationItem> {
        return items.filter { it.name == name }.toTypedArray()
    }
}