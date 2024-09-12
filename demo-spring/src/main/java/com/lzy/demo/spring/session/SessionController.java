package com.lzy.demo.spring.session;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    /**
     * 添加缓存
     *
     * @param sessionValue the session value
     * @param httpSession  the http session
     * @return the string
     */
    @GetMapping("/create")
    public String createSession(@RequestParam("sessionValue") String sessionValue, HttpSession httpSession) {
        httpSession.setAttribute("sessionKey", sessionValue);
        return "success";
    }

    /**
     * 获取缓存
     *
     * @param httpSession the http session
     * @return the string
     */
    @GetMapping("/get")
    public Map<String, Object> getSession(HttpSession httpSession) {
        Map<String, Object> result = new HashMap<>();
        Enumeration<String> keys = httpSession.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            result.put(key, httpSession.getAttribute(key));
        }
        return result;
    }
}
