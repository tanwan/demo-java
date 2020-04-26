/*
 * Created by lzy on 2019/8/27 8:39 AM.
 */
package com.lzy.demo.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 单词统计主类
 *
 * @author lzy
 * @version v1.0
 */
public class WordCount {
    /**
     * 设置working directory和Program arguments(input out/output)
     *
     * @param args the input arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        execute(args, null);
    }


    /**
     * 如果输出文件存在的话,需要清理,gradle的clean已经进行拓展
     *
     * @param args the args
     * @param conf the conf
     * @throws Exception the exception
     */
    public static void execute(String[] args, Configuration conf) throws Exception {
        System.out.println("in:" + args[0] + " out:" + args[1]);
        conf = conf == null ? new Configuration() : conf;
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCount.class);
        // map类
        job.setMapperClass(WordCountMap.class);
        // map输出key类型
        job.setMapOutputKeyClass(Text.class);
        // map输出value类型
        job.setMapOutputValueClass(IntWritable.class);


        // 每一个map可能会产生大量的输出,Combiner的作用就是在map端对输出先做一次合并,以减少传输到reducer的数据量
        // Combiner最基本是实现本地key的归并,Combiner具有类似本地的reduce功能
        // 如果不用Combiner,那么,所有的结果都是reduce完成,效率会相对低下
        // 使用Combiner,先完成的Map会在本地聚合,提升速度
        // Combiner的输入是Map的输出,Combiner的输出是Reducer的输入,因此Combiner绝不能改变最终的计算结果
        job.setCombinerClass(WordCountReduce.class);


        // reduce类
        job.setReducerClass(WordCountReduce.class);
        // 输出key类型
        job.setOutputKeyClass(Text.class);
        // 输出值类型
        job.setOutputValueClass(IntWritable.class);

        // 输入路径
        FileInputFormat.addInputPath(job, new Path(args[0]));
        // 输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
