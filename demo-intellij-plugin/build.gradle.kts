plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.intellij.platform)
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// See https://github.com/JetBrains/intellij-platform-plugin-template/blob/main/build.gradle.kts
// See https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        // 开发此插件使用的ide版本
        intellijIdeaUltimate("2023.3")
        //create(org.jetbrains.intellij.platform.gradle.IntelliJPlatformType.IntellijIdeaUltimate, "2023.3")

        // 插件使用的依赖, 使用<PluginId>[:Version], 内置的插件不需要声明版本, 如果内置插件拥有名称的话,则可以直接使用名称
        // See https://plugins.jetbrains.com/docs/intellij/plugin-dependencies.html
        // java插件 See https://github.com/JetBrains/intellij-community/tree/master/java plugin.xml在下面的plugin目录下
        // intellij其它内置的插件 See https://github.com/JetBrains/intellij-community/tree/master/plugins
        // 内置插件使用bundledPlugins,其它插件使用plugins()
        bundledPlugins(listOf("com.intellij.java", "org.jetbrains.kotlin", "com.intellij.properties"))

        // 应用一些必要的依赖
        instrumentationTools()
    }
}

kotlin {
    // 编译此插件的jdk版本, idea的版本是关联的
    jvmToolchain(17)
}


intellijPlatform {
    pluginConfiguration {
        // 使用providers.gradleProperty(key)可以获取到gradle.properties的值

        version = "1.0.0"

        ideaVersion {
            // 22.3可以使用此插件
            sinceBuild = "223"
            // 22.3.*都可以使用,但是比22.3.*更新的则不行, 没有定义则表示更新的版本都可以使用
            //untilBuild = "223.*"
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion")
            .map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }
}
