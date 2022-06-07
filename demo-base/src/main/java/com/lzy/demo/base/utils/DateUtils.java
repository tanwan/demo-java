package com.lzy.demo.base.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static final String SIMPLE_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date转LocalDateTime
     *
     * @param date the date
     * @return the local date time
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date转LocalDate
     *
     * @param date the date
     * @return the local date
     */
    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDate转Date
     *
     * @param localDate the local date
     * @return the date
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime the local date time
     * @return the date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取 yyyy-MM-dd 格式的当前日期
     *
     * @return now iso date
     */
    public static String getNowIsoDate() {
        return DateTimeFormatter.ISO_DATE.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间,并格式化
     *
     * @param pattern the pattern
     * @return now format
     */
    public static String getNowFormat(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 格式化成iso-date(yyyy-MM-dd)
     *
     * @param temporalAccessor temporalAccessor
     * @return string string
     */
    public static String formatIsoDate(TemporalAccessor temporalAccessor) {
        return DateTimeFormatter.ISO_DATE.format(temporalAccessor);
    }

    /**
     * 格式化日期
     *
     * @param temporalAccessor the temporal accessor
     * @param pattern          the pattern
     * @return the string
     */
    public static String format(TemporalAccessor temporalAccessor, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(temporalAccessor);
    }

    /**
     * 格式化成 yyyy-MM-dd HH:mm:ss
     *
     * @param temporalAccessor the temporalAccessor
     * @return the string
     */
    public static String formatSimplateDateTime(TemporalAccessor temporalAccessor) {
        return format(temporalAccessor, SIMPLE_DATE_TIME_PATTERN);
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss 转成LocalDateTime
     *
     * @param text the text
     * @return the local date time
     */
    public static LocalDateTime parseSimpleDateTimeToLocalDateTime(String text) {
        return parseDateTimeToLocalDateTime(text, SIMPLE_DATE_TIME_PATTERN);
    }

    /**
     * 把text按照指定的样式转换成LocalDateTime
     *
     * @param text    the text
     * @param pattern the pattern
     * @return the local date time
     */
    public static LocalDateTime parseDateTimeToLocalDateTime(String text, String pattern) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当月最后一天
     *
     * @param localDate the local date
     * @return the last date of month
     */
    public static LocalDate getLastDateOfMonth(LocalDate localDate) {
        YearMonth yearMonth = YearMonth.from(localDate);
        return LocalDate.of(localDate.getYear(), localDate.getMonth(), yearMonth.lengthOfMonth());
    }

    /**
     * 获取下个月第一天
     *
     * @param localDate the local date
     * @return the local date
     */
    public static LocalDate getNextMonthFirstDate(LocalDate localDate) {
        LocalDate newLocalDate = localDate.plus(1, ChronoUnit.MONTHS);
        return LocalDate.of(newLocalDate.getYear(), newLocalDate.getMonth(), 1);
    }


    /**
     * 两个日期之间的天数,结果有正有负,第一个日期加上结果等于第二个日期
     *
     * @param localDate1 the local date 1
     * @param localDate2 the local date 2
     * @return the long
     */
    public static long daysBetween(LocalDate localDate1, LocalDate localDate2) {
        return ChronoUnit.DAYS.between(localDate1, localDate2);
    }

    /**
     * 获取星期
     *
     * @param localDate the local date
     * @return the week of date
     */
    public static int getWeekOfDate(LocalDate localDate) {
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * 获取星期的显示名
     *
     * @param localDate the local date
     * @param textStyle TextStyle.FULL 星期一,TextStyle.Narrow 一
     * @return week of date
     */
    public static String getWeekOfDateDispayName(LocalDate localDate, TextStyle textStyle) {
        return localDate.getDayOfWeek().getDisplayName(textStyle, Locale.CHINA);
    }
}
