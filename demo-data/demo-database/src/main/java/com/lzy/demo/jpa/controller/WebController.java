package com.lzy.demo.jpa.controller;

import com.lzy.demo.jpa.entity.SimpleJpa;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

@RestController
public class WebController {


    /**
     * 直接按id查询
     *
     * @param simpleJpa the simple jpa
     * @return the simple jpa
     * @see DomainClassConverter
     */
    @RequestMapping("/jpa/find/{id}")
    public SimpleJpa findOne(@PathVariable("id") SimpleJpa simpleJpa) {
        return simpleJpa;
    }


    /**
     * 分页
     *
     * @param pageable the pageable
     * @return the string
     * @see HandlerMethodArgumentResolver
     */
    @RequestMapping("/jpa/pageable")
    public Pageable page(@PageableDefault(value = 20, page = 0) Pageable pageable) {
        System.out.println(pageable);
        return pageable;
    }

    /**
     * 多个分页
     * 请求参数在前面是限定名和下划线,比如pageable1_size,pageable1_page
     *
     * @param pageable1 the pageable 1
     * @param pageable2 the pageable 2
     * @return the string
     * @see HandlerMethodArgumentResolver
     */
    @RequestMapping("/jpa/pageables")
    public Pageable pages(@Qualifier("pageable1") Pageable pageable1, @Qualifier("pageable2") @SortDefault(value = "firstName", direction = Sort.Direction.DESC) Pageable pageable2) {
        System.out.println(pageable1);
        System.out.println(pageable2);
        return null;
    }
}
