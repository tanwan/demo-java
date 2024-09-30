package com.lzy.demo.quarkus.controller;

import com.lzy.demo.quarkus.service.SimpleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.CookieParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
public class RestController {

    @Inject
    SimpleService simpleService;

    /**
     * url参数, 可以使用@PathParam指定
     *
     * @param pathVariable pathVariable
     * @return string
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/path-variable/{pathVariable}")
    public String pathVariable(@PathParam("pathVariable") String pathVariable) {
        simpleService.simpleMethod();
        return pathVariable;
    }

    /**
     * 请求参数, 单个参数可以使用@QueryParam
     * /rest/request-param?urlParam=urlParam
     * post form-data 和 url的请求参数都会在param中
     *
     * @param param1 param1
     * @param param2 param2
     * @return map
     */
    @GET
    @Path("/request-param")
    public Map<String, Object> requestParam(@QueryParam("param1") String param1, @QueryParam("param2") Integer param2) {
        return Map.of("param1", param1, "param2", param2);
    }

    /**
     * 请求体
     *
     * @param param param
     * @return map
     */
    @POST
    @Path("/body")
    public Map<String, Object> body(Map<String, Object> param) {
        return param;
    }

    /**
     * 请求头
     *
     * @param header header
     * @return Response
     */
    @GET
    @Path("/header")
    public Response header(@HeaderParam("header") String header) {
        return Response.ok(Map.of("header", Optional.ofNullable(header).orElse("")))
                // 设置header
                .header("header", "new " + header)
                .build();
    }

    /**
     * cookie
     *
     * @param cookie cookie
     * @return Response
     */
    @GET
    @Path("/cookie")
    public Response cookie(@CookieParam("cookie") String cookie) {
        return Response.ok(Map.of("cookie", Optional.ofNullable(cookie).orElse("")))
                // 设置cookie
                .cookie(new NewCookie.Builder("cookie")
                        .value(URLEncoder.encode("new " + cookie, StandardCharsets.UTF_8))
                        .build())
                .build();
    }
}
