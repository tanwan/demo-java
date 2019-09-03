/*
 * Created by LZY on 2017/9/5 21:21.
 */
package com.lzy.demo.jpa;

import com.lzy.demo.jpa.application.JpaApplication;
import com.lzy.demo.jpa.dao.SampleAuditingDao;
import com.lzy.demo.jpa.entity.SampleAuditing;
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
@TestPropertySource(properties = "spring.config.location=classpath:jpa/jpa-hakari.yml")
@Commit
public class AuditionTest {
    @Resource
    private SampleAuditingDao sampleAuditingDao;

    /**
     * 测试审计
     */
    @Test
    public void testAudition() {
        SampleAuditing sampleAuditing = new SampleAuditing();
        sampleAuditingDao.save(sampleAuditing);
    }
}
