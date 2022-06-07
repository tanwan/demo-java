package com.lzy.demo.groovy.io

import groovy.yaml.YamlSlurper
import org.junit.jupiter.api.Test

/**
 *
 * @author lzy
 * @version v1.0
 */
class YAMLTest {

    /**
     * 解析
     */
    @Test
    void testParse() {
        def ys = new YamlSlurper()
        def yaml = ys.parseText '''
        root:
            secondary:
                 str: str
                 list: 
                    - 1
                    - 2
                 integer: 3
                 bigDecimal: 2.33

        '''
        assert yaml.root.secondary.str == 'str'
        assert yaml.root.secondary.list == [1, 2]
        assert yaml.root.secondary.integer == 3
        assert yaml.root.secondary.bigDecimal instanceof BigDecimal
    }
}
