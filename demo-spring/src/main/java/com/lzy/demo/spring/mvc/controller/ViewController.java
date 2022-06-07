package com.lzy.demo.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

/**
 * 放在public,static的静态文件可以使用url/fileName直接访问,如 127.0.0.1:8080/view.html
 * 而放在templates的模板文件不能使用url直接访问,需要使用controller
 *
 * @author lzy
 * @version v1.0
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    /**
     * 使用ModelMap向模板传递值
     *
     * @param map      the map
     * @param modelMap the model map
     * @param model    the model
     * @return the string
     */
    @GetMapping("/model-map")
    public String modelMap(Map<String, String> map, ModelMap modelMap, Model model) {
        //这三个参数其实都是同一个BindingAwareModelMap对象
        map.put("map", "1");
        modelMap.addAttribute("modelMap", "2");
        model.addAttribute("model", "3");
        return "modelMap";
    }

    /**
     * 不使用模板的情况下,不使用redirect和forward就可以返回页面
     *
     * @return the string
     */
    @GetMapping("/public-view-without-template")
    public String publicViewWithoutTemplate() {
        //以/开头的表示根路径,请求地址为127.0.0.1/view/public-view-without-template,那么会forward到127.0.0.1/public.html
        //不以/开头的表示当前路径请求地址为127.0.0.1/view/public-view-without-template,那么会forward到127.0.0.1/view/public.html
        //相当于forward
        return "/public.html";
    }

    /**
     * 如果使用模板,要返回非模板的页面,必需使用redirect或者forward
     *
     * @return the string
     */
    @GetMapping("/public-view-with-template")
    public String publicViewWithTemplate() {
        return "forward:/public.html";
    }

    /**
     * 不使用模板的情况下,不使用redirect和forward就可以返回页面
     *
     * @return the modelAndView
     */
    @GetMapping("/public-model-and-view-without-template")
    public ModelAndView publicModelAndViewWithoutTemplate() {
        return new ModelAndView("/public.html");
    }

    /**
     * 如果使用模板,要返回非模板的页面,必需使用redirect或者forward
     *
     * @return the modelAndView
     */
    @GetMapping("/public-model-and-view-with-template")
    public ModelAndView publicModelAndViewWithTemplate() {
        return new ModelAndView("forward:/public.html");
    }

    /**
     * 返回ModelAndView
     *
     * @return the string
     */
    @GetMapping("/model-and-view")
    public ModelAndView modelAndView() {
        Map<String, String> map = new HashMap<>(1);
        map.put("map", "1");
        return new ModelAndView("modelMap", map);
    }

    /**
     * 使用string返回model
     *
     * @param modelMap the model map
     * @return the model and view
     */
    @GetMapping("/string-view")
    public String stringView(ModelMap modelMap) {
        modelMap.addAttribute("map", "1");
        return "modelMap";
    }


    /**
     * 使用String进行重定向,使用模板和禁用模板都适用
     *
     * @return the view
     */
    @GetMapping("/redirect")
    public String redirect() {
        //以/开头,就是重定向到根目录,如果当前访问的路径是127.0.0.1/view/public,那么重定向之后为127.0.0.1/public.html
        //以http(s)开头,就是重定向到绝对路径
        //不以/和http(s)开头的,就是重定向到当前路径的上一级路径,如果当前访问的路径是127.0.0.1/view/public,那么重定向之后为127.0.0.1/view/public.html
        return "redirect:/public.html";
    }

    /**
     * 使用RedirectView进行重定向,使用模板和禁用模板都适用
     *
     * @return the view
     */
    @GetMapping("/redirect-view")
    public View redirectView() {
        return new RedirectView("/public.html");
    }
}
