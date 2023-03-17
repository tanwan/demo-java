package com.lzy.demo.gradle.plugin;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.gradle.internal.impldep.org.junit.Assert.assertEquals;
import static org.gradle.internal.impldep.org.junit.Assert.assertTrue;

/**
 * 测试java plugin
 * See https://docs.gradle.org/current/userguide/test_kit.html#sec:testkit_usage
 *
 * @author lzy
 * @version v1.0
 */
public class JavaPluginTest {

    @TempDir
    private File testProjectDir;
    private File settingsFile;
    private File buildFile;

    @BeforeEach
    public void init() {
        settingsFile = new File(testProjectDir, "settings.gradle");
        buildFile = new File(testProjectDir, "build.gradle");
    }

    /**
     * test java plugin,测试还是使用spock方便
     *
     * @throws IOException IOException
     */
    @Test
    public void testJavaPlugin() throws IOException {
        writeFile(settingsFile, "rootProject.name = 'test'");
        String buildFileContent = "plugins {\n" +
                "id 'com.lzy.demo.simple-java-plugin' version '1.0'\n" +
                "}\n" +
                "javaPlugin{\n" +
                "name = 'javaPlugin'\n" +
                "info = 'java plugin test'\n" +
                "}";
        writeFile(buildFile, buildFileContent);

        BuildResult result = GradleRunner.create()
                //设置为true,用debug运行就可以调试
                .withDebug(true)
                .withPluginClasspath()
                .withProjectDir(testProjectDir)
                .withArguments("simpleJavaPluginTask")
                .build();

        assertTrue(result.getOutput().contains("javaPlugin"));
        assertEquals(TaskOutcome.SUCCESS, result.task(":simpleJavaPluginTask").getOutcome());
    }

    private void writeFile(File destination, String content) throws IOException {
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination))) {
            output.write(content);
        }
    }
}
