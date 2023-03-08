package com.lzy.demo.plugin.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.actionSystem.Separator
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.ui.Splitter
import com.intellij.openapi.util.Key
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.SimpleTree
import com.intellij.ui.treeStructure.SimpleTreeStructure
import com.intellij.util.ui.UI
import com.lzy.demo.plugin.configuration.DemoState
import com.lzy.demo.plugin.extension.Extension
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.tree.TreePath


class DemoToolWindow(project: Project) : SimpleToolWindowPanel(true), Disposable, DumbAware {

    init {
        val actionManager = ActionManager.getInstance()
        // panel的工具栏的按钮
        val group = DefaultActionGroup()
        group.add(actionManager.getAction("com.lzy.demo.plugin.DemoAction"))
        // Separator可以在多个按钮之间加个分隔符
        group.add(Separator.create())
        // panel的工具栏
        val toolbar: ActionToolbar = ActionManager.getInstance().createActionToolbar("Demo Tool Bar", group, false)
        // 设置panel的工具栏
        toolbar.targetComponent = this
        this.toolbar = toolbar.component

        // 设置panel的内容(上部分是tree, 下部分是panel)
        // tree的root节点
        val root = MyNode(project)
        // StructureTreeModel: 只需要知道根节点,子节点通过根节点计算出来
        // 如果不使用DemoToolWindow,而是直接在DemoToolWindowFactory中,直接使用SimpleToolWindowPanel创建的话
        // 那么StructureTreeModel和AsyncTreeModel需要的Disposable参数,可以使用ContentFactory.getInstance().createContent创建出来的content
        val treeModel = StructureTreeModel(SimpleTreeStructure.Impl(root), this)

        // 创建tree
        val tree = SimpleTree(AsyncTreeModel(treeModel, this))
        // 隐藏root
        tree.isRootVisible = false

        val key = Key.create<List<TreePath>>("DemoPlugin.SelectedNode")
        // 为这个tree设置选中监听(还可以设置其它的监听)
        tree.selectionModel.addTreeSelectionListener { tsl ->
            // 可以将数据保存到project
            project.putUserData(key, tsl.paths.toList())
        }

        // 下半部分
        val state = DemoState.getInstance()
        // 获取扩展
        val extensionData = Extension.getExtensions().map { it.provideData() }.toString()
        val panel = UI.PanelFactory.grid()
            .add(UI.PanelFactory.panel(JLabel(state?.username)).withLabel("Exist username:"))
            .add(UI.PanelFactory.panel(JLabel(state?.password)).withLabel("Exist password:"))
            .add(UI.PanelFactory.panel(JLabel(extensionData)).withLabel("Extension data:"))
            .createPanel()

        // Splitter用来将窗口按水平或者垂直分成两个部分
        val splitter = Splitter(true, 0.7f)
        // tree使用JBScrollPane包装
        splitter.firstComponent = JBScrollPane(tree)
        splitter.secondComponent = panel
        this.add(splitter)
    }

    override fun dispose() {
        println("DemoToolWindow dispose")
    }
}