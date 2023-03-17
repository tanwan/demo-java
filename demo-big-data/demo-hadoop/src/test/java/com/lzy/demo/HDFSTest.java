package com.lzy.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * HDFS测试
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HDFSTest {
    private FileSystem fileSystem;

    /**
     * 测试Configuration
     */
    @Test
    public void testConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addResource("simple-configuration.xml");
        assertEquals(10, configuration.getInt("size", 0));
    }

    @BeforeAll
    public void init() throws IOException {
        // 设置hadoop用户,否则默认取跑该代码的用户名
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://hadoop-pseudo:9820");
        fileSystem = FileSystem.get(configuration);
    }

    /**
     * 测试创建文件夹
     *
     * @throws IOException the io exception
     */
    @Test
    public void testMkDir() throws IOException {
        Path path = new Path("/simpleDir");
        fileSystem.mkdirs(path);
    }

    /**
     * 测试显示文件
     *
     * @throws IOException the io exception
     */
    @Test
    public void testList() throws IOException {
        list(new Path("/"));
    }


    /**
     * 测试上传文件
     *
     * @throws IOException the io exception
     */
    @Test
    public void upload() throws IOException {
        // 源路径
        Path src = new Path(this.getClass().getClassLoader().getResource("logback.xml").toString());
        // 目的路径
        Path desc = new Path("/simpleDir/logback.xml");
        fileSystem.copyFromLocalFile(src, desc);
    }

    /**
     * 测试下载文件
     *
     * @throws IOException the io exception
     */
    @Test
    public void download() throws IOException {
        // 源路径
        Path src = new Path("/simpleDir/logback.xml");
        // 目标路径
        Path desc = new Path(System.getProperty("user.home") + "/Desktop/logback.xml");
        fileSystem.copyToLocalFile(src, desc);
    }

    private void list(Path path) throws IOException {
        FileStatus[] fileStatuses = fileSystem.listStatus(path);
        for (int i = 0; i < fileStatuses.length; i++) {
            FileStatus fileStatus = fileStatuses[i];
            if (fileStatus.isDirectory()) {
                System.out.println("目录:" + fileStatus.getPath());
                list(fileStatus.getPath());
            } else {
                System.out.println("文件:" + fileStatus.getPath());
            }
        }
    }

}
