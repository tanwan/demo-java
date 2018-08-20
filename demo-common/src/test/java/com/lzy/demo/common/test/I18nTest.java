/*
 * Created by LZY on 2017/3/16 22:03.
 */
package com.lzy.demo.common.test;

import com.lzy.demo.common.util.I18nUtils;
import org.junit.Test;

/**
 * 国际化测试
 *
 * @author LZY
 * @version v1.0
 */
public class I18nTest {
    @Test
    public void test() throws InterruptedException {
        System.out.println("lang:null,message: " + I18nUtils.getMessage("code", null));
        System.out.println("lang:null,message: " + I18nUtils.getMessage("code2", null));
        System.out.println("lang:zh,message: " + I18nUtils.getMessage("code", "zh"));
        System.out.println("lang:zh,message: " + I18nUtils.getMessage("code2", "zh"));
        System.out.println("lang:zh-CN,message: " + I18nUtils.getMessage("code", "zh-CN"));
        System.out.println("lang:en,message: " + I18nUtils.getMessage("code", "en"));
        System.out.println("lang:en-US,message: " + I18nUtils.getMessage("code", "en-US"));
        System.out.println("lang:fr,message: " + I18nUtils.getMessage("code", "fr"));
    }
}
