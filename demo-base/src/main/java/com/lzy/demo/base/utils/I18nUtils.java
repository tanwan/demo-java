package com.lzy.demo.base.utils;

import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

public class I18nUtils {
    private static final String PROPERTIES_SUFFIX = ".properties";
    private static ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

    static {
        Set<String> set = new HashSet<>();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<String> basenames = Arrays.asList("classpath*:demo-i18n/message", "demo-i18n/error");
        try {
            //可以使用这段代码重写setBasenames方法
            for (String basename : basenames) {
                if (basename.startsWith(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX)) {
                    Resource[] resources = resolver.getResources(basename + PROPERTIES_SUFFIX);
                    for (Resource resource : resources) {
                        String fileName = resource.getFilename();
                        String defaultFileName;
                        if (fileName.contains("_")) {
                            defaultFileName = fileName.substring(0, fileName.indexOf("_")).replace(PROPERTIES_SUFFIX, "");
                        } else {
                            defaultFileName = fileName.replace(PROPERTIES_SUFFIX, "");
                        }
                        set.add(resource.getURI().toString().replace(fileName, defaultFileName));
                    }
                } else {
                    set.add(basename);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageSource.setBasenames(set.toArray(new String[0]));
    }

    public static String getMessage(String code, String lang) {
        return getMessage(code, lang, null);
    }

    public static String getMessage(String code, String lang, Object[] args) {
        String message;
        try {
            if (StringUtils.hasLength(lang)) {
                //语言全部换成中划线
                lang = lang.replace("_", "-");
                Locale locale = Locale.forLanguageTag(lang);
                message = messageSource.getMessage(code, args, locale);
            } else {
                message = messageSource.getMessage(code, args, Locale.getDefault());
            }
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
            message = "";
        }
        return message;
    }
}
