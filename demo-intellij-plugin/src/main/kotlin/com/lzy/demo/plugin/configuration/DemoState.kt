package com.lzy.demo.plugin.configuration

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import com.lzy.demo.plugin.converter.LocalDateTimeConverter
import java.time.LocalDateTime

/**
 * 用来持久化配置, 需要在plugin.xml注册
 * 简单的一些配置,则可以直接使用PropertiesComponent.getInstance()(应用级), PropertiesComponent.getInstance(Project)(project级)
 * 存储路径
 *   application-level:
 *     1. ${idea.config.path}/options/demo-state.xml, idea.config.path见https://intellij-support.jetbrains.com/hc/en-us/articles/206544519
 *     2. 在调式中, 配置存放在当时项目的build/idea-sandbox/config/options中
 *   project-level:
 *     在项目中的.idea目录下
 *
 * PersistentStateComponent的泛型是状态类,它可以是独立的java类,也可以是PersistentStateComponent的实现类
 *
 * 不要存储敏感的数据, See <a href="http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_sensitive_data.html">Persisting Sensitive Data</a>
 *
 * 如果使用@Storage(StoragePathMacros.WORKSPACE_FILE),则存储在workspace文件中
 *
 * @see <a href="https://plugins.jetbrains.com/docs/intellij/persisting-state-of-components.html#using-persistentstatecomponent">PersistentStateComponent</a>
 */
@State(name = "com.lzy.demo.plugin.DemoState", storages = [Storage("demo-state.xml")])
@Service(Service.Level.APP)
class DemoState : PersistentStateComponent<DemoState> {
    var username: String? = null

    // 如果不需要持久化,则使用@com.intellij.util.xmlb.annotations.Transient, 目前这个注解不生效,还是会持久化
    // @Transient
    var password: String? = null

    // 使用@Attribute,在存储文件中是以xml属性的形式存在
    // 使用@OptionTag,在存储文件中是以xml节点的形式存在
    @OptionTag(converter = LocalDateTimeConverter::class)
    var dateTime: LocalDateTime? = null

    /**
     * 该方法用来返回需要保存的值
     */
    override fun getState(): DemoState {
        // 如果状态类是独立类,那么这边就是返回状态类, 这边状态类是PersistentStateComponent的实现类,所以直接返回this
        return this
    }

    /**
     * 当Component加载的时候调用, 或者idea在运行期间, 此配置的文件被改动, 这个方法也会被调用
     */
    override fun loadState(state: DemoState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        /**
         * application级别的
         */
        fun getInstance(): DemoState? {
            return ApplicationManager.getApplication().getService(DemoState::class.java)
        }

        fun getInstance(project: Project?): DemoState? {
            return project?.getService(DemoState::class.java)
        }
    }
}