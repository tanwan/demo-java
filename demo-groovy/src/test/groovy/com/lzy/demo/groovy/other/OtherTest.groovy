package com.lzy.demo.groovy.other

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

/**
 * 一些杂项
 *
 * @author lzy
 * @version v1.0
 */
class OtherTest {

    @TempDir
    File testProjectDir

    /**
     * 调用console打开文件
     */
    @Test
    void testOpenConsole() {
        def tmpFile = new File(testProjectDir, 'tmp')
        tmpFile << 'this is a test temp file'
        // 使用命令行调用ocnsole打开文件
        "open -a Console  ${tmpFile.getAbsolutePath()}".execute()
        Thread.sleep(1000)
    }


}
