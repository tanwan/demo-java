package com.lzy.demo.spring.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 * session的controller
 *
 * @author LZY
 * @version v1.0
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    /**
     * 使用HttpSession 读写session
     *
     * @param httpSession the http session
     * @return the session
     */
    @GetMapping("/http-session")
    public String httpSession(HttpSession httpSession) {
        String session = Optional.ofNullable(httpSession.getAttribute("httpSession")).map(Object::toString).orElse(null);
        if (session == null) {
            httpSession.setAttribute("httpSession", "hello world");
            return "init";
        }
        return session;
    }

    /**
     * 使用HttpServletRequest 读写session
     *
     * @param request the request
     * @return the session
     */
    @GetMapping("/http-servlet-request")
    public String httpServletRequest(HttpServletRequest request) {
        String session = Optional.ofNullable(request.getSession().getAttribute("httpServletRequest")).map(Object::toString).orElse(null);
        if (session == null) {
            request.getSession().setAttribute("httpServletRequest", "hello world");
            return "init";
        }
        return session;
    }

    /**
     * 使用@SessionAttribute获取session
     *
     * @param httpSession      the http session
     * @param sessionAttribute the session attribute
     * @return the string
     */
    @GetMapping("/session-attribute")
    public String sessionAttribute(HttpSession httpSession, @SessionAttribute(value = "sessionAttribute", required = false) String sessionAttribute) {
        if (sessionAttribute == null) {
            httpSession.setAttribute("sessionAttribute", "hello world");
            return "init";
        }
        return sessionAttribute;
    }
}
