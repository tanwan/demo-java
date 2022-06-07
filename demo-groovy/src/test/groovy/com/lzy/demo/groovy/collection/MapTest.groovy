package com.lzy.demo.groovy.collection

import org.junit.jupiter.api.Test

class MapTest {


    /**
     * map声明
     */
    @Test
    void testDeclare() {
        //使用[k:v]声明map,[:]表示空map
        def emptyMap = [:]
        assert emptyMap.size() == 0
        // 相当于emptyMap.size()>0
        assert !emptyMap

        //使用[k:v]声明map
        def map = [k1: 'v1', k2: 'v2']
        println("map:${map}")
        assert map.getClass() == LinkedHashMap

        def k1 = 'k1 value'
        def k2 = 'k2 value'

        //使用[(k):v]声明,k1,k2变量的值作为key
        map = [(k1): 'v1', (k2): 'v2']
        println("map:$map")
    }

    /**
     * 基础操作
     */
    @Test
    void testBase() {
        // 增加
        def map = [:]
        map.put('k1', 'v1')
        // 使用map[key]=value增加
        map['k2'] = 'v2'
        println("map:$map")
        def gstring = "${1 + 1}"
        // 不要把gstring当做key
        map.put(gstring, 'do not use GString as a key')
        assert map['2'] == null
        println("map:$map")

        // 查询
        // 使用get
        assert map.get('k1') == 'v1'
        // 使用map[key]
        assert map['k2'] == 'v2'
        // 使用map.key
        assert map.k2 == 'v2'

        // 修改
        map['k2'] = 'v2 override'
        assert map['k2'] == 'v2 override'

    }

    /**
     * 遍历
     */
    @Test
    void testIterating() {
        def map = [
                k1: 'v1',
                k2: 'v2'
        ]

        // each: 使用entry遍历
        map.each { entry ->
            println "entry: key: $entry.key value: $entry.value"
        }

        // each: 使用key和value遍历
        map.each { key, value ->
            println "key,value: key: $key value: $value"
        }

        // eachWithIndex: 索引加entry遍历
        map.eachWithIndex { entry, i ->
            println "eachWithIndex entry: $i - key: $entry.key value: $entry.value"
        }

        // eachWithIndex: 索引加key,value遍历
        map.eachWithIndex { key, value, i ->
            println "eachWithIndex key,value: $i - key: $key value: $value"
        }


        // collect相当于map再collect
        def newMap = map.collect { k, v -> "override$v" }
        println("newMap:$newMap")
    }

    /**
     * 测试find
     */
    @Test
    void testFind() {
        def people = [
                k1: 'v1',
                k2: 'v2',
                k3: 'vv3',
                k4: 'vv3'
        ]

        // find: 查找第一个符合的entry
        def find = people.find { it.value == 'vv3' }
        println("find:$find")
        // findAll: 查找所有符合的entry
        def findAll = people.findAll { it.value == 'vv3' }
        println("findAll:$findAll")
        // 所有元素都要符合
        assert people.every { k, v -> v.length() > 1 }

        // 任何一个符合
        assert people.any { k, v -> v.length() > 2 }
    }
}
