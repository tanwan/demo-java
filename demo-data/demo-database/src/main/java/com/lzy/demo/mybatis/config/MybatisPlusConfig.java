/*
 * Created by lzy on 2019-07-29 08:27.
 */
package com.lzy.demo.mybatis.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Mybatis plus config.
 *
 * @author lzy
 * @version v1.0
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     *
     * @return the pagination interceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //你的最大单页限制数量,默认500条,小于0如 - 1不受限制
        paginationInterceptor.setLimit(2);
        return paginationInterceptor;
    }

    /**
     * 开启sql性能监控,正式环境不要使用
     *
     * @return the performance interceptor
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        // sql最大执行时间
        performanceInterceptor.setMaxTime(100);
        // sql格式化
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * 乐观锁配置
     *
     * @return the optimistic locker interceptor
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
