package com.lzy.demo.groovy.base;

public class WithConstructorClass extends NoConstructorClass {

    public WithConstructorClass(def stringProperty, def intProperty) {
        super.setStringProperty(stringProperty)
        super.setIntProperty(intProperty)
    }

    boolean asBoolean() {
        return Boolean.TRUE.toString() == stringProperty
    }
}
