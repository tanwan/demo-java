package com.lzy.demo.spring.mvc.controller;

import com.lzy.demo.spring.mvc.bean.Message;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;

/**
 * {@code SessionAttributes}只对当前Controller生效
 *
 * @author LZY
 * @version v1.0
 */
@RestController
@RequestMapping("/session-attributes")
@SessionAttributes("session-attributes")
public class SessionAttributesController {
    /**
     * 写入sessionAttributes
     *
     * @param modelMap the model map
     * @return the session attributes
     */
    @GetMapping("/model-map")
    public Message modelMap(ModelMap modelMap) {
        //可以使用ModelMap获取@SessionAttributes指定的值
        Message message = (Message) modelMap.get("session-attributes");
        if (message == null) {
            message = new Message();
            message.setCode("0");
            //modelMap的params会转存到session中
            modelMap.addAttribute("session-attributes", message);
            return message;
        }
        message.setCode("1");
        return message;
    }

    /**
     * 使用Session获取sessionAttributes
     *
     * @param httpSession the http session
     * @return the session attributes
     */
    @GetMapping("/http-session")
    public Message useHttpSession(HttpSession httpSession) {
        //可以使用session获取@SessionAttributes指定的值
        Message message = (Message) httpSession.getAttribute("session-attributes");
        if (message == null) {
            message = new Message();
            message.setCode("0");
            httpSession.setAttribute("session-attributes", message);
            return message;
        }
        message.setCode("1");
        return message;
    }

    /**
     * 使用@ModelAttribute可以获取从ModelMap转存到Session的属性
     *
     * @param message the message
     * @return the message
     */
    @GetMapping("/model-attribute")
    public Message modelAttribute(@ModelAttribute("session-attributes") Message message) {
        //使用@ModelAttribute可以获取从ModelMap转存到Session的属性
        return message;
    }
}
