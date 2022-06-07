package com.lzy.demo.mapreduce;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 单词统计Map,Mapper的4个泛型表示,输入的Key,Value,输出的Key,Value的类型
 *
 * @author lzy
 * @version v1.0
 */
public class WordCountMap extends Mapper<Object, Text, Text, IntWritable> {

    private static final IntWritable ONE = new IntWritable(1);
    private Text word = new Text();

    /**
     * @param key     行的偏移量,回车也算一个偏移量,比如第一行为apple,第二行为orange,第三行为banana,则第一个key为0,第二个key为6,第三个key为13
     * @param value   行的内容
     * @param context 上下文
     * @throws IOException          IOException
     * @throws InterruptedException InterruptedException
     */
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // StringTokenizer相当于split,如果没有传第二个参数,则以空格分隔
        StringTokenizer itr = new StringTokenizer(value.toString());
        while (itr.hasMoreTokens()) {
            word.set(itr.nextToken());
            context.write(word, ONE);
        }
    }
}
