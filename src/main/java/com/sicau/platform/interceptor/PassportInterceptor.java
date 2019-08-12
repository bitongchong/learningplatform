package com.sicau.platform.interceptor;

import com.sicau.platform.dao.LoginTicketDao;
import com.sicau.platform.dao.UserDao;
import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.LoginTicket;
import com.sicau.platform.entity.User;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author boot liu
 */
@Component
@Order(value = 0)
public class PassportInterceptor implements HandlerInterceptor {
    private final LoginTicketDao loginTicketDao;
    private final UserDao userDao;
    private final HostHolder hostHolder;

    public PassportInterceptor(LoginTicketDao loginTicketDao, UserDao userDao, HostHolder hostHolder) {
        this.loginTicketDao = loginTicketDao;
        this.userDao = userDao;
        this.hostHolder = hostHolder;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String ticket = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("ticket".equals(cookie.getName())) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDao.findByTicket(ticket);
            if (loginTicket == null || (loginTicket.getExpired().before(new Date())) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = userDao.findByUserid(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        hostHolder.clear();
    }
}
