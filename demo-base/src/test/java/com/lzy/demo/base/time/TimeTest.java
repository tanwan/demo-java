package com.lzy.demo.base.time;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * @author LZY
 * @version v1.0
 * @see LocalDate         2017-01-01
 * @see LocalTime                      23:23:23
 * @see LocalDateTime     2017-01-01T23:23:23
 * @see OffsetTime                     23:23:23+01:00
 * @see OffsetDateTime    2017-01-01T23:23:23+01:00
 * @see ZonedDateTime     2017-01-01T23:23:23 Asia/Shanghai
 * @see Year              2017
 * @see YearMonth         2017-01
 * @see MonthDay              -01-01
 * @see Instant
 */
public class TimeTest {


    /**
     * 测试LocalDate
     */
    @Test
    public void testLocalDate() {
        LocalDate today = LocalDate.now();
        System.out.printf("today:%s,year:%d,month:%d,day:%d%n", today, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        //日期加
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        System.out.println("tomorrow:" + tomorrow);
        //日期减
        LocalDate yesterday = today.minusDays(1);
        System.out.println("yesterday:" + yesterday);
    }


    /**
     * 测试DayOfWeek,MonthDay,YearMonth
     */
    @Test
    public void testDay() {
        //生成指定的日期
        LocalDate birthday = LocalDate.of(2017, Month.JANUARY, 1);
        //获取星期
        DayOfWeek dayOfWeek = birthday.getDayOfWeek();
        System.out.println(dayOfWeek); //Tuesday

        //获取生日 MM-dd
        MonthDay monthDay = MonthDay.from(birthday);
        System.out.println("birthday:" + monthDay);

        //获取出生年月 yyyy-MM
        YearMonth yearMonth = YearMonth.from(birthday);
        System.out.println("birthday:" + yearMonth);
    }

    /**
     * 测试日期计算
     */
    @Test
    public void testCalculate() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);

        //判断日期前后
        System.out.println("today before tomorrow:" + today.isBefore(tomorrow));

        //判断两个日期之间的天数
        System.out.println("hour between today and tomorrow:" + ChronoUnit.DAYS.between(today, tomorrow));
        Period period = Period.between(today, tomorrow);
        System.out.println("hour between today and tomorrow:" + period.getDays());

        //获取当前时间
        LocalTime now = LocalTime.now();
        System.out.printf("now:%s,hour:%d,minute:%d,second:%d%n", now, now.getHour(), now.getMinute(), now.getSecond());

        //获取当前日期和时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("localDateTime:" + localDateTime);
    }
}
