package com.sicau.platform.configuration;

import com.sicau.platform.interceptor.LoginRequiredInterceptor;
import com.sicau.platform.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author boot liu
 */
@Component
public class PlatformWebConfiguration implements WebMvcConfigurer {
    private final PassportInterceptor passportInterceptor;
    private final LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    public PlatformWebConfiguration(PassportInterceptor passportInterceptor, LoginRequiredInterceptor loginRequiredInterceptor) {
        this.passportInterceptor = passportInterceptor;
        this.loginRequiredInterceptor = loginRequiredInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).excludePathPatterns("/login")
                .excludePathPatterns("/article/**").excludePathPatterns("/regist").excludePathPatterns("/admin/login")
                .excludePathPatterns("/admin/regist").excludePathPatterns("/file/download").excludePathPatterns("/file/**")
                .excludePathPatterns("/findPassword/*").excludePathPatterns("/forget");
/*
跨域问题拦截器：
registry.addInterceptor(crossDomainInterceptor);
*/
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}