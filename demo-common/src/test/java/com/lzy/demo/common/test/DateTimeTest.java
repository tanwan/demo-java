/*
 * Created by lzy on 17-6-15 下午12:36.
 */
package com.lzy.demo.common.test;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

/**
 * The type Date time test.
 *
 * @author lzy
 * @version v1.0
 */
public class DateTimeTest {

    /**
     * Test day between.
     */
    @Test
    public void testDayBetween() {
        LocalDate localDate = LocalDate.now();
        LocalDate localDate2 = LocalDate.of(2017, 6, 14);
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2 = LocalDateTime.of(2017, 6, 15, 23, 23, 23);
        //不同类型只能获取共同的属性 LocalDate没有Hour,所以不能获取Hour
        System.out.println(ChronoUnit.DAYS.between(localDate2, localDateTime1));
        System.out.println(ChronoUnit.HOURS.between(localDateTime2, localDateTime1));

        YearMonth yearMonth = YearMonth.from(localDateTime1);
        System.out.println(yearMonth.lengthOfMonth());
    }
}
