package com.lzy.demo.atomikos;

import com.lzy.demo.atomikos.first.FirstSimpleAtomikosDao;
import com.lzy.demo.atomikos.second.SecondSimpleAtomikosDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
@SpringBootApplication
public class SpringAtomikosJPATest {


    @Resource
    private AtomikosService atomikosService;

    @Resource
    private FirstSimpleAtomikosDao firstDao;

    @Resource
    private SecondSimpleAtomikosDao secondDao;


    /**
     * 测试插入成功
     */
    @Test
    public void testInsertSuccess() {
        String content = UUID.randomUUID().toString();
        atomikosService.insert(content, false);

        Assertions.assertThat(firstDao.findByName(content)).isNotNull();
        Assertions.assertThat(secondDao.findByName(content)).isNotNull();
    }


    /**
     * 测试回滚
     */
    @Test
    public void testRollback() {
        String content = UUID.randomUUID().toString();
        try {
            atomikosService.insert(content, true);
        } catch (Exception ignore) {
        }

        Assertions.assertThat(firstDao.findByName(content)).isNull();
        Assertions.assertThat(secondDao.findByName(content)).isNull();
    }

}
