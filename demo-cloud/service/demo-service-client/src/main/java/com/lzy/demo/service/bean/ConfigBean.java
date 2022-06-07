package com.lzy.demo.service.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigBean {

    @Value("${business.profiles:}")
    private String businessProfile;

    @Value("${spring.jackson.serialization.indent_output:}")
    private String indentOutput;

    @Value("${application.profiles:}")
    private String applicationProfile;

    public String getBusinessProfile() {
        return businessProfile;
    }

    public void setBusinessProfile(String businessProfile) {
        this.businessProfile = businessProfile;
    }

    public String getIndentOutput() {
        return indentOutput;
    }

    public void setIndentOutput(String indentOutput) {
        this.indentOutput = indentOutput;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public void setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "businessProfile='" + businessProfile + '\'' +
                ", indentOutput='" + indentOutput + '\'' +
                ", applicationProfile='" + applicationProfile + '\'' +
                '}';
    }
}
