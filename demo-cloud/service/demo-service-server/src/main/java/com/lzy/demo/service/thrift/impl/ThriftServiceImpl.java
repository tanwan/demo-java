package com.lzy.demo.service.thrift.impl;

import com.lzy.demo.service.thrift.RequestException;
import com.lzy.demo.service.thrift.ThriftMessage;
import com.lzy.demo.service.thrift.ThriftService;
import org.apache.thrift.TException;

public class ThriftServiceImpl implements ThriftService.Iface {
    @Override
    public ThriftMessage thriftService(ThriftMessage thriftMessage) throws RequestException, TException {
        return thriftMessage;
    }
}
