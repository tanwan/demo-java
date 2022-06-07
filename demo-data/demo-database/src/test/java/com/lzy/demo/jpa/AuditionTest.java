package com.lzy.demo.jpa;

import com.lzy.demo.jpa.dao.SimpleAuditingDao;
import com.lzy.demo.jpa.entity.SimpleAuditing;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest
@ActiveProfiles("jpa")
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
