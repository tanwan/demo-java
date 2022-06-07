package com.lzy.demo.base.jvm.classfile;

/**
 * 反编译这个类当示例
 *
 * @author LZY
 * @version v1.0
 */
public class ClassFile extends SuperClass implements ExtendInterface {

    private static final String staticParam = "stasticStr";

    private String plainParam;

    /**
     * 只定义,而不在此类使用,常量池中不会有这个字段的CONSTANT_NameAndType
     */
    private String noUseParam;

    /**
     * 有定义,并且有初始化,常量池中就会有这个字段的CONSTANT_NameAndType
     */
    private int intNumber1 = 5;
    private int intNumber2 = 127;
    private int intNumber3 = 128;

    private float floatNumber = 2.3f;

    private long longNumber = 1L;

    private double doubleNumber = 2.5d;

    private ReferenceClass referenceClass;

    private ReferenceInterface referenceInterface;

    static {
        System.out.print(staticParam);
    }

    public ClassFile() {
        //由于这里引用了ClassFile(String) 因此常量池中会有<init>:(Ljava/lang/String;)V这个CONSTANT_NameAndType
        this("hello world");
    }

    public ClassFile(String plainParam) {
        //常量池中的<init>:()V这个CONSTANT_NameAndType这个其实是父类的构造函数
        //因为在这个构造函数里,默认调用了super()(每个构造函数中,如果没有调用本类的构造函数,那么就默认调用父类的构造函数)
        this.plainParam = plainParam;
    }

    /**
     * 这个方法在这个类没有被引用,因此常量池中并不会有这个方法的CONSTANT_NameAndType,
     * 但是这个方法里的引用的类和方法会进入常量池中
     *
     * @param var1 the var 1
     * @param var2 the var 2
     */
    public void method(String var1, int var2) {
        //-2147483648~-32769和32768~2147483647使用ldc指令入栈,这个范围内的整数才会进入常量池
        int a = 32768;
        //常量池中会有ReferenceClass这个类和method方法的CONSTANT_NameAndType
        referenceClass = new ReferenceClass();
        referenceClass.method();
        referenceInterface.method();
    }
}
