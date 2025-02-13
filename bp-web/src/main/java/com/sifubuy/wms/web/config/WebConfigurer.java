package com.sifubuy.wms.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * web基础配置
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Resource
    private UserLoginInterceptor userLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor).order(0)
                .addPathPatterns("/**")
                .excludePathPatterns("/webStatus")
                .excludePathPatterns("/test/**");
    }

}
