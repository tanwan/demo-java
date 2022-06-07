package com.lzy.demo.groovy.collection

import org.junit.jupiter.api.Test

class ListTest {

    /**
     * list声明
     */
    @Test
    void testDeclare() {
        // 使用[]声明
        def emptyList = []
        // 相当于emptyList.size()>0
        assert !emptyList
        assert emptyList.size() == 0

        // 直接有初始值
        def list = [1, 2, 3, 4, 5]
        // 类型是ArrayList
        assert list.getClass() == ArrayList
    }

    /**
     * list基础操作
     */
    @Test
    void testBase() {
        def list = []
        // 增加
        list.add(1)
        list.add(2)
        // 使用<<增加,调用leftShift
        list << 3 << 4

        // 查询

        // get和[index](调用getAt)都可以获取值,get是List的方法,所以获取不存在的索引会抛出异常,而[index]则返回null
        assert list.get(1) == 2
        assert list[1] == 2
        //list[-index]:反向取值,list[-1]表示最后一个元素
        assert list[-1] == 4

        // list[index1,...indexn] 返回由索引组成的新list
        assert list[1, 3] == [2, 4]

        // 修改

        //[index](调用putAt)可以修改值,修改索引不存在的会为size到index-1插入null,然后为index赋值
        list[2] = 5
        //set修改值会返回旧值,它是list的方法,所以不允许修改不存在的索引
        assert list.set(0, 2) == 1
        // 可以直接判断list的元素是否相同
        assert list == [2, 2, 5, 4]
    }


    /**
     * 遍历
     */
    @Test
    void testIterate() {
        [1, 2, 3].each { println "Item: $it" }
        // eachWithIndex使用索引迭代,it是元素,i是索引
        ['a', 'b', 'c'].eachWithIndex { it, i -> println "$i: $it" }

        // collect相当于map再collect
        assert [1, 2, 3].collect { it * 2 } == [2, 4, 6]
    }

    /**
     * list切片
     */
    @Test
    void testSlice() {
        def list = [1, 2, 3, 4, 5]
        //使用list[begin..end]进行切片list[begin]~list[end]
        println("list slice: ${list[2..4]}")
    }

    /**
     * list查找
     */
    @Test
    void testFind() {
        def list = [1, 2, 3]

        // find:查找第一个符合的元素
        assert list.find { it > 1 } == 2
        // findAll:查找所有符合的元素
        assert list.findAll { it > 1 } == [2, 3]
        // findIndexOf: 查找第一个符合的元素的索引
        assert ['a', 'b', 'c', 'd', 'e'].findIndexOf { it == 'c' } == 2

        // every: 所有元素都要符合
        assert list.every { it < 5 }
        // any: 任意一个元素符合
        assert list.any { it > 2 }


        // in: 相当于contains
        assert 1 in list
        // containsAll: 判断给元素都需要在list中
        assert list.containsAll([1, 2])
    }

    /**
     * 操作
     */
    @Test
    void testOperations() {
        def numberList = [1, 2, 3, 4, 5]
        def stringList = ['a', 'b', 'c', 'd']

        // sum: 求和
        assert numberList.sum() == 15
        // sum: 字符直接进行连接
        assert stringList.sum() == 'abcd'

        // sum: 传入闭包,相当于先进行map,再进行sum, 数字求和,字符串连接
        assert numberList.sum {
            it * 2
        } == 30

        // sum: 传入初始值
        assert numberList.sum(1000) == 1015

        // join: 连接元素
        assert numberList.join('-') == '1-2-3-4-5'

        // jnject: 就是reduce,传入初始值
        assert [1, 2, 3].inject('counting: ') { str, item -> str + item } == 'counting: 123'

        // max: 最大值
        assert numberList.max() == 5
        // min: 最小值
        assert numberList.min() == 1

        // max和min都可以传入闭包,相当于先map
        assert numberList.max { -it } == 1

        // count: 给定元素出现的次数,可以传入闭包
        assert numberList.count(1) == 1


        // +(调用plus): 为list增加元素,拼接list
        assert [1, 2] + 3 + [4, 5] + 6 == [1, 2, 3, 4, 5, 6]
        def list = [1, 2]
        // 调用+=
        assert (list += [3, 4]) == [1, 2, 3, 4]

        list = [1, 2, 3, 4, 3, 2, 1]
        // -: 为list删除元素
        assert (list -= 3) == [1, 2, 4, 2, 1]
        assert (list -= [2, 4]) == [1, 1]

        // intersect: 交集
        assert numberList.intersect([1, 3, 6, 9, 12]) == [1, 3]
    }

    /**
     * 排序
     */
    @Test
    void testSort() {
        def numberList = [1, 3, 2, 5, 4]
        def stringList = ['abc', 'z', 'xyzuvw', 'Hello', '321']
        // sort: 排序
        assert numberList.sort() == [1, 2, 3, 4, 5]
        // sour传入闭包,相当于先map,再排序
        assert stringList.sort { it.size() } == ['z', 'abc', '321', 'Hello', 'xyzuvw']

        Comparator comparator = { a, b -> a == b ? 0 : Math.abs(a) < Math.abs(b) ? -1 : 1 }
        numberList = [6, -3, 9, 2, -7, 1, 5]
        // 自定义排序
        numberList.sort(comparator)
        assert numberList == [1, 2, -3, 5, 6, -7, 9]
    }
}
