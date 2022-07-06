package com.lzy.demo.base.misc.time;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterTest {

    /**
     * 测试日期格式化
     */
    @Test
    public void testFormatter() {
        LocalDate today = LocalDate.now();

        //ISO_LOCAL_DATE
        System.out.println("ISO_LOCAL_DATE:" + LocalDate.parse("1993-03-23"));

        //BASIC_ISO_DATE
        System.out.println("BASIC_ISO_DATE:" + LocalDate.parse("19930323", DateTimeFormatter.BASIC_ISO_DATE));

        //自定义formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Custom format:" + LocalDate.parse("1993-03-23", formatter));

        //输出格式化时间
        System.out.println("today:" + today.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

}
