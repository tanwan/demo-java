/*
 * Created by LZY on 2017/9/5 21:21.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SimpleAuditingDao;
import com.lzy.demo.jpa.entity.SimpleAuditing;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.Resource;

/**
 * 测试审计
 *
 * @author LZY
 * @version v1.0
 */
@SpringBootTest(classes = JpaApplication.class)
@TestPropertySource(properties = "spring.config.additional-location=classpath:jpa/jpa-hakari.yml")
@Commit
public class AuditionTest {
    @Resource
    private SimpleAuditingDao simpleAuditingDao;

    /**
     * 测试审计
     */
    @Test
    public void testAudition() {
        SimpleAuditing simpleAuditing = new SimpleAuditing();
        simpleAuditingDao.save(simpleAuditing);
    }
}
