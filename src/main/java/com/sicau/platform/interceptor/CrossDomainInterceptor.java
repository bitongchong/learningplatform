package com.sicau.platform.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author boot liu
 */
@Component
public class CrossDomainInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //允许访问的方式
        response.addHeader("Access-Control-Allow-Method", "POST,GET,DELETE,UPDATE");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        return true;
    }
}
