package com.lzy.demo.groovy.io

import org.junit.jupiter.api.Test
import groovy.xml.XmlSlurper
import groovy.xml.XmlUtil
import groovy.xml.XmlParser

class XMLTest {

    def xmlFile = new File(IOTest.class.getResource('/simple-xml.xml').toURI())

    /**
     * 解析xml
     */
    void parseXML(def root) {
        def item1 = root.sub.list.item[0]

        // 使用text获取元素的值, 获取出来是string类型的
        assert item1.text() == '1'
        assert item1.text().toInteger() == 1


        // 使用@attr获取属性
        assert item1.@attr1 == 'item1 attr1'
        assert item1['@attr2'] == 'item1 attr2'

        // *表示 子节点
        def item2 = root.sub.list.'*'.find { node ->
            node.name() == 'item' && node.@attr1 == 'item2 attr1'
        }
        assert item2.text() == 'item2'


        // **表示 子孙节点
        def item3 = root.'**'.find { node ->
            node.name() == 'item' && node.@attr2 == 'item3 attr2'
        }
        assert item3.text() == 'item3'

        // 使用XmlUtil(groovy.xml)序列化xml
        def xmlStr = XmlUtil.serialize(root)
        println xmlStr
    }

    /**
     * 使用XmlSlurper解析
     * 按需求值,速度较慢,消耗内存小
     * 文档格式如果修改了,需要重新初始化XmlSlurper,否则在使用时可能会有问题
     */
    @Test
    void testParseUseXmlSlurper() {
        // groovy.util.XmlSlurper已经Deprecated了,需要使用groovy.xml.XmlSlurper
        // parse: 解析文件, parseText: 解析文本
        def root = new XmlSlurper().parse(xmlFile)
        parseXML(root)
    }

    /**
     * 使用XmlParse解析
     * 一次性将xml解析成dom,速度快,消耗内存大
     * 可以同时进行读取和写入
     */
    @Test
    void testParseUseXmlParse() {
        // groovy.util.XmlParser已经Deprecated了,需要使用groovy.xml.XmlParser
        // parse: 解析文件, parseText: 解析文本
        def root = new XmlParser().parse(xmlFile)
        parseXML(root)
    }

    /**
     * 使用XmlSlurper修改xml
     * @see groovy.xml.slurpersupport.NodeChild
     */
    @Test
    void testModifyUseXmlSlurper() {
        def root = new XmlSlurper().parse(xmlFile)
        def item1 = root.sub.list.item[0]
        def item3 = root.sub.list.item[2]

        // 替换该节点的下的内容(包括子节点)
        item1.replaceBody('item1 override')
        // 属性直接赋值修改
        item1.@attr1 = 'item1 attr1 override'

        // 替换该节点
        item3.replaceNode {
            // 格式为: newNodeName(attributes,body),这边因为item下没有子节点了,所以使用text()
            'itemOverride'(it.attributes(), it.text())
        }

        // 添加节点,这边使用闭包,先执行闭包创建出节点,然后再添加 @See groovy.xml.slurpersupport.Node#appendNode
        root.sub.list.appendNode {
            // 这边有闭包,表示有子节点
            item5 {
                // 这边没有闭包,表示没有子节点
                subItem('subItem1 value', attr1: 'item5 subItem attr1', attr2: 'item5 subItem attr2')
            }
        }
        // 添加节点,这边使用文本,先解析成GPathResult,然后再添加
        root.sub.list.appendNode(new XmlSlurper().parseText('''
           <item6><subItem attr1='item6 subItem attr1'>subItem2</subItem></item6>
        '''))

        def xmlStr = XmlUtil.serialize(root)
        println xmlStr
    }

    /**
     * XmlSlurper没有提供createNode的方法
     * @see groovy.util.Node* @see groovy.util.NodeList
     */
    @Test
    void testModifyUseXmlParser() {
        def root = new XmlParser().parse(xmlFile)
        def item1 = root.sub.list.item[0]
        def item3 = root.sub.list.item[2]

        // 替换该节点的下的内容
        item1.value = 'item1 override'
        // 属性直接赋值修改
        item1.@attr1 = 'item1 attr1 override'

        // 替换该节点, 这边不能像XmlSlurper使用闭包,所以使用NodeBuilder创建出节点
        item3.replaceNode(new NodeBuilder().itemOverride(item3.attributes(), item3.text()))


        // XmlParser这边要注意的是, 子节点是NodeList类型的, 需要获取出特定的Node才能进行添加子节点, 因此这边需要指定list[0]
        // 添加节点,这边不能像XmlSlurper使用闭包,所以使用NodeBuilder创建出节点
        root.sub.list[0].append(
                new NodeBuilder().item5() {
                    subItem('subItem1 value', attr1: 'item5 subItem attr1', attr2: 'item5 subItem attr2')
                }
        )

        // 添加节点,这边使用文本
        root.sub.list[0].append(new XmlParser().parseText('''
           <item6><subItem attr1='item6 subItem attr1'>subItem2</subItem></item6>
        '''))

        def xmlStr = XmlUtil.serialize(root)
        println xmlStr
    }


}
