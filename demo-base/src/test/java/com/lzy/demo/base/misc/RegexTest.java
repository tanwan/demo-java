package com.lzy.demo.base.misc;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest {

    private static final Pattern CHINESE_WHIT_SYMBOL_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+.*([^\\x00-\\xff])+|[\\u4e00-\\u9fa5]+");

    /**
     * 中文
     */
    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]+");

    /**
     * 匹配//左边的字符串
     */
    private static final Pattern SINGLE_COMMENT_PATTERN = Pattern.compile("[^'\"]*(?=//)");

    /**
     * ?表示非贪婪,\\1表示第一个捕获组
     */
    private static final Pattern QUOTE_NO_GREEDY_PATTERN = Pattern.compile("([\"']).*?\\1");

    /**
     * .*是贪婪匹配的,\\1表示第一个捕获组
     */
    private static final Pattern QUOTE_GREEDY_PATTERN = Pattern.compile("([\"']).*\\1");

    /**
     * 零宽度正先行断言
     */
    private static final Pattern MULTI_COMMENT_BEGIN_PATTERN = Pattern.compile("[^'\"]*(?=/\\*)");

    /**
     * 零宽度正回顾后发断言
     */
    private static final Pattern MULTI_COMMENT_END_PATTERN = Pattern.compile("(?<=\\*/)[^'\"]*");


    /**
     * 测试Matches
     */
    @Test
    public void testMatches() {
        String testStr = "贪玩";
        Matcher matcher = CHINESE_PATTERN.matcher(testStr);
        // matches方法是将字符串从开头到结尾,进行匹配,匹配成功即为true,反之为false
        assertTrue(matcher.matches());
        // 将Matcher重置,相当于将匹配的位置重置为字符串开头
        matcher.reset();
        // find方法是将字符串从当前位置(首次调用的位置为字符串开头,后续调用的位置为上一次匹配后的位置)
        // 当匹配成功时,可以调用Matcher#group()获取出匹配的字符串
        assertTrue(matcher.find());
        testStr = "贪玩and玩贪";
        matcher = CHINESE_PATTERN.matcher(testStr);
        // 整个字符串不能匹配,所以是false
        assertFalse(matcher.matches());
        matcher.reset();
        // 从字符串开头开始匹配,匹配到贪玩后,就返回true
        assertTrue(matcher.find());
        // 当find方法返回true时,可以使用group获取到匹配的字符串,这边就是贪玩
        System.out.println(matcher.group());
        // 再继续调用find方法时,就是从a开始匹配,当匹配到玩贪的时候,则返回true
        assertTrue(matcher.find());
        // 这边就是玩贪
        System.out.println(matcher.group());
    }

    /**
     * 测试贪婪匹配
     */
    @Test
    public void testGreedy() {
        String testStr = "\"test\"\"test\"";
        // 贪婪匹配,尽可能的匹配所有文字,因此,这边只会输出一行"test""test"
        printGroup(QUOTE_GREEDY_PATTERN.matcher(testStr));
        // 非贪婪匹配,最小匹配文字,因此,这边会输出两行"test"
        printGroup(QUOTE_NO_GREEDY_PATTERN.matcher(testStr));
    }

    /**
     * 测试零宽度正先行断言
     */
    @Test
    public void testPositiveLookahead() {
        String testStr = "abcd/*";
        // 这边可以匹配出abcd/*,但是group只会获取出abcd
        printGroup(MULTI_COMMENT_BEGIN_PATTERN.matcher(testStr));
    }

    /**
     * 测试零宽度正回顾后发断言
     */
    @Test
    public void testPositiveLookbehind() {
        String testStr = "*/abcd";
        // 这边可以匹配出*/abcd,但是group只会获取出abcd
        printGroup(MULTI_COMMENT_END_PATTERN.matcher(testStr));
    }

    /**
     * 测试append
     */
    @Test
    public void testAppend() {
        Pattern p = Pattern.compile("cat");
        String str = "one cat two cats in the yard";
        Matcher matcher = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            // start表示这次循环匹配开始的索引,end表示这次循环匹配结束的索引
            System.out.println(str.substring(matcher.start(), matcher.end()));
            // 可以理解为sb.append(str.substring(lastAppendPosition,matcher.start())).append(str.substring(matcher.start(),matcher.end()).replace("cat","dog"))
            // lastAppendPosition初始为0,后续等于上一次循环的matcher.end()的值
            // 由于$1,$2...是用来引用正则表达式里面的分组的,因此在替换的时候,$需要进行转义
            matcher.appendReplacement(sb, "\\$dog");
            System.out.println("after replace:" + sb);
        }
        // 理解为sb.append(lastAppendPosition,str.substring(matcher.end(),matcher.length())
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }


    private void printGroup(Matcher matcher) {
        // find方法
        while (matcher.find()) {
            //
            System.out.println(matcher.group());
        }
    }
}
