package com.lzy.demo.groovy.io

import groovy.json.JsonGenerator
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.junit.jupiter.api.Test

/**
 *
 * @author lzy
 * @version v1.0
 */
class JSONTest {

    /**
     * 解析json字符串
     */
    @Test
    void testParse() {
        def jsonSlurper = new JsonSlurper()
        def json = ''' 
         {     
              "name": "lzy", 
              "list": [1,2,3],
              "integer" :3,
              "bigDecimal": 2.33
         } 
         // 注释只能在最后
         /* 注释只能在最后 */
        '''
        def object = jsonSlurper.parseText(json)

        assert object instanceof Map
        assert object.name == 'lzy'
        assert object.list == [1, 2, 3]
        assert object.integer == 3
        // 浮点转为BigDecimal
        assert object.bigDecimal instanceof BigDecimal
    }

    /**
     * 转json
     */
    @Test
    void testToJSON() {

        def json = JsonOutput.toJson([new SimpleClass(name: 'class1'), new SimpleClass(name: 'class2')])
        println("json:$json")


        // 自定义输出
        def generator = new JsonGenerator.Options()
                .excludeNulls()
                .dateFormat('yyyy-MM-dd HH:mm:ss')
                .build()

        json = generator.toJson([new SimpleClass(date: new Date()), new SimpleClass(name: 'class2')])
        // 格式化输出
        json = JsonOutput.prettyPrint(json)
        println("pretty json:$json")
    }

    class SimpleClass {
        String name
        Date date
    }
}
