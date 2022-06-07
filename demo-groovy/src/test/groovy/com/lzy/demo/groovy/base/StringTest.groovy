package com.lzy.demo.groovy.base

import org.junit.jupiter.api.Test

/**
 * 字符串
 * @author lzy
 */
class StringTest {


    /**
     * 三引号
     */
    @Test
    void testTripleQuote() {
        def variable = 'vvvv'
        // 这种首尾会多换行
        def tripleDoubleQuote = """
        aaa
        $variable
        """
        println("$tripleDoubleQuote")
        // 去除缩进
        println("${tripleDoubleQuote.stripIndent()}")
        // 这种stripIndent没有去除缩进
        tripleDoubleQuote = """aaa
        $variable"""
        println("$tripleDoubleQuote")
        println("${tripleDoubleQuote.stripIndent()}")

        // 这种去除缩进也没有换行,\用来去掉首行的换行
        tripleDoubleQuote = """\
        aaa
        $variable"""
        println("$tripleDoubleQuote")
        println("${tripleDoubleQuote.stripIndent()}")
    }

    /**
     * 正则
     */
    @Test
    void testRegex() {
        // ~string表示为正则表达式
        def regex = ~'test regex .*'

        // =~获取match
        def match = "haha test regex haha" =~ regex
        // match在判断中出现,相当于会调用match.find
        assert match
        println(match.group())

        // ==~: 判断字符串是否匹配正则
        assert "test regex haha" ==~ regex
    }

    /**
     * 执行命令行
     */
    @Test
    void testExecute() {
        def process = 'ls -l'.execute()
        // 使用in eachLine
        process.in.eachLine { line ->
            println "process.in.eachLine:$line"
        }
        process = 'ls -l'.execute()
        // 使用text获取全部输出
        println "process.text:$process.text"

        process = 'ls -l'.execute()

        // 如果不能及时写入输入流或读取子进程的输出流,有可能会导致子进程阻塞,所以这边使用consumeProcessOutput
        // 使用输出流来消费执行的输出,也可以使用StringBuffer
        process.consumeProcessOutput(System.out, System.out)
        process.waitFor()
    }

}
