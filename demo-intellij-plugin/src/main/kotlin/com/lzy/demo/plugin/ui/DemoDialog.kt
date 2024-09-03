package com.lzy.demo.plugin.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import com.intellij.util.ui.JBUI
import com.lzy.demo.plugin.configuration.DemoState
import java.time.LocalDateTime
import javax.swing.JComponent

/**
 * 对话框
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/dialog-wrapper.html">dialog</a>
 */
class DemoDialog(project: Project?) : DialogWrapper(project) {
    var username: String = ""
    var password: String = ""
    private var panel = createPanel(DemoState.getInstance())

    init {
        title = "Demo Dialog"
        // 设置ok健的文本
        setOKButtonText("Login")
        init()
    }

    override fun createCenterPanel(): JComponent {
        panel.preferredSize = JBUI.size(300, panel.preferredSize.getHeight().toInt())
        return panel
    }

    private fun createPanel(state: DemoState?): DialogPanel {
        return panel {
            row("Exist username:") {
                label(state?.username ?: "")
            }

            row("Exist password:") {
                label(state?.password ?: "")
            }

            row("Username:") {
                textField()
                    // 需要调用此panel.apply(),才能绑定
                    .bindText(::username)
                    .focused()

            }

            row("Password:") {
                passwordField().bindText(::password)
            }
        }
    }


    /**
     * 点击OK键会先进行校验, 这边还是有问题, 校验失败, 重新输入还是无法点击OK键
     */
    override fun doValidate(): ValidationInfo? {
        // 需要调用apply文本框才能绑定
        panel.apply()
        if (username.isEmpty()) {
            return ValidationInfo("The username can't be empty!", null)
        }
        return if (password.isEmpty()) {
            ValidationInfo("The password can't be empty!", null)
        } else super.doValidate()
    }

    // 校验成功后执行
    override fun doOKAction() {

        val state = DemoState.getInstance()
        if (state != null) {
            state.username = username
            state.password = password
            state.dateTime = LocalDateTime.now()
        }
        super.doOKAction()
    }
}