package com.lzy.demo.gradle.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

import javax.inject.Inject

/**
 * 需要实现Plugin接口,需要在META-INFO/gradle-plugins创建一个文件名是插件名的properties文件
 * 有应用java-gradle-plugin,此文件会自动生成,也可以使用task pluginDescriptors去生成
 *
 * @author lzy
 * @version v1.0
 */
class SimpleGroovyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {

        //添加extension
        project.extensions.create('groovyPlugin', SimpleInfo)

        //添加task
        def task = project.tasks.create('simpleGroovyPluginTask', SimpleGroovyPluginTask)
        task.setGroup('demo-plugin')
    }
}

class SimpleGroovyPluginTask extends DefaultTask {
    //不暴露的字段需要使用@Internal,要暴露给configure的字段,需要使用@Input
    @Internal
    Project project

    @Inject
    SimpleGroovyPluginTask(Project project) {
        this.project = project
    }

    @TaskAction
    def process() {
        println('SimpleGroovyPluginTask execute:' + project.extensions.getByName('groovyPlugin'))
    }
}