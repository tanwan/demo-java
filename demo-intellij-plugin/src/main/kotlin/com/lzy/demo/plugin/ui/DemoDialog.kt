package com.lzy.demo.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UI
import com.lzy.demo.plugin.configuration.DemoState
import org.apache.commons.lang.StringUtils
import java.time.LocalDateTime
import javax.swing.*


/**
 * 对话框
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/dialog-wrapper.html">dialog</a>
 */
class DemoDialog(project: Project?) : DialogWrapper(project) {
    private val nameField: JTextField = JBTextField()
    private val passwordField: JTextField = JPasswordField()

    init {
        title = "Demo Dialog"
        // 设置ok健的文本
        setOKButtonText("Login")
        init()
    }

    override fun createCenterPanel(): JComponent {
        val state = DemoState.getInstance()
        val panel = UI.PanelFactory.grid()
            .add(UI.PanelFactory.panel(JLabel(state?.username)).withLabel("Exist username:"))
            .add(UI.PanelFactory.panel(JLabel(state?.password)).withLabel("Exist password:"))
            .add(UI.PanelFactory.panel(nameField).withLabel("Username:"))
            .add(UI.PanelFactory.panel(passwordField).withLabel("Password:"))
            .createPanel()
        panel.preferredSize = JBUI.size(300, panel.preferredSize.getHeight().toInt())

        return panel
    }


    /**
     * 点击OK键会先进行校验
     */
    override fun doValidate(): ValidationInfo? {
        if (StringUtils.isBlank(nameField.text)) {
            return ValidationInfo("The username can't be empty!", nameField)
        }
        return if (StringUtils.isBlank(passwordField.text)) {
            ValidationInfo("The password can't be empty!", passwordField)
        } else super.doValidate()
    }

    // 校验成功后执行
    override fun doOKAction() {
        val state = DemoState.getInstance()
        state?.apply {
            username = nameField.text
            password = passwordField.text
            dateTime = LocalDateTime.now()
        }
        super.doOKAction()
    }
}