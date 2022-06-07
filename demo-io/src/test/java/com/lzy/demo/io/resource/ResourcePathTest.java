package com.lzy.demo.io.resource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;

import java.io.IOException;

public class ResourcePathTest {
    private static final String WORKING_DIR = System.getenv("HOME") + "/SourceCode/me/demo/demo-java/demo-io";
    private static final String CLASS_PATH = "file:" + WORKING_DIR + "/out/test/classes/";
    private static final String FILE_NAME = "tempFile.tmp";
    private static final String RESOURCE_PATH = "file:" + WORKING_DIR + "/out/test/resources/";
    private static final String FILE_PATH = RESOURCE_PATH + FILE_NAME;


    /**
     * 获取到类加载路径
     */
    @Test
    public void testClassLoaderResourceUrl() {
        Assertions.assertThat(ResourcePathTest.class.getClassLoader().getResource(""))
                .asString().isEqualTo(CLASS_PATH);
    }

    /**
     * 获取到类加载路径
     */
    @Test
    public void testClassLoaderResourceFileUrl() {
        // 从类加载的路径获取资源(不以/开头)
        Assertions.assertThat(ResourcePathTest.class.getClassLoader().getResource(FILE_NAME))
                .asString().isEqualTo(FILE_PATH);

        // 从类加载的路径获取资源(以/开头)
        // 底层使用URL(URL context, String spec),context为类路径,spec为path
        // path以/开头,表示path为绝对路径,则以path以绝对路径 获取资源
        // 使用此方法获取到的资源会判断该资源是否在类加载路径下,如果不是,返回null,因此,不能以/开头
        Assertions.assertThat(ResourcePathTest.class.getClassLoader().getResource("/" + FILE_NAME))
                .isEqualTo(null);
    }

    /**
     * 获取到当前类路径
     */
    @Test
    public void testClassResourceUrl() {
        Assertions.assertThat(ResourcePathTest.class.getResource(""))
                .asString().isEqualTo(CLASS_PATH + "com/lzy/demo/io/resource/");
    }

    /**
     * 从当前类的路径获取资源
     * 以/开头的是绝对路径(相当于类加载路径)
     */
    @Test
    public void testClassResourceFileUrl() {
        // 相当于类加载路径
        Assertions.assertThat(ResourcePathTest.class.getResource("/" + FILE_NAME))
                .asString().isEqualTo(FILE_PATH);

        //不以/开头的是相对路径(相对于当前类)
        Assertions.assertThat(ResourcePathTest.class.getResource("../../../../../" + FILE_NAME))
                .asString().isEqualTo(FILE_PATH);
    }

    /**
     * 工作路径,也就是idea Working directory
     */
    @Test
    public void testUserDir() {
        //运行jar获取到的是 命令的路径(在A路径下运行B路径下的jar包,那么user dir为A路径)
        Assertions.assertThat(System.getProperty("user.dir")).isEqualTo(WORKING_DIR);
    }

    /**
     * 使用spring的ResourceLoader的file协议
     *
     * @throws IOException the io exception
     */
    @Test
    public void testResourceLoaderFile() throws IOException {
        //spring resourceLoader
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        //使用file协议的相对路径,相当工作路径
        Resource fileResource = resourceLoader.getResource("file:./out/test/resources/" + FILE_NAME);
        Assertions.assertThat(fileResource.getFile().exists()).isEqualTo(true);
        //使用file协议的绝对路径
        Resource fileAbsoluteResource = resourceLoader.getResource(FILE_PATH);
        Assertions.assertThat(fileAbsoluteResource.getFile().exists()).isEqualTo(true);
    }

    /**
     * 使用spring的ResourceLoader的classpath协议
     */
    @Test
    public void testResourceLoaderClassPath() {
        //spring resourceLoader
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        //不加协议默认是从classpath
        Assertions.assertThat(resourceLoader.getResource(FILE_NAME))
                //使用classpath
                .isEqualTo(resourceLoader.getResource("classpath:" + FILE_NAME))
                .matches(Resource::exists);
    }

    /**
     * 使用spring的ResourceLoader的http协议
     */
    @Test
    public void testHttpResource() {
        //spring resourceLoader
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        //使用http
        Assertions.assertThat(resourceLoader.getResource("http://www.baidu.com"))
                .matches(Resource::exists);
    }

    /**
     * 使用UrlResource
     *
     * @throws Exception the exception
     */
    @Test
    public void testURLResource() throws Exception {
        //UrlResource 相对路径,相当工作路径
        Assertions.assertThat(new UrlResource("file:./out/test/resources/" + FILE_NAME)).matches(UrlResource::exists);
        // 使用相对路径
        Assertions.assertThat(new UrlResource(FILE_PATH)).matches(UrlResource::exists);
    }
}
