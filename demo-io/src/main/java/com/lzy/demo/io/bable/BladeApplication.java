package com.lzy.demo.io.bable;

import com.hellokaton.blade.Blade;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.request.Cookie;
import com.hellokaton.blade.annotation.request.Form;
import com.hellokaton.blade.annotation.request.Header;
import com.hellokaton.blade.annotation.request.PathParam;
import com.hellokaton.blade.annotation.request.Query;
import com.hellokaton.blade.annotation.route.DELETE;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.annotation.route.PUT;
import com.hellokaton.blade.mvc.RouteContext;
import com.hellokaton.blade.mvc.ui.ResponseType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzy
 * @version v1.0
 * @see <a href="https://github.com/lets-blade/blade">blade</a>
 */
public class BladeApplication {
    private Integer port;

    private Blade blade = Blade.create();

    public BladeApplication(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new BladeApplication(19001).start();
    }

    public void start() {
        blade
                // 可以直接在这边声明handle,也可以使用注解
                //  .get("/path", ctx -> {})
                // 默认使用为main函数所在的启动类, junit的启动类不是当前类,所以这边直接当前类为启动类
                // 扫描的路径就是启动类的package
                .listen(port).start(BladeApplication.class);
    }

    public void stop() {
        blade.stop();
    }

    @Path
    public static class SimpleController {

        @GET(value = "/rest/get/:pathParam", responseType = ResponseType.JSON)
        public Map<String, Object> get(@PathParam String pathParam, @Query String queryParam) {
            return toMap("method", "get", "pathParam", pathParam, "queryParam", queryParam);
        }

        @POST(value = "/rest/post", responseType = ResponseType.JSON)
        public Map<String, Object> post(@Body Map<String, Object> body) {
            return toMap("method", "post", "body", body);
        }

        @PUT(value = "/rest/put/:id", responseType = ResponseType.JSON)
        public Map<String, Object> put(@PathParam Long id, @Body Map<String, Object> body) {
            return toMap("method", "put", "id", id, "body", body);
        }

        @DELETE(value = "/rest/delete/:id", responseType = ResponseType.JSON)
        public Map<String, Object> delete(@PathParam Long id) {
            return toMap("method", "delete", "id", id);
        }

        @POST(value = "/rest/form-data", responseType = ResponseType.JSON)
        public Map<String, Object> formData(@Form String formField, RouteContext ctx) {
            // 使用一个@Form只能获取form表单的一个参数,使用ctx.request().formParams()获取所有的form表单
            return toMap("method", "formData", "formField", formField, "allField", ctx.request().formParams());
        }

        @GET(value = "/rest/header", responseType = ResponseType.JSON)
        public Map<String, Object> header(@Header("header-key") String headerKey, RouteContext ctx) {
            // cxt.headers()可以拿到所有的header
            System.out.println(ctx.headers());
            // 设置header
            ctx.header("header-key", "header-key override");
            return toMap("method", "header", "ctx.header(\"header-key\")", ctx.header("header-key"), "simpleHeader", headerKey);
        }

        @GET(value = "/rest/cookie", responseType = ResponseType.JSON)
        public Map<String, Object> cookie(@Cookie("cookieKey") String cookieKey, RouteContext ctx) {
            // 通过ctx.request().cookies()获取所有cookie
            System.out.println(ctx.request().cookies());
            // 设置cookie
            ctx.cookie("addCookie", "add cookie value");
            return toMap("method", "cookie", "ctx.cookie(\"cookieKey\"", ctx.cookie("cookieKey"), "cookieKey", cookieKey);
        }


        private Map<String, Object> toMap(Object... objs) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < objs.length; i += 2) {
                map.put(objs[i].toString(), objs[i + 1]);
            }
            return map;
        }
    }
}
