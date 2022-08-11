package com.lzy.demo.design.pattern.factory.factorymethod;

import com.lzy.demo.design.pattern.factory.Product;

public interface Creator {

    <T extends Product> T create(Class<T> clazz);
}
