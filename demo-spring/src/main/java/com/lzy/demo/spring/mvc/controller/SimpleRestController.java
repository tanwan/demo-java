package com.lzy.demo.spring.mvc.controller;

import com.lzy.demo.spring.mvc.bean.Message;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/rest")
//这里也可以使用url参数
//@RequestMapping("/rest/{pathVariable}")
public class SimpleRestController {
    /**
     * url参数,注解在类上的@RequestMapping也可以使用url参数
     * /rest/path-variable/lzy
     *
     * @param pathVariable the path variable
     * @return the string
     */
    @RequestMapping("/path-variable/{pathVariable}")
    public String pathVariable(@PathVariable String pathVariable) {
        return pathVariable;
    }

    /**
     * 请求参数
     * /rest/request-param?urlParam=urlParam
     * post form-data 和 url的请求参数都会在param中
     * 入参使用map可以获取所有的请求参数,也可以使用@RequestParam来获取指定的参数,变量名可以跟参数的key一致也可以使用@RequestParam的name来指定
     *
     * @param param the param
     * @return the map
     */
    @RequestMapping("/request-param")
    public Map<String, String> requestParam(@RequestParam Map<String, String> param) {
        return param;
    }

    /**
     * Http body,@RequestBody,@ResponseBody
     *
     * @param param the param
     * @return the message
     */
    @RequestMapping(value = "/body", method = RequestMethod.POST)
    public Message body(@RequestBody Map<String, Object> param) {
        return new Message(param);
    }

    /**
     * 获取请求头
     * 入参使用Map可以获取全部的请求头,也可以使用@RequestHeader获取指定的header,变量名可以跟header的key一致也可以使用@RequestHeader的name来指定
     *
     * @param header the header
     * @return the map
     */
    @RequestMapping("/header")
    public HttpHeaders header(@RequestHeader Map<String, String> header) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("header", header.toString());
        return httpHeaders;
    }

    @GetMapping("/cookie")
    public String cookie(@CookieValue(value = "cookie", required = false) String cookie,
                         HttpServletResponse response) throws UnsupportedEncodingException {
        //cookie只能存在ASCII码为(34~126)的可见字符,因此其它字符需要进行编码
        response.addCookie(new Cookie("cookie", URLEncoder.encode("hello world", StandardCharsets.UTF_8.name())));
        return cookie;
    }

    /**
     * requestAttribute的前一个请求
     *
     * @param request  the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException      the io exception
     */
    @GetMapping("/pre-request-attribute")
    public void preRequestAttribute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("attribute", "hello world");
        request.getRequestDispatcher("/rest/request-attribute").forward(request, response);
    }

    /**
     * requestAttribute 获取HttpServletRequest的attribute的值,可以是forward前的request设的值,也可以是拦截器或者过滤器设的值
     *
     * @param attribute the attribute
     * @return the string
     */
    @GetMapping("/request-attribute")
    public String requestAttribute(@RequestAttribute("attribute") String attribute) {
        return attribute;
    }


    /**
     * 请求参数直接映射成对象
     *
     * @param message the message
     * @return the user
     */
    @PostMapping("/model")
    public Message model(@ModelAttribute Message message) {
        return message;
    }

    /**
     * 可以把请求body的内容转换成HttpEntity/RequestEntity指定的泛型类型
     * 把HttpEntity/ResponseEntity泛型类型的内容转换成response的body
     *
     * @param requestEntity the request entity
     * @return the map
     */
    @PostMapping("/http-entity")
    public ResponseEntity<Map<String, Object>> httpEntity(RequestEntity<Map<String, Object>> requestEntity) {
        //ResponseEntity可以设置Header
        return new ResponseEntity<>(requestEntity.getBody(), HttpStatus.OK);
    }

    /**
     * 获取当前用户认证信息
     *
     * @param principal the principal
     * @return the string
     */
    @GetMapping("/principal")
    public String principal(Principal principal) {
        return "success";
    }

    /**
     * 获取请求方法
     *
     * @param method the method
     * @return the string
     */
    @GetMapping("/method")
    public String method(HttpMethod method) {
        return method.toString();
    }

    /**
     * 使用输入输出流
     *
     * @param is the is
     * @param os the os
     * @throws IOException the io exception
     */
    @PostMapping("/stream")
    public void stream(InputStream is, OutputStream os) throws IOException {
        IOUtils.copy(is, os);
    }

    /**
     * 获取国际化
     *
     * @param locale the locale
     * @return the string
     */
    @GetMapping("/locale")
    public String locale(Locale locale) {
        return locale.toString();
    }

    /**
     * 获取时区
     *
     * @param zoneId the zone id
     * @return the string
     */
    @GetMapping("/zone-id")
    public String zoneId(ZoneId zoneId) {
        return zoneId.toString();
    }

    /**
     * 抛出异常
     *
     * @return the string
     * @throws Exception the exception
     */
    @GetMapping("/exception")
    public String exception() throws Exception {
        throw new RuntimeException("expect");
    }

    /**
     * 使用@ExceptionHandler来处理这个Controller的异常
     *
     * @param e the e
     * @return the string
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        e.printStackTrace();
        return "handle exception";
    }
}
