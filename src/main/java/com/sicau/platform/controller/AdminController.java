package com.sicau.platform.controller;

import com.sicau.platform.entity.Admin;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.entity.User;
import com.sicau.platform.service.AdminService;
import com.sicau.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author boot liu
 */
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    @PostMapping("/admin/regist")
    public Result registAdmin(@RequestBody Admin admin) {
        if (admin == null || admin.getAccount() == null || admin.getPassword() == null) {
            return new Result(false, StatusCode.REGISTERROR, "用户名密码格式错误");
        }
        if (adminService.regist(admin)) {
            return new Result(true, StatusCode.OK, "管理员注册成功");
        } else {
            return new Result(false, StatusCode.REGISTERROR, "用户名已存在");
        }
    }

    @PostMapping("/admin/login")
    public Result loginAdmin(@RequestBody Admin admin, HttpServletResponse response) {
        User adminUser = userService.findByUserAccount(admin.getAccount());
        if (adminUser == null) {
            return new Result(false, StatusCode.LOGINERROR, "管理员不存在");
        } else {
            if (adminUser.getPassword().equals(admin.getPassword())) {
                String ticketName = userService.addTicket(adminUser.getUserid());
                Cookie cookie = new Cookie("ticket", ticketName);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(cookie);
                return new Result(true, StatusCode.OK, "管理员登录成功");
            } else {
                return new Result(false, StatusCode.LOGINERROR, "管理员登录失败");
            }
        }
    }
}
