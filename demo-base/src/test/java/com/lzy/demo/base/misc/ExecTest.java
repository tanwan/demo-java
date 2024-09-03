package com.lzy.demo.base.misc;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ExecTest {

    /**
     * 使用string执行
     * 如果有cd等指令,无法获取到结果,需要使用下面的string数组执行
     *
     * @throws IOException IOException
     */
    @Test
    public void testExecString() throws IOException {
        //使用String
//        System.out.println(IOUtils.toString(Runtime.getRuntime().exec("ls").getInputStream(), StandardCharsets.UTF_8));
        //如果有cd等指令,无法获取到结果
        System.out.println(IOUtils.toString(Runtime.getRuntime().exec("cd ..; ls").getInputStream(), StandardCharsets.UTF_8));
        //可以直接指定路径
//        System.out.println(IOUtils.toString(Runtime.getRuntime().exec("ls", null, new File("..")).getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * 使用string数组执行
     * 可以获取到包含cd的结果
     *
     * @throws IOException IOException
     */
    @Test
    public void testExecStringArray() throws IOException {
        System.out.println(IOUtils.toString(Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "cd ..; ls"}).getInputStream(), StandardCharsets.UTF_8));
    }

    /**
     * 使用commons-exec
     *
     * @throws Exception Exception
     */
    @Test
    public void testCommonsExec() throws Exception {
        CommandLine cmdLine = CommandLine.parse("ls -l");
        DefaultExecutor executor = DefaultExecutor.builder()
                .setWorkingDirectory(new File(".."))
                .get();
        executor.execute(cmdLine);
        // PumpStreamHandler可以指定输出流
        executor.setStreamHandler(new PumpStreamHandler(System.out));
    }
}
