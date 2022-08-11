package com.lzy.demo.design.pattern;

import com.lzy.demo.design.pattern.iterator.Aggregate;
import com.lzy.demo.design.pattern.iterator.ConcreteAggregate;
import com.lzy.demo.design.pattern.iterator.Iterator;
import org.junit.jupiter.api.Test;

/**
 * 测试迭代器
 *
 * @author lzy
 * @version v1.0
 */
public class IteratorTest {

    /**
     * 测试迭代器
     */
    @Test
    public void testIterator() {
        Aggregate<String> aggregate = new ConcreteAggregate<>(new String[]{"one", "two", "three"});
        Iterator<String> iter = aggregate.getIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
