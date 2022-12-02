package com.lzy.demo.jpa;

import com.lzy.demo.jpa.dao.SimpleJpaDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles({"druid", "jpa"})
public class DruidTest {

    @Resource
    private SimpleJpaDao simpleJpaDao;

    /**
     * 测试druid,访问http://127.0.0.1:8080/druid/sql.html
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testDruidMonitor() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() ->
                simpleJpaDao.findTopByOrderByAgeDesc(), 0, 10L, TimeUnit.SECONDS);
        Thread.sleep(1000000);
    }
}
