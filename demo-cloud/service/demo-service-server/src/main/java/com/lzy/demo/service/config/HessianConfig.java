package com.lzy.demo.service.config;

import com.lzy.demo.service.service.SimpleHessianService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
@Profile("hessian")
public class HessianConfig {


    /**
     * simpleHessianService,bean名为调用的url的名称
     *
     * @param simpleHessianService the simple hessian service
     * @return the hessian service exporter
     */
    @Bean("/simpleHessianService")
    public HessianServiceExporter simpleHessianService(SimpleHessianService simpleHessianService) {
        HessianServiceExporter exporter = new HessianServiceExporter();
        //设置service
        exporter.setService(simpleHessianService);
        exporter.setServiceInterface(SimpleHessianService.class);
        return exporter;
    }

}
