module com.lzy.demo.module.A {
    //导出此包底下的类
    //使用exports a to b,限制只有b能访问a
    exports com.lzy.demo.module.a.opens;
    exports com.lzy.demo.module.a.owner;

    //此包下的类可以非public的也可以反射,否则非public将无法反射
    //可以直接使用open module,表示该模块下所有包都进行opens
    opens com.lzy.demo.module.a.opens;

    //表示提供了UseService的实现类UseServiceImpl,UseServiceImpl不需要同UseService在同一个模块下
    provides com.lzy.demo.module.a.opens.UseService with com.lzy.demo.module.a.opens.UseServiceImpl;
}