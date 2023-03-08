plugins {
    // java
    id("java")
    // kotlin
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.intellij")
}

// See https://github.com/JetBrains/intellij-platform-plugin-template/blob/main/build.gradle.kts

// See https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    // 开发此插件使用的ide版本
    version.set("2022.2.3")
    // IC表示IntelliJ IDEA Community Edition
    type.set("IC")

    // 插件使用的依赖, 使用<PluginId>[:Version], 内置的插件不需要声明版本, 如果内置插件拥有名称的话,则可以直接使用名称
    // See https://plugins.jetbrains.com/docs/intellij/plugin-dependencies.html
    // java插件 See https://github.com/JetBrains/intellij-community/tree/master/java plugin.xml在下面的plugin目录下
    // intellij其它内置的插件 See https://github.com/JetBrains/intellij-community/tree/master/plugins
    plugins.set(listOf("com.intellij.java", "org.jetbrains.kotlin", "com.intellij.properties"))
}

kotlin {
    // 2020.3+使用java11, 2022.2+使用java17
    jvmToolchain(17)
}
tasks {

    patchPluginXml {
        // 21.3可以使用此插件
        sinceBuild.set("213")
        // 22.3.*都可以使用,但是更新的则不行, 没有定义则表示更新的版本都可以使用
        //untilBuild.set("223.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}