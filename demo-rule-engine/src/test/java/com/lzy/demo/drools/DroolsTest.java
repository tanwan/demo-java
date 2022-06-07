package com.lzy.demo.drools;

import com.lzy.demo.drools.model.Request;
import com.lzy.demo.drools.model.Result;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroolsTest {

    private final KieSession kieSession = new DroolsBeanFactory().getKieSession();


    /**
     * 测试基本用法
     */
    @Test
    public void testBase() {
        // 在工作内存中插入fact
        Request req = new Request(1, "rule1");
        kieSession.insert(req);
        Result result = new Result();
        kieSession.setGlobal("ret", result);
        kieSession.fireAllRules();
        assertEquals("rule1", result.getValue());
        assertEquals("rule1", req.getResult());
    }

    /**
     * 测试salience(相当于是规则的优先级)
     */
    @Test
    public void testSalience() {
        kieSession.insert(new Request(-1, "rule2"));
        kieSession.fireAllRules();
    }

    /**
     * 测试在drool使用update/insert
     */
    @Test
    public void testInsertUpdate() {
        kieSession.insert(new Request(0, "rule3"));
        kieSession.fireAllRules();
    }
}
