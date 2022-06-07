package com.lzy.demo.service.service.impl;

import com.lzy.demo.service.bean.HessianMessage;
import com.lzy.demo.service.service.SimpleHessianService;
import org.springframework.stereotype.Service;

@Service
public class SimpleHessianServiceImpl implements SimpleHessianService {
    @Override
    public HessianMessage simpleHessian(HessianMessage hessianMessage) {
        return hessianMessage;
    }
}
