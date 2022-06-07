package com.lzy.demo.base.exec;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

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
     * 使用zt-exec
     * 无法执行多条命令,比如ls|grep a,需要将命令写成shell脚本,然后直接调用shell脚本
     *
     * @throws Exception Exception
     * @see <a href="https://github.com/zeroturnaround/zt-exec">zt-exec</a>
     */
    @Test
    public void testExecZT() throws Exception {
        System.out.println(new ProcessExecutor()
                //指定工作目录
                .directory(new File(".."))
                //命令和参数需要分开
                .command("ls", "-l")
                .redirectError(Slf4jStream.of(getClass()).asInfo())
                .readOutput(true).execute()
                .outputUTF8());
    }
}
