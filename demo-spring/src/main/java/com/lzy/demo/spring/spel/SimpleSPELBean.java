package com.lzy.demo.spring.spel;

import java.util.List;
import java.util.Map;

public class SimpleSPELBean {

    private List<Integer> list;
    private Map<String, String> map;
    private InnerClass innerClass = new InnerClass("value");

    /**
     * Method string.
     *
     * @param param the param
     * @return the string
     */
    public String method(String param) {
        return "method:" + param;
    }


    public void setList(List<Integer> list) {
        this.list = list;
    }


    public void setMap(Map<String, String> map) {
        this.map = map;
    }


    public List<Integer> getList() {
        return list;
    }


    public Map<String, String> getMap() {
        return map;
    }

    public InnerClass getInnerClass() {
        return innerClass;
    }

    public static class InnerClass {

        private String property;
        public InnerClass(String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }
    }

}
