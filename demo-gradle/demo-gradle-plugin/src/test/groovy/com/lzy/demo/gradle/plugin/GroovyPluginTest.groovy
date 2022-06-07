package com.lzy.demo.gradle.plugin

import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification
import spock.lang.TempDir

/**
 * 测试groovy plugin
 * See https://docs.gradle.org/current/userguide/test_kit.html#sec:testkit_usage
 *
 * @author lzy
 * @version v1.0
 */
class GroovyPluginTest extends Specification {

    @TempDir
    File testProjectDir
    File settingsFile
    File buildFile

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
    }

    /**
     * test groovy plugin
     */
    def "groovy plugin test"() {
        given:
        settingsFile << "rootProject.name = 'test'"
        buildFile << """
           plugins {
               id 'com.lzy.demo.simple-groovy-plugin' version '1.0'
           }
         
            groovyPlugin {
                name = 'groovyPlugin'
                info = 'groovy plugin test'
            }
        """

        when:
        def result = GradleRunner.create()
        //设置为true,用debug运行就可以调试
                .withDebug(true)
                .withPluginClasspath()
                .withProjectDir(testProjectDir)
                .withArguments('simpleGroovyPluginTask')
                .build()

        then:
        result.output.contains('groovyPlugin')
        result.task(":simpleGroovyPluginTask").outcome == TaskOutcome.SUCCESS
    }
}
