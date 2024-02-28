package com.jy.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: JunYu
 * @Date: 2024/2/25 18:34
 * @Description:
 * @Version: V1.0.0
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "loginInterceptor")
    private HandlerInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);
        //拦截请求
        registration.addPathPatterns("/**");
        //放行请求
        registration.excludePathPatterns(
                "/login",
                "/captcha",
                "/user/login",
                "/user/logout",
                "/layui/**",
                "/lib/**",
                "/webjars/**",
                "/api/**",
                "/css/**",
                "/js/**",
                "/images/**"
        );
    }
}
