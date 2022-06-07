package com.lzy.demo.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;

public class SimpleJavaPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        //添加Extension
        project.getExtensions().create("javaPlugin", SimpleInfo.class);

        //添加task
        Task task = project.getTasks().create("simpleJavaPluginTask", SimpleJavaPluginTask.class);
        task.setGroup("demo-plugin");
    }
}
