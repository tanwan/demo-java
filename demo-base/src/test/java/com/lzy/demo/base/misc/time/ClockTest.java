package com.lzy.demo.base.misc.time;

import org.junit.jupiter.api.Test;

import java.time.*;

/**
 * 时钟测试类
 *
 * @author LZY
 * @version v1.0
 * @see Clock时钟,LocalDate,LocalTime,LocalDateTime的now方法默认使用Clock
 * @see Instant瞬间时间
 * @see ZoneId时区(使用地区表示)
 * @see ZoneOffset时区(使用偏移表示)
 * @see ZonedDateTime包含时区的日期时间
 */
public class ClockTest {
    /**
     * 上海时区
     */
    private ZoneId shanghaiZone = ZoneId.of("Asia/Shanghai");
    /**
     * 菲尼克斯时区
     */
    private ZoneId phoenixZone = ZoneId.of("America/Phoenix");
    /**
     * 与UTC时区偏移的区域 +8:00表示北京时间
     */
    private ZoneOffset zoneOffset = ZoneOffset.of("+08:00");

    /**
     * 测试时区
     */
    @Test
    public void testZone() {
        System.out.println("Shanghai:" + shanghaiZone);
        System.out.println("Phoenix:" + phoenixZone);
        System.out.println("zoneOffset:" + zoneOffset);
    }

    /**
     * 测试时钟
     *
     * @throws Exception the exception
     */
    @Test
    public void testClock() throws Exception {
        //使用ZoneOffset创建时钟
        Clock shanghaiClock = Clock.system(zoneOffset);
        //使用ZoneId创建时钟
        Clock phoenixClock = Clock.system(phoenixZone);
        //返回值是动态的
        System.out.println("Shanghai:" + shanghaiClock.millis());
        Thread.sleep(1000);
        System.out.println("Shanghai:" + shanghaiClock.millis());
        System.out.println("Phoenix:" + phoenixClock.millis());
        //固定时钟
        Clock fixedClock = Clock.fixed(Instant.now(), zoneOffset);
        System.out.println("fixedClock" + fixedClock);
        Thread.sleep(1000);
        System.out.println("fixedClock" + fixedClock);
        //使用clock获取instant,因为instant不关时区,所以两者是一样的
        System.out.println("Shanghai instant:" + Instant.now(shanghaiClock));
        System.out.println("Phoenix instant:" + Instant.now(shanghaiClock));
        //使用clock获取时区时间,LocalDateTime跟时区相关,所以两者不一样
        System.out.println("Shanghai LocalDateTime:" + LocalDateTime.now(shanghaiClock));
        System.out.println("Phoenix LocalDateTime:" + LocalDateTime.now(phoenixClock));
    }

    /**
     * 测试instant
     */
    @Test
    public void testInstant() {
        //瞬时时间,相当于java.util.Date
        Instant instant = Instant.now();
        System.out.println("instant:" + instant);
        //转成秒
        System.out.println("instant seconds:" + instant.getEpochSecond());
        //转成毫秒
        System.out.println("instant milli:" + instant.toEpochMilli());
        //设置时区
        System.out.println("instant at Zone:" + instant.atZone(phoenixZone));
        //从毫秒转成instant
        System.out.println("instant from milli:" + Instant.ofEpochMilli(System.currentTimeMillis()));
    }
}
