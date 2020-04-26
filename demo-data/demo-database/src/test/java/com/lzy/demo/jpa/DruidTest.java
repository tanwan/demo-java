/*
 * Created by lzy on 2019-08-23 16:11.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SimpleJpaDaoSimple;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试druid
 *
 * @author lzy
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-druid.yml")
public class DruidTest {

    @Resource
    private SimpleJpaDaoSimple simpleJpaDao;

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
