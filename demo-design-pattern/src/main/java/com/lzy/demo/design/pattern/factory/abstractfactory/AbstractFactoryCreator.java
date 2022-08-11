package com.lzy.demo.design.pattern.factory.abstractfactory;

import com.lzy.demo.design.pattern.factory.Product;

/**
 * 抽象工厂的接口
 * 如果要添加一个新的产品族,无需修改代码,只要添加相应的工厂类就行
 * 如果要添加一个新的产品等级,那么就必须修改代码
 * 产品族:可以当成有相同功能的一类产品
 * 产品等级:可以当成是同一个品牌的产品
 * 工厂方法是用来创建一个产品等级结构的,而抽象工厂是创建多个产品的等级结构的,工厂方法一般只有一个创建方法,用来
 * 创建一种产品,而抽象工厂有多个创建方法,创建多个产品
 *
 * @author LZY
 * @version v1.0
 */
public interface AbstractFactoryCreator {

    <T extends Product> T createA();

    <T extends Product> T createB();
}
