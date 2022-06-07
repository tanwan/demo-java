package com.lzy.demo.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class HBaseTest {

    private static final String ZOOKEEPER = "hadoop-pseudo:2181";

    private Admin admin;

    private Connection connection;

    /**
     * 初始化
     *
     * @throws IOException the io exception
     */
    @BeforeAll
    public void init() throws IOException {
        // hadoop的配置要求需要有hadoop.home.dir或者HADOOP_HOME,因此这边直接随便设一个值
        System.setProperty("hadoop.home.dir", "/");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", ZOOKEEPER);
        connection = ConnectionFactory.createConnection(conf);
        admin = connection.getAdmin();
    }

    /**
     * 关闭
     *
     * @throws IOException the io exception
     */
    @AfterAll
    public void close() throws IOException {
        connection.close();
        admin.close();
    }

    /**
     * 测试列出表
     *
     * @throws IOException the io exception
     */
    @Test
    public void testListTables() throws IOException {
        // 列出表名
        System.out.println("----table name----");
        Arrays.stream(admin.listTableNames()).forEach(System.out::println);
        // 列出表名(正则)
        System.out.println("----table name with pattern----");
        Arrays.stream(admin.listTableNames(Pattern.compile("^t.*"))).forEach(System.out::println);
        // 列出表定义
        System.out.println("----table descriptors----");
        admin.listTableDescriptors()
                .forEach(t -> System.out.println(t.getTableName()));
        // 列出表定义(正则)
        System.out.println("----table descriptors pattern----");
        admin.listTableDescriptors(Pattern.compile("^t.*"))
                .forEach(t -> System.out.println(t.getTableName()));
        // 列出表定义(表名)
        System.out.println("----table descriptors table name----");
        admin.listTableDescriptors(Collections.singletonList(TableName.valueOf("test")))
                .forEach(t -> System.out.println(t.getTableName()));
    }

    /**
     * 测试表是否存在
     *
     * @throws IOException the io exception
     */
    @Test
    public void testExistTables() throws IOException {
        Assertions.assertThat(admin.tableExists(TableName.valueOf("test")))
                .isEqualTo(true);
        Assertions.assertThat(admin.tableExists(TableName.valueOf("no_exist")))
                .isEqualTo(false);
    }

    /**
     * 测试创建表
     *
     * @throws IOException the io exception
     */
    @Test
    public void testCreateTable() throws IOException {
        //定义表名,创建在default下,如果需要指定namespace,则使用namespace:table
        TableName tableName = TableName.valueOf("test");
        TableDescriptorBuilder tableDescriptor = TableDescriptorBuilder.newBuilder(tableName);
        //构建列族对象
        ColumnFamilyDescriptor family = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("data")).build();
        //设置列族
        tableDescriptor.setColumnFamily(family);
        //创建表
        admin.createTable(tableDescriptor.build());
    }

    /**
     * 测试添加列族
     *
     * @throws IOException the io exception
     */
    @Test
    public void testAddColumn() throws IOException {
        //构建列族对象
        ColumnFamilyDescriptor family = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("data2")).build();
        TableName tableName = TableName.valueOf("test");
        admin.addColumnFamily(tableName, family);
    }

    /**
     * 测试新增
     *
     * @throws IOException the io exception
     */
    @Test
    public void testPut() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        // 创建put对象
        Put put = new Put(Bytes.toBytes("row1"));
        // 添加列
        put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("name"), Bytes.toBytes("lzy"))
                .addColumn(Bytes.toBytes("data"), Bytes.toBytes("age"), Bytes.toBytes(23L));
        table.put(put);
    }


    /**
     * 判断相等,则修改,并返回true,不相等不修改,返回false
     *
     * @throws IOException the io exception
     */
    @Test
    public void testCheckAndMutate() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("age"), Bytes.toBytes(25L));
        boolean result = table.checkAndMutate(Bytes.toBytes("row1"), Bytes.toBytes("data"))
                //指定列
                .qualifier(Bytes.toBytes("name"))
                //判断是否与这个串相等
                .ifEquals(Bytes.toBytes("lzy"))
                //相等就put返回true,否则不put返回false
                .thenPut(put);
        System.out.println(result);
    }


    /**
     * 测试自增(原子操作),需要保证要自增的列是Long型的
     *
     * @throws IOException the io exception
     */
    @Test
    public void testIncrement() throws IOException {
        Table table = connection.getTable(TableName.valueOf("default:test"));
        Increment increment = new Increment(Bytes.toBytes("row1"));
        increment.addColumn(Bytes.toBytes("data"), Bytes.toBytes("age"), 10L);
        table.increment(increment);
    }

    /**
     * 测试获取
     *
     * @throws IOException the io exception
     */
    @Test
    public void testGet() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        Get get = new Get("row1".getBytes());
        // 指定列
        // get.addColumn(Bytes.toBytes("data"),Bytes.toBytes("name"));
        // 指定列族
        // get.addFamily(Bytes.toBytes("data"));
        // 读取所有的版本
        // get.readAllVersions();
        Result result = table.get(get);
        result.listCells().forEach(cell -> {
            // 需要把byte转化为各自的类型,否则会序列化失败或者乱码
            System.out.println("family:" + Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength()));
            System.out.println("qualifier:" + Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength()));
            System.out.println("value:" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            System.out.println("timestamp:" + cell.getTimestamp());
        });
    }

    /**
     * 测试删除
     *
     * @throws IOException the io exception
     */
    @Test
    public void testDelete() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        Delete del = new Delete(Bytes.toBytes("row1"));
        // 删除最后一个版本的记录
        //del.addColumn(Bytes.toBytes("data"),Bytes.toBytes("name"));
        // 删除所有版本的记录
        del.addColumns(Bytes.toBytes("data"), Bytes.toBytes("name"))
                .addColumns(Bytes.toBytes("data"), Bytes.toBytes("age"));
        table.delete(del);
    }

    /**
     * 测试scan
     *
     * @throws IOException the io exception
     */
    @Test
    public void testScan() throws IOException {
        Table table = connection.getTable(TableName.valueOf("test"));
        Scan scan = new Scan()
                .withStartRow(Bytes.toBytes("row1"))
                .setBatch(2);
        ResultScanner resultScanner = table.getScanner(scan);
        for (Result result : resultScanner) {
            result.listCells().forEach(cell -> {
                System.out.println("value:" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            });
        }
    }


    /**
     * 测试批量插入
     *
     * @throws Exception the exception
     */
    @Test
    public void testBatchPut() throws Exception {
        Table table = connection.getTable(TableName.valueOf("test"));
        Put put1 = new Put(Bytes.toBytes("row2"))
                .addColumn(Bytes.toBytes("data"), Bytes.toBytes("name"), Bytes.toBytes("lzy"))
                .addColumn(Bytes.toBytes("data"), Bytes.toBytes("age"), Bytes.toBytes("23"));
        Put put2 = new Put(Bytes.toBytes("row3"))
                .addColumn(Bytes.toBytes("data"), Bytes.toBytes("name"), Bytes.toBytes("lzy"))
                .addColumn(Bytes.toBytes("data"), Bytes.toBytes("age"), Bytes.toBytes("23"));

        List<Row> list = Arrays.asList(put1, put2);

        Object[] results = new Object[list.size()];
        table.batch(list, results);
        for (Object result : results) {
            System.out.println(result);
        }
    }

    /**
     * 测试批量删除
     *
     * @throws Exception the exception
     */
    @Test
    public void testBatchDelete() throws Exception {
        Table table = connection.getTable(TableName.valueOf("test"));
        Delete delete1 = new Delete(Bytes.toBytes("row2"))
                .addColumns(Bytes.toBytes("data"), Bytes.toBytes("name"))
                .addColumns(Bytes.toBytes("data"), Bytes.toBytes("age"));
        Delete delete2 = new Delete(Bytes.toBytes("row3"))
                .addColumns(Bytes.toBytes("data"), Bytes.toBytes("name"))
                .addColumns(Bytes.toBytes("data"), Bytes.toBytes("age"));

        List<Row> list = new ArrayList<>();
        list.add(delete1);
        list.add(delete2);
        Object[] results = new Object[list.size()];
        table.batch(list, results);
        for (Object result : results) {
            System.out.println(result);
        }
    }

    /**
     * 测试批量获取
     *
     * @throws Exception the exception
     */
    @Test
    public void testBatchGet() throws Exception {
        Table table = connection.getTable(TableName.valueOf("test"));
        Get get1 = new Get(Bytes.toBytes("row2"));
        Get get2 = new Get(Bytes.toBytes("row3"));

        List<Row> list = Arrays.asList(get1, get2);
        Object[] results = new Object[list.size()];
        table.batch(list, results);
        //这边的result是org.apache.hadoop.hbase.client.Result
        for (Object result : results) {
            Result r = (Result) result;
            System.out.println("row:" + Bytes.toString(r.getRow()));
            r.listCells().forEach(cell -> {
                System.out.println("value:" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            });
        }
    }
}
