package com.lzy.demo.gateway.dynamic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.actuate.GatewayControllerEndpoint;
import org.springframework.cloud.gateway.route.CachingRouteLocator;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteRefreshListener;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;

@Component
@Profile("dynamic")
public class DynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    private static final String GATEWAY_ROUTES = "gateway.routes";

    @Resource
    private ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        //从redis获取出路由
        //RouteRefreshListener会监听eureka的心跳事件,定期会发布RefreshRoutesEvent事件
        //CachingRouteLocator会监听RefreshRoutesEvent事件,然后重新调用此方法,从而达到动态路由的功能
        return reactiveStringRedisTemplate.opsForHash()
                .values(GATEWAY_ROUTES)
                .map(this::readRouteDefinition);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        //对路由进行保存
        return route.flatMap(r -> {
            if (ObjectUtils.isEmpty(r.getId())) {
                return Mono.error(new IllegalArgumentException("id may not be empty"));
            }
            //如果要及时通知其它实例,则需要通知其它实例发布(RefreshRoutesEvent),mq和redis都可以
            return save(r)
                    //通知其它实例
                    .then(Mono.empty());
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> reactiveStringRedisTemplate.opsForHash().remove(GATEWAY_ROUTES, id)
                .then(Mono.empty()));
    }

    private Mono<Boolean> save(RouteDefinition route) {
        try {
            return reactiveStringRedisTemplate.opsForHash().put(GATEWAY_ROUTES, route.getId(), objectMapper.writeValueAsString(route));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Mono.empty();
    }

    private RouteDefinition readRouteDefinition(Object json) {
        try {
            return objectMapper.readValue(json.toString(), RouteDefinition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RouteDefinition();
    }
}
