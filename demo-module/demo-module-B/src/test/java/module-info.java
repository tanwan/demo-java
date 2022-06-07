//main的module-info.java对测试是不生效的,所以测试需要自行添加module-info.java
//module-info.java只能放在最外层
//拥有module-info.java称为具名模块,只能读取具体模块exports导出的类,要暴露类也需要使用exports,无法读取无名模块
//具名模块读取无名模块时,无名模块会自动变成自动模块,模块名按以下规则生成:先会移除文件扩展名以及版本号,然后使用.替换所有非字母字符
//没有module-info.java的为无名模块,可以读取其它所有的模块,同时也会暴露所有的类给外部
module com.lzy.demo.module.B {
    //声明依赖的模块
    //requires static:编译是必须的,运行是可选的
    //requires transitive:可以进行传递依赖
    requires org.junit.jupiter.api;
    requires org.assertj.core;

    requires com.lzy.demo.module.A;

    exports com.lzy.demo.module.b;

    //使用uses,让此接口能通过ServiceLoader.load获取到它的实现类,类似jndi
    uses com.lzy.demo.module.a.opens.UseService;
}