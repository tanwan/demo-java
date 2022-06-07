package com.lzy.demo.gradle.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

public class SimpleJavaPluginTask extends DefaultTask {
    //不暴露的字段需要使用@Internal,要暴露给configure的字段,需要使用@Input
    @Internal
    private Project project;

    @Inject
    public SimpleJavaPluginTask(Project project) {
        this.project = project;
    }

    /**
     * 入口
     */
    @TaskAction
    public void process() {
        System.out.println("SimpleJavaPluginTask" + project.getExtensions().getByName("javaPlugin"));
    }

    //@Internal的字段需要有getter
    @Override
    public Project getProject() {
        return project;
    }
}
