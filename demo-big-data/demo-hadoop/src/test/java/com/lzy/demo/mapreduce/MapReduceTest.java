package com.lzy.demo.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * map reduce测试
 *
 * @author lzy
 * @version v1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MapReduceTest {

    /**
     * 设置用户
     */
    @BeforeAll
    public void init() {
        System.setProperty("HADOOP_USER_NAME", "root");
    }

    /**
     * 测试单词统计
     *
     * @throws Exception the exception
     */
    @Test
    public void testLocalWordCount() throws Exception {
        // 这边不加/前缀,相对于working directory
        // input和output都是目录
        WordCount.execute(new String[]{"input", "out/output"}, null);
    }

    /**
     * 在hdfs上执行单词统计
     * 这种模式其实并没有提交到远程的hadoop去执行,只是利用了远程的hdfs,map reduce任务其实就是在本地执行的
     *
     * @throws Exception the exception
     */
    @Test
    public void testHadoopWordCount() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop-pseudo:9820");
        // 使用/前缀,表示相对于HDFS的根目录
        // 如果不加/,相对于/user/root
        // input和output都是目录
        WordCount.execute(new String[]{"/input", "/output"}, conf);
    }

    /**
     * 把任务提交到远程的hadoop执行
     *
     * @throws Exception the exception
     */
    @Test
    public void testRealHadoopWordCount() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://hadoop-pseudo:9820/");
        // 使用yarn
        conf.set("mapreduce.framework.name", "yarn");

        // 设置yarn地址
        conf.set("yarn.resourcemanager.address", "hadoop-pseudo:8032");

        // 只能使用全路径,不能使用$HADOOP_HOME代替
        conf.set("yarn.app.mapreduce.am.env", "HADOOP_MAPRED_HOME=/opt/hadoop-3.2.0");
        conf.set("mapreduce.map.env", "HADOOP_MAPRED_HOME=/opt/hadoop-3.2.0");
        conf.set("mapreduce.reduce.env", "HADOOP_MAPRED_HOME=/opt/hadoop-3.2.0");

        // 这边需要将程序打成jar包,然后指定jar路径(相对于working directory)
        conf.set("mapred.jar", "build/libs/demo-hadoop-1.0.jar");
        WordCount.execute(new String[]{"/input", "/output"}, conf);
    }
}
