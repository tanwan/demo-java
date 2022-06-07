package com.lzy.demo.groovy.io

import groovy.io.FileType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

import java.nio.charset.StandardCharsets

/**
 *
 * @author lzy
 */
class IOTest {


    def filePath = IOTest.class.getResource('/simple-file').toURI()

    def tempDir = new File(filePath).getParentFile()

    /**
     * 测试read
     */
    @Test
    void testRead() {
        def file = new File(filePath)
        // eachLine: 读取行,可以有两个入参,另一个是行号
        file.eachLine { println(it) }
        // collect: 读取所有行,转为list
        def lines = file.collect { it }
        println("lines:$lines")

        //读取字节数组
        def bytes = file.bytes
        println("bytes:$bytes.length")

        // withInputStream可以处理is,也可以使用File#newInputStream获取is(需要手动关闭is)
        file.withInputStream {}
    }

    /**
     * 测试write
     */
    @Test
    void testWrite() {
        def wirteContennt = '''\
        hello world 1
        hello world 2
        hello world 3'''.stripMargin().stripIndent()

        // 使用writer按行写入
        new File(tempDir, 'write-file-writer').withWriter(StandardCharsets.UTF_8.toString()) { writer ->
            writer.writeLine 'hello world 1'
            writer.writeLine 'hello world 2'
            writer.writeLine 'hello world 3'
        }

        // 使用<<写文件,这是追加的方法
        new File(tempDir, 'write-file-left-shift') << wirteContennt

        new File(tempDir, 'write-file-writer-left-shift').withWriter { it << wirteContennt }

        // withOutputStream可以处理os,也可以使用File#newOutputStream获取os(需要手动关闭os)
        new File(tempDir, 'write-file-os').withOutputStream {}
    }

    /**
     * 遍历文件树
     */
    @Test
    void testTraversingFileTrees() {
        // eachFile: 遍历当前文件夹, 包括文件夹, 不会递归遍历
        tempDir.eachFile { println "eachFile:$it.name" }
        // eachFileMatch: 匹配文件遍历
        tempDir.eachFileMatch(~/write.*/) { println "eachFileMatch:$it.name" }

        // eachFileRecurse:递归遍历文件夹,包括文件夹
        tempDir.eachFileRecurse { println "eachFileRecurse:$it.name" }
        // 指定类型递归遍历文件夹
        tempDir.eachFileRecurse(FileType.FILES) { println "eachFileRecurse:$it.name" }
    }
}
