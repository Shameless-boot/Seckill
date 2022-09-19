package com.shaun.seckill.config;

import com.shaun.seckill.revolver.UserArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/7/10 17:11
 * @Description:
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    UserArgumentResolver userArgumentResolver;
    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }

    // @Override
    // public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //     // addResourceHandler: 拦截哪些请求的url,/** 拦截所有，addResourceLocations：去哪里找静态资源
    //     registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    // }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
    }
}
