package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.builder.ConcreteBuilder1;
import com.lzy.demo.design.pattern.builder.ConcreteBuilder2;
import com.lzy.demo.design.pattern.builder.Director;
import com.lzy.demo.design.pattern.builder.Product;
import org.junit.jupiter.api.Test;

/**
 * 测试建造者
 *
 * @author LZY
 * @version v1.0
 */
public class BuilderTest {

    /**
     * 使用具体构建者1创建
     */
    @Test
    public void testBuilderUseConcreteBuilder1() {
        Director director1 = new Director(new ConcreteBuilder1());
        Product product1 = director1.buildProduct();
        System.out.println(product1);

    }

    /**
     * 使用具体构建者2创建
     */
    @Test
    public void testBuilderUseConcreteBuilder2() {
        Director director2 = new Director(new ConcreteBuilder2());
        Product product2 = director2.buildProduct();
        System.out.println(product2);
    }


}
