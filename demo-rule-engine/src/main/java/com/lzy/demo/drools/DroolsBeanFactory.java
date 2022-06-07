package com.lzy.demo.drools;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Facts: 规则的输入数据
 * Working Memory: 带有Facts的存储, 它们用于模式匹配,可以被修改,插入和删除
 * Rule: 将Facts与匹配的规则关联起来. 可以写在.drl文件中,也可以写在excel中(Decision Table)
 * Knowledge Session: 包含触发规则所需的所有资源, 所有的Facts都插入到Session中, 然后触发匹配规则
 * Knowledge Base: 它有关于规则的资源信息,同时创建Session
 * Module: 一个模块包含多个base,一个base可以有多个session
 *
 * @author lzy
 * @version v1.0
 */
public class DroolsBeanFactory {

    private KieServices kieServices = KieServices.Factory.get();

    /**
     * 内存文件系统,用来加载规则文件
     *
     * @return KieFileSystem
     */
    private KieFileSystem getKieFileSystem() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        List<String> rules = Arrays.asList("drools/simpleRule.drl");
        for (String rule : rules) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(rule));
        }
        return kieFileSystem;
    }


    /**
     * KieContainer: 指定Module下,包含所有base的容器
     *
     * @return the kie container
     */
    public KieContainer getKieContainer() {
        KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
        kb.buildAll();

        KieModule kieModule = kb.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());
    }


    /**
     * Knowledge Session: 包含触发规则所需的所有资源, 所有的Facts都插入到Session中, 然后触发匹配规则
     *
     * @return the kie session
     */
    public KieSession getKieSession() {
        return getKieContainer().newKieSession();
    }
}
