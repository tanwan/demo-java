package com.lzy.demo.test.spring.springboot2;

import com.lzy.demo.test.spring.SpringBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = SpringBean.class)
public class IgnoreApplication {
    public IgnoreApplication() {
        System.out.println("IgnoreApplication init");
    }
}
