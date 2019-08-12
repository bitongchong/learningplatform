package com.sicau.platform.interceptor;

import com.sicau.platform.entity.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author boot liu
 */
@Order(value = 1)
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

    private final HostHolder hostHolder;

    public LoginRequiredInterceptor(HostHolder hostHolder) {
        this.hostHolder = hostHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (hostHolder.getUser() == null) {
            log.warn("用户未登录");
            throw new Exception("未登录");
        }
        return true;
    }
}
