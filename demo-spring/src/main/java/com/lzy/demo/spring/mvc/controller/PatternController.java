package com.lzy.demo.spring.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * url模式匹配
 *
 * @author LZY
 * @version v1.0
 */
@RestController
@RequestMapping("/pattern")
public class PatternController {

    /**
     * ?匹配一个字符
     * 这个url匹配/question-mark/ab
     * 不匹配/question-mark/a
     *
     * @return the string
     */
    @GetMapping("/question-mark/a?")
    public String questionMark() {
        return "questionMark";
    }

    /**
     * *匹配多个字符,不匹配多层路径
     * 匹配 /single-asterisk/lzy
     * 不匹配 /single-asterisk/lzy/jong
     *
     * @return the string
     */
    @GetMapping("/single-asterisk/*")
    public String singleAsterisk() {
        return "singleAsterisk";
    }

    /**
     * **匹配多个字符也多层路径
     * 匹配 /double-asterisk
     * 匹配 /double-asterisk/lzy
     * 匹配 /double-asterisk/lzy/jong
     *
     * @return the string
     */
    @GetMapping("/double-asterisk/**")
    public String doubleAsterisk() {
        return "doubleAsterisk";
    }

    /**
     * 正则表达式
     * 匹配 /regular/spring-1.0.0
     * 不匹配 /regular/spring3-1.0.0,/regular/spring-1.0
     *
     * @param projectName the project name
     * @param version     the version
     * @return the string
     */
    @GetMapping("/regular/{projectName:[a-z]+}-{version:\\d\\.\\d\\.\\d}")
    public String regularExpression(@PathVariable String projectName, @PathVariable String version) {
        return projectName + version;
    }

    /**
     * 地址映射 默认匹配的后缀是.*
     * 匹配/suffix,/suffix.txt
     * 如果请求suffix.xml,suffix.pdf等,那么Content-Type也就相应地变成application/xml,application/pdf,也就是返回值就需要是xml文件,pdf文件
     *
     * @return the string
     */
    @GetMapping("/suffix.*")
    public String suffix() {
        return "suffix";
    }
}
