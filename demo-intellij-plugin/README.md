# demo-intellij-plugin
## 创建项目
- 使用idea创建项目File > New > Project > IDE Plugin
- 使用[IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)(比较全面)
  可以直接clone再进行修改, 也可以直接利用github直接从该template创建出项目仓库

## 启动和调试
执行(debug)gradle的task "runIde", 然后会启动一个idea

## 本地安装插件
执行gradle的task "buildPlugin", 在build/distributions会有该插件的zip包,然后使用Install Plugin from Disk进行安装

## 自定义语言
安装PsiViewer和Grammar-Kit插件
参考https://plugins.jetbrains.com/docs/intellij/custom-language-support-tutorial.html

## 参考
- https://plugins.jetbrains.com/docs/intellij/developing-plugins.html