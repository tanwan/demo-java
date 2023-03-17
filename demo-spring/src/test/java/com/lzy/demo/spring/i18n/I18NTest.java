package com.lzy.demo.spring.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 国际化测试
 *
 * @author lzy
 * @version v1.0
 * @see MessageSourceAutoConfiguration
 */
@TestPropertySource(properties = {"spring.messages.basename=i18n/messages"})
@SpringJUnitConfig({MessageSourceAutoConfiguration.class})
public class I18NTest {

    /**
     * 测试
     *
     * @param messageSource the message source
     */
    @Test
    public void testI18N(@Autowired MessageSource messageSource) {
        //Locale.getDefault()=en,CN
        assertEquals("英文", messageSource.getMessage("code", null, Locale.getDefault()));
        //相当于是使用Locale.getDefault()
        assertEquals("英文", messageSource.getMessage("code", null, null));
        assertEquals("中文,中国", messageSource.getMessage("code", null, Locale.CHINA));
        //使用中划线
        assertEquals("英文,美国", messageSource.getMessage("code", null, Locale.forLanguageTag("en-US")));
        //这边会是中文
        assertEquals("中文", messageSource.getMessage("code", null, Locale.forLanguageTag("zh")));
        assertEquals("英文", messageSource.getMessage("code", null, Locale.forLanguageTag("en")));
    }

    /**
     * 测试默认值
     *
     * @param messageSource the message source
     * @see MessageSourceAutoConfiguration
     */
    @Test
    public void testDefault(@Autowired MessageSource messageSource) {
        //查不到指定的语言资源时,取决fallbackToSystemLocale的值
        //这边fallbackToSystemLocale等于true,所以先查找messages_en.properties,再查找message.properties
        assertEquals("英文", messageSource.getMessage("code", null, Locale.FRANCE));
        assertEquals("默认", messageSource.getMessage("code.default", null, Locale.CHINA));
    }

    /**
     * 测试不存在的值
     *
     * @param messageSource the message source
     * @see MessageSourceAutoConfiguration
     */
    @Test
    public void testNoExist(@Autowired MessageSource messageSource) {
        //获取不存在的值会抛出NoSuchMessageException
        assertThatExceptionOfType(NoSuchMessageException.class).isThrownBy(() -> messageSource.getMessage("code.noexist", null, Locale.CHINA));
    }

    /**
     * 测试带参数
     *
     * @param messageSource the message source
     * @see MessageSourceAutoConfiguration
     */
    @Test
    public void testWithParam(@Autowired MessageSource messageSource) {
        //插值使用{0},{1},比如中文,中国,参数1:{0},参数2:{1}
        assertEquals("中文,中国,参数1:args1,参数2:args2", messageSource.getMessage("code.param", new Object[]{"args1", "args2"}, Locale.CHINA));
    }
}
